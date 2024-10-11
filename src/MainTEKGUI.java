import tekgui.window.TEKFrame;
import javax.swing.SwingUtilities;

/**
 * Main class for the TEKGUI application.
 *
 * @author Noah Winn
 * @version Oct. 11, 2024
 */
public class MainTEKGUI{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                TEKFrame fram = new TEKFrame();
            }
        });
    }
}
