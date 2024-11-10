package tekgui;
import tekgui.adapter.UndoManager;
import tekgui.adapter.UndoableAction;
import java.io.File;
import java.io.IOException;
import java.io.FileFilter;
import java.util.List;
import java.util.HashSet;
import java.util.Collection;
import java.io.FilenameFilter;
import java.util.ArrayList;

// Java imports
/**
 * Object creation en masse and links to singular creation in TEKPanel.  
 * Links to many other TEKPanel methods for convience.  
 * Supports object relationship.
 *
 * @author Mara Doze, Noah Winn, Coby Zhong
 * @version 11/9/2024
 */
public class TEKManagement{
    private static final String ext = ".html";
    /**
     * TEKManagement Constructor
     *
     */
    private TEKManagement(){}
    /**
     * Creates an objectUI and places it on the screen.
     *
     * @return the objectUI
     */
    public static ObjectUI createObject(){
        ObjectUI object = TEKFile.getFrame().getPanel().generateObject();
        TEKFile.getFrame().getUndoManager().addAction(new UndoableAction(object, UndoableAction.Variant.CREATE));
        return object;
    }
    /**
     * Creates a file with a specified file.
     *
     * @param file the file
     * @return the objectUI
     */
    public static List<ObjectUI> createObject(File file) throws IOException{
        if(file == null){return null;}
        List<File> htmlFile = getHTMLFiles(file);
        List<ObjectUI> object = new ArrayList<>(htmlFile.size());
        for(File f : htmlFile){
            ObjectUI obj = TEKFile.getFrame().getPanel().generateObject();
            obj.initFile(f);
            object.add(obj);
        }
        TEKFile.getFrame().getUndoManager().addAction(new UndoableAction(object, UndoableAction.Variant.CREATE));
        return object;
    }
    /**
     * Gets a list of Files from an Object that MUST be HTML files
     */
    private static List<File> getHTMLFiles(File file) throws IOException{
        List<File> htmlFile = new ArrayList<>(16); // Assume 16
        trueReceiverHTML(file, htmlFile);
        return htmlFile;
    }
    private static void trueReceiverHTML(File file, List<File> htmlFile) throws IOException {
        if(file == null){throw new IOException("File is null.");}
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                if(!f.isHidden()){
                    trueReceiverHTML(f, htmlFile);
                }
            }
            return;
        }
        else if(file.isHidden() || !file.canRead() || !file.canWrite()){
            return;
        }
        else if(!file.getName().toLowerCase().endsWith(ext)){
            return;
        }
        htmlFile.add(file);
    }
    /**
     * Duplicates an object.
     *
     * @param obj An objectUI
     * @return objectUI
     */
    public static ObjectUI createObject(ObjectUI obj){
        if(obj == null){return null;}
        ObjectUI newObj = TEKFile.getFrame().getPanel().generateObject();
        try{
            // We may not want to necessarily want to allow some elements to be duplicated
            // Mainly, those with content?
            newObj.initFile(obj.getFile());
        } catch (Exception ioe){
            ioe.printStackTrace();
        }
        
        TEKFile.getFrame().getUndoManager().addAction(new UndoableAction(newObj, UndoableAction.Variant.CREATE));
        return newObj;
    }
    /**
     * Duplicates objects.
     *
     * @param obj list of objectUIs
     * @return array of objectUIs
     */
    public static ObjectUI[] createObject(List<ObjectUI> obj){
        if(obj == null){return null;}
        ObjectUI[] newObj = new ObjectUI[obj.size()];
        int i = 0;
        int size = obj.size();
        while(i < size){
            ObjectUI object = TEKFile.getFrame().getPanel().generateObject();
            try
            {
                object.initFile(obj.get(i).getFile());
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            newObj[i] = createObject(obj.get(i));
            i++;
        }
        TEKFile.getFrame().getUndoManager().addAction(new UndoableAction(newObj, UndoableAction.Variant.CREATE));
        return newObj;
    }
    /**
     * Removes all of the selected objectUIs
     *
     */
    public static void removeObject(){
        TEKFile.getFrame().getUndoManager().addAction(new UndoableAction(TEKFile.getFrame().getPanel().getSelected(), UndoableAction.Variant.DELETE));
        TEKFile.getFrame().getPanel().sweepSelected();
    }
    /**
     * Removes all of the objectUIs
     *
     */
    public static void removeAllObject(){
        TEKFile.getFrame().getUndoManager().addAction(new UndoableAction(TEKFile.getFrame().getPanel().getObjects(), UndoableAction.Variant.DELETE));
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
