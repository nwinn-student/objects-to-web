package tekgui.window;

// TEKGUI imports
import tekgui.TEKFile;
import tekgui.ObjectUI;
import tekgui.helper.MenuBuilder;
import tekgui.adapter.TEKActionAdapter;
import tekgui.adapter.UndoManager;
import tekgui.adapter.ShortcutSystem;


// Java imports
import javax.swing.JPopupMenu;
import javax.swing.JComponent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import java.awt.event.KeyEvent;
import java.util.List;
/**
 * A popupMenu that allows for editing to occur, alongside more ways of 
 * interacting with the application.  Sends a list of objectUIs.
 *
 * @author Noah Winn
 * @version Nov. 11, 2024
 */
public class TEKPopupMenu extends JPopupMenu{
    private static List<ObjectUI> attachedComponent;
    private static TEKActionAdapter action = new TEKActionAdapter();
    
    /**
     * TEKPopupMenu Constructor
     *
     */
    public TEKPopupMenu(){
        super();
        MenuBuilder.addMenuItem("Edit", this, null, "Pops up the display menu.", action);
        addSeparator();
        MenuBuilder.addMenuItem("Cut", this, null, "Used to cut a selected object.", action);
        MenuBuilder.addMenuItem("Copy", this, null, "Used to copy a selected object.", action);
        MenuBuilder.addMenuItem("Paste", this, null, "Used to paste in an object.", action);
        addSeparator();
        MenuBuilder.addMenuItem("Undo", this, null, "Used to undo the action enacted upon an object.", action);
        MenuBuilder.addMenuItem("Redo", this, null, "Used to redo the action enacted upon an object.", action);
        addSeparator();
        MenuBuilder.addMenuItem("Reset Zoom", this, null, "Used to reset the zoom on the screen.", action);
        setVisible(false);
    }
    private void active(List<ObjectUI> e){
        setAttached(e);
        boolean isEmpty = e.size() > 0;
        getComponent(0).setEnabled(isEmpty); // Edit
        getComponent(2).setEnabled(isEmpty); // Cut, check if possible (1+ selected or current)
        getComponent(3).setEnabled(isEmpty); // Copy, check if possible (1+ selected or current)
        getComponent(4).setEnabled(ShortcutSystem.canPaste()); // Paste, check if possible
        getComponent(6).setEnabled(UndoManager.canUndo()); // Undo, check if possible (1+ action)
        getComponent(7).setEnabled(UndoManager.canRedo()); // Redo, check if possible (1+ action)
        getComponent(9).setEnabled(true); // Reset Zoom, check if possible (zoom != regular)
    }
    /**
     * Method activate
     *
     * @param e A parameter
     */
    public void activate(MouseEvent e){
        active(TEKFile.getFrame().getPanel().getSelected());
        show(e.getComponent(), e.getX(), e.getY());
        setVisible(true);
    }
    public void activate(KeyEvent e){
        active(TEKFile.getFrame().getPanel().getSelected());
        show(e.getComponent(), 10, 10);
        setVisible(true);
    }
    public void deactivate(){
        setAttached(null);
        setVisible(false);
    }
    public List<ObjectUI> getAttached(){return attachedComponent;}
    public void setAttached(List<ObjectUI> attachedComponent){
        this.attachedComponent = attachedComponent;
    }
    /**
     * Activates TEKEditFrame and imports the held attachment.  
     * Hides the TEKPopupMenu.
     * Adjust***, only supports editting 1, but must accomidate later support for multiple
     */
    public void transferAttached(){
        if(attachedComponent == null){return;}
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new TEKEditView(attachedComponent);
                deactivate();
            }
        });
    }
}
