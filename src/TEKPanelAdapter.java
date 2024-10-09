import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Write a description of class TEKPanelAdapter here.
 *
 * @author Zakariya Javed, Noah Winn
 * @version Oct. 2, 2024
 */
public class TEKPanelAdapter implements MouseInputListener, MouseWheelListener, KeyListener{
    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    @Override
    public void mouseExited(MouseEvent e){
        
    }
    @Override
    public void mousePressed(MouseEvent e){
        
    }
    @Override
    public void mouseReleased(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON3){
            TEKFile.getFrame().getPopupMenu().activate(e);
            return;
        }
        TEKFile.getFrame().getPopupMenu().deactivate();
        if(e.getButton() == MouseEvent.BUTTON1){
            Object source = e.getSource();
            if (source instanceof TEKPanel){
                ((TEKPanel) source).clearSelected();
            }
        }
    }
    @Override
    public void mouseMoved(MouseEvent e){}
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            double factor = e.getPreciseWheelRotation() < 0 ? TEKPanel.ZOOM_FACTOR : 1/TEKPanel.ZOOM_FACTOR;
            TEKFile.getFrame().getPanel().zoom(factor, e.getPoint());
        }
    }
    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e){
        if (e.isControlDown()) {
            if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
                TEKFile.getFrame().getPanel().zoom(TEKPanel.ZOOM_FACTOR, TEKPanel.lastMousePosition);
            } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                TEKFile.getFrame().getPanel().zoom(1 / TEKPanel.ZOOM_FACTOR, TEKPanel.lastMousePosition);
            }
        }
    }
    public void keyTyped(KeyEvent e){}
}
