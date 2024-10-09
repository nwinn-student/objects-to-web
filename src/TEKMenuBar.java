import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * <p>TEKMenuBar provides a component that is useful in displaying 
 * menu objects, which the user can select to further select the 
 * items that menu holds.
 * 
 * <p>The default menus are File, Edit, Selection, View, and Help, 
 * these each have their own related items.  The creation of both 
 * menus and their items has been streamlined to make further 
 * creation viable for readability.
 *
 * @see javax.swing.JMenu
 * @see javax.swing.JPopupMenu
 * @see javax.swing.JMenuItem
 * @author Zakariya Javed, Noah Winn
 * @version 9/30/2024
 */
public class TEKMenuBar extends JMenuBar{
    private static TEKActionAdapter action = new TEKActionAdapter();
    private static ArrayList<String> basicKeys = new ArrayList<>();
    /**
     * Creates a  TEKMenuBar with the default menu setup.
     */
    public TEKMenuBar(){
        super();
        propogateKeys();
        JMenu fileMenu = addMenu("File", this, KeyEvent.VK_F, "Use Alt-F to select File.");
        JMenu editMenu = addMenu("Edit", this, KeyEvent.VK_E, "Use Alt-E to select Edit.");
        JMenu selectionMenu = addMenu("Selection", this, KeyEvent.VK_S, "Use Alt-S to select Selection.");
        JMenu viewMenu = addMenu("View", this, KeyEvent.VK_V, "Use Alt-V to select View.");
        JMenu helpMenu = addMenu("Help", this, KeyEvent.VK_H, "Use Alt-H to select Help");
        
        // File
        addMenuItem("Open", fileMenu, KeyEvent.VK_O, "Use Ctrl-O to open a file.");
        addMenuItem("Save", fileMenu, KeyEvent.VK_S, "Use Ctrl-S to save.");
        addMenuItem("Exit", fileMenu, KeyEvent.VK_Q, "Use Ctrl-Q to quit.");
        
        // Edit
        addMenuItem("Undo", editMenu, KeyEvent.VK_Z, "Use Ctrl-Z to undo an action.");
        addMenuItem("Redo", editMenu, KeyEvent.VK_Y, "Use Ctrl-Y to redo an action.");
        
        // Selection
        addMenuItem("Create", selectionMenu, KeyEvent.VK_INSERT, "Use Insert to create a new ObjectUI.");
        addMenuItem("Select All", selectionMenu, KeyEvent.VK_A, "Use Ctrl-A to select all ObjectUIs.");
        addMenuItem("Delete", selectionMenu, KeyEvent.VK_DELETE, "Use Delete to remove all selected ObjectUIs.");
        addMenuItem("Delete All", selectionMenu, 0, "Removes all of the current ObjectUIs.");
        
        // View   NOTE: ctrl + scrollup and ctrl + scrolldown work MUCH better than ctrl-+ and ctrl--. Don't know how to visualize those will fix later.
        addMenuItem("Zoom In", viewMenu, KeyEvent.VK_PLUS, "Use Ctrl-+ to zoom in.");
        addMenuItem("Zoom Out", viewMenu, KeyEvent.VK_MINUS, "Use Ctrl-- to zoom out.");
        // cont. adding more
        
    }
    /**
     * For telling the object which items you wish to not require the user
     * to press CTRL-<key> to use.  Example: "Open" will force "o" to do the open task instead
     * of CTRL-o.
     */
    private void propogateKeys(){
        basicKeys.add("Create");
        basicKeys.add("Delete");
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
        if(key != 0){
             //Key is not undefined
            menu.setMnemonic(key);
        }
        menu.getAccessibleContext().setAccessibleName(title);
        menu.getAccessibleContext().setAccessibleDescription(description);
        parent.add(menu);
        
        return menu;
    }
    /**
     * Used to make JMenuItems easier to create.
     * 
     * @param title, the name of the JMenuItem object to be created
     * @param parent, the JMenu to add the JMenuItem object to
     * @param key, the keyboard key necessary to activate the button w/o clicking
     * @param description, for screen readers to provide extra information
     */
    public static JMenuItem addMenuItem(String title, JComponent parent, int key, String description){
        JMenuItem menuItem = new JMenuItem(title);
        if(key != 0){
            if(basicKeys.contains(title)){
               menuItem.setAccelerator(KeyStroke.getKeyStroke(key, 0));
            } else{
                 //Key is not undefined, will always require CTRL
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, Event.CTRL_MASK));
            }
        }
        menuItem.addActionListener(action);
        menuItem.getAccessibleContext().setAccessibleName(title);
        menuItem.getAccessibleContext().setAccessibleDescription(description);
        parent.add(menuItem);
        return menuItem;
    }
}
