package tekgui.helper;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * The creation of Buttons has been streamlined to make further creation viable for readability.
 * The focusHandler needs to be adjusted to go past here
 */
public class ButtonBuilder{
    private static final transient Insets baseInset = new Insets(5, 0, 5, 0);
    private static final transient Border defaultBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(1, Color.WHITE, new Color(155,155,155)), 
            BorderFactory.createEmptyBorder(4, 5, 4, 5));
    private static final transient Border focusBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.RED, 2, true), 
            BorderFactory.createEmptyBorder(4, 5, 4, 5));
    private static final transient FocusListener focuser;
    static {
        focuser = new FocusListener(){
            @Override
            public void focusGained(FocusEvent f){
                try{
                    // May want a bit more feedback later, like a pulsating
                    ((JComponent)f.getComponent()).setBorder(focusBorder);
                } catch(ClassCastException e){}
            }
            @Override
            public void focusLost(FocusEvent f){
                try{
                    ((JComponent)f.getComponent()).setBorder(defaultBorder);
                } catch(ClassCastException e){}
            }
        };
    }
    private static final transient KeyListener keyer;
    static {
        keyer = new KeyListener(){
            /*
             * Choice, we want to doClick() every time we release the 
             * key or every time it has been typed?  There is a massive 
             * difference.  Classic approach for this is to use keyReleased.
             */ 
            
            @Override
            public void keyPressed(KeyEvent k){}
            @Override
            public void keyReleased(KeyEvent k){
                try{
                    if(k.getKeyCode() == k.VK_ENTER){
                        ((AbstractButton)k.getSource()).doClick();
                    }
                }
                catch(ClassCastException e){}
            }
            @Override
            public void keyTyped(KeyEvent k){}
        };
    }
    /**
     * Used to make JButtons easier to create, has a focusListener attached by default.
     * 
     * @param name, the name of the JButton object to be created
     * @param parent, the JToolBar to add the JButton object to
     * @param description, for screen readers to provide extra information
     * @param action, the actionListener that handles the button activations
     */
    public static JButton addButton(String name, JComponent parent, String description, ActionListener action){
        JButton button = new JButton();
        if(action != null){
            button.addActionListener(action);
        }
        button.setFocusable(true);
        button.setFocusPainted(false);
        button.addFocusListener(focuser); // custom focusPainted border
        button.addKeyListener(keyer); // custom "Press enter on button to enact"
        button.setActionCommand(name);
        button.setText(name);
        button.getAccessibleContext().setAccessibleName(name);
        button.getAccessibleContext().setAccessibleDescription(description);
        button.setToolTipText(name);
        if(parent != null){
            parent.add(button);
        }
        return button;
    }
    public static JButton addButton(String name, JComponent parent, ActionListener action){
        return addButton(name, parent, null, action);
    }
    public static JButton addButton(String name, JComponent parent, String description){
        return addButton(name, parent, description, null);
    }
    public static JButton addButton(String name, JComponent parent){
        return addButton(name, parent, null, null);
    }
    /**
     * Leaves the border alone, but reduces the size and makes it as thin as possible.
     * 
     * @param button the button to adjust
     * @param removeListener will remove all of the button related listeners attached, which will remove alot of 
     *      button functionality.
     */
    public static void skinButton(AbstractButton button){
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(baseInset);
        button.setSize(fontWidth(button), 25);
        button.setFocusPainted(true);
        button.removeFocusListener(focuser); // will add flesh to it
    }
    /**
     * Will add a mouse and motion listener that allow the button to be moved 
     * individually.
     */
    public static void draggableButton(AbstractButton button){
        //Draggable drag = new Draggable();
        //button.addMouseListener(drag);
        //button.addMouseMotionListener(drag);
    }
    /**
     * Not the best, but again, it adds the concept.
     */
    public static void resizeableButton(AbstractButton button){
        //Resizeable resize = new Resizeable();
        //button.addMouseListener(resize);
        //button.addMouseMotionListener(resize);
    }
    public static int fontWidth(AbstractButton comp){
        return comp.getFontMetrics(comp.getFont()).stringWidth(comp.getText()) + 7;
    }
}
