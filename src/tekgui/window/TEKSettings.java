package tekgui.window;
import tekgui.helper.ButtonBuilder;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 * Write a description of class TEKSettings here.
 *
 * @author Mara Doze, Zakariya Javed, Noah Winn
 * @version November 11, 2024
 */
public class TEKSettings extends JDialog{
    public TEKSettings(JFrame parent){
        super(parent, "Settings", true);
        JPanel house = new JPanel(new BorderLayout());
        // Sample
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(300,300));
        
            // Labels and Buttons can go here
        
        JTabbedPane pane = new JTabbedPane();
        pane.add("Container", container);
        
        JPanel exitHouse = new JPanel();
        JButton confirm = ButtonBuilder.addButton("Confirm", exitHouse, "Button to submit changes of settings.", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // parent.updateSettings(...) // send changes to parent
                dispose();
            }
        });

        JButton cancel = ButtonBuilder.addButton("Cancel", exitHouse, "Button to cancel changes to settings.", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        house.add(pane, BorderLayout.CENTER);
        house.add(exitHouse, BorderLayout.PAGE_END);
        add(house, BorderLayout.CENTER);
        
        // Set dialog properties
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    /**
     * Sample to quickly test
     */
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setVisible(true);
        new TEKSettings(frame);
    }
}
