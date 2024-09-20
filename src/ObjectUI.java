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
    public Dimension getSize()
    {
        return size;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Created: " + creationTime + ", Position: (" + position.x + ", " + position.y + "), Size: (" + size.width + " x " + size.height + ")";
        //returns the ObjectUI details in string format
    }
    
}
