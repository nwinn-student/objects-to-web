package tekgui;
import java.io.File;
import java.io.IOException;
import java.io.FileFilter;
import java.util.List;
import java.util.HashSet;
import java.util.Collection;
import java.io.FilenameFilter;

// Java imports
/**
 * Object creation en masse and links to singular creation in TEKPanel.  
 * Links to many other TEKPanel methods for convience.  
 * Supports object relationship.
 *
 * @author Mara Doze, Noah Winn, Coby Zhong
 * @version 11/1/2024
 */
public class TEKManagement{
    private static final String ext = ".html";
    /**
     * TEKManagement Constructor
     *
     */
    public TEKManagement(){}
    /**
     * Creates an objectUI and places it on the screen.
     *
     * @return the objectUI
     */
    public static ObjectUI createObject(){
        return TEKFile.getFrame().getPanel().generateObject();
    }
    /**
     * Creates a file with a specified file.
     *
     * @param file the file
     * @return the objectUI
     */
    public static ObjectUI createObject(File file) throws IOException{
        if(file == null){throw new IOException("File is null.");}
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                if(!f.isHidden()){
                    
                    createObject(f);
                }
                
            }
            return null;
        }
        else if(file.isHidden() || !file.canRead() || !file.canWrite()){
            return null;
        }
        else if(!file.getName().toLowerCase().endsWith(ext)){
            return null;
        }
        long startTime = System.currentTimeMillis();
        ObjectUI obj = TEKFile.getFrame().getPanel().generateObject();
        
        obj.initFile(file);
        System.out.println("OBJ TIME: "+(System.currentTimeMillis() - startTime) + " ms");

        return obj;
    }
    /**
     * Duplicates an object.
     *
     * @param obj An objectUI
     * @return objectUI
     */
    public static ObjectUI createObject(ObjectUI obj){
        ObjectUI newObj = TEKFile.getFrame().getPanel().generateObject();
        try{
            // We may not want to necessarily want to allow some elements to be duplicated
            // Mainly, those with
            newObj.initFile(obj.getFile());
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return newObj;
    }
    /**
     * Duplicates objects.
     *
     * @param obj list of objectUIs
     * @return array of objectUIs
     */
    public static ObjectUI[] createObject(List<ObjectUI> obj){
        ObjectUI[] newObj = new ObjectUI[obj.size()];
        int i = 0;
        int size = obj.size();
        while(i < size){
            newObj[i] = createObject(obj.get(i));
            i++;
        }
        return newObj;
    }
    /**
     * Removes all of the selected objectUIs
     *
     */
    public static void removeObject(){
        TEKFile.getFrame().getPanel().sweepSelected();
    }
    /**
     * Removes all of the objectUIs
     *
     */
    public static void removeAllObject(){
        TEKFile.getFrame().getPanel().clearObjects();
    }
    /**
     * Selects all of the objectUIs
     *
     */
    public static void selectAll(){
        TEKFile.getFrame().getPanel().selectAll();
    }
    /**
     * Opens up a new editView.
     *
     */
    public static void editView(){
        TEKFile.getFrame().getPopupMenu().transferAttached();
    }
    /**
     * Connects ObjectUI instances with similar content.
     */
    public static void connectSimilarContentObjects() {
        Collection<ObjectUI> allObjects = getAllObjects();
        for (ObjectUI obj1 : allObjects) {
            for (ObjectUI obj2 : allObjects) {
                if (!obj1.equals(obj2)) {
                    obj1.addIfSimilarContent(obj2);
                }
            }
        }
    }
    /**
     * Retrieves all ObjectUI instances managed by the application.
     * 
     * @return a collection of all ObjectUI instances
     */
    public static Collection<ObjectUI> getAllObjects() {
        return new HashSet<>(TEKFile.getFrame().getPanel().getObjects());
    }
    /**
     * @param objects the objects to generate html
     * @return the html string
     */
    public static String generateFullHTML(Collection<ObjectUI> objects) { // combines multiple objectUI instances into a single HMTL doc
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>ObjectUI Summary</title></head><body>");
        for (ObjectUI obj : objects) {
            html.append(obj.generateHTML());
        }
        html.append("</body></html>");
        return html.toString();
    }
}
