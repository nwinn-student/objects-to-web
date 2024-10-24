package tekgui.window;
import java.awt.LayoutManager;


/**
 * Methods all Layouts will use.
 * 
 * @see {java.awt.LayoutManager}
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Layout extends LayoutManager{
    /**
     * Condenses the ObjectUI component into a FolderUI
     */
    public void condenseComponent();
}
