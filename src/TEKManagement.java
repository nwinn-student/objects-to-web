
/**
 * Write a description of class TEKManagement here.
 *
 * @author Noah Winn, Coby Zhong,
 * @version 10/1/2024
 */
public class TEKManagement{
    public TEKManagement(){}
    public static void createObject(){
        // Coby here
    }
    public static void removeObject(){
        TEKFile.getFrame().getPanel().sweepSelected();
    }
    public static void removeAllObject(){
        TEKFile.getFrame().getPanel().clearObjects();
    }
}
