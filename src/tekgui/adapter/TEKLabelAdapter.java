package tekgui.adapter;

// TEKGUI imports
import tekgui.window.TEKPanel;
import tekgui.window.TEKLabel;
import tekgui.TEKFile;

// Java imports
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JComponent;

/**
 * Houses TEKLabel's listeners, whatever they may be in time, utilizing mouse events primarily.
 *
 * @see java.awt.event.MouseEvent
 * @author Hayden Verstrat, Noah Winn, Coby Zhong
 * @last edited at 10/29/2024
 */
public class TEKLabelAdapter implements MouseListener{
    TEKPanel pan = null;
    /**
     * Selects and deselects the TEKLabel when left-clicked
     * Activates the PopupMenu when right-clicked
     */
    public void mouseClicked(MouseEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        if(e.getButton() == MouseEvent.BUTTON1){
            TEKLabel label = (TEKLabel) e.getSource();
            if(label.isSelected()){
                pan.removeSelected(TEKPanel.getObjectFromLabel(label));
            } else {
                pan.addSelected(TEKPanel.getObjectFromLabel(label));
            }            
        }        
    }
    /**
     * Upon hover, the cursor is adjusted to the drag cursor to signify that an action can be done.
     */
    public void mouseEntered(MouseEvent e){
    /**
     * ((Component)e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
     * Upon exiting the component, the cursor is reset to default.
     */
         Component component = (Component) e.getSource();
         component.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    if (component instanceof JComponent) {
            ((JComponent) component).setBackground(Color.LIGHT_GRAY); 
            ((JComponent) component).setOpaque(true); 
        }
    }
    public void mouseExited(MouseEvent e){
    /**
     * ((Component)e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     */

        Component component = (Component) e.getSource();
        component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
        if (component instanceof JComponent) {
            ((JComponent) component).setBackground(null); 
            ((JComponent) component).setOpaque(false);
        }
    }
    
    public void mousePressed(MouseEvent e){
        
    }
    public void mouseReleased(MouseEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        if(e.getButton() == MouseEvent.BUTTON3){
            // display popupMenu
            TEKFile.getFrame().getPopupMenu().activate(e);
        }
    }
}
