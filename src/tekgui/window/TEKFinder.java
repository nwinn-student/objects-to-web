package tekgui.window;
import tekgui.TEKFile;
import tekgui.ObjectUI;
import tekgui.helper.ButtonBuilder;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FontMetrics;
import javax.swing.AbstractButton;
import javax.swing.text.JTextComponent;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.util.List;

// SpringLayout later
/**
 * Write a description of class TEKFinder here.
 *
 * @author Noah Winn
 * @version Oct. 29, 2024
 */
public class TEKFinder extends JPanel{
    private final TEKFinder finder = this;
    private static final transient Dimension baseSize = new Dimension(1,35);
    private static final transient Insets baseInset = new Insets(5, 0, 5, 0);
    private static final transient String collapse = "Collapse"; // String calls get expensive
    private static final transient String expand = "Expand";
    private final transient JButton expandButton;
    private final transient JTextField findText;
    private final transient JTextField replaceText;
    private ObjectUI current;
    private ObjectUI[] highlightList = new ObjectUI[32];
    private transient int index = 0;
    /**
     * Constructor for objects of class TEKFinder
     */
    public TEKFinder(){
        setVisible(false);
        setLayout(null); // Tried layouts, too complex
        setPreferredSize(baseSize);
        JLabel find = new JLabel("Find:");
        find.setLocation(5,5);
        find.setSize(55,25);
        find.setHorizontalAlignment(JLabel.RIGHT);
        FinderAdapter action = new FinderAdapter();
        findText = new JTextField(15);
        findText.setLocation(70,5);
        findText.setSize(150,25);
        findText.addActionListener(action);
        findText.setActionCommand("Find");
        add(find);
        add(findText);
        JButton prev = ButtonBuilder.addButton("Prev", this, action);
            ButtonBuilder.skinButton(prev);
            prev.setLocation(225, 5);
        JButton next = ButtonBuilder.addButton("Next", this, action);
            ButtonBuilder.skinButton(next);
            next.setLocation(285, 5);
        expandButton = ButtonBuilder.addButton(expand, this, action);
            ButtonBuilder.skinButton(expandButton);
            expandButton.setLocation(330, 5);
        JButton exit = ButtonBuilder.addButton("Exit", this, action);
            ButtonBuilder.skinButton(exit);
            exit.setLocation(400, 5);
        
        JLabel replace = new JLabel("Replace:");
            replace.setLocation(5, 35);
            replace.setSize(55,25);
            replace.setHorizontalAlignment(JLabel.RIGHT);
        replaceText = new JTextField(15);
            replaceText.addActionListener(action);
            replaceText.setActionCommand("Replace");
            replaceText.setFocusable(false);
            replaceText.setLocation(70, 35);
            replaceText.setSize(150, 25);
        add(replace);
        add(replaceText);        
    }
    private void highlightPrev(){
        if(current == null){
            
        }
    }
    private void highlightNext(){
        if(current == null && highlightList[index++] != null){
            current = highlightList[index];
            current.getLabel().setBackground(TEKLabel.getHighlightColor());
            return;
        }
        current.getLabel().setBackground(TEKLabel.getDefaultColor());
        current = highlightList[index];
        current.getLabel().setBackground(TEKLabel.getHighlightColor());
        
    }
    /**
     * work on, next/prev doesn't work rn, also gotta have it conn to content
     */
    private void triggerHighlight(boolean enable){
        if(enable){
            List<ObjectUI> list = TEKFile.getFrame().getPanel().getObjects();
            int index = 0;
            try{
                for(ObjectUI obj : list){
                    if(obj.getName().contains(findText.getText())){
                        // highlight
                        System.out.println(obj);
                        highlightList[index++] = obj;
                    }
                }
                
                if(highlightList[0] != null){
                    System.out.println(highlightList.length);
                    //highlightNext();
                }
            }
            catch(Exception e){System.out.println(e);}
            this.index = 0;
            return;
        }
        // List of highlighted?
        int len = highlightList.length;
        for(int i = 0; i < len; i++){
            if(highlightList[i] == null)
                break;
            highlightList[i].getLabel().setBackground(TEKLabel.getDefaultColor());
        }
        index = 0;
    }
    public void trigger(){
        setVisible(isVisible() ? false : true);
    }
    public void triggerSizeModification(){
        if(getPreferredSize().equals(baseSize)){
            setPreferredSize(new Dimension(1, 70));
            expandButton.setText(collapse);
            replaceText.setFocusable(true);
            ButtonBuilder.skinButton(expandButton);
            setVisible(false);
            setVisible(true);
        } else {
            setPreferredSize(baseSize);
            expandButton.setText(expand);
            replaceText.setFocusable(false);
            ButtonBuilder.skinButton(expandButton);
            setVisible(false);
            setVisible(true);
        }
    }
    private class FinderAdapter implements ActionListener{
        public void actionPerformed(ActionEvent a){
            switch(a.getActionCommand()){
                case "Find":
                    triggerHighlight(true);
                    break;
                case "Prev":
                    //highlightPrev();
                    break;
                case "Next":
                    //highlightNext();
                    break;
                case expand:
                    finder.triggerSizeModification();
                    break;
                case "Exit":
                    triggerHighlight(false);
                    trigger();
                    break;
            }
        }
    }
}
