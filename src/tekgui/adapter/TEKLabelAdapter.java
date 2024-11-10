package tekgui.adapter;

// TEKGUI imports
import tekgui.window.TEKPanel;
import tekgui.window.TEKLabel;
import tekgui.TEKFile;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusListener;
import javax.swing.JRootPane;

// Java imports
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.MouseMotionListener;
import java.awt.Point;

/**
 * Houses TEKLabel's listeners, whatever they may be in time, utilizing mouse events primarily.
 *
 * @see java.awt.event.MouseEvent
 * @author Hayden Verstrat, Noah Winn, Coby Zhong
 * @version Nov. 9, 2024
 */
public class TEKLabelAdapter implements MouseListener, KeyListener, FocusListener, MouseMotionListener{
    TEKPanel pan = null;
    private static final transient Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private static final transient Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private boolean focusDebounce = false;
    private Point dragStart = null; // Track where drag started
    {
        pan = TEKFile.getFrame().getPanel();
    }
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
        ((JComponent)e.getSource()).grabFocus();
    }
    /**
     * Upon hover, the cursor is adjusted to the drag cursor to signify that an action can be done.
     */
    public void mouseEntered(MouseEvent e){
        try{
            //((JComponent)e.getComponent()).grabFocus(); 
            e.getComponent().setCursor(handCursor);
            e.getComponent().setBackground(TEKLabel.getHighlightColor()); 
        } catch(ClassCastException k){}
    }
    /**
     * Upon exiting the component, the cursor is reset to default.
     */
    public void mouseExited(MouseEvent e){
        try{
            e.getComponent().setCursor(defaultCursor);
            e.getComponent().setBackground(TEKLabel.getDefaultColor()); 
            //((JComponent)e.getComponent().getParent()).grabFocus();
        } catch(ClassCastException k){}
    }
    public void mousePressed(MouseEvent e){
        if (e.getButton() == MouseEvent.BUTTON1) {  // Left click only
            dragStart = e.getPoint();
            ((Component)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }
    public void mouseReleased(MouseEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        if(e.getButton() == MouseEvent.BUTTON3){
            // display popupMenu
            TEKFile.getFrame().getPopupMenu().activate(e);
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            dragStart = null;
            ((Component)e.getSource()).setCursor(handCursor);
        }
    }
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        try{
            if(e.getKeyCode() == e.VK_ENTER && !focusDebounce){
                if(!TEKFile.getFrame().getPopupMenu().isVisible()){
                    TEKFile.getFrame().getPopupMenu().activate(e);
                }
                return;
            }
            focusDebounce = false;
        }
        catch(ClassCastException k){}
    }
    public void keyTyped(KeyEvent e){}
    public void focusGained(FocusEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        try{
            TEKLabel comp = (TEKLabel)e.getComponent();
            if(e.getOppositeComponent() != null){
                if(e.getOppositeComponent().getClass() == JRootPane.class){
                    focusDebounce = true;
                }
            }
            pan.addSelected(TEKPanel.getObjectFromLabel(comp));
        } catch(ClassCastException k){}
    }
    public void focusLost(FocusEvent e){
        if(e.getOppositeComponent() == null){
            return;
        }
        if(e.getOppositeComponent().getClass() == JRootPane.class){
            return;
        }
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        try{
            TEKLabel comp = (TEKLabel)e.getComponent();
            pan.removeSelected(TEKPanel.getObjectFromLabel(comp));
        } catch(ClassCastException k){}
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart != null) {
            Component comp = e.getComponent();
            Point parentLocation = comp.getParent().getLocationOnScreen();
            Point mouseLocation = e.getLocationOnScreen();
            
            // Calculate new position relative to parent container
            int newX = mouseLocation.x - parentLocation.x - dragStart.x;
            int newY = mouseLocation.y - parentLocation.y - dragStart.y;
            
            // prevents dragging outside window
            newX = Math.max(0, Math.min(newX, comp.getParent().getWidth() - comp.getWidth()));
            newY = Math.max(0, Math.min(newY, comp.getParent().getHeight() - comp.getHeight()));
            
            comp.setLocation(newX, newY);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    
    }
}