import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GraphicsEnvironment;

/**
 * Write a description of class TestFrame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestFrame{
    private static Internal internalFrame;
    private static JDesktopPane desktop;
    public static JFrame createTestFrame(){
        TEKFrame cover = new TEKFrame();
        JFrame frame = new JFrame("TestFrame");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JMenuBar menuBar = new JMenuBar();
        TEKMenuBar.addMenuItem("Exit", 
            TEKMenuBar.addMenu("File", menuBar, KeyEvent.VK_F, "Use Alt-F to select File."), 
            KeyEvent.VK_Q, "Use Ctrl-Q to quit.");
        
        frame.setJMenuBar(menuBar);
        desktop = new JDesktopPane();
        UIManager.put("InternalFrame:InternalFrameTitlePane[Enabled].textForeground", Color.BLACK);
        desktop.updateUI();
        internalFrame = new Internal(cover);
        desktop.add(internalFrame);
        JScrollPane scrollPane = new JScrollPane(new ConsoleArea(0,100,false));
        JPanel panel = new JPanel();
        //panel.setLayout(null);
        panel.add(new JLabel("Credit to L. Cornelius Dol.  Source: https://stackoverflow.com/questions/342990/"));
        JSplitPane credit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            panel, scrollPane);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            desktop, credit);
        
        frame.setContentPane(split);
        UIManager.getDefaults().forEach((x,y)->{System.out.println(x+ " : " +y);});

        try {
            internalFrame.setSelected(true);
        } catch (Exception e) {}
        cover.dispose();
        frame.setVisible(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        System.out.println(frame.getHeight());
        split.setDividerLocation((int)(frame.getHeight() * 0.75));
        credit.setDividerLocation((int)(frame.getHeight() * 0.05));return frame;
    }
    public static Internal respawnInternal(TEKFrame frame, boolean isSaved){
        frame.setVisible(false);
        frame.setSaved(isSaved);
        if(internalFrame != null){
            desktop.remove(internalFrame);
            internalFrame.dispose();
        }
        internalFrame = new Internal(frame);
        desktop.add(internalFrame);
        return internalFrame;
    }
}
