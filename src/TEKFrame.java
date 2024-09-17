import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

/**
 * The Frame that will contain all of the visuals and handle them appropriately.
 *
 * @author Noah Winn
 * @version Sept. 16, 2024
 */
public class TEKFrame extends JFrame{
    // To be later used with save, creation, deletion, and modification of Objects
    private boolean unSavedChanges = false;
    private Dimension screenSize = null;
    /**
     * Constructor for objects of class TEKFrame
     * Titles the TEKFrame class and initializes it.
     */
    public TEKFrame(){
        super("TEK-GUI");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.addWindowListener(new TEKWindowAdapter(this));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width - (int)(screenSize.width/12.5),screenSize.height - (int)(screenSize.width/12.5));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        TEKPanel panel = new TEKPanel(this);
        scrollPane.setViewportView(panel);
        
        // Add menuBar and toolBar here, for fullscreen later we will want to pass in scrollPane
        // for now though panel should be fine 
        // (scrollPane.getViewport().getView() will get panel and panel.getParent().getParent() will get scrollPane)
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        // Adjust to subtract toolBar height and meuBar height later
        panel.setPreferredSize(new Dimension(
            (int)(getWidth()-getInsets().left-getInsets().right+10*scrollPane.getViewportBorderBounds().getWidth()),
            (int)(getHeight()-getInsets().top-getInsets().bottom+10*scrollPane.getViewportBorderBounds().getHeight())));
        
        this.add(scrollPane);
    }
    
    /**
     * Used to tell the fileManager to update the file information with the current, when Generating HTML has been complete.
     */
    public void save(){
        // to be done later, will allow for saving
    }
    /**
     * Used by the fileManager to ask whether it is worth it to save when the user tells the application to.
     * Also used by the windowAdapter to ask whether a save could occur.
     */
    public boolean unSavedChanges(){
        return unSavedChanges;
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
