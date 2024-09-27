import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
// up to you guys if you want to specifically mention each individual javax.swing utility or just do swing.* to cover all fields doesn't really matter either way ig

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
    static final int ICON_HEIGHT = 16;

    /**
     * Constructor for objects of class TEKFrame
     * Titles the TEKFrame class and initializes it.
     */
    public TEKFrame(){
        super("TEK-GUI");
        this.setDefaultCloseOperation(TEKFrame.EXIT_ON_CLOSE);
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
            // Create the menu bar and titles
                JMenuBar menuBar = new JMenuBar();
                String[] menuItems = {"File", "Edit", "Selection", "View", "Help"};
                for (String item : menuItems) {
                    JMenu menu = new JMenu(item);  // Create a new jMenu for each item
                    
                    // Add Open and Save items to the "File" menu
                    if (item.equals("File")) {
                        JMenuItem openItem = new JMenuItem("Open");
                        JMenuItem saveItem = new JMenuItem("Save");
                        menu.add(openItem);
                        menu.add(saveItem);
                        // Add action listeners for Open and Save ( more to come later just did this for now to make sure it won't cause issues for other aspects)
                        openItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Open option selected");
                            }
                        });
                        saveItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Save option selected");
                            }
                        });
                    }

                    //Add Undo and Redo items in "Edit" menu
                    if (item.equals("Edit")){
                        JMenuItem redoItem = new JMenuItem("Redo");
                        JMenuItem undoItem = new JMenuItem("Undo");
                        menu.add(undoItem);
                        menu.add(redoItem);

                        //Action listeners for Undo and Redo
                        redoItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Redo option selected");
                            }
                        });
                        undoItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Undo option selected");
                            }
                        });
                    }
                    menuBar.add(menu);
                }
                // Add the menu bar to the frame ( make sure this is only ever called once bc that gave me alot of problems and was hard to figure out .-.)
                this.setJMenuBar(menuBar);
        // for now though panel should be fine 
        // (scrollPane.getViewport().getView() will get panel and panel.getParent().getParent() will get scrollPane)
        
        //Create JToolBar
            JToolBar toolBar = new JToolBar();
           // toolBar.add(Box.createHorizontalGlue());
            toolBar.add(Box.createVerticalGlue());
            JButton button = new JButton("test");
            toolBar.add(button);    
                
        //Adding toolbar to pane
            getContentPane().add(toolBar, BorderLayout.NORTH);

            this.setVisible(true);
            this.setLocationRelativeTo(null);
        
        //Creating buttons with icons
            JButton redoButton = new JButton();
            JButton undoButton = new JButton();
       
        try{
            Image redoImage = ImageIO.read(getClass().getResource("/res/redo_arrow.png")).getScaledInstance(ICON_HEIGHT, ICON_HEIGHT, Image.SCALE_DEFAULT);
            Image undoImage = ImageIO.read(getClass().getResource("/res/undo_arrow.png")).getScaledInstance(ICON_HEIGHT, ICON_HEIGHT, Image.SCALE_DEFAULT);

            redoButton.setIcon(new ImageIcon(redoImage));
            undoButton.setIcon(new ImageIcon(undoImage));
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        //Adding icons to toolbar
            toolBar.add(undoButton);
            toolBar.add(redoButton);
            panel.setVisible(true);

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
