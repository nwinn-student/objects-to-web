import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.io.*;
import java.awt.event.*;

/**
 * The Frame that will contain all of the visuals and handle them appropriately.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKFrame extends JFrame{
    // To be later used with save, creation, deletion, and modification of Objects
    private boolean unSavedChanges = false;
    private Dimension screenSize = null;
    /**
     * Constructor for objects of class TEKFrame
     * Titles the TEKFrame class and initializes it.
     */
    public TEKFrame(){
        super("TEK-GUI");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.addWindowListener(new TEKWindowAdapter(this));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width - (int)(screenSize.width/12.5),screenSize.height - (int)(screenSize.width/12.5));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        TEKPanel panel = new TEKPanel(this);
        scrollPane.setViewportView(panel);
        
        // Add menuBar and toolBar here, for fullscreen later we will want to pass in scrollPane
        // for now though panel should be fine 
        // (scrollPane.getViewport().getView() will get panel and panel.getParent().getParent() will get scrollPane)
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        // Adjust to subtract toolBar height and meuBar height later
        panel.setPreferredSize(new Dimension(
            (int)(getWidth()-getInsets().left-getInsets().right+10*scrollPane.getViewportBorderBounds().getWidth()),
            (int)(getHeight()-getInsets().top-getInsets().bottom+10*scrollPane.getViewportBorderBounds().getHeight())));
        
        this.add(scrollPane);
        
         // Initialize the list of ObjectUIs
        objects = new ArrayList<>();
        createSampleObjects(panel); // create sample objects
        
        // Adding menu options for Open and Save
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }
     // Method to create sample ObjectUI 
    private void createSampleObjects(TEKPanel panel) {
        //sample objects, name, position, and size
        objects.add(new ObjectUI("Object 1", new Point(50, 50), new Dimension(300, 100)));
        objects.add(new ObjectUI("Object 2", new Point(500, 250), new Dimension(300, 100)));
        objects.add(new ObjectUI("Object 3", new Point(100, 300), new Dimension(300, 100)));

        // Display the objects in the panel
        panel.displayObjects(objects);
    }
    // Method to open a file
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                // Assuming ObjectUI has a method to load data from the file
                loadObjectsFromData(content.toString());
                unSavedChanges = false;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
            }
        }
    }

    // Method to save a file
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this); // opens a file-saving dialog
        if (result == JFileChooser.APPROVE_OPTION) { // if "SAVE", file is obtained
            File file = fileChooser.getSelectedFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(getDataToSave());
                writer.close();
                unSavedChanges = false; // checks for no unsaved changes
            } catch (IOException ex) { 
                JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage());
            }
        }
    }

    // Method to generate a string representation of objects for saving
    private String getDataToSave() {
        StringBuilder data = new StringBuilder();
        for (ObjectUI object : objects) { //for-each loop for every object in the list
            data.append(object.getDataToSave()).append("\n"); // seperating and appending strings
        }
        return data.toString();
    }

    // Method to load objects from file data
    private void loadObjectsFromData(String data) {
        objects.clear(); //clears existing list of object
        String[] lines = data.split("\n"); //splits data string into an array of lines
        for (String line : lines) {
            // Parse the object data and add new ObjectUI instances to the list
        }
    }
    
    /**
     * Used to tell the fileManager to update the file information with the current, when Generating HTML has been complete.
     */
    public void save(){
        // to be done later, will allow for saving
    }
    /**
     * Used by the fileManager to ask whether it is worth it to save when the user tells the application to.
     * Also used by the windowAdapter to ask whether a save could occur.
     */
    public boolean unSavedChanges(){
        return unSavedChanges;
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                TEKFrame fram = new TEKFrame();
            }
        });
    }
}
