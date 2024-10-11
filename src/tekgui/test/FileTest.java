package tekgui.test;

import tekgui.test.window.*;
import tekgui.TEKFile;
import tekgui.window.TEKFrame;
import tekgui.helper.Helper;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
/**
 * Write a description of class FileTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FileTest extends Test {
    private static Internal frame;
    public FileTest(){
        frame = TestFrame.respawnInternal(new TEKFrame(), true);
    }
    public void canFrameSave(){
        frame.setTitle("Can Frame Save");
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothMove(Helper.getCenter(frame.getJMenuBar().getMenu(0)));
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        // Click VK_DOWN
        Helper.keyClick(KeyEvent.VK_DOWN);
        Helper.keyClick(KeyEvent.VK_DOWN);
        Helper.keyClick(KeyEvent.VK_ENTER);
        Helper.wait(Helper.SPEED_MS);
        Helper.mouseMove(Helper.getCenter());
        Helper.wait(Helper.SPEED_MS);
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        Helper.wait(Helper.SPEED_MS);
        //Helper.keyClick(KeyEvent.VK_H);
        //Helper.keyClick(KeyEvent.VK_E);
        //Helper.keyClick(KeyEvent.VK_L);
        //Helper.keyClick(KeyEvent.VK_L);
        //Helper.keyClick(KeyEvent.VK_O);
        //Helper.keyClick(KeyEvent.VK_PERIOD);
        //Helper.keyClick(KeyEvent.VK_T);
        //Helper.keyClick(KeyEvent.VK_X);
        //Helper.keyClick(KeyEvent.VK_T);
        //Helper.wait(Helper.SPEED_MS);
        //Helper.keyClick(KeyEvent.VK_ENTER);
    }
    
    public void canFrameOpen(){
        // Not looking for the loading functionality as it does not exist, but purely to opening of the JOptionPane
        frame.setTitle("Can Frame Open");
        Helper.wait(Helper.SPEED_MS);
        Helper.smoothMove(Helper.getCenter(frame.getJMenuBar().getMenu(0)));
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        // Click VK_DOWN
        Helper.keyClick(KeyEvent.VK_DOWN);
        Helper.keyClick(KeyEvent.VK_ENTER);
        Helper.wait(Helper.SPEED_MS);
        Helper.keyClick(KeyEvent.VK_H);
        Helper.keyClick(KeyEvent.VK_E);
        Helper.keyClick(KeyEvent.VK_L);
        Helper.keyClick(KeyEvent.VK_L);
        Helper.keyClick(KeyEvent.VK_O);
        Helper.keyClick(KeyEvent.VK_PERIOD);
        Helper.keyClick(KeyEvent.VK_T);
        Helper.keyClick(KeyEvent.VK_X);
        Helper.keyClick(KeyEvent.VK_T);
        Helper.keyClick(KeyEvent.VK_ENTER);
    }
}
