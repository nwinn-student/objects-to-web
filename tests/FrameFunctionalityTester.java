import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Robot;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;

/**
 * Not complete, will complete later.  It currently only works on my screen but not others so I will have to fix that.
 *
 * @author  Noah Winn
 * @version 9/17/2024
 */
public class FrameFunctionalityTester{
    private TEKFrame frame = new TEKFrame();
    
    private Robot rob = null;
    /**
     * Default constructor for test class FrameFunctionalityTester
     */
    public FrameFunctionalityTester(){
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){}

    @Test
    public void canFrameShow(){
        frame.setTitle("Can Frame Show");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        assert(frame.isVisible() == true);
    }
    
    @Test
    public void canFrameIconify(){
        frame.setTitle("Can Frame Iconify");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        try{rob = new Robot();} catch(Exception e){}
        rob.mouseMove((int)getModePoint("Minimize").getX(),(int)getModePoint("Minimize").getY());
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameMaximize(){
        frame.setTitle("Can Frame Maximize");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        try{rob = new Robot();} catch(Exception e){}
        rob.mouseMove((int)getModePoint("Maximize").getX(),(int)getModePoint("Maximize").getY());
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameScale(){
        frame.setTitle("Can Frame Scale");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        try{rob = new Robot();} catch(Exception e){}
        rob.mouseMove((int)getCorner().getX(),(int)getCorner().getY());
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove((int)getRandomPoint().getX(), (int)getRandomPoint().getY());
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameMove(){
        frame.setTitle("Can Frame Move");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        try{rob = new Robot();} catch(Exception e){}
        rob.mouseMove((int)getModePoint("").getX(),(int)getModePoint("").getY());
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        rob.mouseMove((int)getModePoint("").getX(),(int)getModePoint("").getY());
        rob.mouseMove((int)getRandomPoint().getX(), (int)getRandomPoint().getY());
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameGetAddedTo(){
        frame.setTitle("Can Frame Get Added To");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        JToolBar toolBar = new JToolBar("TEKToolBar");
        toolBar.add(new JButton("Hello there traveler."));
        frame.add(toolBar, BorderLayout.WEST);
    }
    
    @Test
    public void canFrameClose(){
        frame.setTitle("Can Frame Close");
        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){}
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        try{rob = new Robot();} catch(Exception e){}
        rob.mouseMove((int)getModePoint("Exit").getX(),(int)getModePoint("Exit").getY());
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    private void closeFrame(){
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    private Point getCorner(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int)(screenSize.width*12/12.5),(int)(screenSize.height/12.5 - frame.getInsets().top));
    }
    private Point getModePoint(String mode){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(mode == "Minimize"){
            return new Point((int)(screenSize.width*11/12.5),(int)(screenSize.height/12.5 - frame.getInsets().top + 1));
        } else if(mode == "Maximize"){
            return new Point((int)(screenSize.width*11.5/12.5),(int)(screenSize.height/12.5 - frame.getInsets().top + 1));
        } else if(mode == "Exit"){
            return new Point((int)(screenSize.width*11.75/12.5),(int)(screenSize.height/12.5 - frame.getInsets().top + 1));
        } // else top
        return new Point((int)(screenSize.width*0.5),(int)(screenSize.height/12.5 - frame.getInsets().top + 1));
    }
    private Point getRandomPoint(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int)(Math.random() * screenSize.width/2), (int)(Math.random() * screenSize.height/2));
    }
    public void exit(){
        closeFrame();
        System.exit(0);
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown(){
        try{TimeUnit.SECONDS.sleep(3);}catch (Exception e){}
        closeFrame();
        frame = new TEKFrame();
    }
}
