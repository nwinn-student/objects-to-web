package tekgui.window;
// TEKGUI imports
import tekgui.helper.MenuBuilder;
import tekgui.adapter.TEKActionAdapter;
import tekgui.TEKFile;

// Java imports
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.util.List;
import java.io.File;

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
 * @version 11/17/2024
 */
public class TEKMenuBar extends JMenuBar{
    private static TEKActionAdapter action = new TEKActionAdapter();
    private static final RecentFilesManager recentFilesManager = new RecentFilesManager(); // Manage recent files
    private final JMenu recentFilesMenu; // Store recent files menu
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
        MenuBuilder.addMenuItem("Open", fileMenu, KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK, true), "Use Ctrl-O to open a file.", action);
        recentFilesMenu = MenuBuilder.addMenu("Recent Files", fileMenu);
        MenuBuilder.addMenuItem("Save", fileMenu, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, true), "Use Ctrl-S to save.", action);
        MenuBuilder.addMenuItem("Exit", fileMenu, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK, true), "Use Ctrl-Q to quit.", action);
        
        // Edit
        MenuBuilder.addMenuItem("Undo", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK, true), "Use Ctrl-Z to undo an action.", action);
        MenuBuilder.addMenuItem("Redo", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK, true), "Use Ctrl-Y to redo an action.", action);
        MenuBuilder.addMenuItem("Clear Undo", editMenu, null, "Clears the undoable actions, great for space management.", action);
        MenuBuilder.addSeparator(editMenu);
        MenuBuilder.addMenuItem("Copy", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, true), "Use Ctrl-C to copy the selected items.", action);
        MenuBuilder.addMenuItem("Cut", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK, true), "Use Ctrl-X to cut the selected items.", action);
        MenuBuilder.addMenuItem("Paste", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, true), "Use Ctrl-V to paste the previously selected elements.", action);
        MenuBuilder.addMenuItem("Duplicate", editMenu, KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK, true), "Use Ctrl-D to duplicate the selected items.", action);
        
        // Selection        // Adjust create to ask a name for the file <since we are making a file>
        MenuBuilder.addMenuItem("Create", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0, false), "Use Insert to create a new ObjectUI.", action);
        MenuBuilder.addMenuItem("Find", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK, true), "Use Ctrl-F to find an item.", action);
        MenuBuilder.addMenuItem("Connect", selectionMenu, null, "Connects all of the ObjectUIs.", action);
        MenuBuilder.addMenuItem("Display Connected", selectionMenu, null, "Displays all of the connected ObjectUIs.", action);
        MenuBuilder.addMenuItem("Select All", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK, true), "Use Ctrl-A to select all ObjectUIs.", action);
        MenuBuilder.addMenuItem("Delete", selectionMenu, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, true), "Use Delete to remove all selected ObjectUIs.", action);
        MenuBuilder.addMenuItem("Delete All", selectionMenu, null, "Removes all of the current ObjectUIs.", action);
        
        // View   NOTE: ctrl + scrollup and ctrl + scrolldown work MUCH better than ctrl-+ and ctrl--. Don't know how to visualize those will fix later.
        MenuBuilder.addMenuItem("Settings", viewMenu, KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, Event.CTRL_MASK, true), "Use Ctrl-comma to open up the Settings menu.", action);
        MenuBuilder.addSeparator(viewMenu);
        MenuBuilder.addMenuItem("Zoom In", viewMenu, KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Event.CTRL_MASK, true), "Use Ctrl-+ to zoom in.", action);
        MenuBuilder.addMenuItem("Zoom Out", viewMenu, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK, true), "Use Ctrl-- to zoom out.", action);
        MenuBuilder.addMenuItem("Reset Zoom", viewMenu, KeyStroke.getKeyStroke(KeyEvent.VK_0, Event.CTRL_MASK, true), "Use Ctrl-0 to reset zoom.", action);
        // Help information about application
        MenuBuilder.addMenuItem("About", helpMenu, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true), "Learn more about TEK-GUI", action);
        
        updateRecentFilesMenu();
        // cont. adding more
    }
        /**
     * make the Recent Files menu with the current list of files.
     */
    public void updateRecentFilesMenu() {
        recentFilesMenu.removeAll(); // Clear existing items

        List<File> recentFiles = recentFilesManager.getRecentFiles();
        if (recentFiles.isEmpty()) {
            JMenuItem noRecentItem = new JMenuItem("No recent files");
            noRecentItem.setEnabled(false);
            recentFilesMenu.add(noRecentItem);
        } else {
            for (File file : recentFiles) {
                JMenuItem item = new JMenuItem(file.getName());
                item.addActionListener(e -> openFile(file));
                recentFilesMenu.add(item);
            }
        }
    }
    /**
     * Open the selected file and update the recent files menu.
     */
    private void openFile(File file) {
        //System.out.println("Opening: " + file.getAbsolutePath()); // Replace with actual file logic
        TEKFile.openFile(file);        
        updateRecentFilesMenu(); // Refresh the menu
    }
    public void remember(File file){ 
        recentFilesManager.addFile(file); // Add the file to recent files
        updateRecentFilesMenu();
    }
}
