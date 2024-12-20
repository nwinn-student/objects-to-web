package tekgui.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.Properties;
import java.awt.Rectangle;

/**
 * Description: 
 * Utility class for saving and loading the frame's location
 * 
 * @author Coby Zhong
 * @version 20/11/2024
 */
public class FrameLocationManager {
    private static final String STOREDFILE = "frame_config.properties";
    // Create a file that stores in the Frame's location and size

    public static void saveFrameState(int x, int y, int width, int height, int screenWidth, int screenHeight) {
        Properties properties = new Properties();

        try (FileOutputStream out = new FileOutputStream(STOREDFILE)) {
            properties.setProperty("x", String.valueOf((double) x / screenWidth));
            properties.setProperty("y", String.valueOf((double) y / screenHeight));
            properties.setProperty("width", String.valueOf((double) width / screenWidth));
            properties.setProperty("height", String.valueOf((double) height / screenHeight));
            properties.store(out, "Frame Location and Size");
            out.close();
        } catch (IOException e) {
            System.err.println("There was an error saving the : " + e.getMessage());
        }
    }
    public static void saveFrameState(Rectangle rect){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        saveFrameState(rect.x, rect.y, rect.width, rect.height, screenSize.width, screenSize.height);
    }
    //Saves the frame location and size percentages in a text file to be read from upon opening up the application.  

   
    public static int[] loadFrameState(int screenWidth, int screenHeight) {
        Properties properties = new Properties();

        try (FileInputStream in = new FileInputStream(STOREDFILE)) {
            properties.load(in);
            in.close();
            int x = (int) (Double.parseDouble(properties.getProperty("x")) * screenWidth);
            int y = (int) (Double.parseDouble(properties.getProperty("y")) * screenHeight);
            int width = (int) (Double.parseDouble(properties.getProperty("width")) * screenWidth);
            int height = (int) (Double.parseDouble(properties.getProperty("height")) * screenHeight);
            return new int[]{x, y, width, height};
        } catch (IOException | NumberFormatException e) {
            System.err.println("There were an error loading the file to the frame: " + e.getMessage());
            return null;
        }
    }
}