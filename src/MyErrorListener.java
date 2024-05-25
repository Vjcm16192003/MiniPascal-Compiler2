import org.antlr.v4.runtime.*;

import java.util.Collections;
import java.util.List;

public class MyErrorListener extends BaseErrorListener {
    public static boolean hasError = false;
    private static int cont=0;

    private static String errors="";

    @Override
    public void syntaxError(
            Recognizer<?, ?> recognizer,
            Object offendingSymbol, int line, int charPositionInLine,
            String msg, RecognitionException e) {
        hasError = true;
        if(cont==0){
            errors="Hay errores en la sintaxis \n";
            System.err.println(errors);
            cont++;
        }
        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);

        if(msg.contains("missing")){
            msg = msg.replace("missing", "falta");
        }
        if(msg.contains("no viable alternative at input")){
            msg = msg.replace("no viable alternative at input", "ninguna otra opcion viable en la entrada");
        }
        if(msg.contains("at")){
            msg = msg.replace("at", "en");
        }
        if(msg.contains("extraneous input")){
            msg = msg.replace("extraneous input", "entrada extraña");
        }
        if(msg.contains("expecting")){
            msg = msg.replace("expecting", "se esperaba");
        }
        if(msg.contains("mismatched input")){
            msg = msg.replace("mismatched input", "entrada no coincidente");
        }
        if(msg.contains("mismenched input")){
            msg = msg.replace("mismenched input", "entrada no coincidente");
        }
        if(msg.contains("IDENT")){
            msg = msg.replace("IDENT", "un identificador");
        }
        if(msg.contains("'<EOF>'")){
            msg = msg.replace("'<EOF>'", "'end'");
        }
        errors = errors+"\nLexema " + "\"" + ((Token) offendingSymbol).getText() + "\""
                +
                " (linea "+line+", columna " + (charPositionInLine+1) + ")"
                +
                ": "+msg;
        System.err.println("Lexema " + "\"" + ((Token) offendingSymbol).getText() + "\""
                +
                " (linea "+line+", columna " + (charPositionInLine+1) + ")"
                +
                ": "+msg);


        //System.out.println("Rule stack: "+stack);

    }

    public static String getErrors(){
        return errors;
    }

    public static void reset(){
        hasError =false;
        errors="";
        cont=0;
    }
}
