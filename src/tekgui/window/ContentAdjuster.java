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
public class ContentAdjuster extends JFrame {
    private ObjectUI object;
    private  JTextArea contentArea;
    private JButton saveButton, cancelButton;
    private UndoManager undoManager;


    public ContentAdjuster(ObjectUI object) {
        this.object = object;
        setTitle("Content Adjuster"+ object.getName());
        setLayout(new BorderLayout());
        setSize(400, 200);
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
    }

    private void loadObjectContent() {
        if (object != null) {
            List<String> contentList = object.getContent();
            String content = String.join(", ", contentList); // Join list elements into a single String
            contentArea.setText(content);
        }
    }

    private void saveChanges() {
        if (object != null) {
            String contentText = contentArea.getText();
            List<String> contentList = Arrays.asList(contentText.split("\\n")); // Split by new lines

           // Updates the object content
            object.setContent(new ArrayList<>(contentList));

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
        }
    }
}
