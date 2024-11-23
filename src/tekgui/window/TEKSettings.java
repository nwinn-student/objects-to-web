package tekgui.window;
import tekgui.helper.ButtonBuilder;
import tekgui.TEKFile;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

/**
 * Write a description of class TEKSettings here.
 *
 * @author Mara Doze, Zakariya Javed, Noah Winn
 * @version November 18, 2024
 */
public class TEKSettings extends JDialog{
    public TEKSettings(JFrame parent, MainWindow settingsInfo){
        super(parent, "Settings", true);
        JPanel house = new JPanel(new BorderLayout());
        // Sample
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(300,300));
        
        JLabel fontLabel = new JLabel("Select Font Size:");
        String[] fontSizes = {"Small", "Medium", "Large"}; //font size options
        JComboBox<String> fontSizeComboBox = new JComboBox<>(fontSizes);
        fontSizeComboBox.setSelectedItem(settingsInfo.getFontSize());
        
        // window size adjustment
        JLabel widthLabel = new JLabel("Window Width:");
        JTextField widthField = new JTextField(""+TEKFile.getFrame().getWidth(), 10); // Default width
        JLabel heightLabel = new JLabel("Window Height:");
        JTextField heightField = new JTextField(""+TEKFile.getFrame().getHeight(), 10); // Default height

        // components to the container panel
        container.setLayout(new GridLayout(4, 2));
        container.add(fontLabel);
        container.add(fontSizeComboBox);
        container.add(widthLabel);
        container.add(widthField);
        container.add(heightLabel);
        container.add(heightField);
        
        JTabbedPane pane = new JTabbedPane();
        pane.add("Container", container);
        
        // Panel for confirm and cancel buttons
        JPanel exitHouse = new JPanel();
        JButton confirm = ButtonBuilder.addButton("Confirm", exitHouse, "Button to submit changes of settings.", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // user inputs for settings 
                String selectedFontSize = (String) fontSizeComboBox.getSelectedItem();
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());

                // Calling the parent method to update settings
                settingsInfo.updateSettings(selectedFontSize, width, height);                
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
}