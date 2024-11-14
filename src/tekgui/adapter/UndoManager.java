package tekgui.adapter;
import java.util.Stack;

import tekgui.ObjectUI;
import tekgui.TEKFile;

import java.util.Deque;
import java.util.List;


/**
 * Adds the capability to undo specified actions.
 *
 * @author Hayden Verstrat, Noah Winn
 * @version Nov. 9, 2024
 */
public class UndoManager{
    // Undo is at the top, Redo is at the bottom**
    private static enum Action {UNDO, REDO}
    public static enum Style{CLASSIC, LEGACY}
    private static short MAX_SIZE = 255;
    private static Style style = Style.CLASSIC;
    private static final Stack<UndoableAction> undoAbleList = new Stack();
    private static final Stack<UndoableAction> redoAbleList = new Stack();
    
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
            // Default, clears the redoable list each time undo is cast
            // Common for web browsers and most other applications, LEGACY could confuse
            if(style.equals(Style.CLASSIC)){
                redoAbleList.clear();
            }
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
    public static void clear(){
        undoAbleList.clear();
        redoAbleList.clear();
    }
    private static void performAction(UndoableAction action, Action type){
        //System.out.println(action.getVariant());
        switch(action.getVariant()){
            case CREATE:
                Object state = action.getObject();
            // Check the type of state and handle accordingly
            if (type == Action.UNDO) {
                // Undo CREATE (remove the object)
                if (state instanceof ObjectUI) {
                    TEKFile.getFrame().getPanel().removeObject((ObjectUI) state);
                } else if (state instanceof List) {
                    List<ObjectUI> objects = (List<ObjectUI>) state;
                    for (ObjectUI obj : objects) {
                        TEKFile.getFrame().getPanel().removeObject(obj);
                    }
                } else if (state instanceof ObjectUI[]) {
                    ObjectUI[] objectsArray = (ObjectUI[]) state;
                    for (ObjectUI obj : objectsArray) {
                        TEKFile.getFrame().getPanel().removeObject(obj);
                    }
                }
            } else if (type == Action.REDO) {
                // Redo CREATE (re-add the object)
                if (state instanceof ObjectUI) {
                    TEKFile.getFrame().getPanel().addObject((ObjectUI) state);
                } else if (state instanceof List) {
                    List<ObjectUI> objects = (List<ObjectUI>) state;
                    for (ObjectUI obj : objects) {
                        TEKFile.getFrame().getPanel().addObject(obj);
                    }
                } else if (state instanceof ObjectUI[]) {
                    ObjectUI[] objectsArray = (ObjectUI[]) state;
                    for (ObjectUI obj : objectsArray) {
                        TEKFile.getFrame().getPanel().addObject(obj);
                    }
                }
            }
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
