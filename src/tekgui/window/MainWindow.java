package tekgui.window;
import tekgui.TEKFile;

public class MainWindow  extends JFrame {
    public MainWindow() {
        // Set initial window properties
        setTitle("Main Window");
        setSize(800, 600);  // Initial size
        setVisible(true);

        // Open the settings dialog
        TEKSettings settingsDialog = new TEKSettings(this, this);
    }

    // Method to update settings from the TEKSettings dialog
    public void updateSettings(String fontSize, int width, int height) {
        // Apply the font size change
        setFont(new Font("Arial", Font.PLAIN, getFontSize(fontSize)));

        // Apply the window size change
        setSize(width, height);

        // Refresh the UI after changes
        SwingUtilities.updateComponentTreeUI(this);

        // Print the applied settings for verification
        System.out.println("Applied settings: Font Size = " + fontSize + ", Width = " + width + ", Height = " + height);
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
   public static void main(String[] args) {
        new MainWindow();
    }
}
