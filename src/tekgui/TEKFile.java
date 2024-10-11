package tekgui;

// TEKGUI imports
import tekgui.window.TEKFrame;

// Java imports
import java.io.BufferedReader;
import java.io.File;
import javax.swing.JFileChooser;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.UIManager;

/**
 * manages files
 *
 * @Mara Doze
 * @9/19/24
 */
public class TEKFile
{
    private static TEKFrame frame = null;
    public TEKFile(){}
    public static TEKFrame getFrame()
    {
        return frame;
    }
    public static void setFrame(TEKFrame fram)
    {
        frame = fram;
    }
    public static void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
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
                frame.save();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
            }
        }
    }

    // Method to save a file
    public static void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame); // opens a file-saving dialog
        if (result == JFileChooser.APPROVE_OPTION) { // if "SAVE", file is obtained
            File file = fileChooser.getSelectedFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(getDataToSave());
                writer.close();
                frame.save(); // checks for no unsaved changes
            } catch (IOException ex) { 
                JOptionPane.showMessageDialog(frame, "Error writing to file: " + ex.getMessage());
            }
        }
    }
    private static String getDataToSave() {
        StringBuilder data = new StringBuilder();
        for (ObjectUI object : frame.getPanel().getObjects()) { //for-each loop for every object in the list
            data.append(object.getDataToSave()).append("\n"); // seperating and appending strings
        }
        return data.toString();
    }

    // Method to load objects from file data
    private static void loadObjectsFromData(String data) {
        frame.getPanel().clearObjects(); //clears existing list of object
        String[] lines = data.split("\n"); //splits data string into an array of lines
        for (String line : lines) {
            // Parse the object data and add new ObjectUI instances to the list
        }
    }
}
