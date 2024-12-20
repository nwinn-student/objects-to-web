package tekgui.test.window;
import tekgui.helper.MenuBuilder;

import javax.swing.JMenuBar;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Write a description of class TestMenuBar here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestMenuBar extends JMenuBar{
    public TestMenuBar(){
        super();
        MenuBuilder.addMenuItem("Exit", 
            MenuBuilder.addMenu("File", this, KeyEvent.VK_F, "Use Alt-F to select File."), 
            KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK), "Use Ctrl-Q to quit.", new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(e.getActionCommand() == "Exit"){
                        System.exit(0);
                    }
                }
            });
    }
}
