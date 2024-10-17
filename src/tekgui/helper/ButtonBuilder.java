package tekgui.helper;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.event.ActionListener;
/**
 * The creation of Buttons has been streamlined to make further creation viable for readability.
 */
public class ButtonBuilder{
    /**
     * Used to make JButtons easier to create.
     * 
     * @param name, the name of the JButton object to be created
     * @param parent, the JToolBar to add the JButton object to
     * @param description, for screen readers to provide extra information
     * @param action, the actionListener that handles the button activations
     */
    public static JButton addButton(String name, JComponent parent, String description, ActionListener action){
        JButton button = new JButton();
        if(action != null){
            button.addActionListener(action);
        }
        button.setFocusable(false);
        button.setActionCommand(name);
        button.setText(name);
        button.getAccessibleContext().setAccessibleName(name);
        button.getAccessibleContext().setAccessibleDescription(description);
        button.setToolTipText(name);
        if(parent != null){
            parent.add(button);
        }
        return button;
    }
    public static JButton addButton(String name, JComponent parent, ActionListener action){
        return addButton(name, parent, null, action);
    }
    public static JButton addButton(String name, JComponent parent, String description){
        return addButton(name, parent, description, null);
    }
    public static JButton addButton(String name, JComponent parent){
        return addButton(name, parent, null, null);
    }
}
