import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.JInternalFrame;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
/**
 * Write a description of class FunctionalityTester here.
 *
 * @author Noah Winn
 * @version Oct 2, 2024
 */
public class MainTester{
    public static void main(String[] args){
        //Helper help = new Helper();
        //help.displayOptions();
        //FrameFunctionalityTest frameFunc = new FrameFunctionalityTest();
        //frameFunc.run();
        
        //TEKFile.getFrame().dispose();
        //System.exit(0);
        TEKFrame cover = new TEKFrame();
        JFrame frame = new JFrame("Parent");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        frame.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                // too scared to remove this honestly
                if(e.getKeyCode() == KeyEvent.VK_F4 && e.isAltDown()){
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        frame.setLayout(null);
        
        JDesktopPane pan = new JDesktopPane();
        pan.updateUI();
        
        JInternalFrame f = Internal.convertFrame(cover);
        pan.add(f);
        try {
            f.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        frame.setContentPane(pan);
        //frame.add(f);
        cover.dispose();
        frame.setVisible(true);
    }
}
