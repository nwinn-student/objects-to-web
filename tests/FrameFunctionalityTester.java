import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Component;
/**
 * The test class FrameFunctionalityTester.
 *
 * @author  Noah Winn
 * @version 9/18/2024
 */
public class FrameFunctionalityTester{
    protected TEKFrame frame = new TEKFrame();
    private Helper help = new Helper();
    /**
     * Default constructor for test class FrameFunctionalityTester
     */
    public FrameFunctionalityTester(){}

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
        help.wait(500);
        assert(frame.isVisible() == true);
    }
    
    @Test
    public void canFrameIconify(){
        frame.setTitle("Can Frame Iconify");
        help.wait(500);
        help.smoothMove(help.getMinimize(frame));
        help.wait(500);
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameMaximize(){
        frame.setTitle("Can Frame Maximize");
        help.wait(500);
        help.smoothMove(help.getMaximize(frame));
        help.wait(500);
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameScale(){
        frame.setTitle("Can Frame Scale");
        help.wait(500);
        help.smoothMove(help.getCorner(frame));
        help.wait(500);
        help.smoothDrag(InputEvent.BUTTON1_DOWN_MASK, help.getRandomPoint());
    }
    
    @Test
    public void canFrameMove(){
        frame.setTitle("Can Frame Move");
        help.wait(500);
        help.smoothMove(help.getTop(frame));
        help.wait(500);
        help.smoothDrag(InputEvent.BUTTON1_DOWN_MASK, help.getRandomPoint());
    }
    
    @Test
    public void canFrameGetAddedTo(){
        frame.setTitle("Can Frame Get Added To");
        help.wait(500);
        JToolBar toolBar = new JToolBar("TEKToolBar");
        toolBar.add(new JButton("Hello there traveler."));
        frame.add(toolBar, BorderLayout.WEST);
    }
    
    @Test
    public void canFrameClose(){
        frame.setTitle("Can Frame Close");
        help.wait(500);
        help.smoothMove(help.getCenter(frame));
        help.wait(500);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        help.smoothMove(help.getExit(frame));
        help.wait(500);
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    public void exit(){
        frame.dispose();
        System.exit(0);
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown(){
        help.wait(3000);
        frame.dispose();
        //frame = new TEKFrame();
    }
}
