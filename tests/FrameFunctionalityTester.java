import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.InputEvent;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import java.awt.event.KeyEvent;
/**
 * The test class FrameFunctionalityTester.
 *
 * @author  Noah Winn
 * @version 9/18/2024
 */
public class FrameFunctionalityTester{
    protected TEKFrame frame = new TEKFrame();
    private Helper help = new Helper();
    private int SPEED_MS = 500;
    private final boolean HYPER_MODE = false;
    /**
     * Default constructor for test class FrameFunctionalityTester
     */
    public FrameFunctionalityTester(){}
    public FrameFunctionalityTester(Helper help){
        this.help = help;
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
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
    }
    
    @Test
    public void canFrameIconify(){
        frame.setTitle("Can Frame Iconify");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        if(HYPER_MODE){
            help.mouseMove(help.getMinimize(frame));
        } else{ help.smoothMove(help.getMinimize(frame)); }
        help.wait(Helper.SPEED_MS);
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameMaximize(){
        frame.setTitle("Can Frame Maximize");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        if(HYPER_MODE){
            help.mouseMove(help.getMaximize(frame));
        } else{ help.smoothMove(help.getMaximize(frame)); }
        help.wait(Helper.SPEED_MS);
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameScale(){
        frame.setTitle("Can Frame Scale");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        if(HYPER_MODE){
            help.mouseMove(help.getCorner(frame));
        } else{ help.smoothMove(help.getCorner(frame)); }
        help.wait(Helper.SPEED_MS);
        help.smoothDrag(InputEvent.BUTTON1_DOWN_MASK, help.getRandomPoint());
    }
    
    @Test
    public void canFrameMove(){
        frame.setTitle("Can Frame Move");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        if(HYPER_MODE){
            help.mouseMove(help.getTop(frame));
        } else{ help.smoothMove(help.getTop(frame)); }
        help.wait(Helper.SPEED_MS);
        help.smoothDrag(InputEvent.BUTTON1_DOWN_MASK, help.getRandomPoint());
    }
    
    @Test
    public void canFrameGetAddedTo(){
        frame.setTitle("Can Frame Get Added To");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        JToolBar toolBar = new JToolBar("TEKToolBar");
        frame.add(toolBar, BorderLayout.WEST);
    }
    
    @Test
    public void canFrameClose(){
        frame.setTitle("Can Frame Close");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        if(HYPER_MODE){
            help.mouseMove(help.getCenter(frame));
        } else{ help.smoothMove(help.getCenter(frame)); }
        help.wait(Helper.SPEED_MS);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        if(HYPER_MODE){
            help.mouseMove(help.getExit(frame));
        } else{ help.smoothMove(help.getExit(frame)); }
        help.wait(Helper.SPEED_MS);
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    @Test
    public void canFrameSave(){
        frame.setTitle("Can Frame Save");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        help.smoothMove(help.getCenter(frame.getJMenuBar().getMenu(0)));
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        // Click VK_DOWN
        help.keyClick(KeyEvent.VK_DOWN);
        help.keyClick(KeyEvent.VK_DOWN);
        help.keyClick(KeyEvent.VK_ENTER);
        help.wait(Helper.SPEED_MS);
        help.keyClick(KeyEvent.VK_H);
        help.keyClick(KeyEvent.VK_E);
        help.keyClick(KeyEvent.VK_L);
        help.keyClick(KeyEvent.VK_L);
        help.keyClick(KeyEvent.VK_O);
        help.keyClick(KeyEvent.VK_PERIOD);
        help.keyClick(KeyEvent.VK_T);
        help.keyClick(KeyEvent.VK_X);
        help.keyClick(KeyEvent.VK_T);
        help.keyClick(KeyEvent.VK_ENTER);
    }
    
    @Test
    public void canFrameOpen(){
        // Not looking for the loading functionality as it does not exist, but purely to opening of the JOptionPane
        frame.setTitle("Can Frame Open");
        help.setPriority(0);
        help.wait(Helper.SPEED_MS);
        help.smoothMove(help.getCenter(frame.getJMenuBar().getMenu(0)));
        help.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        // Click VK_DOWN
        help.keyClick(KeyEvent.VK_DOWN);
        help.keyClick(KeyEvent.VK_ENTER);
        help.wait(Helper.SPEED_MS);
        help.keyClick(KeyEvent.VK_H);
        help.keyClick(KeyEvent.VK_E);
        help.keyClick(KeyEvent.VK_L);
        help.keyClick(KeyEvent.VK_L);
        help.keyClick(KeyEvent.VK_O);
        help.keyClick(KeyEvent.VK_PERIOD);
        help.keyClick(KeyEvent.VK_T);
        help.keyClick(KeyEvent.VK_X);
        help.keyClick(KeyEvent.VK_T);
        help.keyClick(KeyEvent.VK_ENTER);
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
        help.wait(6*SPEED_MS);
        frame.dispose();
        frame = new TEKFrame();
    }
}
