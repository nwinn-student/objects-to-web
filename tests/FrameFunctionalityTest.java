import java.awt.event.InputEvent;
/**
 * Improved tester class for the Frame UI Creation and Functionality 
 * feature.
 *
 * @author Noah Winn
 * @version Oct 2, 2024
 */
public class FrameFunctionalityTest{
    private static TEKFrame frame;
    public FrameFunctionalityTest(){
        if(TEKFile.getFrame() == null){
            frame = new TEKFrame();
        }
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
        frame = new TEKFrame();
    }
    // Individual tests
    public void canFrameShow(){
        frame.setTitle("Can Frame Show");
        Helper.wait(Helper.SPEED_MS);
    }
    public void canFrameIconify(){
        frame.setTitle("Can Frame Iconify");
        Helper.wait(Helper.SPEED_MS);
        //if(Helper.getSpeed() == Helper.MOUSE_INSTANT){
            
        //}
        //if(HYPER_MODE){
            Helper.mouseMove(Helper.getMinimize(frame));
        //} else{ Helper.smoothMove(Helper.getMinimize(frame)); }
        Helper.wait(Helper.SPEED_MS);
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    // All tests combined
    
}
