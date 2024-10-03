import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Cursor;
/**
 * Houses TEKLabel's listeners, whatever they may be in time, 
 * utilizing mouse events primarily.
 *
 * @see java.awt.event.MouseEvent
 * @author Hayden Verstrat, Noah Winn
 * @version Sept. 30, 2024
 */
public class TEKLabelAdapter implements MouseListener{
/**
     * Selects and deselects the TEKLabel when clicked
     */
    public void mouseClicked(MouseEvent e) {
        TEKLabel label = (TEKLabel) e.getSource(); // Cast to TEKLabel
        if (label.isSelected) {
            label.deselect(); // If currently selected, deselect it
        } else {
            label.select(); // Otherwise, select it
        }
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