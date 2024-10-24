package tekgui.adapter;
import tekgui.ObjectUI;
import tekgui.TEKFile;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write a description of class ShortcutSystem here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ShortcutSystem{
    enum Shortcut {COPY, CUT, PASTE, FIND, DUPLICATE}
    public static void enact(Shortcut sys){
        if(TEKFile.getFrame() == null){
            return;
        } if(TEKFile.getFrame().getPanel() == null){
            return;
        }
        List<ObjectUI> obj = TEKFile.getFrame().getPanel()
                             .getSelected()
                             .stream()
                             .collect(Collectors.toList());
        
        switch(sys){
            case CUT:
                // Copy and delete, so delete selected
                TEKFile.getFrame().getPanel().sweepSelected();
            case COPY:
                
                break;
            case PASTE:
                // create from text, so set text
            case DUPLICATE:
                //?
            case FIND:
                break;
        }
    }
}
