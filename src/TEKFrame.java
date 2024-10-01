import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.BorderLayout;

/**
 * The Frame that will contain all of the visuals and handle them appropriately.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKFrame extends JFrame{
    // To be later used with save, creation, deletion, and modification of Objects
    private boolean saved = false;
    private Dimension screenSize = null;
    private TEKPanel panel; // Reference to the main panel 
    /**
     * Constructor for objects of class TEKFrame
     * Titles the TEKFrame class and initializes it.
     */
    public TEKFrame(){
        super("TEK-GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.addWindowListener(new TEKWindowAdapter(this));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width - (int)(screenSize.width/12.5),screenSize.height - (int)(screenSize.width/12.5));
        TEKFile.setFrame(this); // reference for this TEKFrame

        setJMenuBar(new TEKMenuBar());
        add(new TEKToolBar(), BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        panel = new TEKPanel(this);
        scrollPane.setViewportView(panel);
        
        // Add menuBar and toolBar here, for fullscreen later we will want to pass in scrollPane
        // for now though panel should be fine 
        // (scrollPane.getViewport().getView() will get panel and panel.getParent().getParent() will get scrollPane)
        // Initialize the list of ObjectUIs
        
        createSampleObjects(panel); // create sample objects
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        // Adjust to subtract toolBar height and meuBar height later
        panel.setPreferredSize(new Dimension(
            (int)(getWidth()-getInsets().left-getInsets().right+10*scrollPane.getViewportBorderBounds().getWidth()),
            (int)(getHeight()-getInsets().top-getInsets().bottom+10*scrollPane.getViewportBorderBounds().getHeight())));
        
        this.add(scrollPane);
        pack();
    }
    public TEKPanel getPanel(){return panel;}
     // Method to create sample ObjectUI 
    private void createSampleObjects(TEKPanel panel) {
        //sample objects, name, position, and size
        ArrayList<ObjectUI> objects = new ArrayList<>();
        objects.add(new ObjectUI("Object 1", new Point(50, 50), new Dimension(300, 100)));
        objects.add(new ObjectUI("Object 2", new Point(500, 250), new Dimension(300, 100)));
        objects.add(new ObjectUI("Object 3", new Point(100, 300), new Dimension(300, 100)));

        // Display the objects in the panel
        panel.displayObjects(objects);
    }
    /**
     * 
     */
    public void save(){
        saved = true;
    }
    /**
     * Used by the fileManager to ask whether it is worth it to save when the user tells the application to.
     * Also used by the windowAdapter to ask whether a save could occur.
     */
    public boolean hasSaved(){
        return saved;
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                TEKFrame fram = new TEKFrame();
            }
        });
    }
}
