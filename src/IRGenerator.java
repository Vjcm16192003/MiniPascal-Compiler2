import Expression.Data;
import antlr.MiniPascalBaseVisitor;
import antlr.MiniPascalParser;

import java.awt.*;
import java.util.*;
import java.util.regex.Pattern;

public class IRGenerator extends MiniPascalBaseVisitor<String> {
    private Stack<HashMap<String, Data>> localVars = new Stack<>();
    private HashMap<String, IRData> names = new HashMap<>();
    private ArrayList<String> irInstructions = new ArrayList<>();
    private ArrayList<String> func_procs = new ArrayList<>();
    private ArrayList<String> global_strings = new ArrayList<>();
    private ArrayList<String> write = new ArrayList<>();
    private ArrayList<String> repetitions = new ArrayList<>();

    private boolean scope =false, ifstate=false, exprepeat=false;
    private int count =0, cparam=0, temps=0, condcount=0, matches=0;
    String IR="";

    public IRGenerator(Stack<HashMap<String, Data>> localVars){
        this.localVars = localVars;
    }

    @Override
    public String visitProgram(MiniPascalParser.ProgramContext ctx) {
        System.out.println("Segunda pasada, estoy en visitProgram");
        scope=false;
        return super.visitProgram(ctx);
    }

    @Override
    public String visitProgramHeading(MiniPascalParser.ProgramHeadingContext ctx) {
        System.out.println("Segunda pasada, estoy en visitProgramHeading");

        return super.visitProgramHeading(ctx);
    }
    boolean ts=false;
    @Override
    public String visitVariableDeclarationPart(MiniPascalParser.VariableDeclarationPartContext ctx) {
        ts= scope;
        for(MiniPascalParser.VariableDeclarationContext c: ctx.variableDeclaration()){
            irInstructions.add(this.visit(c));
        }
        return null;
    }

    @Override
    public String visitCompoundStatement(MiniPascalParser.CompoundStatementContext ctx) {
        count=0;
        cparam=0;
        if(!scope){
            irInstructions.add("\ndefine i32 @main() {\nentry:");
            this.visit(ctx.statements());
            irInstructions.add("\nend:\n\tret i32 0\n}\n");
            return null;
        }
        return super.visitCompoundStatement(ctx);
    }

    @Override
    public String visitVariableDeclaration(MiniPascalParser.VariableDeclarationContext ctx) {
        Data value = null;
        String varName = ctx.identifierList().getText();
        String[] vNames;
        if(varName.contains(",")){
            vNames = parseString(varName);
        }else{
            vNames=null;
        }
        if(vNames==null){ //Si las variables están declaradas sin coma, separadas por ;

            if(!scope){
                names.put(varName,new IRData("@"+varName, ctx.type_().getText().toLowerCase(), false));
                irInstructions.add("\n@"+varName+" = global "+getLLVMDataType(ctx.type_().getText().toLowerCase())+" 0");
                return "";
            }else{
                names.put(varName,new IRData("%"+varName, ctx.type_().getText().toLowerCase(), false));
                //irInstructions.add("\n\t%"+varName+" = alloca "+getLLVMDataType(ctx.type_().getText().toLowerCase()));
                return "\n\t%"+varName+" = alloca "+getLLVMDataType(ctx.type_().getText().toLowerCase());
            }

        }
        else{ //Var a,b,c: boolean, por ejemplo
            String line="";
            for(int i=0; i<vNames.length; i++){
                if(!scope){
                    names.put(vNames[i],new IRData("@"+vNames[i], ctx.type_().getText().toLowerCase(), false));
                    irInstructions.add("\n@"+vNames[i]+" = global "+getLLVMDataType(ctx.type_().getText().toLowerCase())+" 0");
                    line = "";
                }else{
                    names.put(vNames[i],new IRData("%"+vNames[i], ctx.type_().getText().toLowerCase(), false));
                    //irInstructions.add("\n\t%"+vNames[i]+" = alloca "+getLLVMDataType(ctx.type_().getText().toLowerCase()));
                    line = line+ "\n\t%"+vNames[i]+" = alloca "+getLLVMDataType(ctx.type_().getText().toLowerCase());
                }
            }
            return line;
        }
    }

