package tekgui;

// TEKGUI imports
import tekgui.window.TEKFrame;
import tekgui.window.TEKMenuBar;

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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;
import java.util.List;
import javax.swing.filechooser.FileFilter;

/**
 * manages files
 *
 * @Mara Doze
 * @10/24/24
 */
public class TEKFile
{
    private static TEKFrame frame = null;
    private static final transient JFileChooser fileChooser = new JFileChooser();
    public TEKFile(){}
    public static TEKFrame getFrame(){
        return frame;
    }
    public static void setFrame(TEKFrame fram){
        frame = fram;
    }
    public static void openFile() {
        fileChooser.setCurrentDirectory( new File("*") ); // adjust to recent location
        // filter to only allow html or directories**
	// ....
        fileChooser.setFileSelectionMode(fileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(frame.getPanel());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();            
            ((TEKMenuBar)frame.getJMenuBar()).remember(file);
            openFile(file);
        }
    }
    public static void openFile(File file){
        try {
            // Open the object and create it onto the screen

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading file: " + ex);
        }
    }
    // Method to save a file
    public static void saveFile() {
        int result = fileChooser.showSaveDialog(frame.getPanel()); // opens a file-saving dialog
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
    public static void saveFile(ObjectUI object){
        // should the file exist, we update the contents, should it not, we create
        //  <file>.createNewFile()
    }
    public static void saveFile(ObjectUI... object){
        for(ObjectUI o : object){
            saveFile(o);
        }
    }
    public static void saveFile(List<ObjectUI> object){
        for(ObjectUI o : object){
            saveFile(o);
        }
    }
    private static String getDataToSave() {
        // adjust***
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
