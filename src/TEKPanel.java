import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.awt.Dimension;
/**
 * The panel that will house the Objects.
 *
 * @author Mara Doze, Hayden Verstrat, Noah Winn, Coby Zhong
 * @version Sept. 30, 2024
 */
public class TEKPanel extends JPanel{
    private TEKFrame frame = null;
    private ArrayList<ObjectUI> objects = new ArrayList<>();
    private ArrayList<ObjectUI> selected = new ArrayList<>();
    /**
     * Default constructor
     */
    public TEKPanel(){super();}
    /**
     * Creates a new TEKPanel with the specified frame.
     */
    public TEKPanel(TEKFrame frame){
        super();
        this.frame = frame;
        this.setLayout(null);
        // will be transparent later, just like this for testing
        this.setBackground(new Color(200,255,230)); 
        //this.setOpaque(false);
    }
    /**
     * Gets all the ObjectUIs in this TEKPanel.
     * @return arrayList of ObjectUIs
     */
    public ArrayList<ObjectUI> getObjects(){return objects;}
    /**
     * Creates a new objectUI and adds it to the arrayList of objects to be displayed.
     */
    public void generateObject(){
        // Coby here
    }
    /**
     * Adds an ObjectUI to the arrayList of objects to be displayed.
     * @param obj the object added
     */
    public void addObject(ObjectUI obj){
        if(obj == null){return;}
        objects.add(obj);
        displayObjects();
    }
    /**
     * Removes an ObjectUI from the arrayList of objects to be displayed.
     * @param obj the object to be removed
     */
    public void removeObject(ObjectUI obj){
        if(obj == null){return;}
        selected.remove(obj);
        objects.remove(obj);
        displayObjects();
    }
    /**
     * Clears the arrayList of objects and refreshes the screen.
     */
    public void clearObjects(){
        selected.clear();
        while(objects.size() > 0){
            objects.remove(0);
        }
        displayObjects();
    }
    /**
     * Gets all the selected ObjectUIs in this TEKPanel
     * @return arrayList of ObjectUIs
     */
    public ArrayList<ObjectUI> getSelected(){return selected;}
    /**
     * Adds the specified ObjectUI into the list of selected ObjectUIs.  
     * Should an indication be used to determine whether an 
     * ObjectUI is selected, the indication must be set.
     * 
     * @param obj the ObjectUI to be added
     */
    public void addSelected(ObjectUI obj){
        if(obj == null){return;}
        if(objects.contains(obj)){
            selected.add(obj);
        }
    }
    /**
     * Removes the specified selected ObjectUI. Should an indication 
     * be used to determine whether an ObjectUI is selected, the 
     * indication must be reset.
     * 
     * @param obj the ObjectUI to be removed
     * @return whether the ObjectUI was within the list of selected ObjectUIs
     */
    public boolean removeSelected(ObjectUI obj) {
        if (obj == null) {
            return false; // Return false if obj is null
        }

        // Check if the object is in the selected list
        if (selected.contains(obj)) {
            obj.deselect(); // Reset the selection indication
            return selected.remove(obj); // Remove from the selected list and return true
        }

        return false; // Return false if the object was not in the selected list
    }
    /**
     * Clears the selected ObjectUIs. Should an indication be 
     * used to determine whether an ObjectUI is selected, the 
     * indication must be reset.
     */
    public void clearSelected() {
        for (ObjectUI obj : selected) {
            obj.deselect(); // Assuming ObjectUI has a deselect() method
        }
        selected.clear(); // Clear the selected list
    }
    /**
     * Clears the selected ObjectUIs and removes them from the 
     * arrayList of objects, thus from the screen as well.
     */
    public void sweepSelected(){
        while(selected.size() > 0){
            objects.remove(selected.remove(0));
        }
        displayObjects();
    }
    private void displayObjects(){
        displayObjects(objects);
    }
    /**
     * Repaints the screen with the specified array of ObjectUIs, with each generating a TEKLabel that can be viewed.
     * @param objects the array of ObjectUIs
     */
    public void displayObjects(ArrayList<ObjectUI> objects) {
        this.objects = objects;  //store objects in the field
        removeAll(); // Clear existing information 
        for (ObjectUI obj : objects) {
            add(new TEKLabel(obj)); // Use the HTML-formatted string
        }
        revalidate();
        repaint();
    }

    /**
     * Formats object details as multi-line text and returns the details.
     * @param obj the object to get the details of
     * @return the object's details
     */
    public static String formatObjectDetails(ObjectUI obj) {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("Name: ").append(obj.getName()).append("<br>"); // break after name
        sb.append("Created: ").append(obj.getCreationTime()).append("<br>"); //break after time
        sb.append("Position: (").append(obj.getPosition().x).append(", ").append(obj.getPosition().y).append(")<br>"); //break after position
        sb.append("Size: (").append(obj.getSize().width).append(" x ").append(obj.getSize().height).append(")"); //break after size
        sb.append("</html>");
        return sb.toString(); //object converted to string
    }
}
