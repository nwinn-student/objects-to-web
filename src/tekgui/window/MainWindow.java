import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private String currentTheme;
    private String currentFontSize;
    private int currentWidth;
    private int currentHeight;

    public MainWindow() {
        // Initialize with default values
        this.currentTheme = "Light";
        this.currentFontSize = "Medium";
        this.currentWidth = 800;
        this.currentHeight = 600;

        // Create and display the settings dialog when needed
        TEKSettings settingsDialog = new TEKSettings(this);
    }

    // This is the method being called by TEKSettings to update settings
    public void updateSettings(String theme, String fontSize, int width, int height) {
        this.currentTheme = theme;
        this.currentFontSize = fontSize;
        this.currentWidth = width;
        this.currentHeight = height;

        // Apply the theme (you can extend this logic to change the actual look and feel)
        System.out.println("Applied settings: Theme = " + theme + ", Font Size = " + fontSize);
        
        // Apply window size changes
        setSize(currentWidth, currentHeight);
        // Apply font size change if necessary, for example:
        // setFont(new Font("Arial", Font.PLAIN, getFontSize(fontSize)));
    }

    // Helper method to convert font size string to actual size
    private int getFontSize(String fontSize) {
        switch (fontSize) {
            case "Small": return 12;
            case "Medium": return 16;
            case "Large": return 20;
            default: return 16; // Default to medium size
        }
    }

    public static void main(String[] args) {
        // Initialize the main window
        new MainWindow();
    }
}
