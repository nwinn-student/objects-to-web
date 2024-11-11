package tekgui.window;

// TEKGUI imports
import tekgui.ObjectUI;

// Java imports
import javax.swing.*; // removed java.awt.* since it has List class
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * Write a description of class TEKEditView here.
 * 
 * @author Mara Doze
 * @version 11/11/2024
 */
public class TEKEditView extends JFrame {
    
    private List<ObjectUI> obj;
    private ArrayList<JLabel> labels;
    private JPanel labelPanel;
    private JScrollPane scrollPane;

    public TEKEditView(List<ObjectUI> obj) {
        super("Edit Object Details");
        this.obj = obj;
        this.labels = new ArrayList<>();

        //window layout 
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);

        //label panel
        labelPanel = new JPanel(new FlowLayout());
        scrollPane = new JScrollPane(labelPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Adding object details for the label
        populateLabels();

        //button panel for "Submit" and "Cancel"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Action listeners for buttons
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitChanges();  // Placeholder for future 
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeView();
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void populateLabels() {
        //the TEKPanel method is used to format and display ObjectUI details
        String details = TEKPanel.formatObjectDetails(obj.get(0));
        labels.add(new JLabel(details));
        for (JLabel label : labels) {
            labelPanel.add(label);
        }
        //** ObjectUI.getContent(), may need to do a little more
    }

    private void closeView() {
        this.dispose();
    }

    private void submitChanges() {
        // Placeholder for the future to handle modified objects
        JOptionPane.showMessageDialog(this, "Changes submitted!");
        closeView();
    }
}
