package tekgui;
import java.io.File;
import java.io.IOException;
import java.io.FileFilter;

// TEKGUI imports

// Java imports
/**
 * Write a description of class TEKManagement here.
 *
 * @author Noah Winn, Coby Zhong,
 * @version 10/23/2024
 */
public class TEKManagement{
    public TEKManagement(){}
    public static ObjectUI createObject(){
        return TEKFile.getFrame().getPanel().generateObject();
    }
    // Used when you know the file
    public static ObjectUI createObject(File file) throws IOException{
        if(file == null){throw new IOException("File is null.");}
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                createObject(f);
            }
            return null;
        }
        else if(file.isHidden() || !file.exists() || !file.canRead() || !file.canWrite()){
            return null;
        }
        else if(!file.getName().toLowerCase().endsWith(".html")){
            return null;
        }        
        ObjectUI obj = TEKFile.getFrame().getPanel().generateObject();
        obj.initFile(file);
        return obj;
    }
    public static void removeObject(){
        TEKFile.getFrame().getPanel().sweepSelected();
    }
    public static void removeAllObject(){
        TEKFile.getFrame().getPanel().clearObjects();
    }
    public static void selectAll(){
        TEKFile.getFrame().getPanel().selectAll();
    }
    public static void editView(){
        TEKFile.getFrame().getPopupMenu().transferAttached();
    }
}
