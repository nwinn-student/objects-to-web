package tekgui.helper;
import javax.swing.JMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.ActionListener;
import javax.swing.JSeparator;

/**
 * The creation of both menus and their items has been streamlined to make further creation viable for readability.
 */
public class MenuBuilder{
    public static void addSeparator(JComponent parent){
        JSeparator menu = new JSeparator();
        menu.setEnabled(false);
        if(parent != null){
            parent.add(menu);
        }
    }
/**
     * Used to make JMenus easier to create.
     * 
     * @param title, the name of the JMenu object to be created
     * @param parent, the JMenuBar to add the JMenu object to
     * @param key, the keyboard key necessary to activate the button w/o clicking
     * @param description, for screen readers to provide extra information
     */
    public static JMenu addMenu(String title, JComponent parent, int key, String description){
        JMenu menu = new JMenu(title);
        if(key > 0){
             //Key is not undefined
            menu.setMnemonic(key);
        }
        menu.getAccessibleContext().setAccessibleName(title);
        menu.getAccessibleContext().setAccessibleDescription(description);
        if(parent != null){
            parent.add(menu);
        }
        return menu;
    }
    public static JMenu addMenu(String title, JComponent parent, int key){
        return addMenu(title, parent, key, null);
    }
    public static JMenu addMenu(String title, JComponent parent){
        return addMenu(title, parent, -1, null);
    }
    /**
     * Used to make JMenuItems easier to create.
     * 
     * @param title, the name of the JMenuItem object to be created
     * @param parent, the JMenu to add the JMenuItem object to
     * @param key, the keyboard key necessary to activate the button w/o clicking
     * @param description, for screen readers to provide extra information
     * @param action, the actionListener that handles the button activations
     */
    public static JMenuItem addMenuItem(String title, JComponent parent, KeyStroke key, String description, ActionListener action){
        JMenuItem menuItem = new JMenuItem(title);
        if(key != null){
            menuItem.setAccelerator(key);
        }
        if(action != null){
            menuItem.addActionListener(action);
        }
        menuItem.getAccessibleContext().setAccessibleName(title);
        menuItem.getAccessibleContext().setAccessibleDescription(description);
        if(parent != null){
            parent.add(menuItem);
        }
        return menuItem;
    }
    public static JMenuItem addMenuItem(String title, JComponent parent, KeyStroke key, ActionListener action){
        return addMenuItem(title, parent, key, null, action);   
    }
    public static JMenuItem addMenuItem(String title, JComponent parent, KeyStroke key, String description){
        return addMenuItem(title, parent, key, description, null);   
    }
    public static JMenuItem addMenuItem(String title, JComponent parent, KeyStroke key){
        return addMenuItem(title, parent, key, null, null);   
    }
    public static JMenuItem addMenuItem(String title, JComponent parent){
        return addMenuItem(title, parent, null, null, null);   
    }
}
