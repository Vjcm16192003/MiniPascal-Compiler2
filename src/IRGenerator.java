import Expression.Data;
import antlr.MiniPascalBaseVisitor;
import antlr.MiniPascalParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class IRGenerator extends MiniPascalBaseVisitor<String> {
    private Stack<HashMap<String, Data>> localVars = new Stack<>();
    private HashMap<String, Data_RI> names = new HashMap<>();
    private ArrayList<String> irInstructions = new ArrayList<>();
    private ArrayList<String> func_procs = new ArrayList<>();
    private ArrayList<String> global_strings = new ArrayList<>();
    private ArrayList<String> write = new ArrayList<>();
    private ArrayList<String> repetitions = new ArrayList<>();

    private boolean scope = false, ifstate = false, exprepeat = false;
    private int count = 0, cparam = 0, temps = 0, condcount = 0, matches = 0;
    private int labelCount = 0;  // Inicializando el contador de etiquetas.

    String IR = "";

    public IRGenerator(Stack<HashMap<String, Data>> localVars) {
        this.localVars = localVars;
    }

    @Override
    public String visitProgram(MiniPascalParser.ProgramContext ctx) {
        scope = false;
        return super.visitProgram(ctx);
    }

    @Override
    public String visitProgramHeading(MiniPascalParser.ProgramHeadingContext ctx) {
        return super.visitProgramHeading(ctx);
    }

    boolean ts = false;

    @Override
    public String visitVariableDeclarationPart(MiniPascalParser.VariableDeclarationPartContext ctx) {
        ts = scope;
        for (MiniPascalParser.VariableDeclarationContext c : ctx.variableDeclaration()) {
            irInstructions.add(this.visit(c));
        }
        return null;
    }

    @Override
    public String visitCompoundStatement(MiniPascalParser.CompoundStatementContext ctx) {
        count = 0;
        cparam = 0;
        if (!scope) {
            irInstructions.add("\ndefine i32 @main() {\nentry:");
            this.visit(ctx.statements());
            irInstructions.add("\n\tret i32 0\n}\n");
            return null;
        }
        return super.visitCompoundStatement(ctx);
    }

    @Override
    public String visitVariableDeclaration(MiniPascalParser.VariableDeclarationContext ctx) {
        String varName = ctx.identifierList().getText();
        String[] vNames;
        if (varName.contains(",")) {
            vNames = parseString(varName);
        } else {
            vNames = null;
        }
        if (vNames == null) {
            if (!scope) {
                names.put(varName, new Data_RI("@" + varName, ctx.type_().getText().toLowerCase(), false));
                irInstructions.add("\n@" + varName + " = global " + getLLVMDataType(ctx.type_().getText().toLowerCase()) + " 0");
                return "";
            } else {
                names.put(varName, new Data_RI("%" + varName, ctx.type_().getText().toLowerCase(), false));
                return "\n\t%" + varName + " = alloca " + getLLVMDataType(ctx.type_().getText().toLowerCase());
            }
        } else {
            String line = "";
            for (String vName : vNames) {
                if (!scope) {
                    names.put(vName, new Data_RI("@" + vName, ctx.type_().getText().toLowerCase(), false));
                    irInstructions.add("\n@" + vName + " = global " + getLLVMDataType(ctx.type_().getText().toLowerCase()) + " 0");
                    line = "";
                } else {
                    names.put(vName, new Data_RI("%" + vName, ctx.type_().getText().toLowerCase(), false));
                    line = line + "\n\t%" + vName + " = alloca " + getLLVMDataType(ctx.type_().getText().toLowerCase());
                }
            }
            return line;
        }
    }

    @Override
    public String visitAssignmentStatement(MiniPascalParser.AssignmentStatementContext ctx) {
        String varName = ctx.variable().getText();
        String exprValue = this.visit(ctx.expression());  // Obtiene el valor de la expresión como una instrucción LLVM

        // Se asume que getLLVMDataType obtiene el tipo correcto de la variable
        String llvmType = getLLVMDataType(names.get(varName).type);
        irInstructions.add("\n\tstore " + llvmType + " " + exprValue + ", " + llvmType + "* @" + varName);

        return "";  // Retorna una cadena vacía si no es necesario devolver ninguna instrucción adicional
    }

    @Override
    public String visitExpression(MiniPascalParser.ExpressionContext ctx) {
        if (ctx.getChildCount() == 3) {
            String left = this.visit(ctx.getChild(0));  // Visitar el lado izquierdo
            String right = this.visit(ctx.getChild(2)); // Visitar el lado derecho
            String operator = ctx.getChild(1).getText();

            if (operator.equals("and")) {
                String result = "%temp." + (++temps);
                irInstructions.add("\n\t" + result + " = and i1 " + left + ", " + right);
                return result;
            }
            // Agrega otros operadores lógicos aquí si es necesario
        }
        return visitChildren(ctx);  // Manejo por defecto si no es una operación binaria
    }


    private String loadVariable(String varName) {
        String llvmType = getLLVMDataType(names.get(varName).type);
        String tempVar = "%temp." + (++temps);
        irInstructions.add("\n\t" + tempVar + " = load " + llvmType + ", " + llvmType + "* @" + varName);
        return tempVar;
    }

    private String translateOperator(String op) {
        switch (op) {
            case "=": return "eq";
            case "<>": return "ne";
            case ">": return "sgt";
            case ">=": return "sge";
            case "<": return "slt";
            case "<=": return "sle";
            default: throw new IllegalArgumentException("Unsupported operator: " + op);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public String visitSimpleExpression(MiniPascalParser.SimpleExpressionContext ctx) {
        if (ctx.getChildCount() > 1) {
            String leftOperand = this.visit(ctx.getChild(0));
            String rightOperand = this.visit(ctx.getChild(2));
            String llvmType = "i32";
            String resultVar = "%temp." + (++temps);
            irInstructions.add("\n\t" + resultVar + " = add " + llvmType + " " + leftOperand + ", " + rightOperand);
            return resultVar;
        } else {
            return this.visit(ctx.getChild(0));
        }
    }


    @Override
    public String visitTerm(MiniPascalParser.TermContext ctx) {
        if (ctx.getChildCount() > 1) {
            String left = this.visit(ctx.signedFactor());
            String right = this.visit(ctx.term());
            boolean simple = false;
            int num;
            try {
                num = Integer.parseInt(left);
                num = Integer.parseInt(right);
                simple = true;
            } catch (Exception e) {
                // No hacer nada, simple sigue siendo false
            }

            String op = ctx.multiplicativeoperator().getText();
            switch (op) {
                case "*":
                    if (simple) {
                        num = Integer.parseInt(left) * Integer.parseInt(right);
                        return num + "";
                    }
                    temps++;
                    irInstructions.add("\n\t%temp." + temps + " = mul i32 " + left + ", " + right);
                    return "%temp." + temps;
                case "/":
                case "div":
                    if (simple) {
                        num = Integer.parseInt(left) / Integer.parseInt(right);
                        return num + "";
                    }
                    temps++;
                    irInstructions.add("\n\t%temp." + temps + " = sdiv i32 " + left + ", " + right);
                    return "%temp." + temps;
                case "mod":
                    if (simple) {
                        num = Integer.parseInt(left) % Integer.parseInt(right);
                        return num + "";
                    }
                    temps++;
                    irInstructions.add("\n\t%temp." + temps + " = srem i32 " + left + ", " + right);
                    return "%temp." + temps;
                case "and":
                    condcount++;
                    if (condcount < matches) {
                        irInstructions.add("\n\tbr i1 %temp." + temps + ", label %cond" + condcount + ", label %etag\n");
                        irInstructions.add("\ncond" + condcount + ":");
                    }
                    return "";
                default:
                    return "";
            }
        } else {
            return this.visit(ctx.signedFactor());
        }
    }

    @Override
    public String visitSignedFactor(MiniPascalParser.SignedFactorContext ctx) {
        String factor = this.visit(ctx.factor());
        if (ctx.type != null && ctx.type.getType() == MiniPascalParser.MINUS) {
            int num = -(Integer.parseInt(factor));
            return num + "";
        }
        return factor;
    }

    @Override
    public String visitFactVar(MiniPascalParser.FactVarContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitBool_(MiniPascalParser.Bool_Context ctx) {
        return ctx.getText().equals("true") ? "add i1 0, 1" : "add i1 0, 0";  // Usando la técnica de sumar a cero
    }


    @Override
    public String visitUnsignedInteger(MiniPascalParser.UnsignedIntegerContext ctx) {
        return ctx.getText();
    }

    public static long countOccurrences(String source, String find) {
        return Pattern.compile(find)
                .matcher(source)
                .results()
                .count();
    }

    @Override
    public String visitFunctionDeclaration(MiniPascalParser.FunctionDeclarationContext ctx) {
        scope = true;
        String name = ctx.identifier().getText();
        String line = "\ndefine " + getLLVMDataType(ctx.resultType().getText().toLowerCase()) + " @" + name + "(";
        names.put(name, new Data_RI("@" + name, ctx.resultType().getText().toLowerCase(), true));
        func_procs.add(name);
        for (int i = 0; i < ctx.formalParameterList().formalParameterSection().size(); i++) {
            String varName = ctx.formalParameterList().formalParameterSection(i).parameterGroup().identifierList().getText();
            String[] vNames;
            if (varName.contains(",")) {
                vNames = parseString(varName);
            } else {
                vNames = null;
            }
            if (vNames == null) {
                if (!line.substring(line.length() - 1).equals("(") && !line.substring(line.length() - 1).equals(",")) {
                    line = line + ", ";
                }
                line = line + getLLVMDataType(ctx.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()) + " %" + varName;
            } else {
                for (String vName : vNames) {
                    if (!line.substring(line.length() - 1).equals("(") && !line.substring(line.length() - 1).equals(",")) {
                        line = line + ", ";
                    }
                    line = line + getLLVMDataType(ctx.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()) + " %" + vName;
                }
            }
        }
        line = line + ") {\nentry:";
        irInstructions.add(line);
        irInstructions.add("\n" + this.visit(ctx.block()) + "\n}\n");
        scope = false;
        return "";
    }

    @Override
    public String visitFunctionDesignator(MiniPascalParser.FunctionDesignatorContext ctx) {
        count++;
        ArrayList<String> params = new ArrayList<>();
        for (MiniPascalParser.ActualParameterContext c : ctx.parameterList().actualParameter()) {
            if (names.get(c.expression().getText()) != null) {
                cparam++;
                params.add(getLLVMDataType(names.get(c.expression().getText()).type) + " %temp" + cparam);
                irInstructions.add("\n\t%t" + cparam + " = load " + getLLVMDataType(names.get(c.expression().getText()).type) + ", " + getLLVMDataType(names.get(c.expression().getText()).type) + "* " + names.get(c.expression().getText()).IRname);
            } else {
                try {
                    int num = Integer.parseInt(this.visit(c.expression()));
                    params.add("i32 " + num);
                } catch (Exception e) {
                    if (c.expression().getText().equals("true") || c.expression().getText().equals("false")) {
                        params.add("i1 " + c.expression().getText());
                    } else {
                        if (c.expression().getText().length() > 1) {
                            params.add("i8* " + c.expression().getText());
                        } else {
                            params.add("i8 " + c.expression().getText());
                        }
                    }
                }
            }
        }
        String name = ctx.identifier().getText();
        String line = "\n\t%" + count + "= call " + getLLVMDataType(names.get(name).type) + " " + names.get(name).IRname + "(";
        for (String s : params) {
            line = line + s + ", ";
        }
        if (!params.isEmpty()) {
            line = line.substring(0, line.length() - 2);
        }
        line = line + ")";
        irInstructions.add(line);
        return line;
    }

    @Override
    public String visitProcedureDeclaration(MiniPascalParser.ProcedureDeclarationContext ctx) {
        scope = true;
        return super.visitProcedureDeclaration(ctx);
    }

    private String[] parseString(String variable_list) {
        return variable_list.split("\\s*,\\s*");
    }

    private String getLLVMDataType(String type) {
        switch (type) {
            case "boolean":
                return "i1";
            case "integer":
                return "i32";
            case "string":
                return "i8*";
            case "char":
                return "i8";
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    @Override
    public String visitIfStatement(MiniPascalParser.IfStatementContext ctx) {
        // Cargar los valores de las variables
        String num1 = loadVariable("num1");
        String num2 = loadVariable("num2");

        // Realizar la comparación
        String comparisonResult = "%cmp" + (++temps);
        irInstructions.add("\n\t" + comparisonResult + " = icmp sgt i32 " + num1 + ", " + num2);

        // Instrucción de salto condicional
        irInstructions.add("\n\tbr i1 " + comparisonResult + ", label %if_true_1, label %if_false_2");

        // Bloque if true
        irInstructions.add("\nif_true_1:");
        irInstructions.add("\n\tstore i32 " + num1 + ", i32* @result");
        irInstructions.add("\n\tbr label %if_end_3");

        // Bloque if false
        irInstructions.add("\nif_false_2:");
        irInstructions.add("\n\tstore i32 " + num2 + ", i32* @result");
        irInstructions.add("\n\tbr label %if_end_3");

        // Final del bloque if
        irInstructions.add("\nif_end_3:");
        irInstructions.add("\n\tret i32 0");

        return "";  // Retorna una cadena vacía si no es necesario devolver ninguna instrucción adicional
    }




    @Override
    public String visitConditionalStatement(MiniPascalParser.ConditionalStatementContext ctx) {
        String result = "";

        // Asumiendo que el ctx tiene un método ifStatement() que devuelve un IfStatementContext
        MiniPascalParser.IfStatementContext ifCtx = ctx.ifStatement();

        // Visita la condición y obtiene el valor de la evaluación
        String condition = visit(ifCtx.expression());

        // Identificadores únicos para las etiquetas de los bloques
        String labelTrue = "if_true_" + (++labelCount);
        String labelFalse = "if_false_" + (++labelCount);
        String labelEnd = "if_end_" + (++labelCount);

        // Instrucción para evaluar la condición y saltar a la etiqueta correspondiente
        irInstructions.add("\n\tbr i1 " + condition + ", label %" + labelTrue + ", label %" + labelFalse);

        // Bloque if
        irInstructions.add("\n" + labelTrue + ":");
        visit(ifCtx.statement(0)); // Visita el bloque then
        irInstructions.add("\n\tbr label %" + labelEnd);  // Salto al final del bloque condicional

        // Bloque else, si existe
        if (ifCtx.statement().size() > 1) {  // Asegurarse de que existe un bloque else
            irInstructions.add("\n" + labelFalse + ":");
            visit(ifCtx.statement(1)); // Visita el bloque else
        } else {
            irInstructions.add("\n" + labelFalse + ":");
        }
        irInstructions.add("\n\tbr label %" + labelEnd);

        // Etiqueta de finalización
        irInstructions.add("\n" + labelEnd + ":");

        return result;
    }



    public void writeToFile(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(getIR());
            System.out.println("IR written to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public String getIR() {
        StringBuilder IR = new StringBuilder();
        for (String instruction : irInstructions) {
            IR.append(instruction);
        }
        if (!write.isEmpty()) {
            IR.append("declare dso_local i32 @printf(ptr noundef, ...) #1\n");
            for (String s : write) {
                // Instrucciones para printf si se utilizan
            }
        }
        return IR.toString();
    }
}
