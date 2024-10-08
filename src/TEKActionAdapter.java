import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.event.KeyEvent;
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
        TEKFrame frame = TEKFile.getFrame();
        if(frame == null){return;}
        TEKPanel panel = frame.getPanel();
        if(panel == null){return;}
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
            case "Select All":
                TEKManagement.selectAll();
                return;
            case "Delete":
                TEKManagement.removeObject();
                return;
            case "Delete All":
                TEKManagement.removeAllObject();
                return;
            case "Edit":
                TEKManagement.editView();
                break;
            case "Zoom In":
                panel.zoomIn();
                return;
            case "Zoom Out":
                panel.zoomOut();
                return;
            case "Exit":
                TEKFile.getFrame().save();
                System.out.println("Exiting through automated ALT-F4.");
                Helper.keyMaskedClick(KeyEvent.VK_F4, KeyEvent.VK_ALT);
                break;
            // add more..
        }
        // another switch for toolBar if that is desired
    }
}
