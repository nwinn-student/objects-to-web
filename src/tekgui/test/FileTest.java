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
    public void canFrameSave(){
        frame.setTitle("Can Frame Save");
        Helper.wait(Helper.SPEED_MS);
        
        Helper.smoothMove(Helper.getCenter(frame));
        Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        Helper.keyMaskedClick(KeyEvent.VK_S, KeyEvent.VK_CONTROL);
        Helper.wait(Helper.SPEED_MS);
        Helper.keyClick(KeyEvent.VK_TAB, 6); // not consistently 6, find a better way!
        //Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        //Helper.smoothMove(Helper.getCenter(frame.getJMenuBar().getMenu(0).getItem(1)));
        //Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        //Helper.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        // Click VK_DOWN
        //Helper.keyClick(KeyEvent.VK_DOWN);
        //Helper.keyClick(KeyEvent.VK_DOWN);
        //Helper.keyClick(KeyEvent.VK_ENTER);
        //Helper.keyClick(KeyEvent.VK_ENTER);
        //Helper.wait(20*Helper.SPEED_MS);
        //Helper.keyClick(KeyEvent.VK_TAB);
        //Helper.keyMaskedClick(KeyEvent.VK_TAB, KeyEvent.VK_ALT);
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
