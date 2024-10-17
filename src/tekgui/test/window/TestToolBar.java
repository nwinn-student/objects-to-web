package tekgui.test.window;
import tekgui.window.TEKFrame;
import tekgui.helper.ButtonBuilder;

import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Write a description of class TestToolBar here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestToolBar extends JToolBar implements ActionListener{
    public TestToolBar(){
        super();
        setFloatable(false);
        ButtonBuilder.addButton("New", this, "Creates a new TEKFrame simulation.", this);
        addSeparator();
        ButtonBuilder.addButton("Run", this, "Runs the current test.", this);
        //ButtonBuilder.addButton("Run Section", this, "Runs all tests in a section.", this);
        
        ButtonBuilder.addButton("Pause", this, "Halts the current test.", this);
        addSeparator();
        //ButtonBuilder.addButton("Redo", this, "Redoes the current test.", this);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        switch(e.getActionCommand()){
            case "New":
                TestFrame.respawnInternal(new TEKFrame(), true);
                break;
            case "Run":
                TestFrame.getTree().fire();
        }
    }
}
