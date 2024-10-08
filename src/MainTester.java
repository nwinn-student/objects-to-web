import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * A hub for the tester classes to be tested using a safer environment that ensures the application 
 * cannot harm any developers or users by adding emergency exits.
 *
 * @author Noah Winn
 * @version Oct 8, 2024
 */
public class MainTester{
    public static void main(String[] args){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        
        Helper help = new Helper();
        help.displayOptions();
        TestFrame.createTestFrame();
        // need to adjust to allow for more customizability, like selecting a segment to run
        FrameFunctionalityTest frameFunc = new FrameFunctionalityTest();
        frameFunc.run(); 
        
    }
}
