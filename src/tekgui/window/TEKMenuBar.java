package tekgui.window;
// TEKGUI imports
import tekgui.helper.MenuBuilder;
import tekgui.adapter.TEKActionAdapter;

// Javha imports
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
 * these each have their own related items.  
 *
 * @see javax.swing.JMenu
 * @see javax.swing.JPopupMenu
 * @see javax.swing.JMenuItem
 * @author Zakariya Javed, Noah Winn
 * @version 10/13/2024
 */
public class TEKMenuBar extends JMenuBar{
    private static TEKActionAdapter action = new TEKActionAdapter();
    /**
     * Creates a  TEKMenuBar with the default menu setup.
     */
    public TEKMenuBar(){
        super();
        JMenu fileMenu = MenuBuilder.addMenu("File", this, KeyEvent.VK_F, "Use Alt-F to select File.");
        JMenu editMenu = MenuBuilder.addMenu("Edit", this, KeyEvent.VK_E, "Use Alt-E to select Edit.");
        JMenu selectionMenu = MenuBuilder.addMenu("Selection", this, KeyEvent.VK_S, "Use Alt-S to select Selection.");
        JMenu viewMenu = MenuBuilder.addMenu("View", this, KeyEvent.VK_V, "Use Alt-V to select View.");
        JMenu helpMenu = MenuBuilder.addMenu("Help", this, KeyEvent.VK_H, "Use Alt-H to select Help");
        
        // Name     Parent       Key to Press     Description     ActionListener
        
        // File                 
        MenuBuilder.addMenuItem("Open", fileMenu, KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK), "Use Ctrl-O to open a file.", action);
        MenuBuilder.addMenuItem("Save", fileMenu, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK), "Use Ctrl-S to save.", action);
        MenuBuilder.addMenuItem("Exit", fileMenu, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK), "Use Ctrl-Q to quit.", action);
        
        // Edit
        MenuBuilder.addMenuItem("Undo", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK), "Use Ctrl-Z to undo an action.", action);
        MenuBuilder.addMenuItem("Redo", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK), "Use Ctrl-Y to redo an action.", action);
        
        // Selection
        MenuBuilder.addMenuItem("Create", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "Use Insert to create a new ObjectUI.", action);
        MenuBuilder.addMenuItem("Select All", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK), "Use Ctrl-A to select all ObjectUIs.", action);
        MenuBuilder.addMenuItem("Delete", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Use Delete to remove all selected ObjectUIs.", action);
        MenuBuilder.addMenuItem("Delete All", selectionMenu, null, "Removes all of the current ObjectUIs.", action);
        
        // View   NOTE: ctrl + scrollup and ctrl + scrolldown work MUCH better than ctrl-+ and ctrl--. Don't know how to visualize those will fix later.
        MenuBuilder.addMenuItem("Zoom In", viewMenu, KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Event.CTRL_MASK), "Use Ctrl-+ to zoom in.", action);
        MenuBuilder.addMenuItem("Zoom Out", viewMenu, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK), "Use Ctrl-- to zoom out.", action);
        // cont. adding more
    }
}
