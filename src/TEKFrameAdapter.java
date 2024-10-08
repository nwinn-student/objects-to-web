import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JOptionPane;
import java.awt.event.WindowListener;

/**
 * Used to handle popupMenu and saving when exiting.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKFrameAdapter implements WindowListener{
    public TEKFrameAdapter(){}
    @Override
    public void windowActivated(WindowEvent e){
        
    }
    @Override
    public void windowDeactivated(WindowEvent e){
        // For PopupMenu Later
        TEKFile.getFrame().getPopupMenu().deactivate();
    }
    @Override
    public void windowIconified(WindowEvent e){
        // For PopupMenu Later
        TEKFile.getFrame().getPopupMenu().deactivate();
    }
    @Override
    public void windowDeiconified(WindowEvent e){
        
    }
    @Override
    public void windowClosing(WindowEvent e){
        if(TEKFile.getFrame() == null || TEKFile.getFrame().hasSaved()){return;}
        // Add support later for if a change has occurred to ask if they want to save, until then, this does nothing
        int optionChosen = JOptionPane.showConfirmDialog(TEKFile.getFrame(), 
            "Do you wish to save your changes?",
            "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (optionChosen == JOptionPane.YES_OPTION) {
            // save here
            TEKFile.getFrame().save();
            TEKFile.getFrame().setDefaultCloseOperation(TEKFile.getFrame().EXIT_ON_CLOSE);
        } else if (optionChosen == JOptionPane.NO_OPTION) {
            TEKFile.getFrame().setDefaultCloseOperation(TEKFile.getFrame().EXIT_ON_CLOSE);
        } else {
            TEKFile.getFrame().setDefaultCloseOperation(TEKFile.getFrame().DO_NOTHING_ON_CLOSE);
        }
    }
    @Override
    public void windowOpened(WindowEvent e){
        
    }
    @Override
    public void windowClosed(WindowEvent e){
        
    }
}
