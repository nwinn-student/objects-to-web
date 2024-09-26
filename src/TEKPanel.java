import javax.swing.JPanel;
import java.awt.Color;

/**
 * The panel that will house the Objects.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKPanel extends JPanel{
    private TEKFrame frame = null;
    public TEKPanel(){}
    public TEKPanel(TEKFrame frame){
        super();
        this.frame = frame;
        this.setLayout(null);
        // will be transparent later, just like this for testing
        this.setBackground(new Color(200,255,230)); 
        //this.setOpaque(false);
    }
}
