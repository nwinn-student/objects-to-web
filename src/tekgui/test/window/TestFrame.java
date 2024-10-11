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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Write a description of class TestFrame here.
 *
 * @author Noah Winn
 * @version Oct. 11, 2024
 */
public class TestFrame{
    private static Internal internalFrame;
    private static JDesktopPane desktop;
    private static boolean isLogOpen = false;
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
        
        JButton redoTest; // redoes the current test
        JButton runTest; // runs the current test
        JButton runAllTests; // runs all tests in a section
        JButton haltTest; // halts the current test
        frame.setJMenuBar(menuBar);
        desktop = new JDesktopPane();
        UIManager.put("InternalFrame:InternalFrameTitlePane[Enabled].textForeground", Color.BLACK);
        desktop.updateUI();
        internalFrame = new Internal(cover);
        desktop.add(internalFrame);
        JScrollPane scrollPane = new JScrollPane(new ConsoleArea(0,100,false));
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
        tab.add("Tester",desktop);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            tab, panel);
        
        frame.setContentPane(split);
        //UIManager.getDefaults().forEach((x,y)->{System.out.println(x+ " : " +y);});
        initializeLog();
        try {
            internalFrame.setSelected(true);
        } catch (Exception e) {}
        cover.dispose();
        frame.setVisible(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        split.setDividerLocation((int)(frame.getHeight() - 60));
        split.setEnabled(false);
        split.setDividerSize(1);
        frame.getRootPane().setDefaultButton(log);
        return frame;
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
        //... should add JTree later w/ tests and the methods to allow for
        // group or singular selection
    }
    public static void addTests(Test... test){
        if(test == null){
            System.out.println("# FAILED TO ADD TESTS");
            return;}
        for(Test i : test){
            addTests(test);
        }
    }
}
