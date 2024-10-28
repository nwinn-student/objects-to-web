package tekgui.window;

// TEKGUI imports
import tekgui.helper.ButtonBuilder;
import tekgui.adapter.TEKActionAdapter;

// Java imports
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JComponent;

/**
 * <p>TEKToolBar provides a component that is useful for displaying 
 * commonly used Actions.
 * 
 * <p>The default actions are Undo, Redo, <add more later>.
 *
 * @see javax.swing.Action 
 * @author Hayden Verstrat, Noah Winn
 * @version 10/13/2024
 */
public class TEKToolBar extends JToolBar{
    private static TEKActionAdapter action = new TEKActionAdapter();
    /**
     * Creates a TEKToolBar with the default button setup.
     */
    public TEKToolBar() {
        super();
        //setRollover(true);
        setFloatable(false);
        int ICON_HEIGHT = 16;
        JButton undoButton = ButtonBuilder.addButton("Undo", this, "Reverts to the previous state of the window.", action);
        JButton redoButton = ButtonBuilder.addButton("Redo", this, "", action);
        try{
            Image redoImage = ImageIO.read(getClass().getResource("/res/redo_arrow.png")).getScaledInstance(ICON_HEIGHT, ICON_HEIGHT, Image.SCALE_DEFAULT);
            Image undoImage = ImageIO.read(getClass().getResource("/res/undo_arrow.png")).getScaledInstance(ICON_HEIGHT, ICON_HEIGHT, Image.SCALE_DEFAULT);

            redoButton.setIcon(new ImageIcon(redoImage));
            undoButton.setIcon(new ImageIcon(undoImage));
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        ButtonBuilder.addButton("Copy", this, "Copies the selected items.", action);
        ButtonBuilder.addButton("Cut", this, "Deletes and copies the selected items.", action);
        ButtonBuilder.addButton("Paste", this, "Pastes the copied items.", action);
    }
}
