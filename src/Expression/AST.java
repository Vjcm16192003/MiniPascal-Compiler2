package Expression;

import java.awt.*;
import java.util.*;
import java.util.List;

import antlr.MiniPascalBaseVisitor;
import antlr.MiniPascalParser;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


// Our custom visitor extends the base visitor
public class AST extends MiniPascalBaseVisitor<Data>{

    // Diferentes listas de variables para el encapsulamiento
    Stack<HashMap<java.lang.String, Data>> localVars = new Stack<>();
    // Global vars will be the variables at the lowest scope level
    HashMap<java.lang.String, Data> globalVars = new HashMap<>();
    java.lang.String name;

    int cont=1;
    // Un hashMap para las funciones
    HashMap<java.lang.String, MiniPascalParser.FunctionDeclarationContext> functions = new HashMap<>();
    // Keep a map of all the user-defined procedures
    HashMap<java.lang.String, MiniPascalParser.ProcedureDeclarationContext> procedures = new HashMap<>();
    // Storing variables names
    List<java.lang.String> procedureVars = new ArrayList<>();
    final List<java.lang.String> holder = new LinkedList<java.lang.String>();

    // Break/continue. Excuse the stupid names, but both are reserved words, so something must be done...
    boolean break_or_nah = false;
    boolean continue_or_nah = false;
    JTextPane writer;
    Boolean semantic;

    public AST(JTextPane writer) {
        this.writer = writer;
        this.writer.setText("");
        semantic=true;
    }

    /*************** Implementing the abstract methods of MiniPascalBaseVisitor ***************/




    @Override
    public Data visitProgram(MiniPascalParser.ProgramContext ctx) {
        localVars.push(globalVars);
        return super.visitProgram(ctx);
    }

    @Override
    public Data visitProgramHeading(MiniPascalParser.ProgramHeadingContext ctx) {
        System.out.println("Running "+ ctx.identifier().getText());
        setName(ctx.identifier().getText());
        return super.visitProgramHeading(ctx);
    }


