package tekgui;
import java.io.File;
import java.io.IOException;
import java.io.FileFilter;
import java.util.List;

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
    public static ObjectUI createObject(ObjectUI obj){
        ObjectUI newObj = TEKFile.getFrame().getPanel().generateObject();
        try{
            newObj.initFile(obj.getFile());
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return newObj;
    }
    public static ObjectUI[] createObject(List<ObjectUI> obj){
        ObjectUI[] newObj = new ObjectUI[obj.size()];
        int i = 0;
        while(i < obj.size()){
            newObj[i] = createObject(obj.get(i));
            i++;
        }
        return newObj;
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
