import java.awt.Dimension;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * manage object properties
 *
 * @Mara Doze
 * @9/19/24
 */
public class ObjectUI
{
    private String name;
    private String creationTime;
    private Point position;
    private Dimension size;
    public ObjectUI(String name, Point position, Dimension size) //initialize objects
    {
        this.name = name;
        this.creationTime = getCurrentTime();
        this.position = position;
        this.size = size;
    }
     private String getCurrentTime() { 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(LocalDateTime.now()); //obtain timestamp
    }
    public String getName()
    {
        return name;
    }
    public String getCreationTime()
    {
        return creationTime;
    }
    public Point getPosition()
    {
        return position;
    }
    public void setPosition(Point position){this.position = position;}
    public Dimension getSize()
    {
        return size;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Created: " + creationTime + ", Position: (" + position.x + ", " + position.y + "), Size: (" + size.width + " x " + size.height + ")";
        //returns the ObjectUI details in string format
    }
    // Method to generate a string that can be saved to a file (for saving object state)
    public String getDataToSave() {
        // Format the data as: name, x-position, y-position, width, height, creationTime
        return name + "," + position.x + "," + position.y + "," + size.width + "," + size.height + "," + creationTime;
    }

    // Static method to create an ObjectUI from saved data
    public static ObjectUI fromData(String data) {
        // Split the saved data string by commas to extract the properties
        String[] parts = data.split(",");
        String name = parts[0];
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int width = Integer.parseInt(parts[3]);
        int height = Integer.parseInt(parts[4]);
        String creationTime = parts[5];

        // Create the ObjectUI with parsed data
        ObjectUI objectUI = new ObjectUI(name, new Point(x, y), new Dimension(width, height));
        // Manually set the creation time
        objectUI.creationTime = creationTime;

        return objectUI;
    }
    
}
