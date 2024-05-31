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
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener {

    JButton button, clearButton, loadButton, saveButton;
    JTextArea Code;
    JTextPane console;
    JScrollPane wscroller;
    JPanel codearea;
    JFrame pFrame = new JFrame(), viewIR = new JFrame();

    MyFrame() {
        this.setTitle("Compilador de Mini Pascal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());
        this.setSize(850, 700);
        this.setLocationRelativeTo(null);

        codearea = new JPanel();
        codearea.setLayout(new BoxLayout(codearea, BoxLayout.Y_AXIS));

        button = new JButton("Iniciar Programa");
        button.addActionListener(this);

        clearButton = new JButton("Limpiar");
        clearButton.addActionListener(this);

        loadButton = new JButton("Cargar Archivo");
        loadButton.addActionListener(this);

        saveButton = new JButton("Guardar Archivo");
        saveButton.addActionListener(this);

        Code = new JTextArea(10, 40);
        console = new JTextPane();
        console.setBounds(0,0,50,50);
        wscroller = new JScrollPane(console){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 50);
            }
        };
        wscroller.setBounds(0,0,600,600);
        Code.setEditable(true);
        JLabel editor = new JLabel("Code Editor");
        JLabel logs = new JLabel("Console Log");

        codearea.add(editor);
        codearea.add(new JScrollPane(Code));
        codearea.add(logs);
        codearea.add(wscroller);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(button);
        buttonPanel.add(clearButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);

        this.add(codearea, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
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

                // List
                List<String> rules = new ArrayList<>();
                rules.addAll(Arrays.asList(parser.getRuleNames()));

                TreeViewer viewer = new TreeViewer(rules, tree);
                viewer.setScale(viewer.getScale() / 1.5);
                pFrame.add(viewer);
                pFrame.pack();
                pFrame.setLocationRelativeTo(null);
                pFrame.setVisible(true);
                pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                AST visitor = new AST(console);
                visitor.visit(tree);
                if (visitor.getSemantic()) {
                    IRGenerator irGenerator = new IRGenerator(visitor.symbolTable());
                    irGenerator.visit(tree);
                    viewIR = new JFrame("Representacion Intermedia de Codigo de Mini Pascal");
                    viewIR.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    viewIR.setLayout(new FlowLayout());
                    viewIR.setSize(1000, 620);
                    viewIR.setLocationRelativeTo(null);
                    try {
                        FileWriter file = new FileWriter(visitor.getName() + ".ll");
                        file.write(irGenerator.getIR());
                        file.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JTextArea IRcode = new JTextArea(30, 60);
                    IRcode.setEditable(false);
                    IRcode.setText(irGenerator.getIR());
                    viewIR.add(new JScrollPane(IRcode));
                    viewIR.setVisible(true);
                }
            } else {
                console.setText(null);
                appendToPane(console, MyErrorListener.getErrors() + "\n", Color.RED);
                console.update(console.getGraphics());
                MyErrorListener.reset();
            }
        } else if (e.getSource() == clearButton) {
            Code.setText("");
            console.setText("");
        } else if (e.getSource() == loadButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (FileReader reader = new FileReader(selectedFile)) {
                    Code.read(reader, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }else if (e.getSource() == saveButton){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./src"));
            fileChooser.setDialogTitle("Save TXT File");

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                // Asegurarse de que el archivo tenga la extensión .txt
                if (!filePath.endsWith(".txt")) {
                    filePath += ".txt";
                    selectedFile = new File(filePath);
                }

                try {
                    // Obtener el texto del JTextArea
                    String textContent = Code.getText();

                    // Escribir el contenido en el archivo
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                        writer.write(textContent);
                    }

                    System.out.println("Archivo guardado exitosamente: " + filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
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

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
