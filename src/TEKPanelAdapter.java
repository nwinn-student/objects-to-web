import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 * Write a description of class TEKPanelAdapter here.
 *
 * @author Zakariya Javed, Noah Winn
 * @version Oct. 2, 2024
 */
public class TEKPanelAdapter implements MouseInputListener{
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        
    }
    public void mousePressed(MouseEvent e){
        
    }
    public void mouseReleased(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON3){
            TEKFile.getFrame().getPopupMenu().activate(e);
            return;
        }
        TEKFile.getFrame().getPopupMenu().deactivate();
    }
    public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
}
