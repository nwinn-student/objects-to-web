import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
/**
 * Write a description of class TEKLabel here.
 *
 * @author Mara Doze, Hayden Verstrat, Noah Winn
 * @version Sept. 30, 2024
 */
public class TEKLabel extends JLabel{
    private void init(){
        addMouseListener(new TEKLabelAdapter());
        // Ensure the label is set to automatically resize vertically based on content
        setVerticalAlignment(TOP);  // Align text to top
        setHorizontalAlignment(LEFT); // Align text to left
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adding a border
    }
    /**
     * Default constructor, adds a mouseListener, aligns top left, and has a black border.
     */
    public TEKLabel(){
        super();
        init();
    }
    /**
     * Creates a new TEKLabel with the specified text.
     */
    public TEKLabel(String text){
        super(text);
        init();
    }
    /**
     * Creates a new TEKLabel with the specified objectUI.
     */
    public TEKLabel(ObjectUI obj){
        super(TEKPanel.formatObjectDetails(obj));
        setBounds(obj.getPosition().x, obj.getPosition().y, obj.getSize().width, obj.getSize().height);
        init();
    }
    private ObjectUI getObjectFromText(){
        /*
         * Can be heavy, but storing the object in here, since it 
         * is getting removed constantly, can lead to memory leaks
         * 
         * I recommend having two variants, one to find object to select
         * and the other to find the object to deselect.  Just speeds up 
         * deselection.
         */ 
        for(ObjectUI obj : ((TEKPanel) getParent()).getObjects()){
            if(TEKPanel.formatObjectDetails(obj).equals(getText())){
                return obj;
            }
        }
        return null;
    }
    /**
     * Selects the TEKLabel and signifies it to the user by coloring the border and foreground.
     */
    public void select(){
        // Hayden here
    }
    /**
     * Deselects the TEKLabel and signifies it to the user by decoloring the border and foreground.
     */
    public void deselect(){
        // Hayden here
    }
}
