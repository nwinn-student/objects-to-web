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
 * <p>The default actions are Undo, Redo, <add more later> however 
 * the creation of Actions has been streamlined to make further 
 * creation viable for readability.
 *
 * @see javax.swing.Action 
 * @author Hayden Verstrat, Noah Winn
 * @version 9/30/2024
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
        // can set Text to "" and in TEKActionAdapter we do e.getSource().getToolTipText()
        JButton undoButton = addButton("Undo", this, "Reverts to the previous state of the window.");
        JButton redoButton = addButton("Redo", this, "");
        try{
            Image redoImage = ImageIO.read(getClass().getResource("/res/redo_arrow.png")).getScaledInstance(ICON_HEIGHT, ICON_HEIGHT, Image.SCALE_DEFAULT);
            Image undoImage = ImageIO.read(getClass().getResource("/res/undo_arrow.png")).getScaledInstance(ICON_HEIGHT, ICON_HEIGHT, Image.SCALE_DEFAULT);

            redoButton.setIcon(new ImageIcon(redoImage));
            undoButton.setIcon(new ImageIcon(undoImage));
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    /**
     * Used to make JButtons easier to create.
     * 
     * @param name, the name of the JButton object to be created
     * @param parent, the JToolBar to add the JButton object to
     * @param description, for screen readers to provide extra information
     */
    public static JButton addButton(String name, JComponent parent, String description){
        JButton button = new JButton(name);
        button.addActionListener(action);
        button.setFocusable(false);
        button.getAccessibleContext().setAccessibleName(name);
        button.getAccessibleContext().setAccessibleDescription(description);
        button.setToolTipText(name);
        parent.add(button);
        return button;
    }
}
