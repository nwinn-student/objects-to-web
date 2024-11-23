package tekgui.window;
import tekgui.TEKFile;
import java.awt.Font;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Container;

public class MainWindow {
    private String currentFontSize;
    public MainWindow() {
        //initializing default values
        this.currentFontSize = "Small";
    }
    public String getFontSize(){return currentFontSize;}
    //  the method being called by TEKSettings to update settings
    public void updateSettings(String fontSize, int width, int height) {
        this.currentFontSize = fontSize;

        //System.out.println("Applied settings: Font Size = " + fontSize);
        
        // Apply window size changes
        TEKFile.getFrame().setSize(width, height);
        //TEKFile.getFrame().setFont(new Font("Arial", Font.PLAIN, ));
        updateFont(TEKFile.getFrame(), getFontSize(fontSize));
    }
    /**
     * 
     * 
     * @param ancestor
     * @param fontSize
     */
    public void updateFont(Component ancestor, float fontSize){
        Font font = ancestor.getFont();
        ancestor.setFont(font.deriveFont(fontSize));
        try{
            Container cont = (Container) ancestor;
            for(Component comp : cont.getComponents()){
                updateFont(comp, fontSize);
            }
        } catch(ClassCastException e){
            // Not a container
        }
    }
    // Helper method to convert font size string to actual size
    private int getFontSize(String fontSize) {
        switch (fontSize) {
            case "Small": return 12;
            case "Medium": return 16;
            case "Large": return 20;
            default: return 16; // medium size 
        }
    }
    /**
     * Creates a new TEKSettings object and displays it.
     */
    public void displaySettings(){
        // Create and display the settings dialog (when needed)
        TEKSettings settingsDialog = new TEKSettings(TEKFile.getFrame(), this);
    }
}
