import javax.swing.JFrame;
import java.awt.Point;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.InputEvent;
import javax.swing.JOptionPane;
import java.awt.MouseInfo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * A class to assist with testing various UI elements.
 *
 * @author Noah Winn
 * @version Sept. 18, 2024
 */
public class Helper{
    public static final byte MOUSE_LOW_PRIORITY = 0;
    public static final byte MOUSE_HIGH_PRIORITY = 1;
    /**
     * Fastest speed the mouse can travel, does not trace.
     */
    public static final byte MOUSE_INSTANT = -1;
    public static final byte MOUSE_SMOOTH_INSTANT = 0;
    public static final byte MOUSE_SMOOTH_FAST = 1;
    public static final byte MOUSE_SMOOTH_SLOW = 5;
    
    public static short SPEED_MS = 500;
    public static byte AMOUNT = 1;
    
    private static byte currentMouseSpeed = 0;
    private static byte currentMousePriority = 0;
    private static Robot rob = null;
    public Helper(){
        try{rob = new Robot();} catch (Exception e){}
    }
    /**
     * Displays an OptionPane that allows the developer to 
     * customize the speed.  We have MOUSE_INSTANT (-1), MOUSE_SMOOTH_INSTANT (0), 
     * MOUSE_SMOOTH_FAST (1), and MOUSE_SMOOTH_SLOW (5).  The scale will be integer-based.
     */
    public static void displayOptions(){
        String input = JOptionPane.showInputDialog("Choose Testing Mouse Speed (Fast -> Slow): \n[-1 or 0 or 1 or 5]", "0");
        if(input == null){System.exit(0);}
        try{
            switch(Integer.parseInt(input)){
                case MOUSE_INSTANT:
                    currentMouseSpeed = MOUSE_INSTANT;
                    break;
                case MOUSE_SMOOTH_INSTANT:
                    currentMouseSpeed = MOUSE_SMOOTH_INSTANT;
                    break;
                case MOUSE_SMOOTH_FAST:
                    currentMouseSpeed = MOUSE_SMOOTH_FAST;
                    break;
                case MOUSE_SMOOTH_SLOW:
                    currentMouseSpeed = MOUSE_SMOOTH_SLOW;
                    break;
                default:
                    currentMouseSpeed = MOUSE_SMOOTH_INSTANT;
                    break;
            }
        } catch(Exception e){}
        
        input = JOptionPane.showInputDialog("Choose Testing Delay (ms):", "500");
        if(input == null){System.exit(0);}
        try{
            if(Integer.parseInt(input) > 0 && Integer.parseInt(input) < 30000){
                SPEED_MS = (short)Integer.parseInt(input);
            }
        } catch(Exception e){}
        
        input = JOptionPane.showInputDialog("Choose Testing Amount (1-10):", "1");
        if(input == null){System.exit(0);}
        try{
            if(Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 10){
                AMOUNT = (byte)Integer.parseInt(input);
            }
        } catch(Exception e){}
        
        JDialog f = new JDialog();
        f.setTitle("Emergency");
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
        
    }
    /**
     * Gets the center of the component
     * @param comp the component
     * @return a point at the center of the component
     */
    public static Point getCenter(Component comp){
        return new Point((int)(comp.getLocationOnScreen().getX()+comp.getBounds().getWidth()/2), 
            (int)(comp.getLocationOnScreen().getY()+comp.getBounds().getHeight()/2));
    }
    /**
     * Determines the top of the component
     * @param comp the component to get the top of
     * @return a point at the top section of the component
     */
    public static Point getTop(Component comp){
        return new Point((int)(comp.getLocationOnScreen().getX()+comp.getBounds().getWidth()/2), 
            (int)(comp.getLocationOnScreen().getY()));
    }
    /**
     * Determines the top of the container, below the point at which one can resize it.
     * @param cont the container
     * @return a point at the top section of the container
     */
    public static Point getTop(Container cont){
        return new Point(getCenter(cont).x, (int)cont.getBounds().getY()+cont.getInsets().top/2);
    }
    /**
     * Gets the corner of the input component
     * @param comp the component to the corner of
     * @return a point at the corner of the component
     */
    public static Point getCorner(Component comp){
        return new Point((int)comp.getLocationOnScreen().getX(), (int)comp.getLocationOnScreen().getY());
    }
    /**
     * Smoothly moves the mouse from the current mouse position to the specified end position
     * @param end the point to end at
     */
    public static void smoothMove(Point end){
        // Calculate how far each drag should be to make it relatively smooth whilst taking only one second
        if(Toolkit.getDefaultToolkit().getScreenSize().width < end.x || Toolkit.getDefaultToolkit().getScreenSize().height < end.y){
            return;
        }
        Point begin = MouseInfo.getPointerInfo().getLocation();
        while(!begin.equals(end)){
            begin.setLocation(MouseInfo.getPointerInfo().getLocation()); // ensures that even if user moves mouse we can still work
            if(begin.x > end.x && begin.y > end.y){
                //Shift left/down
                begin.translate(-1,-1);
                mouseMove(begin);
            } else if(begin.x > end.x && begin.y == end.y){
                // shift left
                begin.translate(-1,0);
                mouseMove(begin);
            } else if(begin.x > end.x && begin.y < end.y){
                // shift left/up
                begin.translate(-1,1);
                mouseMove(begin);
            } else if(begin.x == end.x && begin.y > end.y){
                // shift down
                begin.translate(0,-1);
                mouseMove(begin);
            } else if(begin.x == end.x && begin.y < end.y){
                // shift up
                begin.translate(0,1);
                mouseMove(begin);
            } else if(begin.x < end.x && begin.y > end.y){
                // shift right/down
                begin.translate(1,-1);
                mouseMove(begin);
            } else if(begin.x < end.x && begin.y == end.y){
                // shift right
                begin.translate(1,0);
                mouseMove(begin);
            } else {
                // shift right/up
                begin.translate(1,1);
                mouseMove(begin);
            }
        }
    }
    /**
     * Moves the mouse to the designated point.
     * @param p the point
     */
    public static void mouseMove(Point p){
        rob.mouseMove((int)p.getX(), (int)p.getY());
    }
    /**
     * Forces the mouse to click the screen at its current location
     * @param buttonMask the button to click
     */
    public static void mouseClick(int buttonMask){
        rob.mousePress(buttonMask);
        rob.mouseRelease(buttonMask);
    }
    /**
     * Smoothly drags the mouse across the screen from its starting point to the specified end point.
     * The mouse will be holding a determined key as designated by the buttonMask
     * @param buttonMask the button to click
     * @param end the point to end at
     */
    public static void smoothDrag(int buttonMask, Point end){
        rob.mousePress(buttonMask);
        smoothMove(end);
        rob.mouseRelease(buttonMask);
    }
    public static void keyClick(int keyMask){
        rob.keyPress(keyMask);
        rob.keyRelease(keyMask);
    }
    public int getSpeed(){
        if(rob == null){return 0;}
        return rob.getAutoDelay();
    }
    public void setSpeed(int speed){
        if(rob == null){return;}
        if(speed < 0){
            rob.setAutoDelay(20);
        } else if (speed > 20){
            rob.setAutoDelay(0);
        } else {
            rob.setAutoDelay((int)(20/speed));
        }
    }
    public void setPriority(int priority){
        if(rob == null){return;}
        switch(priority){
            case MOUSE_LOW_PRIORITY:
                rob.setAutoWaitForIdle(true);
            case MOUSE_HIGH_PRIORITY:
                rob.setAutoWaitForIdle(false);
            default:
                rob.setAutoWaitForIdle(false);
        }
    }
    /**
     * Stalls the thread for a specified amount of time
     * @param millis the time in milliseconds
     */
    public static void wait(int millis){
        try{
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    /**
     * Determines the location within the container where an exit button could be for WindowsOS
     * @param frame the container
     * @return a point that the exit could be
     */
    public static Point getExit(Container frame){
        return new Point((int)(frame.getBounds().getX()+frame.getBounds().getWidth()-frame.getInsets().top),
            (int)frame.getBounds().getY()+frame.getInsets().top/2);
    }
    /**
     * Determines the location within the container where a maximize button could be for WindowsOS
     * @param frame the container
     * @return a point where the maximize could be
     */
    public static Point getMaximize(Container frame){
        return new Point((int)(frame.getBounds().getX()+frame.getBounds().getWidth()-3*frame.getInsets().top+frame.getInsets().left+frame.getInsets().right),
            (int)frame.getBounds().getY()+frame.getInsets().top/2);
    }
    /**
     * Determines the location within the container where a minimize button could be for WindowsOS
     * @param frame the container
     * @return a point where the minimize could be
     */
    public static Point getMinimize(Container frame){
        return new Point((int)(frame.getBounds().getX()+frame.getBounds().getWidth()-5*frame.getInsets().top+2*(frame.getInsets().left+frame.getInsets().right)),
            (int)frame.getBounds().getY()+frame.getInsets().top/2);
    }
    /**
     * Determines a pseudo-random location within the current screen
     * @return a point
     */
    public static Point getRandomPoint(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int)(Math.random() * screenSize.width/2), 
            (int)(Math.random() * screenSize.height/2));
    }
    /**
     * Checks whether this component lies within the container
     * @param frame the container
     * @param comp the component
     * @returns whether component is within container
     */
    public static boolean contains(Container frame, Component comp){
        if(comp.getParent().equals(frame)){
            return true;
        }
        return false;
    }
}