    @Override
    public String visitAssignmentStatement(MiniPascalParser.AssignmentStatementContext ctx) {
        String varName = ctx.variable().getText();
        String valor = ctx.expression().getText();
        String line="";

        //OBTENER EL TIPO DE DATO QUE SE LE ASIGNÓ A LA VARIABLE, EJEMPLO: c:='5', obtener CHAR
        for (HashMap<String, Data> scope : localVars) {
            Set<String> variableNames = scope.keySet();
            for (String name : variableNames) {
                if(varName.equals(name) && scope.get(name).instanceOf().equals("char")){
                    int asciiValue = valor.charAt(1); //'a', solo obtengo la a y la convierto a su ascii
                    valor = ""+asciiValue;
                }
            }
        }

        String[] vNames;
        if (varName.contains(",")) {
            vNames = parseString(varName);
        }
        else{
            vNames = null;
        }

        if (vNames == null) {
            if(names.get(varName).result){
                line = "\n\tret "+ getLLVMDataType(names.get(varName).type) +" "+valor;
            }else{
                boolean val=false;
                for(String s: func_procs){
                    if(ctx.expression().getText().contains(s)){
                        val=true;
                    }
                }
                if(!val){
                    line= "\n\tstore "+getLLVMDataType(names.get(varName).type)+" "+valor+", ptr "+
                            names.get(varName).IRname;
                }else{
                    line = this.visit(ctx.expression());
                }
            }


        }
        else{
            for (int i = 0; i < vNames.length; i++){
                line= "\n\tstore "+getLLVMDataType(names.get(vNames[i]).type)+" "+valor+", ptr "+
                        names.get(vNames[i]).IRname;

            }
        }

        //System.out.println("Local initialized variables: " + localVars.peek());
        irInstructions.add(line);
        return line;
    }

