package tekgui.window;

// TEKGUI imports
import tekgui.TEKFile;
import tekgui.ObjectUI;
import tekgui.adapter.TEKFrameAdapter;
import tekgui.adapter.UndoManager;

// Java imports
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.util.Collection;
/**
 * The Frame that will contain all of the visuals and handle them appropriately.
 *
 * @author Noah Winn
 * @version Nov. 9, 2024
 */
public class TEKFrame extends JFrame{
    // To be later used with save, creation, deletion, and modification of Objects
    private boolean saved = true; // set to false when a change occurs
    private Dimension screenSize = null;
    private TEKPanel panel; // Reference to the main panel
    private TEKPopupMenu popupMenu;
    private UndoManager manager = new UndoManager();
    private TEKFinder finder;
    private MainWindow settings = new MainWindow();
    /**
     * Constructor for objects of class TEKFrame
     * Titles the TEKFrame class and initializes it.
     */
    public TEKFrame(){
        super("TEK-GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        addWindowListener(new TEKFrameAdapter());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width - (int)(screenSize.width/12.5),screenSize.height - (int)(screenSize.width/12.5));
        TEKFile.setFrame(this); // reference for this TEKFrame
        popupMenu = new TEKPopupMenu();
        setIconImage(new ImageIcon(getClass().getResource("/res/iconGrade.png")).getImage());
        
        // Add menuBar and toolBar here, for fullscreen later we will want to pass in scrollPane
        setJMenuBar(new TEKMenuBar());
        getContentPane().add(new TEKToolBar(), BorderLayout.NORTH);
        finder = new TEKFinder();
        getContentPane().add(finder, BorderLayout.SOUTH);
        // for now though panel should be fine 
        // (scrollPane.getViewport().getView() will get panel and panel.getParent().getParent() will get scrollPane)
        
        // JScrollPane errored once saying that I input Boolean when it shouldve been Color.
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        panel = new TEKPanel(this);
        scrollPane.setViewportView(panel.generateLayer());
        
        setVisible(true);
        setLocationRelativeTo(null);
        
        // Adjust to subtract toolBar height and meuBar height later
        panel.setPreferredSize(new Dimension(
            (int)(getWidth()-getInsets().left-getInsets().right+10*scrollPane.getViewportBorderBounds().getWidth()),
            (int)(getHeight()-getInsets().top-getInsets().bottom+10*scrollPane.getViewportBorderBounds().getHeight())));
        getContentPane().setFocusable(true); // intended to ensure that eyes are not distracted
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();
        
        // load previous
        int[] loadedLoc = FrameLocationManager.loadFrameState(screenSize.width, screenSize.height);
        if(loadedLoc != null){
            setBounds(loadedLoc[0], loadedLoc[1], loadedLoc[2], loadedLoc[3]);
        }
    }
    public TEKPanel getPanel(){return panel;}
    public TEKPopupMenu getPopupMenu(){return popupMenu;}
    public UndoManager getUndoManager(){return manager;}
    public TEKFinder getFinder(){return finder;}
    public MainWindow getSettings(){return settings;}
    /**
     * 
     */
    public void save(){
        saved = true;
        // call to TEKFile to do a save
    }
    public void setSaved(boolean saved){
        this.saved = saved;
    }
    /**
     * Used by the fileManager to ask whether it is worth it to save when the user tells the application to.
     * Also used by the windowAdapter to ask whether a save could occur.
     */
    public boolean hasSaved(){
        return saved;
    }
    // method to display or interact with similar content of a given ObjectUI instance
    public void showSimilarContent(ObjectUI object) {
        Collection<ObjectUI> similarObjects = object.getSimilarContent();
        
        if (similarObjects.isEmpty()) {
            System.out.println("No similar content found for this object.");
        } else {
            System.out.println("Similar content objects:");
            for (ObjectUI similarObject : similarObjects) {
                // Replace with actual logic to display or interact with similarObject
                System.out.println(" - " + similarObject.toString()); // Assuming ObjectUI has a meaningful toString()
            }
        }
    }

}
