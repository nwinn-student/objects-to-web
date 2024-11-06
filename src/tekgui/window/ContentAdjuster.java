import java.awt.Point;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.*;
import java.util.List;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class ContentAdjuster extends JFrame {
    private ObjectUI object;
    private JTextField contentField;
    private JButton saveButton, cancelButton;

    public ContentAdjuster(ObjectUI object) {
        this.object = object;
        setTitle("Content Adjuster");
        setLayout(new BorderLayout());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize UI components
        initializeUI();

        // create fields with the object data
        loadObjectContent();
    }

    private void initializeUI() {
        contentField = new JTextField(20);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        panel.add(new JLabel("Content:"));
        panel.add(contentField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadObjectContent() {
        if (object != null) {
            List<String> contentList = object.getContent();
            String content = String.join(", ", contentList); // Join list elements into a single String
            contentField.setText(content);
        }
    }

    private void saveChanges() {
        if (object != null) {
            String contentText = contentField.getText();
            List<String> contentList = Arrays.asList(contentText.split(",\\s*")); // Split by comma and optional spaces

            object.setContent(contentList);

            JOptionPane.showMessageDialog(this, "Content updated successfully!");
            dispose();
        }
    }
}
