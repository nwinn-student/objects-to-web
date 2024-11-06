package tekgui.adapter;

// TEKGUI imp orts
import tekgui.TEKFile;
import tekgui.window.TEKPanel;
import tekgui.window.TEKFrame;
import tekgui.TEKManagement;
import tekgui.helper.Helper;
import tekgui.ObjectUI;
import tekgui.window.AboutText;

// Java imports
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.event.KeyEvent;
/**
 * The TEKAdapter for receiving action events.  Is used within a 
 * component's <b>addActionListener</b> method, and when the action 
 * event occurs, the actionPerformed method is invoked.
 * May not be worth it to have all strings saved perm, 
 *
 * @see java.awt.event.ActionEvent
 * @author Zakariya Javed
 * @version 9/30/2024
 */
public class TEKActionAdapter implements ActionListener{
    /*
     * Expected to be used many times throughout the life of the app
     */
    private static final transient String save = "Save";
    private static final transient String find = "Find";
    private static final transient String selectAll = "Select All";
    private static final transient String undo = "Undo";
    private static final transient String redo = "Redo";
    private static final transient String copy = "Copy";
    private static final transient String cut = "Cut";
    private static final transient String paste = "Paste";
    private static final transient String edit = "Edit";
    private static final transient String settings = "Settings";
    @Override
    public void actionPerformed(ActionEvent e){
        TEKFrame frame = TEKFile.getFrame();
        if(frame == null){return;}
        TEKPanel panel = frame.getPanel();
        if(panel == null){return;}
        switch(e.getActionCommand()){
            case "Open":
                TEKFile.openFile();
                return;
            case save:
                TEKFile.saveFile();
                return;
            // note that holding down this key [Insert] WILL break the app, find a fix later
            case "Create": 
                TEKManagement.createObject();
                return;
            case find:
                ShortcutSystem.enact(ShortcutSystem.Shortcut.FIND);
                return;
            case selectAll:
                TEKManagement.selectAll();
                return;
            case "Delete":
                TEKManagement.removeObject();
                return;
            case "Delete All":
                TEKManagement.removeAllObject();
                return;
            case undo:
                UndoManager.undo();
                return;
            case redo:
                UndoManager.redo();
                return;
            case "Clear Undo":
                UndoManager.clear();
                return;
            case copy:
                ShortcutSystem.enact(ShortcutSystem.Shortcut.COPY);
                return;
            case cut:
                ShortcutSystem.enact(ShortcutSystem.Shortcut.CUT);
                return;
            case paste:
                ShortcutSystem.enact(ShortcutSystem.Shortcut.PASTE);
                return;
            case "Duplicate":
                ShortcutSystem.enact(ShortcutSystem.Shortcut.DUPLICATE);
                break;
            case edit:
                TEKManagement.editView();
                break;
            case "Zoom In":
                panel.zoomIn();
                return;
            case "Zoom Out":
                panel.zoomOut();
                return;
            case "Reset Zoom":
                return;
            case settings:
                // Open up a new window for ^*^ customization ^*^
                return;
            case "Exit":
                TEKFile.getFrame().save();
                System.out.println("Exiting through automated ALT-F4.");
                Helper.keyMaskedClick(KeyEvent.VK_F4, KeyEvent.VK_ALT);
            case "About":
                SwingUtilities.invokeLater(() -> {
                    new AboutText(frame);
                break;
            // add more..
        }
        // another switch for toolBar if that is desired
    }
}