    @Override
    public String visitExpression(MiniPascalParser.ExpressionContext ctx) {
        if(ctx.getChildCount()>1){
            String left = this.visit(ctx.simpleExpression());
            String right = this.visit(ctx.expression());


            if (ctx.relationaloperator().getText().equals("=")){
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = icmp eq i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = icmp eq i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.relationaloperator().getText().equals("<>")){
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = icmp ne i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = icmp ne i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.relationaloperator().getText().equals(">")){
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = icmp sgt i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = icmp sgt i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.relationaloperator().getText().equals(">=")){
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = icmp sge i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = icmp sge i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.relationaloperator().getText().equals("<")){
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = icmp slt i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = icmp slt i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.relationaloperator().getText().equals("<=")){
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = icmp sle i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = icmp sle i32 "+left+", "+right);
                }
                return "";
            }else{
                return "";
            }
        }else{
            return this.visit(ctx.simpleExpression());
        }
    }



    @Override
    public String visitSimpleExpression(MiniPascalParser.SimpleExpressionContext ctx) {
        if(ctx.getChildCount()>1){
            String left = this.visit(ctx.term());
            String right = this.visit(ctx.simpleExpression());
            boolean simple = false;
            int num;
            try{
                num = Integer.parseInt(left);
                num = Integer.parseInt(right);
                simple=true;
            }catch (Exception e){

            }

            if (ctx.additiveoperator().getText().equals("+")){
                if(simple){
                    num = Integer.parseInt(left)+Integer.parseInt(right);
                    return num+"";
                }
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = add i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = add i32 "+left+", "+right);
                }
                return "";

            }
            else if (ctx.additiveoperator().getText().equals("-")){
                if(simple){
                    num = Integer.parseInt(left)-Integer.parseInt(right);
                    return num+"";
                }
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = sub i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = sub i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.additiveoperator().getText().equalsIgnoreCase("or")){
                if(ifstate){
                    condcount++;
                    if(condcount<matches) {
                        irInstructions.add("\n\tbr i1 %temp." + temps + ", label %itag, label %cond" + condcount + "\n");
                        irInstructions.add("\ncond"+condcount+":");
                    }
                }
                return "";
            }
            else{
                return"";
            }
        }else{
            return this.visit(ctx.term());
        }
    }

    @Override
    public String visitTerm(MiniPascalParser.TermContext ctx) {
        if(ctx.getChildCount()>1){
            String left = this.visit(ctx.signedFactor());
            String right = this.visit(ctx.term());
            boolean simple = false;
            int num;
            try{
                num = Integer.parseInt(left);
                num = Integer.parseInt(right);
                simple=true;
            }catch (Exception e){

            }


            if (ctx.multiplicativeoperator().getText().equals("*")){
                if(simple){
                    num = Integer.parseInt(left)*Integer.parseInt(right);
                    return num+"";
                }
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = mul i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = mul i32 "+left+", "+right);
                }
                return "";

            }
            else if (ctx.multiplicativeoperator().getText().equals("/")){
                if(simple){
                    num = Integer.parseInt(left)/Integer.parseInt(right);
                    return num+"";
                }
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = sdiv i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = sdiv i32 "+left+", "+right);
                }
                return "";
            }
            else if (ctx.multiplicativeoperator().getText().equalsIgnoreCase("div")){
                if(simple){
                    num = Integer.parseInt(left)/Integer.parseInt(right);
                    return num+"";
                }
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = sdiv i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = sdiv i32 "+left+", "+right);
                }
                return "";
            }else if (ctx.multiplicativeoperator().getText().equalsIgnoreCase("mod")){
                if(simple){
                    num = Integer.parseInt(left)%Integer.parseInt(right);
                    return num+"";
                }
                if(ifstate){
                    temps++;
                    irInstructions.add("\n\t%temp."+temps+" = srem i32 "+left+", "+right);
                }else{
                    count++;
                    irInstructions.add("\n\t%"+count+" = srem i32 "+left+", "+right);
                }
                return "";
            }else if (ctx.multiplicativeoperator().getText().equalsIgnoreCase("and")){
                if(ifstate){
                    condcount++;
                    if(condcount<matches) {
                        irInstructions.add("\n\tbr i1 %temp." + temps + ", label %cond" + condcount + ", label %etag\n");
                        irInstructions.add("\ncond"+condcount+":");
                    }
                }
            }
            return "";
        }else{
            return this.visit(ctx.signedFactor());
        }
    }

    @Override
    public String visitSignedFactor(MiniPascalParser.SignedFactorContext ctx) {
        String factor= this.visit(ctx.factor());
        if(!(ctx.type==null)){
            if(ctx.type.getType()==MiniPascalParser.MINUS){
                int num = -(Integer.parseInt(factor));
                return num+"";
            }
        }
        return this.visit(ctx.factor());
    }

    @Override
    public String visitFactVar(MiniPascalParser.FactVarContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitBool_(MiniPascalParser.Bool_Context ctx) {
        return ctx.getText();
    }

    @Override
    public String visitUnsignedInteger(MiniPascalParser.UnsignedIntegerContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitIfStatement(MiniPascalParser.IfStatementContext ctx) {
        ifstate=true;
        temps=0;
        repetitions = new ArrayList<>();
        String s = ctx.expression().getText().toLowerCase();
        matches = (int) countOccurrences(s, "and");
        matches = matches + (int) countOccurrences(s, "or");
        matches++;
        this.visit(ctx.expression());
        irInstructions.add("\n\tbr i1 %temp." + temps + ", label %itag, label %etag\n");
        irInstructions.add("\nitag:");
        this.visit(ctx.statement(0));
        irInstructions.add("\netag:");
        this.visit(ctx.statement(1));
        condcount=0;
        return "";
    }

    public static long countOccurrences(String source, String find) {

        return Pattern.compile(find) // Pattern
                .matcher(source) // Mather
                .results()       // Stream<MatchResults>
                .count();
    }

    @Override
    public String visitFunctionDeclaration(MiniPascalParser.FunctionDeclarationContext ctx) {
        scope=true;
        String name = ctx.identifier().getText();
        String line = "\ndefine "+getLLVMDataType(ctx.resultType().getText().toLowerCase())+" @"+name+"(";
        names.put(name, new IRData("@"+name, ctx.resultType().getText().toLowerCase(), true));
        func_procs.add(name);
        for (int i = 0; i < ctx.formalParameterList().formalParameterSection().size(); i++){

            String varName = ctx.formalParameterList().formalParameterSection(i).parameterGroup().identifierList().getText();
            String[] vNames;
            if (varName.contains(",")) {
                vNames = parseString(varName);
            }
            else{
                vNames = null;
            }
            System.out.println(line.substring(line.length()-1));

            // Place the variable name and its data value into this scope's variables (i.e. localVars)
            if (vNames == null) {
                if(!line.substring(line.length() - 1).equals("(") && !line.substring(line.length() - 1).equals(",")){
                    line = line + ", ";
                }
                    //System.out.println("Added: " + val);
                    // peek() gets us the top element, i.e. current scope
                    line = line+getLLVMDataType(ctx.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText())+" %"+varName;

            }
            else{
                for (int k = 0; k < vNames.length; k++){
                    if(!line.substring(line.length() - 1).equals("(") && !line.substring(line.length() - 1).equals(",")){
                        line = line + ", ";
                    }
                    line = line+getLLVMDataType(ctx.formalParameterList().formalParameterSection(i).parameterGroup().typeIdentifier().getText())+" %"+vNames[k];


                }
            }
        }
        line = line+") {\nentry:";
        irInstructions.add(line);
        irInstructions.add("\n"+this.visit(ctx.block())+"\n}\n");
        scope=false;

        return "";
    }

    @Override
    public String visitFunctionDesignator(MiniPascalParser.FunctionDesignatorContext ctx) {
        count++;
        ArrayList<String> params = new ArrayList<>();
        for(MiniPascalParser.ActualParameterContext c :ctx.parameterList().actualParameter()){
            if(names.get(c.expression().getText())!=null){
                cparam++;
                params.add(getLLVMDataType(names.get(c.expression().getText()).type)+" %t"+cparam);
                irInstructions.add("\n\t%t"+cparam+" = load "+getLLVMDataType(names.get(c.expression().getText()).type)+", "+getLLVMDataType(names.get(c.expression().getText()).type)+"* "+names.get(c.expression().getText()).IRname);
            }else{
                try{
                    int num  = Integer.parseInt(this.visit(c.expression()));
                    params.add("i32 "+num);
                }catch (Exception e){
                    if(c.expression().getText().equals("true") || c.expression().getText().equals("false")){
                        params.add("i1 "+c.expression().getText());
                    }else{
                        if(c.expression().getText().length()>1){
                            params.add("i8* "+c.expression().getText());
                        }else{
                            params.add("i8 "+c.expression().getText());
                        }
                    }
                }
            }
        }
        String name = ctx.identifier().getText();
        String line = "\n\t%"+count+"= call "+getLLVMDataType(names.get(name).type)+" "+names.get(name).IRname+"(";
        for(String s: params){
            line = line+s+", ";
        }
        line = line.substring(0, line.length()-2);
        line = line+")";
        return line;
    }

    @Override
    public String visitProcedureDeclaration(MiniPascalParser.ProcedureDeclarationContext ctx) {
        scope=true;
        return super.visitProcedureDeclaration(ctx);
    }

    private String[] parseString(String variable_list){
        return variable_list.split("\\s*,\\s*");
    }

    private String getLLVMDataType(String type){
        switch(type){
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

    public String getIR(){
        String IR= "";
        /*for(String s: global_strings){
            IR = IR + s+"\n";
        }*/
        for(int i=0; i<irInstructions.size(); i++){
            IR = IR + irInstructions.get(i);
        }
        if(!write.isEmpty()){
            IR = IR+"declare dso_local i32 @printf(ptr noundef, ...) #1\n";
            for(String s: write){

            }
        }
        return IR;
    }

}