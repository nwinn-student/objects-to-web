import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * The TEKAdapter for receiving action events.  Is used within a 
 * component's <b>addActionListener</b> method, and when the action 
 * event occurs, the actionPerformed method is invoked.
 *
 * @see java.awt.event.ActionEvent
 * @author Zakariya Javed
 * @version 9/30/2024
 */
public class TEKActionAdapter implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
        switch(e.getActionCommand()){
            case "Open":
                TEKFile.openFile();
                return;
            case "Save":
                TEKFile.saveFile();
                return;
            // note that holding down this key [Insert] WILL break the app, find a fix later
            case "Create": 
                TEKManagement.createObject();
                return;
            case "Delete":
                TEKManagement.removeObject();
                return;
            case "Delete All":
                TEKManagement.removeAllObject();
                return;
            // add more..
        }
        // another switch for toolBar if that is desired
    }
}
