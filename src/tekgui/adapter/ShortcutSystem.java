package tekgui.adapter;
import tekgui.ObjectUI;
import tekgui.TEKFile;
import tekgui.TEKManagement;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Write a description of class ShortcutSystem here.
 *
 * @author Noah Winn, Coby Zhong
 * @version 11/01/2024
 */
public class ShortcutSystem{
    
    private static List<ObjectUI> clipboard = new ArrayList<>(); // Temporary storage for the copied objects. Resets every time a new copy or cut operation is performed.
/**
 * I used an in-memory list, which acts as a temporary storage for copied objects within the application session, rather than using the system clipboard or session storage
 */
    public static enum Shortcut {COPY, CUT, PASTE, FIND, DUPLICATE}
    private static boolean hasCopied = false;
    
    public static boolean canPaste(){
        return hasCopied;
    }
    public static void enact(Shortcut sys){
        if(TEKFile.getFrame() == null){
            return;
        } if(TEKFile.getFrame().getPanel() == null){
            return;
        }
        // sadly, doesn't deep copy, so if the object is deleted then that is that
        List<ObjectUI> obj = TEKFile.getFrame().getPanel()
                             .getSelected()
                             .stream()
                             .collect(Collectors.toList());
        
        switch(sys){
            case CUT:
                // Copy and delete, so delete selected
                clipboard.clear();
                clipboard.addAll(obj); 
                hasCopied = true;
                TEKFile.getFrame().getPanel().sweepSelected();
                break;
            case COPY:
                clipboard.clear();
                clipboard.addAll(obj); 
                hasCopied = true;
                break;
            case PASTE:
                if (hasCopied) {
                    // Paste by re-adding clipboard items to the panel (shallow copy)
                    TEKFile.getFrame().getPanel().addObjects(new ArrayList<>(clipboard));
                }
                break;
                // create from text, so set text
            case DUPLICATE:
                TEKManagement.createObject(obj);
                break;
            case FIND:
                // Selects OR highlights, uncertain which
                
                break;
        }
    }
}
