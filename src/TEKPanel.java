import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * The panel that will house the Objects.
 *
 * @author Mara Doze, Hayden Verstrat, Noah Winn, Coby Zhong, Zakariya Javed (temp until I make zoom file)
 * @version Sept. 30, 2024
 */
public class TEKPanel extends JPanel {
    private TEKFrame frame = null;
    private ArrayList<ObjectUI> objects = new ArrayList<>();
    private ArrayList<ObjectUI> selected = new ArrayList<>();
    // adds zoomfactor and mouse position to jpanel
    private double zoomFactor = 1.1;
    private Point lastMousePosition = new Point(0, 0);
    private static final double ZOOM_FACTOR = 1.1;

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

// functionality for keypresses Ctrl+ and ctrl-  this needs work, ctrl scroll up and down is "fine" though. 
/*         addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
                        zoom(ZOOM_FACTOR, lastMousePosition);
                    } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                        zoom(1 / ZOOM_FACTOR, lastMousePosition);
                        
                    }
                }
            }
        }); */
        
        // addition of scroll wheel functionality for zooming purposes. 
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    double factor = e.getPreciseWheelRotation() < 0 ? ZOOM_FACTOR : 1 / ZOOM_FACTOR;
                    zoom(factor, e.getPoint());
                }
            }
        });
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
     * Removes the specified selected ObjectUI.  Should an indication 
     * be used to determine whether an ObjectUI is selected, the 
     * indication must be reset.
     * 
     * @param obj the ObjectUI to be removed
     * @return whether the ObjectUI was within the list of selected ObjectUIs
     */
    public boolean removeSelected(ObjectUI obj){
        if(obj == null){return false;}
        // Hayden here
        return selected.remove(obj);
    }
    /**
     * Clears the selected ObjectUIs.  Should an indication be 
     * used to determine whether an ObjectUI is selected, the 
     * indication must be reset.
     */
    public void clearSelected(){
        // Hayden here
        selected.clear();
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
    // more zoom functionality
    private void zoom(double factor, Point zoomCenter) {
        double initialZoom = zoomFactor;
        zoomFactor *= factor;
        zoomFactor = Math.max(0.5, Math.min(zoomFactor, 5.0)); // Limit zoom between 0.5x and 5xq

        double actualFactor = zoomFactor / initialZoom;

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
                
                // Update font size based on zoom factor
                float newFontSize = (float) (12 * zoomFactor); // Assuming 12 is the base font size, not sure.
                label.setFont(label.getFont().deriveFont(newFontSize));
            }
        }

        revalidate();
        repaint();
    }

    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(zoomFactor, zoomFactor);
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
