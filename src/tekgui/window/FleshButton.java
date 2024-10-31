package tekgui.window;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.AbstractButton;

/**
 * A skin tight button that is the minimal size.
 *
 * @author Noah Winn
 * @version Oct. 30, 2024
 */
public class FleshButton{
    private static final transient Insets baseInset = new Insets(5, 0, 5, 0);
    public static JButton generateButton(){
        JButton button = new JButton();
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(baseInset);
        button.setSize(fontWidth(button),25);
        return button;
    }
    public static int fontWidth(AbstractButton comp){
        return comp.getFontMetrics(comp.getFont()).stringWidth(comp.getText()) + 7;
    }
}
