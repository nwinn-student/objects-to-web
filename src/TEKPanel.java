import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.awt.Dimension;
import java.util.stream.Collectors;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
/**
 * The panel that will house the Objects.
 *
 * @author Mara Doze, Hayden Verstrat, Noah Winn, Coby Zhong
 * @version Sept. 30, 2024
 */
public class TEKPanel extends JPanel{
    private TEKFrame frame = null;
    private static HashMap<ObjectUI, TEKLabel> labels = new HashMap<>();
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
        setLayout(null);
        // will be transparent later, just like this for testing
        setBackground(new Color(200,255,230)); 
        //this.setOpaque(false);
        addMouseListener(new TEKPanelAdapter());
        addMouseMotionListener(new TEKPanelAdapter());
    }
    /**
     * Returns the attached label to the input objectUI
     * @param obj objectUI
     * @return the TEKLabel attached
     */
    public static TEKLabel getLabel(ObjectUI obj){
        return labels.get(obj);
    }
    private static Map<TEKLabel,ObjectUI> flipLabels(){
        return labels.entrySet()
                     .stream()
                     .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
    /**
     * Returns the attached objectUI to the input label
     * @param label the TEKLabel
     * @return the ObjectUI attached
     */
    public static ObjectUI getObjectFromLabel(TEKLabel label){
        if(label == null){return null;}
        return flipLabels().get(label);
    }
    public static List<ObjectUI> getObjectFromLabel(List<TEKLabel> label){
        if(label == null){return null;}
        Map<TEKLabel,ObjectUI> flippedMap = flipLabels();
        List<ObjectUI> obj = new ArrayList<>();
        for(TEKLabel lab : label){
            obj.add(flippedMap.get(lab));
        }
        return obj;
    }
    public static List<ObjectUI> getObjectFromLabel(TEKLabel... label){
        if(label == null){return null;}
        return getObjectFromLabel(Arrays.asList(label));
    }
    /**
     * Gets all the ObjectUIs in this TEKPanel.
     * @return arrayList of ObjectUIs
     */
    public List<ObjectUI> getObjects(){
        List<ObjectUI> objList = new ArrayList<ObjectUI>();
        objList.addAll(labels.keySet());
        return objList;
    }
    /**
     * Gets all the TEKLabels in this TEKPanel.
     * @return arrayList of TEKLabels
     */
    public List<TEKLabel> getLabels(){
        List<TEKLabel> labelList = new ArrayList<TEKLabel>();
        labelList.addAll(labels.values());
        return labelList;
    }
    /**
     * Creates a new objectUI and adds it to the arrayList of objects to be displayed.
     */
    public void generateObject() {
    
    Point initialPosition = new Point(50, 50);
    Dimension size = new Dimension(100, 100);

    ObjectUI newObject = new ObjectUI("New Object", initialPosition, size);


    for (ObjectUI obj : objectList) {
        if (boundsOverlap(newObject, obj)) {
            initialPosition = shiftPosition(initialPosition);
            newObject.setPosition(initialPosition);
        }
    }
    addObject(newObject);
}
private boolean boundsOverlap(ObjectUI obj1, ObjectUI obj2) {
    Rectangle bounds1 = new Rectangle(obj1.getPosition(), obj1.getSize());
    Rectangle bounds2 = new Rectangle(obj2.getPosition(), obj2.getSize());
    return bounds1.intersects(bounds2);
}
private Point shiftPosition(Point position) {
    return new Point(position.x + 10, position.y + 10); // Shift by 10 units as an example
}

    /**
     * Adds an ObjectUI to the arrayList of objects to be displayed.
     * @param obj the object added
     */
    public void addObject(ObjectUI obj){
        if(obj == null){return;}
        TEKLabel label = new TEKLabel(obj);
        labels.put(obj, label);
        add(label);
        repaint();
    }
    public void addObject(List<ObjectUI> obj){
        if(obj == null){return;}
        for(ObjectUI o : obj){
            if(o == null){continue;}
            TEKLabel label = new TEKLabel(o);
            labels.put(o, label);
            add(label);
        }
        repaint();
    }
    public void addObject(ObjectUI... obj){
        if(obj == null){return;}
        addObject(Arrays.asList(obj));
    }
    /**
     * Removes an ObjectUI from the arrayList of objects to be displayed.
     * @param obj the object to be removed
     */
    public void removeObject(ObjectUI obj){
        if(obj == null){return;}
        selected.remove(obj);
        try{remove(labels.remove(obj));}
            catch(Exception e){}
        repaint();
    }
    public void removeObject(List<ObjectUI> obj){
        if(obj == null){return;}
        for(ObjectUI o : obj){
            if(o == null){continue;}
            selected.remove(o);
            try{remove(labels.remove(o));}
                catch(Exception e){}
        }
        repaint();
    }
    public void removeObject(ObjectUI... obj){
        if(obj == null){return;}
        removeObject(Arrays.asList(obj));
    }
    /**
     * Clears the arrayList of objects and refreshes the screen.
     */
    public void clearObjects(){
        selected.clear();
        labels.clear();
        removeAll();
        revalidate();
        repaint();
    }
    private void select(TEKLabel label){
        if(label.getForeground().equals(Color.RED)){
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label.setForeground(Color.BLACK);
            return;
        }
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        label.setForeground(Color.RED);
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
        if(labels.containsKey(obj)){
            selected.add(obj);
            labels.get(obj).select();
        }
    }
    public void addSelected(List<ObjectUI> obj){
        if(obj == null){return;}
        for(ObjectUI o : obj){
            addSelected(o);
        }
    }
    public void addSelected(ObjectUI... obj){
        if(obj == null){return;}
        addSelected(Arrays.asList(obj));
    }
    public void selectAll(){
        addSelected( getObjects() );
    }
    /**
     * Removes the specified selected ObjectUI.  Should an indication 
     * be used to determine whether an ObjectUI is selected, the 
     * indication must be reset.
     * 
     * @param obj the ObjectUI to be removed
     * @return whether the ObjectUI was within the list of selected ObjectUIs
     */
    public boolean removeSelected(ObjectUI obj){
        if(obj == null){return false;}
        if(labels.containsKey(obj)){
            labels.get(obj).deselect();
        }
        if(selected.contains(obj)){
            return selected.remove(obj);
        }
        return false;
    }
    /**
     * Clears the selected ObjectUIs.  Should an indication be 
     * used to determine whether an ObjectUI is selected, the 
     * indication must be reset.
     */
    public void clearSelected(){
        while(selected.size() > 0){
            removeSelected(selected.get(0));
        }
    }
    /**
     * Clears the selected ObjectUIs and removes them from the 
     * arrayList of objects, thus from the screen as well.
     */
    public void sweepSelected(){
        while(selected.size() > 0){
            removeObject(selected.remove(0));
        }
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
