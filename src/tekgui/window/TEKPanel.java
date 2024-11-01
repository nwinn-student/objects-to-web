package tekgui.window;

// TEKGUI imports
import tekgui.ObjectUI;
import tekgui.adapter.TEKPanelAdapter;

// Java imports
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
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collection;


/**
 * The panel that will house the Objects.
 *
 * @author Mara Doze, Zakariya Javed, Hayden Verstrat, Noah Winn, Coby Zhong
 * @version Oct 31, 2024
 */
public class TEKPanel extends JPanel{
    private TEKFrame frame = null;
    private static HashMap<ObjectUI, TEKLabel> labels = new HashMap<>();
    private ArrayList<ObjectUI> selected = new ArrayList<>();
    // adds zoomfactor and mouse position to jpanel
    private double zoomFactor = 1;
    public static Point lastMousePosition = new Point(0, 0);
    public static final double ZOOM_FACTOR = 1.1;
    private Rectangle selectionRect = new Rectangle(); // Rectangle for the selection box
    private Point startPoint = null; // Starting point for the selection box

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
        TEKPanelAdapter sample = new TEKPanelAdapter();

    // Adding mouse listeners for selection
    addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            selectionRect.setBounds(startPoint.x, startPoint.y, 0, 0);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Finalize the selection rectangle
            selectionRect.setBounds(
                Math.min(startPoint.x, e.getX()),
                Math.min(startPoint.y, e.getY()),
                Math.abs(startPoint.x - e.getX()),
                Math.abs(startPoint.y - e.getY())
            );

            // Select objects within the rectangle
            selectObjectsInRectangle(selectionRect);

            // Reset selection
            startPoint = null;
            selectionRect.setBounds(0, 0, 0, 0);
            repaint();
        }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            // Update selection rectangle as the mouse drags
            selectionRect.setBounds(
                Math.min(startPoint.x, e.getX()),
                Math.min(startPoint.y, e.getY()),
                Math.abs(startPoint.x - e.getX()),
                Math.abs(startPoint.y - e.getY())
            );
            repaint();
        }
    });
        addMouseWheelListener(sample);
        addKeyListener(sample);
    }
    private void selectObjectsInRectangle(Rectangle rect) {
        clearSelected(); // Clear previous selections
        for (ObjectUI obj : getObjects()) {
            Rectangle objBounds = new Rectangle(obj.getPosition(), obj.getSize());
            if (rect.intersects(objBounds)) {
                addSelected(obj); // Add to selected list
            }
        }
    }
    private void alterData(){
        if(frame.hasSaved()){frame.setSaved(false);}
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
    private Point shiftPosition(Point initialPosition, Dimension size) {
        int shiftX = 10;
        int shiftY = 10;
        Point newPosition = new Point(initialPosition);
        boolean overlapDetected;
        do {
            overlapDetected = false;
            Rectangle newBounds = new Rectangle(newPosition, size);

            for (ObjectUI existingObj : getObjects()) {
                Rectangle existingBounds = new Rectangle(existingObj.getPosition(), existingObj.getSize());

                if (newBounds.intersects(existingBounds)) {
                    overlapDetected = true;
                    newPosition.translate(shiftX, shiftY); 
                    newBounds.setLocation(newPosition);
                    break;
                }
            }
        } while (overlapDetected);
        return newPosition;
    }
    public ObjectUI generateObject() {
        Point initialPosition = new Point(0, 0);
        Dimension size = new Dimension(100, 100);
        ObjectUI newObject = new ObjectUI("New Object", initialPosition, size);
        Point finalPosition = shiftPosition(initialPosition, size);
        newObject.setPosition(finalPosition);
        addObject(newObject); 
        return newObject;
    }
    /**
     * Adds an ObjectUI to the arrayList of objects to be displayed.
     * @param obj the object added
     */
    public void addObject(ObjectUI obj){
        if(obj == null){return;}
        labels.put(obj, obj.getLabel());
        add(obj.getLabel());
        repaint();
        // maybe alterData() here, need to confirm
    }
    public void addObject(List<ObjectUI> obj){
        if(obj == null){return;}
        for(ObjectUI o : obj){
            if(o == null){continue;}
            labels.put(o, o.getLabel());
            add(o.getLabel());
        }
        repaint();
        // maybe alterData() here, need to confirm
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
        alterData();
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
        alterData();
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
        alterData();
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
    /**
     * Displays objects with similar content to the selected object.
     * @param selectedObject the selected ObjectUI to find similar objects
     */
    public void displaySimilarContent(ObjectUI selectedObject) {
        Collection<ObjectUI> similarObjects = selectedObject.getSimilarContent();
        for (ObjectUI similarObj : similarObjects) {
            TEKLabel similarLabel = getLabel(similarObj);
            if (similarLabel != null) {
                similarLabel.setBorder(BorderFactory.createDashedBorder(Color.BLUE));
                similarLabel.setForeground(Color.BLUE);
            }
        }
    }
    // more zoom functionality
    public void zoom(double factor, Point zoomCenter) {
        double initialZoom = zoomFactor;
        zoomFactor *= factor;
        zoomFactor = Math.max(0.5, Math.min(zoomFactor, 5.0)); // Limit zoom between 0.5x and 5xq

        double actualFactor =  zoomFactor / initialZoom;

        for (int i = 0; i < getComponentCount(); i++) {
            // handles objects found in TEKLabel and resizes them accordingly... almost.
            if (getComponent(i) instanceof TEKLabel) {
                TEKLabel label = (TEKLabel) getComponent(i);
                Point oldPosition = label.getLocation();
                Dimension oldSize = label.getSize();

                int newX = (int) ((oldPosition.x - zoomCenter.x) * actualFactor + zoomCenter.x);
                int newY = (int) ((oldPosition.y - zoomCenter.y) * actualFactor + zoomCenter.y);
                int newWidth = (int) (oldSize.width * actualFactor);
                int newHeight = (int) (oldSize.height * actualFactor);                
                label.setBounds(newX, newY, newWidth, newHeight);
                System.out.println(actualFactor +""+ label.getBounds());
                // Update font size based on zoom factor
                float newFontSize = (float) (12 * zoomFactor); // Assuming 12 is the base font size, not sure.
                label.setFont(label.getFont().deriveFont(newFontSize));
            }
        }

        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(zoomFactor, zoomFactor);
    
        // Draw the selection rectangle
        if (startPoint != null) {
            g2d.setColor(new Color(0, 0, 255, 50)); // Semi-transparent blue
            g2d.fillRect(selectionRect.x, selectionRect.y, selectionRect.width, selectionRect.height);
            g2d.setColor(Color.BLUE);
            g2d.drawRect(selectionRect.x, selectionRect.y, selectionRect.width, selectionRect.height); // Optional: draw border
        }
    
        g2d.dispose();
    }
    public void zoomIn() {
        setZoomFactor(zoomFactor);
    }

    public void zoomOut() {
        setZoomFactor(zoomFactor);
    }

    private void setZoomFactor(double newZoomFactor) {
        newZoomFactor = Math.max(0.5, Math.min(newZoomFactor, 5.0));
        if (newZoomFactor != zoomFactor) {
            zoomFactor = newZoomFactor;
            revalidate();
            repaint();
        }
    }

    // when adding new components, adjusts dimensions based on current zoom 
    public Dimension getPreferredSize() {
        Dimension unzoomed = super.getPreferredSize();
        return new Dimension(
            (int) (unzoomed.width * zoomFactor),
            (int) (unzoomed.height * zoomFactor)
        );
    }
}
