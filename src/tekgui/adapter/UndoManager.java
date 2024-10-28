package tekgui.adapter;
import java.util.Stack;
import java.util.Deque;


/**
 * Adds the capability to undo specified actions.
 *
 * @author Hayden Verstrat, Noah Winn
 * @version Oct. 28, 2024
 */
public class UndoManager{
    // Undo is at the top, Redo is at the bottom**
    private enum Action {UNDO, REDO}
    private static short MAX_SIZE = 50;
    private static Stack<UndoableAction> undoAbleList = new Stack();
    private static Stack<UndoableAction> redoAbleList = new Stack();
    
    public static void setActionLimit(short actionLimit){
        MAX_SIZE = actionLimit;
    }
    public static boolean canUndo(){
        if(undoAbleList.empty()){
            return false;
        }
        return true;
    }
    public static boolean canRedo(){
        if(redoAbleList.empty()){
            return false;
        }
        return true;
    }
    private static void updateList(){
        if(undoAbleList.size() > MAX_SIZE){
            undoAbleList.removeElementAt(MAX_SIZE);
        }
    }
    public static void addAction(UndoableAction a){
        undoAbleList.push(a);
        updateList();
    }
    public static void undo(){
        if(canUndo()){
            redoAbleList.push(undoAbleList.pop());
            performAction(redoAbleList.peek(), Action.UNDO);
        }
    }
    public static void redo(){
        if(canRedo()){
            undoAbleList.push(redoAbleList.pop());
            performAction(undoAbleList.peek(), Action.REDO);
        }
    }
    private static void performAction(UndoableAction action, Action type){
        switch(action.getVariant()){
            case CREATE:
                break;
            case DELETE:
                break;
            case ADJUST:
                break;
            case MOVE:
                break;
            case UNKNOWN:
                break;
        }
    }
}
