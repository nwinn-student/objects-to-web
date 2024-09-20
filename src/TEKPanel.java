import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The panel that will house the Objects.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKPanel extends JPanel{
    private TEKFrame frame = null;
    public TEKPanel(){}
    public TEKPanel(TEKFrame frame){
        super();
        this.frame = frame;
        this.setLayout(null);
        // will be transparent later, just like this for testing
        this.setBackground(new Color(200,255,230)); 
        //this.setOpaque(false);
    }
    // the method to display objects in the panel
    public void displayObjects(ArrayList<ObjectUI> objects) {
        removeAll(); // Clear existing information 

        for (ObjectUI obj : objects) { // For each ObjectUI in the list, A new JLabel is created and formatted
            JLabel label = new JLabel(formatObjectDetails(obj)); // Use the HTML-formatted string
            label.setBounds(obj.getPosition().x, obj.getPosition().y, obj.getSize().width, obj.getSize().height);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adding a border

            label.setVerticalAlignment(JLabel.TOP);  // Align text to top
            label.setHorizontalAlignment(JLabel.LEFT); // Align text to left

            add(label);
        }

        revalidate(); //revalidates layout
        repaint(); //refreshes visual panel
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
