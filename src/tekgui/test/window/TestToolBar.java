package tekgui.test.window;
import tekgui.helper.ButtonBuilder;
import javax.swing.JToolBar;


/**
 * Write a description of class TestToolBar here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestToolBar extends JToolBar{
    public TestToolBar(){
        super();
        setFloatable(false);
        ButtonBuilder.addButton("New", this, "Creates a new TEKFrame simulation.");
        addSeparator();
        ButtonBuilder.addButton("Run", this, "Runs the current test.");
        ButtonBuilder.addButton("Run Section", this, "Runs all tests in a section.");
        
        ButtonBuilder.addButton("Pause", this, "Halts the current test.");
        addSeparator();
        ButtonBuilder.addButton("Redo", this, "Redoes the current test.");
    }
}
