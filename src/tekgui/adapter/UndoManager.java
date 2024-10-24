package tekgui.adapter;
import java.util.Stack;
import java.util.Deque;


/**
 * Write a description of class UndoManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UndoManager{
    // Undo is at the top, Redo is at the bottom**
    private final short MAX_ACTION;
    Deque<UndoableAction> undoToRedo;
    public UndoManager(){
        this.MAX_ACTION = 500;
    }
    public UndoManager(short MAX_ACTION){
        this.MAX_ACTION = MAX_ACTION;
    }
    public void addAction(UndoableAction a){
        
    }
}
