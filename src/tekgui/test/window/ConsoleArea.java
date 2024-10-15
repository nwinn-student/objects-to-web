package tekgui.test.window;

import javax.swing.JTextArea;
import java.io.PrintStream;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Write a description of class ConsoleArea here.
 *
 * @author Noah Winn
 * @version Oct. 7, 2024
 */
public class ConsoleArea extends JTextArea{
    /**
     * Constructor for objects of class ConsoleArea
     */
    public ConsoleArea(){
        super();
        init();
    }
    public ConsoleArea(int rows, int columns){
        super(rows, columns);
        init();
    }
    public ConsoleArea(int rows, int columns, boolean isEditable){
        super(rows, columns);
        setEditable(isEditable);
        init();
    }
    protected void init(){
        try{
            setFont(new Font("Serif", Font.PLAIN, 20));
        } catch(Exception e){}
        TextAreaOutputStream textAreaStream = new TextAreaOutputStream(this, 1000);
        PrintStream printStream = new PrintStream(textAreaStream);
        System.setOut(printStream);
        System.setErr(printStream);
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_K){
                    textAreaStream.clear();
                }
            }
        });
        // Create right-click popupmenu
    }
}
