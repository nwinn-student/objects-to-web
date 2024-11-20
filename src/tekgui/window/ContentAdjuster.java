package tekgui.window;
import tekgui.ObjectUI;

import java.awt.Point;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.*;
import java.util.List;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.undo.UndoManager;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;
import java.awt.event.KeyEvent;   
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author: 
 * @version Nov. 18, 2024
 */
public class ContentAdjuster extends JDialog {
    private ObjectUI object;
     private JTextField nameField;
    private JTextField widthField, heightField;
    private JButton saveButton, cancelButton;
    private UndoManager undoManager;

     public ContentAdjuster(ObjectUI object) {
        this.object = object;
        setTitle("Content Adjuster - " + object.getName());
        setLayout(new BorderLayout());
        setSize(400, 250);  // Size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize UI components
        initializeUI();

        // Load object details
        loadObjectContent();

        // Confirm closing the window
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                handleWindowClosing();
            }
        });
    }

    private void initializeUI() {
       // Create text fields for Object Name and Object Size
        nameField = new JTextField(20);
        widthField = new JTextField(10);
        heightField = new JTextField(10);

        // Undo/Redo setup
        undoManager = new UndoManager();

        // Setting up the layout for fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Object Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);

        // Save and Cancel buttons
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadObjectContent() {
        if (object != null) {
            nameField.setText(object.getName());
            widthField.setText(String.valueOf(object.getSize().width));
            heightField.setText(String.valueOf(object.getSize().height));
        }
    }

    private void saveChanges() {
        if (object != null) {
            try {
                // Get user input from fields
                String newName = nameField.getText();
                int newWidth = Integer.parseInt(widthField.getText());
                int newHeight = Integer.parseInt(heightField.getText());

                // Update the object with the new data
                object.editName(newName);
                object.editSize(new Dimension(newWidth, newHeight));

                // Notifies the user of successful changes
                JOptionPane.showMessageDialog(this, "Object updated successfully!");
                dispose();  // Close the window after saving
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleWindowClosing() {
        int response = JOptionPane.showConfirmDialog(this, "You have unsaved changes. Do you really want to exit?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    
    @Override
    public void dispose() {
        // Save window size settings before closing
        SettingsHelper.saveWindowSize(getWidth(), getHeight());
        SettingsHelper.saveLineWrap(nameField.getText().length() > 0);  // example
        super.dispose();
    }
}
