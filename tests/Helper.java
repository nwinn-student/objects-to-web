import javax.swing.JFrame;
import java.awt.Point;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.MouseInfo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * A class to assist with testing various UI elements.
 *
 * @author Noah Winn
 * @version Sept. 18, 2024
 */
public class Helper{
    private Robot rob = null;
    public Helper(){
        try{rob = new Robot();} catch (Exception e){}
    }
    /**
     * Gets the center of the component
     * @param comp the component
     * @return a point at the center of the component
     */
    public Point getCenter(Component comp){
        return new Point((int)(comp.getLocationOnScreen().getX()+comp.getBounds().getWidth()/2), 
            (int)(comp.getLocationOnScreen().getY()+comp.getBounds().getHeight()/2));
    }
    /**
     * Determines the top of the component
     * @param comp the component to get the top of
     * @return a point at the top section of the component
     */
    public Point getTop(Component comp){
        return new Point((int)(comp.getLocationOnScreen().getX()+comp.getBounds().getWidth()/2), 
            (int)(comp.getLocationOnScreen().getY()));
    }
    /**
     * Determines the top of the container, below the point at which one can resize it.
     * @param cont the container
     * @return a point at the top section of the container
     */
    public Point getTop(Container cont){
        return new Point(getCenter(cont).x, (int)cont.getBounds().getY()+cont.getInsets().top/2);
    }
    /**
     * Gets the corner of the input component
     * @param comp the component to the corner of
     * @return a point at the corner of the component
     */
    public Point getCorner(Component comp){
        return new Point((int)comp.getLocationOnScreen().getX(), (int)comp.getLocationOnScreen().getY());
    }
    /**
     * Smoothly moves the mouse from the current mouse position to the specified end position
     * @param end the point to end at
     */
    public void smoothMove(Point end){
        // Calculate how far each drag should be to make it relatively smooth whilst taking only one second
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
    public void mouseMove(Point p){
        rob.mouseMove((int)p.getX(), (int)p.getY());
    }
    /**
     * Forces the mouse to click the screen at its current location
     * @param buttonMask the button to click
     */
    public void mouseClick(int buttonMask){
        rob.mousePress(buttonMask);
        rob.mouseRelease(buttonMask);
    }
    /**
     * Smoothly drags the mouse across the screen from its starting point to the specified end point.
     * The mouse will be holding a determined key as designated by the buttonMask
     * @param buttonMask the button to click
     * @param end the point to end at
     */
    public void smoothDrag(int buttonMask, Point end){
        rob.mousePress(buttonMask);
        smoothMove(end);
        rob.mouseRelease(buttonMask);
    }
    /**
     * Stalls the thread for a specified amount of time
     * @param millis the time in milliseconds
     */
    public void wait(int millis){
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
    public Point getExit(Container frame){
        return new Point((int)(frame.getBounds().getX()+frame.getBounds().getWidth()-frame.getInsets().top),
            (int)frame.getBounds().getY()+frame.getInsets().top/2);
    }
    /**
     * Determines the location within the container where a maximize button could be for WindowsOS
     * @param frame the container
     * @return a point where the maximize could be
     */
    public Point getMaximize(Container frame){
        return new Point((int)(frame.getBounds().getX()+frame.getBounds().getWidth()-3*frame.getInsets().top+frame.getInsets().left+frame.getInsets().right),
            (int)frame.getBounds().getY()+frame.getInsets().top/2);
    }
    /**
     * Determines the location within the container where a minimize button could be for WindowsOS
     * @param frame the container
     * @return a point where the minimize could be
     */
    public Point getMinimize(Container frame){
        return new Point((int)(frame.getBounds().getX()+frame.getBounds().getWidth()-5*frame.getInsets().top+2*(frame.getInsets().left+frame.getInsets().right)),
            (int)frame.getBounds().getY()+frame.getInsets().top/2);
    }
    /**
     * Determines a pseudo-random location within the current screen
     * @return a point
     */
    public Point getRandomPoint(){
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
    public boolean contains(Container frame, Component comp){
        Component[] frameComp = frame.getComponents();
        for(Component c : frameComp){
            if(comp.equals(c)){
                return true;
            }
        }
        return false;
    }
}
