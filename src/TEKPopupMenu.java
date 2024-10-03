import javax.swing.JPopupMenu;
import javax.swing.JMenuBar;
import javax.swing.JComponent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
/**
 * Write a description of class TEKPopupMenu here.
 *
 * @author Noah Winn
 * @version Oct. 2, 2024
 */
public class TEKPopupMenu extends JPopupMenu{
    private static JComponent attachedComponent;
    public TEKPopupMenu(){
        super();
        TEKMenuBar.addMenuItem("Edit", this, 0, "Pops up the display menu.");
        addSeparator();
        setVisible(false);
    }
    public void activate(MouseEvent e){
        setAttached((JComponent)e.getSource());
        setLocation(e.getLocationOnScreen());
        setVisible(true);
        repaint();
    }
    public void deactivate(){
        setAttached(null);
        setVisible(false);
    }
    public JComponent getAttached(){return attachedComponent;}
    public void setAttached(JComponent attachedComponent){
        this.attachedComponent = attachedComponent;
    }
    /**
     * Activates TEKEditFrame and imports the held attachment.  
     * Hides the TEKPopupMenu.
     */
    public void transferAttached(){
        if(attachedComponent == null){return;}
        if(!(attachedComponent instanceof TEKLabel)){return;}
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new TEKEditView(TEKPanel.getObjectFromLabel((TEKLabel)attachedComponent));
                deactivate();
            }
        });
        
    }
}
