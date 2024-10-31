import java.io.*;
import java.util.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class RecentFilesManager {
    private static final int MAX_RECENT_FILES = 5; // Maximum number of recent files
    private final List<File> recentFiles;

    public RecentFilesManager() {
        recentFiles = new ArrayList<>();
        loadRecentFiles(); // Load files from disk if needed
    }

    // Add a new file to the recent files list
    public void addFile(File file) {
        recentFiles.remove(file); // Remove if already exists to reorder
        recentFiles.add(0, file); // Add to the top of the list

        if (recentFiles.size() > MAX_RECENT_FILES) {
            recentFiles.remove(MAX_RECENT_FILES); // Maintain size limit
        }

        saveRecentFiles(); // Save to disk
    }

    // Get the recent files list
    public List<File> getRecentFiles() {
        return Collections.unmodifiableList(recentFiles);
    }

    // Load recent files from disk
    private void loadRecentFiles() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("recentFiles.dat"))) {
            List<File> files = (List<File>) in.readObject();
            recentFiles.addAll(files);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No recent files loaded: " + e.getMessage());
        }
    }

    // Save recent files to disk
    private void saveRecentFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("recentFiles.dat"))) {
            out.writeObject(recentFiles);
        } catch (IOException e) {
            System.err.println("Failed to save recent files: " + e.getMessage());
        }
    }
}
