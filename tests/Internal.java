import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.BorderLayout;
/**
 * Write a description of class Internal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Internal extends JInternalFrame{
    public Internal(){super();}
    public static JInternalFrame convertFrame(JFrame frame){
        JInternalFrame intern = new JInternalFrame(frame.getTitle(), true, true, true, true);
        intern.setJMenuBar(frame.getJMenuBar());
        intern.setSize(frame.getSize());
        intern.setLocation(frame.getLocation());
        while(frame.getContentPane().getComponents().length > 0){
            intern.add(frame.getContentPane().getComponents()[0], 
                ((BorderLayout)frame.getContentPane().getLayout()).getConstraints(frame.getContentPane().getComponents()[0]));
        }
        intern.setVisible(true);
        return intern;
    }
}
