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
 * The class that allows for the user to adjust the content within a file, alongside the path associated with 
 * the file should it have yet to be attached.
 *
 * @author: Mara Doze
 * @version Nov. 23, 2024
 */
public class ContentAdjuster extends JDialog {
    private ObjectUI object;
    private  JTextArea contentArea;
    private JButton saveButton, cancelButton;
    private UndoManager undoManager;
    private JTextField nameField;
    
    public ContentAdjuster(List<ObjectUI> object) {
        if(object.size() > 1){
            // get the similar content and display that instead? idk
            
        }
        this.object = object.get(0);
        setTitle("Content Adjuster - "+ object.get(0).getName());
        setLayout(new BorderLayout());
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize UI components
        initializeUI();

        // create fields with the object data
        loadObjectContent();

        //method to confirm w/ user to close the window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
    }

    private void initializeUI() {
        contentArea = new JTextArea(); 
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        // Undo/Redo setup
        undoManager = new UndoManager();
        contentArea.getDocument().addUndoableEditListener(undoManager);

        // Keyboard shortcuts for Undo/Redo
        contentArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        contentArea.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });
        contentArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
        contentArea.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
    
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Object Name:"));
        nameField = new JTextField(20);
        panel.add(nameField);
        
        // Change later, temporary for when only 1 object selected
        if(object.getFile() != null){
            nameField.setEditable(false);
        }
        nameField.setText(object.getName());
        
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(contentArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());

        // word and character count update
        contentArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateStatus();
            }
        });
        
        setVisible(true);
    }

    private void loadObjectContent() {
        if (object != null) {
            List<String> contentList = object.getContent();
            if(contentList == null)
                return;
            String content = String.join("\n", contentList); // Join list elements into a single String
            contentArea.setText(content);
        }
    }

    private void saveChanges() {
        if (object != null) {
            String contentText = contentArea.getText();
            List<String> contentList = Arrays.asList(contentText.split("\\n")); // Split by new lines

            // Updates the object content
            object.setContent(new ArrayList<>(contentList));
            try{
                object.setName(nameField.getText());
            } catch (java.io.IOException ioe){
                // Should not be possible, but just in case
                JOptionPane.showMessageDialog(this, "File already exists, cannot change name!");
            }
            
            // Notifies the user of successful changes
            JOptionPane.showMessageDialog(this, "Content updated successfully!");
            dispose();
        }
    }

    private void updateStatus() {
        // method for word and character count
        String content = contentArea.getText();
        int words = content.trim().isEmpty() ? 0 : content.trim().split("\\s+").length;
        int characters = content.length();
        setTitle("Content Adjuster " + object.getName() + " | Words: " + words + ", Characters: " + characters);
    }

    private void handleWindowClosing() {
        int response = JOptionPane.showConfirmDialog(this, "You have unsaved changes. Do you really want to exit?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            dispose();
        } else {
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        }
    }
}