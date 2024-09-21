import javax.swing.JPanel;
import java.awt.Color;

/**
 * The panel that will house the Objects.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKPanel extends JPanel{
    private TEKFrame frame = null;
    private ArrayList<ObjectUI> objects = new ArrayList<>();  
    public TEKPanel(){}
    public TEKPanel(TEKFrame frame){
        super();
        this.frame = frame;
        this.setLayout(null);
        // will be transparent later, just like this for testing
        this.setBackground(new Color(200,255,230)); 
        //this.setOpaque(false);
    }
    public void displayObjects(ArrayList<ObjectUI> objects) {
        this.objects = objects;  //store objects in the field
        removeAll(); // Clear existing information 

        for (ObjectUI obj : objects) {
            JLabel label = new JLabel(formatObjectDetails(obj)); // Use the HTML-formatted string
            label.setBounds(obj.getPosition().x, obj.getPosition().y, obj.getSize().width, obj.getSize().height);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adding a border

            // Ensure the label is set to automatically resize vertically based on content
            label.setVerticalAlignment(JLabel.TOP);  // Align text to top
            label.setHorizontalAlignment(JLabel.LEFT); // Align text to left

            add(label);
        }

        revalidate();
        repaint();
    }

    // method to format object details as multi-line text
    private String formatObjectDetails(ObjectUI obj) {
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("Name: ").append(obj.getName()).append("<br>"); // break after name
        sb.append("Created: ").append(obj.getCreationTime()).append("<br>"); //break after time
        sb.append("Position: (").append(obj.getPosition().x).append(", ").append(obj.getPosition().y).append(")<br>"); //break after position
        sb.append("Size: (").append(obj.getSize().width).append(" x ").append(obj.getSize().height).append(")"); //break after size
        sb.append("</html>");
        return sb.toString(); //object converted to string
    }
}
}
