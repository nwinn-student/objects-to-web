import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JOptionPane;

/**
 * Used to handle popupMenu and saving when exiting.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKWindowAdapter extends WindowAdapter{
    private TEKFrame frame = null;
    public TEKWindowAdapter(){}
    public TEKWindowAdapter(TEKFrame frame){this.frame = frame;}
    @Override
    public void windowDeactivated(WindowEvent e){
        // For PopupMenu Later
    }
    @Override
    public void windowIconified(WindowEvent e){
        // For PopupMenu Later
    }
    @Override
    public void windowClosing(WindowEvent e){
        if(frame == null || !frame.hasSaved()){return;}
        // Add support later for if a change has occurred to ask if they want to save, until then, this does nothing
        int optionChosen = JOptionPane.showConfirmDialog(frame, 
            "Do you wish to save your changes?",
            "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (optionChosen == JOptionPane.YES_OPTION) {
            // save here
            frame.save();
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        } else if (optionChosen == JOptionPane.NO_OPTION) {
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        } else {
            frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
        }
    }
}
