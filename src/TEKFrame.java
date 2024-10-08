import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

/**
 * The Frame that will contain all of the visuals and handle them appropriately.
 *
 * @author Noah Winn
 * @version Oct. 8, 2024
 */
public class TEKFrame extends JFrame{
    // To be later used with save, creation, deletion, and modification of Objects
    private boolean saved = true; // set to false when a change occurs
    private Dimension screenSize = null;
    private TEKPanel panel; // Reference to the main panel
    private TEKPopupMenu popupMenu = new TEKPopupMenu();
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

        setIconImage(new ImageIcon(getClass().getResource("/res/iconGrade.png")).getImage());
        
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
        
        setVisible(true);
        setLocationRelativeTo(null);
        
        // Adjust to subtract toolBar height and meuBar height later
        panel.setPreferredSize(new Dimension(
            (int)(getWidth()-getInsets().left-getInsets().right+10*scrollPane.getViewportBorderBounds().getWidth()),
            (int)(getHeight()-getInsets().top-getInsets().bottom+10*scrollPane.getViewportBorderBounds().getHeight())));
        
        add(scrollPane);
        pack();
    }
    public TEKPanel getPanel(){return panel;}
    public TEKPopupMenu getPopupMenu(){return popupMenu;}
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
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                TEKFrame fram = new TEKFrame();
            }
        });
    }
}
