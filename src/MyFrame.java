import Expression.AST;
import antlr.MiniPascalLexer;
import antlr.MiniPascalParser;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener {

    JButton button;
    TextArea Code;
    JTextPane console;
    JScrollPane wscroller;
    Box codearea;
    JFrame pFrame = new JFrame(), viewIR = new JFrame();
    MyFrame(){
        this.setTitle("Mini Pascal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new FlowLayout());
        this.setSize(1000, 620);
        this.setLocationRelativeTo(null);
        codearea = Box.createVerticalBox();
        button = new JButton("Iniciar Programa");
        button.addActionListener(this);
        Code = new TextArea(30, 80);
        console = new JTextPane();
        console.setBounds(0,0,600,600);
        wscroller = new JScrollPane(console){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 100);
            }
        };
        wscroller.setBounds(0,0,600,600);
        Code.setEditable(true);
        JLabel editor = new JLabel("Editor");
        JLabel logs = new JLabel("Log");
        codearea.add(editor);
        codearea.add(Code);
        codearea.add(logs);
        codearea.add(wscroller);
        this.add(codearea, BorderLayout.CENTER);
        this.add(button);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            pFrame.setVisible(false);
            viewIR.setVisible(false);
            CharStream input = CharStreams.fromString(Code.getText());
            MiniPascalLexer lexer = new MiniPascalLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniPascalParser parser = new MiniPascalParser(tokens);

            parser.removeErrorListeners();
            parser.addErrorListener(new MyErrorListener());
            ParseTree tree = parser.program();
            if (!MyErrorListener.hasError) {
                pFrame = new JFrame("Parse Tree");

                //List
                List<String> rules = new ArrayList<>();
                rules.addAll(Arrays.asList(parser.getRuleNames()));

                TreeViewer viewer = new TreeViewer(rules, tree);
                viewer.setScale(viewer.getScale()/1.5);
                pFrame.add(viewer);
                pFrame.pack();
                pFrame.setLocationRelativeTo(null);
                //frame.setSize(frame.getWidth(), frame.getHeight());
                pFrame.setVisible(true);
                pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                AST visitor  = new AST(console);
                visitor.visit(tree);
                if(visitor.getSemantic()){
                    IRGenerator irGenerator = new IRGenerator(visitor.symbolTable());
                    irGenerator.visit(tree);
                    viewIR = new JFrame("IR de Codigo de Mini Pascal");
                    viewIR.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    viewIR.setLayout(new FlowLayout());
                    viewIR.setSize(1280, 720);
                    viewIR.setLocationRelativeTo(null);
                    try {

                        FileWriter file = new FileWriter(visitor.getName()+".ll");
                        file.write(irGenerator.getIR());
                        file.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    TextArea IRcode = new TextArea(40, 80);
                    IRcode.setEditable(false);
                    IRcode.setText(irGenerator.getIR());
                    viewIR.add(IRcode);
                    viewIR.setVisible(true);
                }
            }else{
                console.setText(null);
                appendToPane(console, MyErrorListener.getErrors()+"\n", Color.RED);
                console.update(console.getGraphics());
                MyErrorListener.reset();
            }
        }
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
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
