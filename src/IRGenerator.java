import Expression.Data;
import antlr.MiniPascalBaseVisitor;
import antlr.MiniPascalParser;

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
    String IR = "";

    public IRGenerator(Stack<HashMap<String, Data>> localVars) {
        this.localVars = localVars;
    }

    @Override
    public String visitProgram(MiniPascalParser.ProgramContext ctx) {
        //System.out.println("Segunda pasada, estoy en visitProgram");
        scope = false;
        return super.visitProgram(ctx);
    }

    @Override
    public String visitProgramHeading(MiniPascalParser.ProgramHeadingContext ctx) {
        //System.out.println("Segunda pasada, estoy en visitProgramHeading");
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
            irInstructions.add("\nend:\n\tret i32 0\n}\n");
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
        if (vNames == null) { // Si las variables están declaradas sin coma, separadas por ;
            if (!scope) {
                names.put(varName, new Data_RI("@" + varName, ctx.type_().getText().toLowerCase(), false));
                irInstructions.add("\n@" + varName + " = global " + getLLVMDataType(ctx.type_().getText().toLowerCase()) + " 0");
                return "";
            } else {
                names.put(varName, new Data_RI("%" + varName, ctx.type_().getText().toLowerCase(), false));
                return "\n\t%" + varName + " = alloca " + getLLVMDataType(ctx.type_().getText().toLowerCase());
            }
        } else { // Var a,b,c: boolean, por ejemplo
            String line = "";
            for (int i = 0; i < vNames.length; i++) {
                if (!scope) {
                    names.put(vNames[i], new Data_RI("@" + vNames[i], ctx.type_().getText().toLowerCase(), false));
                    irInstructions.add("\n@" + vNames[i] + " = global " + getLLVMDataType(ctx.type_().getText().toLowerCase()) + " 0");
                    line = "";
                } else {
                    names.put(vNames[i], new Data_RI("%" + vNames[i], ctx.type_().getText().toLowerCase(), false));
                    line = line + "\n\t%" + vNames[i] + " = alloca " + getLLVMDataType(ctx.type_().getText().toLowerCase());
                }
            }
            return line;
        }
    }

    @Override
    public String visitAssignmentStatement(MiniPascalParser.AssignmentStatementContext ctx) {
        String varName = ctx.variable().getText();
        String valor = this.visit(ctx.expression());
        String line = "";

        if (names.get(varName).result) {
            line = "\n\tret " + getLLVMDataType(names.get(varName).type) + " " + valor;
        } else {
            boolean val = false;
            for (String s : func_procs) {
                if (ctx.expression().getText().contains(s)) {
                    val = true;
                }
            }
            if (!val) {
                line = "\n\tstore " + getLLVMDataType(names.get(varName).type) + " " + valor + ", " + getLLVMDataType(names.get(varName).type) + "* " + names.get(varName).IRname;
            } else {
                line = this.visit(ctx.expression());
            }
        }

        irInstructions.add(line);
        return line;
    }

    @Override
    public String visitExpression(MiniPascalParser.ExpressionContext ctx) {
        if (ctx.relationaloperator() != null) {
            String leftVar = this.visit(ctx.getChild(0));
            String rightVar = this.visit(ctx.getChild(2));
            String left = "";
            String right = "";

            if (isNumeric(leftVar)) {
                left = leftVar;
            } else {
                if (names.containsKey(leftVar)) {
                    temps++;
                    left = "%temp." + temps;
                    irInstructions.add("\n\t" + left + " = load i32, i32* @" + leftVar);
                } else {
                    left = leftVar;
                }
            }

            if (isNumeric(rightVar)) {
                right = rightVar;
            } else {
                if (names.containsKey(rightVar)) {
                    temps++;
                    right = "%temp." + temps;
                    irInstructions.add("\n\t" + right + " = load i32, i32* @" + rightVar);
                } else {
                    right = rightVar;
                }
            }

            String op = ctx.relationaloperator().getText();
            String comparisonResult = "";
            switch (op) {
                case "=":
                    temps++;
                    comparisonResult = "%temp." + temps;
                    irInstructions.add("\n\t" + comparisonResult + " = icmp eq i32 " + left + ", " + right);
                    break;
                case "<>":
                    temps++;
                    comparisonResult = "%temp." + temps;
                    irInstructions.add("\n\t" + comparisonResult + " = icmp ne i32 " + left + ", " + right);
                    break;
                case ">":
                    System.out.println("Entro a la comparativa >");
                    temps++;
                    comparisonResult = "%temp." + temps;
                    System.out.println("Comparison result en el >: " + comparisonResult);
                    irInstructions.add("\n\t" + comparisonResult + " = icmp sgt i32 " + left + ", " + right);
                    break;
                case ">=":
                    temps++;
                    comparisonResult = "%temp." + temps;
                    irInstructions.add("\n\t" + comparisonResult + " = icmp sge i32 " + left + ", " + right);
                    break;
                case "<":
                    temps++;
                    comparisonResult = "%temp." + temps;
                    irInstructions.add("\n\t" + comparisonResult + " = icmp slt i32 " + left + ", " + right);
                    break;
                case "<=":
                    temps++;
                    comparisonResult = "%temp." + temps;
                    irInstructions.add("\n\t" + comparisonResult + " = icmp sle i32 " + left + ", " + right);
                    break;
                default:
                    break;
            }
            System.out.println("Comparison Result a retornar: "+ comparisonResult);
            return comparisonResult;
        } else {
            //System.out.println("Llego al return que no deberia.");
            return this.visit(ctx.simpleExpression());
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public String visitSimpleExpression(MiniPascalParser.SimpleExpressionContext ctx) {
        if (ctx.getChildCount() > 1) {
            String left = this.visit(ctx.term());
            String right = this.visit(ctx.simpleExpression());
            boolean simple = false;
            int num;
            try {
                num = Integer.parseInt(left);
                num = Integer.parseInt(right);
                simple = true;
            } catch (Exception e) {
            }

            String op = ctx.additiveoperator().getText();
            switch (op) {
                case "+":
                    if (simple) {
                        num = Integer.parseInt(left) + Integer.parseInt(right);
                        return num + "";
                    }
                    temps++;
                    irInstructions.add("\n\t%temp." + temps + " = add i32 " + left + ", " + right);
                    return "%temp." + temps;
                case "-":
                    if (simple) {
                        num = Integer.parseInt(left) - Integer.parseInt(right);
                        return num + "";
                    }
                    temps++;
                    irInstructions.add("\n\t%temp." + temps + " = sub i32 " + left + ", " + right);
                    return "%temp." + temps;
                case "or":
                    condcount++;
                    if (condcount < matches) {
                        irInstructions.add("\n\tbr i1 %temp." + temps + ", label %itag, label %cond" + condcount + "\n");
                        irInstructions.add("\ncond" + condcount + ":");
                    }
                    return "";
                default:
                    return "";
            }
        } else {
            return this.visit(ctx.term());
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
        return ctx.getText().equals("true") ? "1" : "0";
    }

    @Override
    public String visitUnsignedInteger(MiniPascalParser.UnsignedIntegerContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitIfStatement(MiniPascalParser.IfStatementContext ctx) {
        ifstate = true;
        temps = 0;
        repetitions = new ArrayList<>();
        String conditionResult = this.visit(ctx.expression());
        irInstructions.add("\n\tbr i1 " + conditionResult + ", label %itag, label %etag\n");
        irInstructions.add("\nitag:");
        this.visit(ctx.statement(0));
        irInstructions.add("\n\tbr label %end"); // Añadir br para saltar al final después del itag
        irInstructions.add("\netag:");
        this.visit(ctx.statement(1));
        irInstructions.add("\n\tbr label %end"); // Añadir br para saltar al final después del etag
        condcount = 0;
        return "";
    }


    public static long countOccurrences(String source, String find) {
        return Pattern.compile(find) // Pattern
                .matcher(source) // Matcher
                .results()       // Stream<MatchResults>
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
            // Place the variable name and its data value into this scope's variables (i.e. localVars)
            if (vNames == null) {
                if (!line.substring(line.length() - 1).equals("(") && !line.substring(line.length() - 1).equals(",")) {
                    line = line + ", ";
                }
                line = line + getLLVMDataType(ctx.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()) + " %" + varName;
            } else {
                for (int k = 0; k < vNames.length; k++) {
                    if (!line.substring(line.length() - 1).equals("(") && !line.substring(line.length() - 1).equals(",")) {
                        line = line + ", ";
                    }
                    line = line + getLLVMDataType(ctx.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()) + " %" + vNames[k];
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
                return "ERROR";
        }
    }

    public String getIR() {
        String IR = "";
        for (int i = 0; i < irInstructions.size(); i++) {
            IR = IR + irInstructions.get(i);
        }
        if (!write.isEmpty()) {
            IR = IR + "declare dso_local i32 @printf(ptr noundef, ...) #1\n";
            for (String s : write) {
                // Instrucciones para printf si se utilizan
            }
        }
        return IR;
    }
}
