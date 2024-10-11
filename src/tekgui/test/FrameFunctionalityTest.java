package tekgui.test;

// TEKGUI imports
import tekgui.test.window.*;
import tekgui.window.TEKFrame;
import tekgui.window.TEKToolBar;
import tekgui.helper.Helper;

// Java imports
import java.awt.event.InputEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
/**
 * Improved tester class for the Frame UI Creation and Functionality 
 * feature.
 *
 * @author Noah Winn
 * @version Oct 2, 2024
 */
public class FrameFunctionalityTest extends Test{
    private static Internal frame;
    public FrameFunctionalityTest(){
        frame = TestFrame.respawnInternal(new TEKFrame(), true);
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    public void tearDown(){
        Helper.wait(6*Helper.SPEED_MS);
        frame.dispose();
        frame = null;
        frame = TestFrame.respawnInternal(new TEKFrame(), true);
    }
    // Individual tests
    public void canFrameShow(){
        frame.setTitle("Can Frame Show");
        Helper.wait(Helper.SPEED_MS);
    }
    public void canFrameIconify(){
        frame.setTitle("Can Frame Iconify");
        Helper.wait(Helper.SPEED_MS);
        System.out.println(Helper.getMinimize(frame));
        System.out.println(frame.getBounds());
        System.out.println(frame.getInsets());
        System.out.println(frame.getBorder());
        System.out.println(frame.getTrueInsets());
        Helper.smoothMove(Helper.getMinimize(frame));
        Helper.wait(Helper.SPEED_MS);
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    public void canFrameMaximize(){
        frame.setTitle("Can Frame Maximize");
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothMove(Helper.getMaximize(frame));
        Helper.wait(Helper.SPEED_MS);
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    public void canFrameScale(){
        frame.setTitle("Can Frame Scale");
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothMove(Helper.getCorner(frame));
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothDrag(InputEvent.BUTTON1_DOWN_MASK, Helper.getRandomPoint());
    }
    public void canFrameMove(){
        frame.setTitle("Can Frame Move");
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothMove(Helper.getTop(frame));
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothDrag(InputEvent.BUTTON1_DOWN_MASK, Helper.getRandomPoint());
    }
    public void canFrameGetAddedTo(){
        frame.setTitle("Can Frame Get Added To");
        Helper.wait(Helper.SPEED_MS);
        frame.setContentPane(TEKToolBar.addButton("Sample", frame,""));
        frame.repaint();
    }
    public void canFrameClose(){
        frame.setTitle("Can Frame Close");
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothMove(Helper.getExit(frame));
        Helper.wait(Helper.SPEED_MS);
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    // All tests combined
    public void run(){
        canFrameShow();
        tearDown();
        canFrameIconify();
        tearDown();
        canFrameMaximize();
        tearDown();
        canFrameScale();
        tearDown();
        canFrameMove();
        tearDown();
        canFrameGetAddedTo();
        tearDown();
        canFrameClose();
        tearDown();
    }
}