    /*************** Variable block ***************/
    // Where vars are just declared, but not initialized with a value
    @Override
    public Data visitVariableDeclaration(MiniPascalParser.VariableDeclarationContext ctx) {
        if(semantic){
            java.lang.String varName = ctx.identifierList().getText();
            java.lang.String[] vNames;
            if(varName.contains(",")){
                vNames = parseString(varName);
            }else{
                vNames=null;
            }

            Data value = null;

            java.lang.String arrayVer = ctx.type_().getText().toLowerCase();
            if (arrayVer.contains("array")) {
                MiniPascalParser.ArrayTypeContext arraytype = ctx.type_().structuredType().unpackedStructuredType().arrayType();
                Data[] size = this.visit(arraytype.typeList().indexType().simpleType()).toArray();
                System.out.println("size: "+size[1].toString());
                System.out.println("Pos: "+size[0]);
                value = new Data(new Data[size[1].toInteger()], localVars.size());
                value.setPos1(size[0].toInteger());
            }

            if(ctx.type_().getText().equalsIgnoreCase("boolean")){
                value = new Data(false, localVars.size());
            }
            else if (ctx.type_().getText().equalsIgnoreCase("integer")){
                value = new Data(0, localVars.size());
            }else if (ctx.type_().getText().equalsIgnoreCase("string")){
                value = new Data("", localVars.size());
            }else if (ctx.type_().getText().equalsIgnoreCase("char")){
                value = new Data(' ', localVars.size());
            }

            if (vNames == null) {
                // peek() gets us the top element, i.e. current scope
                //validacion de que si la variable ya habia sido inicializada
                if(localVars.peek().get(varName)!=null && localVars.peek().get(varName).getScope()==cont){//en caso de que se quiera inicializar una variable ya inicializada
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": '"+varName+"' ya ha sido declarada";
                    appendToPane(writer, err, Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                }else{//aqui se agregara una nueva variable al hashmap de variable locales
                    localVars.peek().put(varName, value);
                    System.out.println("Se declaro la variable: "+varName+" tipo: "+ctx.type_().getText());
                }

            }
            else{
                for (int i = 0; i < vNames.length; i++){
                    if(semantic){
                        //validacion de que si la variable ya habia sido inicializada
                        if(localVars.peek().get(vNames[i])!=null && localVars.peek().get(vNames[i]).getScope()==cont){//en caso de que se quiera inicializar una variable ya inicializada
                            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": '"+vNames[i]+"' ya ha sido declarada";
                            appendToPane(writer, err+"\n", Color.RED);
                            writer.update(writer.getGraphics());
                            semantic=false;
                        }else{//aqui se agregara una nueva variable al hashmap de variable locales
                            localVars.peek().put(vNames[i], value);
                            System.out.println("Se declaro la variable: "+vNames[i]+" tipo: "+ctx.type_().getText());
                        }
                    }
                }
            }
            return value;
        }else{
            return null;
        }
    }

    @Override
    public Data visitScalar(MiniPascalParser.ScalarContext ctx) {
        java.lang.String name = ctx.scalarType().identifier().getText();
        Data size = localVars.peek().get(name);
        Data[] temp = new Data[2];
        temp[0]=new Data(0, localVars.size());
        temp[1]=size;
        return new Data(temp, localVars.size());
    }

    @Override
    public Data visitSubType(MiniPascalParser.SubTypeContext ctx) {
        int num1=0, num2=0;
        java.lang.String name;
        if(ctx.subrangeType().constant(0).identifier()!=null){
            name = ctx.subrangeType().constant(0).getText();
            num1 = localVars.peek().get(name).toInteger();
        }
        if(ctx.subrangeType().constant(1).identifier()!=null){
            name = ctx.subrangeType().constant(1).getText();
            num2 = localVars.peek().get(name).toInteger();
        }
        if(ctx.subrangeType().constant(0).unsignedNumber()!=null){
            name = ctx.subrangeType().constant(0).getText();
            num1 = Integer.parseInt(name);
            if(ctx.subrangeType().constant(0).sign()!=null){
                if(ctx.subrangeType().constant(0).sign().getText().equalsIgnoreCase("-")){
                    num1= -num1;
                }
            }
        }
        if(ctx.subrangeType().constant(1).unsignedNumber()!=null){
            name = ctx.subrangeType().constant(1).getText();
            num2 = Integer.parseInt(name);
            if(ctx.subrangeType().constant(1).sign()!=null){
                if(ctx.subrangeType().constant(1).sign().getText().equalsIgnoreCase("-")){
                    num2= -num2;
                }
            }
        }
        Data size = new Data(num2-num1+1, localVars.size());
        Data[] pos = new Data[2];
        pos[0]=new Data(num1, localVars.size());
        pos[1]= size;
        return new Data(pos, localVars.size());
    }

    @Override
    public Data visitAssignmentStatement(MiniPascalParser.AssignmentStatementContext ctx) {
        if(semantic){
            java.lang.String varName = ctx.variable().getText();

            java.lang.String[] vNames;
            if (varName.contains(",")) {
                vNames = parseString(varName);
            }
            else{
                vNames = null;
            }
            Data value = this.visit(ctx.expression());
            if(ctx.variable().expression(0)!=null){
                Data pos1 = this.visit(ctx.variable().expression(0));
                System.out.println("Position: "+pos1.toString());
                // peek() gets us the top element, i.e. current scope
                StringTokenizer st = new StringTokenizer(varName, "[");
                java.lang.String name = st.nextToken();
                Data temp = localVars.peek().get(name);
                System.out.println("Pos1: " +temp.getPos1());
                Data[] var = temp.toArray();
                var[pos1.toInteger()-temp.getPos1()] = value;
                localVars.peek().put(varName, new Data(var, localVars.size()));
                System.out.println("Se le asigno a la variable: " + varName + ", el siguiente valor: " + value);
            }

            if (vNames == null) {
                // peek() gets us the top element, i.e. current scope
                //localVars.peek().put(varName, value);

                //hacer chequeo si vName esta en la tabla de simbolos como una variable, o si pertenece a una función
                if(!localVars.peek().containsKey(varName) && !functions.containsKey(varName) && !procedures.containsKey(varName)){
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": no existe variable, funcion o proceso llamado '"+varName+"'";
                    appendToPane(writer, err+"\n", Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                } else {

                    System.out.println("Se le asigno a la variable: " + varName + ", el siguiente valor: " + value);
                    if(localVars.peek().containsKey(varName)) {

                        if(value == null){
                            System.out.println("error, vuelva a intentarlo");
                            return null;
                        }
                        System.out.println("Tipo de variable Instanciado como: " + localVars.peek().get(varName).instanceOf()
                                + " asignándole: " + value.instanceOf());

                        //System.out.println("el value es "+value);//succ


                        if (!localVars.peek().get(varName).instanceOf().equals(value.instanceOf())
                                && !((localVars.peek().get(varName).instanceOf().equals("array") && value.instanceOf().equals("integer")))) {
                            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Se esparaba tipo: '"+localVars.peek().get(varName).instanceOf()+
                                    "' en la variable '"+varName+"', pero se dio tipo: '"+value.instanceOf()+"'";
                            appendToPane(writer, err+"\n", Color.RED);
                            writer.update(writer.getGraphics());
                            semantic=false;
                        } else {
                            localVars.peek().put(varName, value);
                        }
                    } else if(functions.containsKey(varName)){
                        System.out.println("la variable es una función");
                        System.out.println("Instanciado como " + functions.get(varName).resultType().getText()
                                + " asignándole " + value.instanceOf());

                        if (!functions.get(varName).resultType().getText().equals(value.instanceOf())
                                && !((functions.get(varName).resultType().getText().equals("array") && value.instanceOf().equals("integer")))) {
                            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Se esparaba tipo: '"+localVars.peek().get(varName).instanceOf()+
                                    "' en la variable '"+varName+"', pero se dio tipo: '"+value.instanceOf()+"'"; //CAMBIOOOOO
                            semantic=false;
                            appendToPane(writer, err+"\n", Color.RED);
                            writer.update(writer.getGraphics());
                        } else {
                            localVars.peek().put(varName, value);
                        }

                    }
                }

            }
            else{
                for (int i = 0; i < vNames.length; i++){
                    if(!localVars.peek().containsKey(vNames[i])){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": no existe variable, funcion o proceso llamado '"+vNames[i]+"'";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    } else{
                        System.out.println("Se asigno a la variable: " + vNames[i] + ", el valor: " + value);

                        System.out.println("Instanciado como " + localVars.peek().get(vNames[i]).instanceOf()
                                + " asignándole " + value.instanceOf());


                        if (!localVars.peek().get(vNames[i]).instanceOf().equals(value.instanceOf())
                                && !((localVars.peek().get(vNames[i]).instanceOf().equals("array") && value.instanceOf().equals("integer")))) {
                            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Se esparaba tipo: '"+localVars.peek().get(vNames[i]).instanceOf()+
                                    "' en la variable '"+vNames[i]+"', pero se dio tipo: '"+value.instanceOf()+"'"; //CAMBIOOOOOOOOOOO
                            appendToPane(writer, err+"\n", Color.RED);
                            writer.update(writer.getGraphics());
                            semantic=false;
                        } else {
                            localVars.peek().put(vNames[i], value);
                        }

                    }
                }
            }

            return value;
        }else{
            return null;
        }
    }

    @Override
    public Data visitString(MiniPascalParser.StringContext ctx) {
        java.lang.String temp = ctx.getText().substring(1, ctx.getText().length()-1);
        return new Data(temp, localVars.size());
    }

    @Override
    public Data visitCharacter(MiniPascalParser.CharacterContext ctx) {
        char temp = ctx.getText().charAt(1);
        return new Data(temp, localVars.size());
    }

    @Override
    public Data visitExpression(MiniPascalParser.ExpressionContext ctx) {
        if(semantic){
            if(ctx.getChildCount()>1){
                Data left = visit(ctx.simpleExpression());
                Data right = visit(ctx.expression());

                Data result = null;

                if (ctx.relationaloperator().getText().equals("=")){
                    result = new Data(left.equals(right), localVars.size());
                    System.out.println(left+"="+right+":"+result);

                }
                else if (ctx.relationaloperator().getText().equals("<>")){
                    result = new Data(!(left.equals(right)), localVars.size());
                    System.out.println(left+"<>"+right+":"+result);
                }
                else if (ctx.relationaloperator().getText().equals(">")){
                    result = new Data(left.toInteger() > right.toInteger(), localVars.size());
                    System.out.println(left+">"+right+":"+result);
                }
                else if (ctx.relationaloperator().getText().equals(">=")){
                    result = new Data(left.toInteger() >= (right.toInteger()), localVars.size());
                    System.out.println(left+">="+right+":"+result);
                }
                else if (ctx.relationaloperator().getText().equals("<")){
                    result = new Data(left.toInteger() < (right.toInteger()), localVars.size());
                    System.out.println(left+"<"+right+":"+result);
                }
                else if (ctx.relationaloperator().getText().equals("<=")){
                    result = new Data(left.toInteger() <= (right.toInteger()), localVars.size());
                    System.out.println(left+"<="+right+":"+result);
                }
                else{
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Operacion de comparacion desconocida";
                    appendToPane(writer, err+"\n", Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                }
                return result;
            }else{
                return this.visit(ctx.simpleExpression());
            }
        }else{
            return null;
        }
    }

    @Override
    public Data visitParExpr(MiniPascalParser.ParExprContext ctx) {
        if(semantic){
            if(ctx.expression().expression()!=null && ctx.expression().simpleExpression()!=null){
                Data left = visit(ctx.expression().simpleExpression());
                Data right = visit(ctx.expression().expression());

                Data result = null;

                if(left!=null && right!=null){
                    if (ctx.expression().relationaloperator().getText().equals("=")){
                        result = new Data(left.equals(right), localVars.size());
                        System.out.println(left+"="+right+":"+result);

                    }
                    else if (ctx.expression().relationaloperator().getText().equals("<>")){
                        result = new Data(!(left.equals(right)), localVars.size());
                        System.out.println(left+"<>"+right+":"+result);
                    }
                    else if (ctx.expression().relationaloperator().getText().equals(">")){
                        result = new Data(left.toInteger() > right.toInteger(), localVars.size());
                        System.out.println(left+">"+right+":"+result);
                    }
                    else if (ctx.expression().relationaloperator().getText().equals(">=")){
                        result = new Data(left.toInteger() >= (right.toInteger()), localVars.size());
                        System.out.println(left+">="+right+":"+result);
                    }
                    else if (ctx.expression().relationaloperator().getText().equals("<")){
                        result = new Data(left.toInteger() < (right.toInteger()), localVars.size());
                        System.out.println(left+"<"+right+":"+result);
                    }
                    else if (ctx.expression().relationaloperator().getText().equals("<=")){
                        result = new Data(left.toInteger() <= (right.toInteger()), localVars.size());
                        System.out.println(left+"<="+right+":"+result);
                    }
                    else{
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Operacion de comparacion desconocida";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    }
                    return result;
                }else{
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor llamado es nulo";
                    appendToPane(writer, err+"\n", Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                    return  null;
                }
            }else{
                return this.visit(ctx.expression().simpleExpression());
            }
        }else{
            return null;
        }
    }

    @Override
    public Data visitSimpleExpression(MiniPascalParser.SimpleExpressionContext ctx) {
        if(semantic){
            if(ctx.getChildCount()>1){
                Data left = visit(ctx.term());
                Data right = visit(ctx.simpleExpression());

                Data result = null;

                if (ctx.additiveoperator().getText().equals("+")){
                    result = new Data(left.toInteger()+right.toInteger(), localVars.size());
                    System.out.println(left+"+"+right+"="+result);

                }
                else if (ctx.additiveoperator().getText().equals("-")){
                    result = new Data(left.toInteger()-right.toInteger(), localVars.size());
                    System.out.println(left+"-"+right+"="+result);
                }
                else if (ctx.additiveoperator().getText().equalsIgnoreCase("or")){
                    result = new Data(left.toBoolean() || right.toBoolean(), localVars.size());
                    System.out.println(left+" OR "+right+"="+result);
                }
                else{
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Operacion de adicion desconocida";
                    appendToPane(writer, err+"\n", Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                }
                return result;
            }else{
                return this.visit(ctx.term());
            }
        }else{
            return null;
        }
    }

    @Override
    public Data visitTerm(MiniPascalParser.TermContext ctx) {
        if(semantic){
            if(ctx.getChildCount()>1){
                Data left = visit(ctx.signedFactor());
                Data right = visit(ctx.term());
                System.out.println(left.toString());
                System.out.println(right.toString());

                Data result = null;

                if (ctx.multiplicativeoperator().getText().equals("*")){
                    result = new Data(left.toInteger()*right.toInteger(), localVars.size());
                    System.out.println(left+"*"+right+"="+result);

                }
                else if (ctx.multiplicativeoperator().getText().equals("/")){
                    result = new Data(left.toInteger()/right.toInteger(), localVars.size());
                    System.out.println(left+"/"+right+"="+result);
                }
                else if (ctx.multiplicativeoperator().getText().equalsIgnoreCase("div")){
                    result = new Data(left.toInteger()/right.toInteger(), localVars.size());
                    System.out.println(left+" div "+right+"="+result);
                }else if (ctx.multiplicativeoperator().getText().equalsIgnoreCase("mod")){
                    result = new Data(left.toInteger()%right.toInteger(), localVars.size());
                    System.out.println(left+" mod "+right+"="+result);
                }else if (ctx.multiplicativeoperator().getText().equalsIgnoreCase("and")){
                    System.out.println("left: "+left.toString()+", right: "+right.toString());
                    result = new Data(left.toBoolean()&&right.toBoolean(), localVars.size());
                    System.out.println(left+" AND "+right+":"+result);
                }
                else{
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Operacion de multiplicacion desconocida";
                    appendToPane(writer, err+"\n", Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                }
                return result;
            }else{
                return this.visit(ctx.signedFactor());
            }
        }else{
            return  null;
        }
    }

    @Override
    public Data visitSignedFactor(MiniPascalParser.SignedFactorContext ctx) {
        Data factor =visit(ctx.factor());
        if(!(ctx.type==null)){
            if(ctx.type.getType()==MiniPascalParser.MINUS){
                return new Data(-factor.toInteger(), localVars.size());
            }
        }
        return this.visit(ctx.factor());
    }



    @Override
    public Data visitNotFactor(MiniPascalParser.NotFactorContext ctx) {
        Data bool = visit(ctx.factor());
        return new Data(!bool.toBoolean(), localVars.size());
    }

    @Override
    public Data visitFactVar(MiniPascalParser.FactVarContext ctx) {
        Data var = new Data(ctx.getText(), localVars.size());
        Data value;
        if(var.toString().contains("[")){
            Data pos1 = this.visit(ctx.variable().expression(0));
            System.out.println("Position: "+pos1.toString());
            // peek() gets us the top element, i.e. current scope
            StringTokenizer st = new StringTokenizer(var.toString(),"[");
            java.lang.String name = st.nextToken();
            Data temp = localVars.peek().get(name);
            System.out.println("Pos1: " +temp.getPos1());
            Data[] array = temp.toArray();
            value = array[pos1.toInteger()-temp.getPos1()];
        }else{
            value = localVars.peek().get(var.toString());
        }

        return value;
    }

    @Override
    public Data visitUnsignedConst(MiniPascalParser.UnsignedConstContext ctx) {
        return this.visit(ctx.unsignedConstant());
    }

    @Override
    public Data visitBool_(MiniPascalParser.Bool_Context ctx) {
        if (ctx.getText().equalsIgnoreCase("true")){
            return new Data(true, localVars.size());
        }else{
            return new Data(false, localVars.size());
        }
    }

    @Override
    public Data visitUnsignedInteger(MiniPascalParser.UnsignedIntegerContext ctx) {
        java.lang.String temp = ctx.getText();
        return new Data(Integer.parseInt(temp), localVars.size());
    }

    /***************** if statement *********************/
    @Override
    public Data visitIfStatement(MiniPascalParser.IfStatementContext ctx) {
        if (semantic){
            try{
                if(this.visit(ctx.expression()).toBoolean()){
                    this.visit(ctx.statement(0));
                }else{
                    this.visit(ctx.statement(1));
                }
            }catch (Exception e){
                java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor llamado es nulo";
                appendToPane(writer, err+"\n", Color.RED);
                writer.update(writer.getGraphics());
                semantic=false;
                return  null;
            }
        }

        return null;
    }

    /*************** Writeln, readln ***************/
    @Override
    public Data visitWriter(MiniPascalParser.WriterContext ctx) {
        if (semantic){
            StringBuilder result = new StringBuilder();
            if(ctx.write().writeContent().variable()!=null){
                Data var = new Data(ctx.write().writeContent().variable().getText(), localVars.size());
                if(var.toString().contains("[")){
                    Data pos1 = this.visit(ctx.write().writeContent().variable().expression(0));
                    System.out.println("Position: "+pos1.toString());
                    // peek() gets us the top element, i.e. current scope
                    StringTokenizer st = new StringTokenizer(var.toString(),"[");
                    java.lang.String name = st.nextToken();
                    Data temp = localVars.peek().get(name);
                    System.out.println("Pos1: " +temp.getPos1());
                    Data[] value = temp.toArray();
                    try{
                        result.append(value[pos1.toInteger()-temp.getPos1()].toString());
                    }catch (Exception e){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor invalido en Write()";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    }
                }else{
                    Data value = localVars.peek().get(var.toString());
                    try{
                        result.append(value.toString());
                    }catch (Exception e){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor invalido en Write()";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    }
                }
            }else{
                StringTokenizer st = new StringTokenizer(ctx.write().writeContent().getText(),"'");
                while(st.hasMoreTokens())
                    result.append(st.nextToken());
            }
            if(ctx.write().writeVar().variable()!=null){
                Data var = new Data(ctx.write().writeVar().variable().getText(), localVars.size());
                if(var.toString().contains("[")){
                    Data pos1 = this.visit(ctx.write().writeVar().variable().expression(0));
                    // peek() gets us the top element, i.e. current scope
                    StringTokenizer st = new StringTokenizer(var.toString(),"[");
                    java.lang.String name = st.nextToken();
                    Data temp = localVars.peek().get(name);
                    Data[] value = temp.toArray();
                    try{
                        result.append(value[pos1.toInteger()-temp.getPos1()].toString());
                    }catch (Exception e){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor invalido en Write()";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    }
                }else{
                    Data value = localVars.peek().get(var.toString());
                    try{
                        result.append(value.toString());
                    }catch (Exception e){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor invalido en Write()";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    }
                }
            }
            appendToPane(writer,result.toString()+"\n", Color.black);
            writer.update(writer.getGraphics());
            System.out.println("Write: "+result);
        }
        return null;
    }

    Boolean bo;
    @Override
    public Data visitReader(MiniPascalParser.ReaderContext ctx) {
        if(semantic){
            java.lang.String name = ctx.read().identifier().getText();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input: ");
            if(scanner.hasNextInt() && localVars.peek().get(name).instanceOf().equals("integer")){
                System.out.println("estas en int");
                int num = scanner.nextInt();
                appendToPane(writer,"Input: "+num+"\n", Color.BLACK);
                writer.update(writer.getGraphics());
                Data value = new Data(num, localVars.size());
                localVars.peek().put(name, value);
                //System.out.println("Assigned Name: " + name + ", Value: " + value);
            }
            else if (scanner.hasNext()
                    && (localVars.peek().get(name).instanceOf().equals("string") || localVars.peek().get(name).instanceOf().equals("char"))){
                System.out.println("estas en string");
                java.lang.String st = scanner.next();
                appendToPane(writer,"Input: "+st+"\n", Color.BLACK);
                writer.update(writer.getGraphics());
                Data value=null;
                if(st.length()==1){
                    value = new Data(st.charAt(0), localVars.size());
                }else{
                    value = new Data(st, localVars.size());
                }
                localVars.peek().put(name, value);
            }
            else{
                //System.out.println("NOT A VALID INPUT");
                appendToPane(writer,"Input: "+scanner.next()+"\n", Color.BLACK);
                java.lang.String err = "Error semantico: Se esperaba un ("+localVars.peek().get(name).instanceOf()+") para la variable '"+name+"' en la linea "+ctx.getStart().getLine();
                appendToPane(writer, err+"\n", Color.RED);
                writer.update(writer.getGraphics());
                semantic=false;
            }
        }
        return null;
    }



    /***************************** while, for, repeat loops ****************************/

    @Override
    public Data visitWhileStatement(MiniPascalParser.WhileStatementContext ctx) {
        if(semantic){
            // Make a new scope for within the while loop
            HashMap<java.lang.String, Data> newScope = new HashMap<java.lang.String, Data>();
            // Make sure the new scope has access to the global variables too by using putAll()
            newScope.putAll(localVars.peek());
            localVars.push(newScope);

            Boolean condition = this.visit(ctx.expression()).toBoolean();
            while (condition){
                if (!break_or_nah) {
                    if (!continue_or_nah) {
                        this.visit(ctx.statement());
                        // Re-evaluate the condition to determine if we continue
                        condition = this.visit(ctx.expression()).toBoolean();
                    }
                    else{
                        // Re-evaluate the condition to determine if we continue
                        condition = this.visit(ctx.expression()).toBoolean();
                    }
                }
                else{
                    break;
                }
            }

            // Reset break/continue
            break_or_nah = false;
            continue_or_nah = false;

            // Once we're done with our loop, return to the original scope
            localVars.pop();
        }

        return null;
    }

    @Override
    public Data visitForStatement(MiniPascalParser.ForStatementContext ctx) {
        if(semantic){
            // Make a new scope for within the while loop
            HashMap<java.lang.String, Data> newScope = new HashMap<java.lang.String, Data>();
            // Make sure the new scope has access to the global variables too by using putAll()
            newScope.putAll(localVars.peek());
            localVars.push(newScope);

            Data tempVar = new Data(this.visit(ctx.forList()).toInteger(), localVars.size());
            localVars.peek().put(ctx.identifier().getText(),tempVar);

            int cond = this.visit(ctx.forList().finalValue()).toInteger();
            if(ctx.forList().type.getType()==MiniPascalParser.TO){
                for(int i = tempVar.toInteger(); i <= cond; i++){
                    if (!break_or_nah) {
                        if (!continue_or_nah) {
                            this.visit(ctx.statement());
                            tempVar.add1();
                        }
                        else{
                            tempVar.add1();
                        }
                    }
                    else{
                        break;
                    }
                }
            }else{
                for(int i = tempVar.toInteger(); i >= cond; i--){
                    if (!break_or_nah) {
                        if (!continue_or_nah) {
                            this.visit(ctx.statement());
                            tempVar.sub1();
                        }
                        else{
                            tempVar.sub1();
                        }
                    }
                    else{
                        break;
                    }
                }
            }

            // Reset break/continue
            break_or_nah = false;
            continue_or_nah = false;

            // Once we're done with our loop, return to the original scope
            localVars.pop();
        }

        return null;
    }

    @Override
    public Data visitForList(MiniPascalParser.ForListContext ctx) {
        return new Data(this.visit(ctx.initialValue()).toInteger(), localVars.size());
    }

    @Override
    public Data visitRepeatStatement(MiniPascalParser.RepeatStatementContext ctx) {
        if(semantic){
            // Make a new scope for within the while loop
            HashMap<java.lang.String, Data> newScope = new HashMap<java.lang.String, Data>();
            // Make sure the new scope has access to the global variables too by using putAll()
            newScope.putAll(localVars.peek());
            localVars.push(newScope);

            Boolean condition;
            do {
                if (!break_or_nah) {
                    if (!continue_or_nah) {
                        this.visit(ctx.statements());
                        // Re-evaluate the condition to determine if we continue
                        condition = this.visit(ctx.expression()).toBoolean();
                    }
                    else{
                        // Re-evaluate the condition to determine if we continue
                        condition = this.visit(ctx.expression()).toBoolean();
                    }
                }
                else{
                    break;
                }
            }while(condition);

            // Reset break/continue
            break_or_nah = false;
            continue_or_nah = false;

            // Once we're done with our loop, return to the original scope
            localVars.pop();

        }
        return null;
    }



    @Override
    public Data visitBreakd(MiniPascalParser.BreakdContext ctx) {
        break_or_nah = true;
        return null;
    }

    @Override
    public Data visitContinued(MiniPascalParser.ContinuedContext ctx) {
        continue_or_nah = true;
        return null;
    }



    /************************ functions ***********************/

    @Override
    public Data visitFuncDesignator(MiniPascalParser.FuncDesignatorContext ctx) {
        if(semantic){
            cont++;
            // Make a new scope for within the function
            HashMap<java.lang.String, Data> newScope = new HashMap<java.lang.String, Data>();
            // Make sure the new scope has access to the global variables too by using putAll()
            newScope.putAll(localVars.peek());
            localVars.push(newScope);

            // Get the context of the function
            MiniPascalParser.FunctionDeclarationContext function = functions.get(ctx.functionDesignator().identifier().getText());
            // Get the name of the function
            java.lang.String functionName = function.identifier().getText();
            System.out.println(functionName);
            // Keep track of where we are in the list of parameters
            int position = 0;
            for (int i = 0; i < function.formalParameterList().formalParameterSection().size(); i++){

                java.lang.String varName = function.formalParameterList().formalParameterSection(i).parameterGroup().identifierList().getText();
                java.lang.String[] vNames;
                if (varName.contains(",")) {
                    vNames = parseString(varName);
                }
                else{
                    vNames = null;
                }
                // Place the variable name and its data value into this scope's variables (i.e. localVars)
                if (vNames == null) {
                    //System.out.println("I-sing: " + position);
                    //System.out.println("VarName: " + varName);
                    //Data val = this.visit(function.parameterList().actualParameter(i).parameterwidth(position).expression());
                    //System.out.println("existen "+ localVars.peek().get(varName).getScope() +"parametros");

                    if(localVars.peek().get(varName)!=null && localVars.peek().get(varName).getScope()==cont-1) {//en caso de que se dar una variable ya usada
                        java.lang.String err = "Error Semantico en la linea " + ctx.getStart().getLine() + ": '" + varName + "' ya ha sido declarada";
                        appendToPane(writer, err, Color.RED);
                        writer.update(writer.getGraphics());
                        semantic = false;
                        return null;
                    }
                    if(position>=ctx.functionDesignator().parameterList().actualParameter().size()){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": no coinciden los parametros con los argumentos de la funcion "+
                                functionName+"; args: "+ ctx.functionDesignator().parameterList().actualParameter().size()  + ", Params: "+ (position+1);//CAMBIOOOO
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                        return null;
                    }
                    Data val = this.visit(ctx.functionDesignator().parameterList().actualParameter(position).expression());
                        System.out.println("El parametro "+ctx.functionDesignator().parameterList().actualParameter(position).getText()+" con el valor: "+val.toString()+" se dio como argumento a la variable "+ varName+" de la funcion "+functionName);

                    System.out.println("param instanciado "+val.instanceOf()+" asignándole "
                            +function.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText());

                    if(!val.instanceOf().equals( function.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText() )){
                        java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Se esparaba tipo: '"+function.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()+
                                "', pero se dio tipo: '"+val.instanceOf()+"'";
                        appendToPane(writer, err+"\n", Color.RED);
                        writer.update(writer.getGraphics());
                        semantic=false;
                    } else{
                        //System.out.println("Added: " + val);
                        // peek() gets us the top element, i.e. current scope
                        localVars.peek().put(varName, val);
                        position += 1;
                    }


                }
                else{
                    for (int k = 0; k < vNames.length; k++) {
                        if(position>=ctx.functionDesignator().parameterList().actualParameter().size()){
                            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": no coinciden los parametros con los argumentos de la funcion "+
                                    functionName+"; args: "+ ctx.functionDesignator().parameterList().actualParameter().size()  + ", Params: "+ (position+1);//CAMBIOOOO
                            appendToPane(writer, err+"\n", Color.RED);
                            writer.update(writer.getGraphics());
                            semantic=false;
                            return null;
                        }
                        Data val = this.visit(ctx.functionDesignator().parameterList().actualParameter(position).expression());
                        if (val == null) {
                            String nonDeclaredVarName = ctx.functionDesignator().parameterList().actualParameter(position).getText();
                            java.lang.String err = "Error Semantico en la linea " + ctx.getStart().getLine() + ": no existe variable llamada '" + nonDeclaredVarName + "'";
                            appendToPane(writer, err + "\n", Color.RED);
                            writer.update(writer.getGraphics());
                            semantic = false;
                            return null;
                        } else {

                            if(localVars.peek().get(vNames[k])!=null && localVars.peek().get(vNames[k]).getScope()==cont-1) {//en caso de que se dar una variable ya usada
                                java.lang.String err = "Error Semantico en la linea " + ctx.getStart().getLine() + ": '" + vNames[k] + "' ya ha sido declarada";
                                appendToPane(writer, err, Color.RED);
                                writer.update(writer.getGraphics());
                                semantic = false;
                                return null;
                            }
                            System.out.println(ctx.functionDesignator().parameterList().actualParameter().size());
                            System.out.println(position);

                            System.out.println("El parametro " + ctx.functionDesignator().parameterList().actualParameter(position).getText() + " con el valor: " + val.toString() + " se dio como argumento a la variable " + vNames[k] + " de la funcion " + functionName);

                            System.out.println("param instanciado " + val.instanceOf() + " asignándole "
                                    + function.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText());

                            if (!val.instanceOf().equals(function.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText())) {
                                java.lang.String err = "Error Semantico en la linea " + ctx.getStart().getLine() + ": Se esparaba tipo: '" + function.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText() +
                                        "', pero se dio tipo: '" + val.instanceOf() + "'";
                                appendToPane(writer, err + "\n", Color.RED);
                                writer.update(writer.getGraphics());
                                semantic = false;
                                return null;
                            } else {
                                // peek() gets us the top element, i.e. current scope
                                localVars.peek().put(vNames[k], val);
                                //System.out.println("Table: " + localVars.peek());
                                position += 1;
                                //System.out.println("Position: " + position);
                            }
                        }
                    }
                }
            }
            if(position == ctx.functionDesignator().parameterList().actualParameter().size()){
                this.visitChildren(function.block());
                Data returnVal = localVars.peek().get(functionName);
                //System.out.println("ojito, se retorna un tipo "+returnVal.instanceOf());      //cambio clave*
                //System.out.println(returnVal.toString());

                // Move back down to lower scope
                localVars.pop();

                return returnVal;
            }else{
                java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": no coinciden los parametros con los argumentos de la funcion "+
                        functionName+"; args: "+ ctx.functionDesignator().parameterList().actualParameter().size()  + ", Params: "+ position;//CAMBIOOOO
                appendToPane(writer, err+"\n", Color.RED);
                writer.update(writer.getGraphics());
                semantic=false;
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public Data visitProcStatement(MiniPascalParser.ProcStatementContext ctx) {
        try{
            if (semantic){
                cont++;
                // Make a new scope for within the function
                HashMap<java.lang.String, Data> newScope = new HashMap<java.lang.String, Data>();
                // Make sure the new scope has access to the global variables too by using putAll()
                newScope.putAll(localVars.peek());
                localVars.push(newScope);


                // Get the context of the function
                MiniPascalParser.ProcedureDeclarationContext procedure = procedures.get(ctx.identifier().getText());
                // Get the names of the variables that will be updated at the end of the procedure
                List<java.lang.String> procNames = new ArrayList<>();
                java.lang.String[] pNames;
                java.lang.String pName = "";
                for (int i = 0 ; i < ctx.parameterList().actualParameter().size(); i++) {
                    pName = ctx.parameterList().actualParameter(i).getText();
                    procNames.add(pName);
                }
                //System.out.println("Varnames: " + varNames);
                for (int i = 0; i < procNames.size(); i++) {
                    if (procNames.get(i).contains(",")) {
                        pNames = parseString(procNames.get(i));
                        for (int j = 0; j < pNames.length; j++){
                            procedureVars.add(pNames[j]);
                        }
                    } else {
                        procedureVars.add(procNames.get(i));
                    }
                }
                //System.out.println("ProcedureVars: " + procedureVars);

                // Get the names of variables we have manipulated here
                List<java.lang.String> usedVars = new ArrayList<>();


                int position = 0;

                for (int i = 0; i < procedure.formalParameterList().formalParameterSection().size(); i++){
                    java.lang.String varName = procedure.formalParameterList().formalParameterSection(i).parameterGroup().identifierList().getText();
                    java.lang.String[] vNames;
                    if (varName.contains(",")) {
                        vNames = parseString(varName);
                    }
                    else{
                        vNames = null;
                    }

                    // Place the variable name and its data value into this scope's variables (i.e. localVars)
                    if (vNames == null) {
                        //System.out.println("I-sing: " + position);
                        //System.out.println("VarName: " + varName);
                        Data val = this.visit(ctx.parameterList().actualParameter(position).expression());

                        System.out.println("El parametro "+ctx.parameterList().actualParameter(position).getText()+" con el valor: "+val.toString()+" se dio como argumento a la variable "+ varName+" del procedimiento "+ctx.identifier().getText());

                        System.out.println("param instanciado "+val.instanceOf()+" asignándole "
                                +procedure.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText());

                        if(!val.instanceOf().equals( procedure.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText() )){
                            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Se esparaba tipo: '"+procedure.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()+
                                    "', pero se dio tipo: '"+val.instanceOf()+"'";
                            appendToPane(writer, err+"\n", Color.RED);
                            writer.update(writer.getGraphics());
                            semantic=false;
                        }else{
                            // peek() gets us the top element, i.e. current scope
                            localVars.peek().put(varName, val);
                            usedVars.add(varName);
                            position += 1;
                        }



                    }
                    else{
                        for (int k = 0; k < vNames.length; k++){
                            //System.out.println("Vnames: " + vNames[k]);
                            if(ctx.parameterList().actualParameter(position)!=null){
                                Data val = this.visit(ctx.parameterList().actualParameter(position).expression());
                                System.out.println("El parametro "+ctx.parameterList().actualParameter(position).getText()+" con el valor: "+val.toString()+" se dio como argumento a la variable "+ vNames[k]+" del procedimiento "+ctx.identifier().getText());

                                System.out.println("param instanciado "+val.instanceOf()+" asignándole "
                                        +procedure.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText());

                                if(!val.instanceOf().equals( procedure.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText() )){
                                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Se esparaba tipo: '"+procedure.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText()+
                                            "', pero se dio tipo: '"+val.instanceOf()+"'";
                                    appendToPane(writer, err+"\n", Color.RED);
                                    writer.update(writer.getGraphics());
                                    semantic=false;
                                }else{
                                    // peek() gets us the top element, i.e. current scope
                                    localVars.peek().put(vNames[k], val);
                                    usedVars.add(vNames[k]);
                                    //System.out.println("Table: " + localVars.peek());
                                    position +=1;
                                }
                            }

                        }
                    }
                }

                if(position == ctx.parameterList().actualParameter().size()){
                    this.visitChildren(procedure);

                    // Move back down and transfer over any variables as necessary
                    HashMap<java.lang.String, Data> current = localVars.pop();
                    HashMap<java.lang.String, Data> parent = localVars.peek();

                    // Go through and figure out which global variables to update
                    for (int i = 0; i < procedureVars.size(); i++){
                        if (parent.containsKey(procedureVars.get(i))){
                            Data value = current.get(usedVars.get(i));
                            parent.put(procedureVars.get(i), value);
                        }
                    }

                    procedureVars.clear();


                }else{
                    java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": no coinciden los parametros con los argumentos de la funcion "+
                            procedure.identifier().getText()+"; args: "+ ctx.parameterList().actualParameter().size()  + ", Params: "+ position; //CAMBIOOOO
                    appendToPane(writer, err+"\n", Color.RED);
                    writer.update(writer.getGraphics());
                    semantic=false;
                }
            }
        }catch (Exception e){
            java.lang.String err = "Error Semantico en la linea "+ctx.getStart().getLine()+": Valor llamado es nulo";
            appendToPane(writer, err+"\n", Color.RED);
            writer.update(writer.getGraphics());
            semantic=false;
        }

        return null;

    }

    @Override
    public Data visitFunctionDeclaration(MiniPascalParser.FunctionDeclarationContext ctx) {
        if(semantic){
            java.lang.String name = ctx.identifier().getText();
            functions.put(name, ctx);
        }
        return null;
    }

    @Override
    public Data visitProcedureDeclaration(MiniPascalParser.ProcedureDeclarationContext ctx) {
        if (semantic){
            java.lang.String name = ctx.identifier().getText();
            procedures.put(name, ctx);
        }
        return null;
    }

    /*************** Utility methods ***************/


    // Separate the variable name list into usable names
    private java.lang.String[] parseString(java.lang.String variable_list){
        return variable_list.split("\\s*,\\s*");
    }

    public Stack<HashMap<java.lang.String, Data>> symbolTable(){
        return localVars;
    }

    public java.lang.String getName(){
        return name;
    }

    private void setName(java.lang.String name){
        this.name = name;
    }

    public Boolean getSemantic(){
        return semantic;
    }

    private void appendToPane(JTextPane tp, java.lang.String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        System.out.println(msg);
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}