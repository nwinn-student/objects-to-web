package tekgui.adapter;
import tekgui.ObjectUI;
import tekgui.TEKFile;
import tekgui.TEKManagement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write a description of class ShortcutSystem here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ShortcutSystem{
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
                TEKFile.getFrame().getPanel().sweepSelected();
            case COPY:
                hasCopied = true;
                break;
            case PASTE:
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
