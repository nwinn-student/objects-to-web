package tekgui.window;
import tekgui.TEKFile;

public class MainWindow {
    private String currentTheme;
    private String currentFontSize;
    private int currentWidth;
    private int currentHeight;
    public MainWindow() {
        //initializing default values
        this.currentTheme = "Light";
        this.currentFontSize = "Medium";
        this.currentWidth = 800;
        this.currentHeight = 600;
    }

    //  the method being called by TEKSettings to update settings
    public void updateSettings(String theme, String fontSize, int width, int height) {
        this.currentTheme = theme;
        this.currentFontSize = fontSize;
        this.currentWidth = width;
        this.currentHeight = height;

        // Apply the theme
        System.out.println("Applied settings: Theme = " + theme + ", Font Size = " + fontSize);
        
        // Apply window size changes
        TEKFile.getFrame().setSize(currentWidth, currentHeight);
    
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
    public void displaySettings(){
        // Create and display the settings dialog (when needed)
        TEKSettings settingsDialog = new TEKSettings(TEKFile.getFrame(), this);
    }
}
