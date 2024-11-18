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
import javax.swing.*;
import java.awt.*;

/**
 * Write a description of class TEKSettings here.
 *
 * @author Mara Doze, Zakariya Javed, Noah Winn
 * @version November 18, 2024
 */
public class TEKSettings extends JDialog{
    public TEKSettings(JFrame parent){
        super(parent, "Settings", true);
        JPanel house = new JPanel(new BorderLayout());
        // Sample
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(300,300));
        
        // settings options in the container panel
        JLabel themeLabel = new JLabel("Select Theme:");
        String[] themes = {"Light", "Dark"}; //theme options
        JComboBox<String> themeComboBox = new JComboBox<>(themes);
        
        JLabel fontLabel = new JLabel("Select Font Size:");
        String[] fontSizes = {"Small", "Medium", "Large"}; //font size options
        JComboBox<String> fontSizeComboBox = new JComboBox<>(fontSizes);

        // the components for the window size adjustment
        JLabel widthLabel = new JLabel("Window Width:");
        JTextField widthField = new JTextField("500", 10); // Default width
        JLabel heightLabel = new JLabel("Window Height:");
        JTextField heightField = new JTextField("300", 10); // Default height

        // components to the container panel
        container.setLayout(new GridLayout(4, 2));
        container.add(themeLabel);
        container.add(themeComboBox);
        container.add(fontLabel);
        container.add(fontSizeComboBox);
        container.add(widthLabel);
        container.add(widthField);
        container.add(heightLabel);
        container.add(heightField);
        
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
                // Save settings 
                String selectedTheme = (String) themeComboBox.getSelectedItem();
                String selectedFontSize = (String) fontSizeComboBox.getSelectedItem();
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());

                // Calling the parent method to update settings
                ((MainWindow) parent).updateSettings(selectedTheme, selectedFontSize, width, height);
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
