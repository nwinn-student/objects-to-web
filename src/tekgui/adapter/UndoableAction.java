package tekgui.adapter;


/**
 * Stores the state of the object or list of objects.
 *
 * @author Noah Winn
 * @version Oct. 28, 2024
 */
public class UndoableAction{
    public static enum Variant {CREATE, DELETE, ADJUST, MOVE, UNKNOWN}
    private Object state;
    private Variant var;
    public UndoableAction(){
        this(null, Variant.UNKNOWN);
    }
    public UndoableAction(Object state){
        this(state, Variant.UNKNOWN);
    }
    public UndoableAction(Object state, Variant var){
        this.state = state;
        this.var = var;
    }
    public Variant getVariant(){return var;}
    public Object getObject(){return state;}
}
