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

/**
 * Houses TEKLabel's listeners, whatever they may be in time, utilizing mouse events primarily.
 *
 * @see java.awt.event.MouseEvent
 * @author Hayden Verstrat, Noah Winn, Coby Zhong
 * @version Nov. 1, 2024
 */
public class TEKLabelAdapter implements MouseListener, KeyListener, FocusListener{
    TEKPanel pan = null;
    private static final transient Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private static final transient Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private boolean focusDebounce = false;    
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
        try{
            ((JComponent)e.getComponent()).grabFocus(); 
        } catch(ClassCastException k){}
    }
    /**
     * Upon exiting the component, the cursor is reset to default.
     */
    public void mouseExited(MouseEvent e){
        try{
            ((JComponent)e.getComponent().getParent()).grabFocus();
        } catch(ClassCastException k){}
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        if(e.getButton() == MouseEvent.BUTTON3){
            // display popupMenu
            TEKFile.getFrame().getPopupMenu().activate(e);
        }
    }
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        if(pan == null){pan = TEKFile.getFrame().getPanel();}
        try{
            if(e.getKeyCode() == e.VK_ENTER && !focusDebounce){
                TEKLabel label = (TEKLabel) e.getSource();
                if(label.isSelected() && !TEKFile.getFrame().getPopupMenu().isVisible()){
                    TEKFile.getFrame().getPopupMenu().activate(e);
                } else {
                    pan.addSelected(TEKPanel.getObjectFromLabel(label));
                }
                return;
            }
            focusDebounce = false;
        }
        catch(ClassCastException k){}
    }
    public void keyTyped(KeyEvent e){}
    public void focusGained(FocusEvent e){
        try{
            JComponent comp = (JComponent)e.getComponent();
            if(e.getOppositeComponent() != null){
                if(e.getOppositeComponent().getClass() == JRootPane.class){
                    focusDebounce = true;
                }
            }
            comp.setCursor(handCursor);
            comp.setBackground(TEKLabel.getHighlightColor()); 
        } catch(ClassCastException k){}
    }
    public void focusLost(FocusEvent e){
        try{
            JComponent comp = (JComponent)e.getComponent();
            comp.setCursor(defaultCursor);
            comp.setBackground(TEKLabel.getDefaultColor());            
        } catch(ClassCastException k){}
    }
}