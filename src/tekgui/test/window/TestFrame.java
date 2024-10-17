package tekgui.test.window;

// TEKGUI imports
import tekgui.window.TEKFrame;
import tekgui.window.TEKMenuBar;
import tekgui.test.Test;

// Java imports
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
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Write a description of class TestFrame here.
 *
 * @author Noah Winn
 * @version Oct. 11, 2024
 */
public class TestFrame{
    private static Internal internalFrame;
    private static JDesktopPane desktop;
    private static TestTree tree;
    private static boolean isLogOpen = false;
    public static JFrame createTestFrame(){
        
        TEKFrame cover = new TEKFrame();
        cover.setVisible(false);
        JFrame frame = new JFrame("TestFrame");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setJMenuBar(new TestMenuBar());
        desktop = new JDesktopPane();
        internalFrame = new Internal(cover);
        desktop.add(internalFrame);
        JScrollPane scrollPane = new JScrollPane(new ConsoleArea(0,60,false));
        JTabbedPane tab = new JTabbedPane();
        JPanel panel = new JPanel();
        JLabel lab = new JLabel("Credit to L. Cornelius Dol.  Source: https://stackoverflow.com/questions/342990/");
        panel.add(lab, BorderLayout.CENTER);
        JButton log = new JButton("Log");
        log.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!isLogOpen){
                    tab.add("Log", scrollPane);
                    System.out.println("## LOG OPENED ["+System.currentTimeMillis()+"] ##");
                } else {
                    tab.remove(scrollPane);
                    System.out.println("## LOG CLOSED ["+System.currentTimeMillis()+"] ##");
                }
                isLogOpen = !isLogOpen;
            }
        });
        panel.add(log, BorderLayout.EAST);
        tree = new TestTree();
        tab.add("Tester",desktop);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
            tree, tab);
        split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
            new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if(desktop.getAllFrames().length > 0){
                    ((Internal)desktop.getAllFrames()[0]).updateSize();
                }
            }
        });
        frame.getRootPane().setDefaultButton(log);
        frame.getContentPane().add(new TestToolBar(), BorderLayout.PAGE_START);
        frame.getContentPane().add(split, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.PAGE_END);
        //UIManager.getDefaults().forEach((x,y)->{System.out.println(x+ " : " +y);});
        initializeLog();
        cover.dispose();
        
        //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        frame.setVisible(true);
        split.setDividerLocation((int)(frame.getWidth()*0.1));        
        return frame;
    }
    public static Internal respawnInternal(TEKFrame frame, boolean isSaved){
        if(frame == null){return null;}
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
    private static void initializeLog(){
        System.out.println("## SYSTEM LOG ##");
        System.out.println("# General Information:");
        System.out.println("# \tSample text.");
        System.out.println("# \tSample text.");
        System.out.println("## END ##");
    }
    public static void addTests(Test test){
        // to do later
        if(test == null){
            System.out.println("# FAILED TO ADD TESTS");
            return;}
        tree.add(test);
        //... should add JTree later w/ tests and the methods to allow for
        // group or singular selection
    }
    /**
     * Method addTests
     *
     * @param test A parameter
     */
    public static void addTests(Test... test){
        if(test == null){
            System.out.println("# FAILED TO ADD TESTS");
            return;}
        for(Test i : test){
            addTests(i);
        }
    }
    public static TestTree getTree(){return tree;}
}
