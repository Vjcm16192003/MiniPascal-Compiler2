import antlr.MiniPascalLexer;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        new MyFrame();

    }

    public static void miniPascalLexer() throws IOException {
        CharStream input = CharStreams.fromFileName("test.txt");
        MiniPascalLexer lexer = new MiniPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        System.out.println("Hay "+tokens.size()+" tokens");

        for(int i=0; i<tokens.size(); i++){
            Token token = tokens.get(i);
            String typename;
            if(token.getType() >=0 ){
                typename = lexer.getTokenNames()[token.getType()];
            }
            else{
                typename = "EOF";
            }
            System.out.println(typename + ": "+ token.getText());
        }
    }
}