import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import javax.swing.SwingUtilities;
/**
 * Houses TEKLabel's listeners, whatever they may be in time, 
 * utilizing mouse events primarily.
 *
 * @see java.awt.event.MouseEvent
 * @author Hayden Verstrat, Noah Winn
 * @version Oct. 2, 2024
 */
public class TEKLabelAdapter implements MouseListener{
    /**
     * Selects and deselects the TEKLabel
     */
    public void mouseClicked(MouseEvent e){
        //right-click opens the Edit View
        if (SwingUtilities.isRightMouseButton(e)) {
            TEKLabel label = (TEKLabel) e.getSource();
            ObjectUI obj = label.getObjectFromText(); // Retrieve object from the label
            if (obj != null) {
                new TEKEditView(obj);  // Open Edit View for the object
            }
        }
        // Hayden here
        
    }
    /**
     * Upon hover, the cursor is adjusted to the drag cursor to signify that an action can be done.
     */
    public void mouseEntered(MouseEvent e){
        ((Component)e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    /**
     * Upon exiting the component, the cursor is reset to default.
     */
    public void mouseExited(MouseEvent e){
        ((Component)e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void mousePressed(MouseEvent e){
        
    }
    public void mouseReleased(MouseEvent e){
        
    }
}
