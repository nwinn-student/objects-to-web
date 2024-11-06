package tekgui.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * @author Zakariya Javed
 * @version 11/5/2024
 */
public class AboutText extends JDialog {
    
    public AboutText(JFrame parent) {
        super(parent, "About TEK-GUI", true);
        
        // Create main panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        

        // Add title
        JLabel titleLabel = new JLabel("TEK-GUI");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add version/authors/something?  ( simply remove the comments to add it back)

      /**  optional addition in case more info is wanting to be added
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        */
        
        // Add description
        JTextArea descArea = new JTextArea(
            "TEK-GUI is a graphical user interface tool for creating and managing " +
            "visual components. It provides features for object manipulation, " +
            "editing, and organization within a virtual workspace. It is also epic and really cool!"
        );
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setPreferredSize(new Dimension(300, 100));
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add copyright looks professional :P
        JLabel copyrightLabel = new JLabel("© 2024 TEK-GUI Team");
        copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create OK button which closes it 
        JButton okButton = new JButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Add components to panel with spacing
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        // mainPanel.add(versionLabel);   uncomment if the " version label" thing above is wanting to be used
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(descArea);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(copyrightLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(okButton);
        
        // Add panel to dialog
        add(mainPanel);
        
        // Set dialog properties
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
} 
