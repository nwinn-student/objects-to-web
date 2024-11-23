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
import tekgui.ObjectUI;

/**
 * Houses TEKLabel's listeners, whatever they may be in time, utilizing mouse events primarily.
 *
 * @see java.awt.event.MouseEvent
 * @author Zakariya Javed, Hayden Verstrat, Noah Winn, Coby Zhong
 * @version Nov. 19, 2024
 */
public class TEKLabelAdapter implements MouseListener, KeyListener, FocusListener, MouseMotionListener{
    TEKPanel pan = null;
    private static final transient Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private static final transient Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private boolean focusDebounce = false;
    private Point dragStart = null; // Track where drag started
    private boolean resizing = false; // Track if resizing is active
    private Point resizeStart = null; // Track the starting point for resizing
    private static final int CORNER_SIZE = 15; // Size of the corner area to detect
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
            TEKLabel label = (TEKLabel) e.getComponent();
            e.getComponent().setCursor(handCursor);
            e.getComponent().setBackground(TEKLabel.getHighlightColor());

            // Change cursor to resize if near any corner
            if (isNearCorner(e.getPoint(), label)) {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
            } else {
                e.getComponent().setCursor(handCursor); // Default hand cursor for dragging
            }
        } catch(ClassCastException k){}
    }
    /**
     * Upon exiting the component, the cursor is reset to default.
     */
    public void mouseExited(MouseEvent e){
        try{
            e.getComponent().setCursor(defaultCursor);
            e.getComponent().setBackground(TEKLabel.getDefaultColor());
        } catch(ClassCastException k){}
    }
    public void mousePressed(MouseEvent e){
        if (e.getButton() == MouseEvent.BUTTON1) {  // Left click only
            TEKLabel label = (TEKLabel) e.getSource();
            if (isNearCorner(e.getPoint(), label)) {
                resizing = true;
                resizeStart = e.getPoint();
            } else {
                dragStart = e.getPoint();
                ((Component)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        }
    }
    public void mouseReleased(MouseEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        if(e.getButton() == MouseEvent.BUTTON3){
            // display popupMenu
            TEKLabel label = (TEKLabel) e.getSource();
            pan.clearSelected();
            pan.addSelected(TEKPanel.getObjectFromLabel(label));
            TEKFile.getFrame().getPopupMenu().activate(e);
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            dragStart = null;
            ((Component)e.getSource()).setCursor(handCursor);
        }
        if (resizing) {
            resizing = false; // Stop resizing
            resizeStart = null; // Clear the resize start point
        }
    }
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        try{
            TEKLabel comp = (TEKLabel) e.getComponent();
            pan.addSelected(TEKPanel.getObjectFromLabel(comp));
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
            if(e.getOppositeComponent() != null){
                if(e.getOppositeComponent().getClass() == JRootPane.class){
                    focusDebounce = true;
                }
            }
            e.getComponent().setBackground(TEKLabel.getHighlightColor());
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
            e.getComponent().setBackground(TEKLabel.getDefaultColor());
        } catch(ClassCastException k){}
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (resizing) {
            TEKLabel label = (TEKLabel) e.getSource();
            int deltaX = e.getX() - resizeStart.x;
            int deltaY = e.getY() - resizeStart.y;

            // Determine which corner is being dragged
            if (isNearCorner(resizeStart, label)) {
                if (resizeStart.x <= CORNER_SIZE && resizeStart.y <= CORNER_SIZE) { // Top-left corner
                    label.setSize(Math.max(50, label.getWidth() - deltaX), Math.max(50, label.getHeight() - deltaY));
                    label.setLocation(label.getX() + deltaX, label.getY() + deltaY);
                } else if (resizeStart.x >= label.getWidth() - CORNER_SIZE && resizeStart.y <= CORNER_SIZE) { // Top-right corner
                    label.setSize(Math.max(50, label.getWidth() + deltaX), Math.max(50, label.getHeight() - deltaY));
                    label.setLocation(label.getX(), label.getY() + deltaY);
                } else if (resizeStart.x <= CORNER_SIZE && resizeStart.y >= label.getHeight() - CORNER_SIZE) { // Bottom-left corner
                    label.setSize(Math.max(50, label.getWidth() - deltaX), Math.max(50, label.getHeight() + deltaY));
                    label.setLocation(label.getX() + deltaX, label.getY());
                } else if (resizeStart.x >= label.getWidth() - CORNER_SIZE && resizeStart.y >= label.getHeight() - CORNER_SIZE) { // Bottom-right corner
                    label.setSize(Math.max(50, label.getWidth() + deltaX), Math.max(50, label.getHeight() + deltaY));
                }
            }
            resizeStart = e.getPoint(); // Update the starting point for the next drag
        } else if (dragStart != null && pan != null) {
            Component sourceComp = e.getComponent();
            Point parentLocation = sourceComp.getParent().getLocationOnScreen();
            Point mouseLocation = e.getLocationOnScreen();
            
            int deltaX = mouseLocation.x - parentLocation.x - dragStart.x - sourceComp.getX();
            int deltaY = mouseLocation.y - parentLocation.y - dragStart.y - sourceComp.getY();
            
            // Move all "selected" components simutaneously
            for (ObjectUI obj : pan.getSelected()) {
                Component comp = obj.getLabel();
                int newX = comp.getX() + deltaX;
                int newY = comp.getY() + deltaY;
                    
                // Prevent dragging outside window
                newX = Math.max(0, Math.min(newX, comp.getParent().getWidth() - comp.getWidth()));
                newY = Math.max(0, Math.min(newY, comp.getParent().getHeight() - comp.getHeight()));
                    
                comp.setLocation(newX, newY);
            }
        }
    }
    public void mouseMoved(MouseEvent e){}
 
    //  method to check if the mouse is near any corner of the object and changes cursor to "drag mode"
    private boolean isNearCorner(Point point, TEKLabel label) {
        return (point.x <= CORNER_SIZE && point.y <= CORNER_SIZE) || // Top-left corner
               (point.x >= label.getWidth() - CORNER_SIZE && point.y <= CORNER_SIZE) || // Top-right corner
               (point.x <= CORNER_SIZE && point.y >= label.getHeight() - CORNER_SIZE) || // Bottom-left corner
               (point.x >= label.getWidth() - CORNER_SIZE && point.y >= label.getHeight() - CORNER_SIZE); // Bottom-right corner
    }
}