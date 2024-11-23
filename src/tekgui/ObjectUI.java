package tekgui;

// TEKGUI imports
import tekgui.window.TEKLabel;
import tekgui.window.TEKPanel;
import tekgui.text.CleanedHTMLExtractor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

// Java imports
import java.awt.Dimension;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.io.IOException;
import java.io.FileWriter;

/**
 * manage object properties
 *
 * @Mara Doze, Noah Winn
 * @11/23/24
 */
public class ObjectUI
{
    private String name;
    private String creationTime;
    private TEKLabel label;
    private File file;
    private List<String> content; // separated for a reason
    private List<String> alteredContent;
    private boolean canSave = false;
    // Collection to store ObjectUI instances with similar content
    private Collection<ObjectUI> similarContent = new HashSet<>();
    public ObjectUI(String name, Point position, Dimension size) //initialize objects
    {
        this.name = name;
        this.label = new TEKLabel(this);
        setPosition(position);
        setSize(size);
        label.setText(TEKPanel.formatObjectDetails(this));
    }
    private String getModifiedTime() { 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochMilli(file.lastModified());
        LocalDateTime modifiedDate =
            LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dtf.format(modifiedDate); //obtain timestamp
    }
    public String getName(){return name;}
    public void setName(String name) throws IOException{
        if(file != null){
            throw new IOException("File already exists, it cannot be adjusted.");
        }
        // Will be placed in the selected directory under this name?
        this.name = name;
        label.setText(TEKPanel.formatObjectDetails(this));
    }
    public TEKLabel getLabel(){ return label;}
    public void setLabel(TEKLabel label){this.label = label;}
    public String getCreationTime(){return creationTime;}
    public List<String> getContent(){return alteredContent;}
    public void setContent(List<String> content){
        this.alteredContent = new ArrayList<>(content); // Create a copy of the list to avoid external modifications
        this.canSave = true; // Mark that the content has been altered and can be saved
    }
    public boolean canSave(){return canSave;}
    public Point getPosition(){return label.getLocation();}
    public void setPosition(Point position){label.setLocation(position);}
    public Dimension getSize(){return label.getSize();}
    private void setSize(Dimension size){label.setSize(size);}
    public void initFile(File file) throws IOException{
        if(this.file != null){
            throw new IOException(file.getAbsolutePath()+"File already exists, it cannot be adjusted.");
        }
        this.file = file;
        if(file != null){
            this.name = file.getAbsolutePath(); // for locating the file
            this.creationTime = getModifiedTime();
            this.content = CleanedHTMLExtractor.cleanFile(file);
            this.alteredContent = new ArrayList<String>(content);
        }
        label.setText(TEKPanel.formatObjectDetails(this));
    }
    public File getFile(){return file;}
    @Override
    public String toString() {
        return "Name: " + name + ", Created: " + creationTime + ", Position: (" + getPosition().x + ", " + getPosition().y + "), Size: (" + getSize().width + " x " + getSize().height + ")";
        //returns the ObjectUI details in string format
    }
    // Method to generate a string that can be saved to a file (for saving object state)
    public String getDataToSave() {
        // Adjust when generating**
        return name + "," + getPosition().x + "," + getPosition().y + "," + getSize().width + "," + getSize().height + "," + creationTime;
    }

    // Static method to create an ObjectUI from saved data
    public static ObjectUI fromData(String data) {
        // Split the saved data string by commas to extract the properties
        String[] parts = data.split(",");
        String name = parts[0];
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int width = Integer.parseInt(parts[3]);
        int height = Integer.parseInt(parts[4]);
        String creationTime = parts[5];

        // Create the ObjectUI with parsed data
        ObjectUI objectUI = new ObjectUI(name, new Point(x, y), new Dimension(width, height));
        // Manually set the creation time
        objectUI.creationTime = creationTime;

        return objectUI;
    }
    // add an ObjectUI to similarContent if it has similar content
    public void addIfSimilarContent(ObjectUI other) {
        if (isSimilarContent(other)) {
            similarContent.add(other);
        }
    }
    // compare content of this ObjectUI with another
    private boolean isSimilarContent(ObjectUI other) {
        if(this.getContent() == null)
            return false;
        if(other == null)
            return false;
        if(other.getContent() == null)
            return false;
        // Very heavy**
        for(String val : this.getContent()){
            for(String lav : other.getContent()){
                if(val.equals(lav))
                    return true;
            }
        }
        return false;
    }
    // Retrieve similar content objects
    public Collection<ObjectUI> getSimilarContent() {
        return similarContent;
    }
    // Optional method to handle altered content connections
    public void updateContentConnection(ObjectUI altered) {
        if (!similarContent.contains(altered)) {
            similarContent.add(altered);
        }
    }
    /**
     * Method to generate HTML representation of this ObjectUI
     * Do not call when file exists****
     * Expected:
     *  <!DOCTYPE HTML>
     *  <html lang="en">
     *  <head>
     *      <!-- Generated by tekguidoc -->
     *      <title>object.getName()</title>
     *      <meta name="viewport" content="width=device-width, initial-scale=1">
     *      <meta charset="UTF-8">
     *      <meta name="author" content="John Doe">
     *      <meta name="keywords" content="documentation">
     *      <meta name="description" content="Sample description pertaining to the contents within this file">
     *      <meta name="generator" content="TEK-GUI">
     *  </head>
     *  <body>
     *      <!-- Content within object.getName() -->
     *      <div class='object-ui'>
     *          ...
     *      </div>
     *  </body>
     */
    public String generateHTML() {
        if(file != null){return null;}
        StringBuilder html = new StringBuilder(1023);
        
        html.append("<!DOCTYPE HTML>\n");
        html.append("<html lang='en'>\n");
        // header
        html.append("<head>\n");
            html.append("\t<!-- Generated by tekguidoc -->\n");
            html.append("\t<title>").append(name).append("</title>\n");
            html.append("\t<meta name='viewport' content='width=device-width, initial-scale=1'>\n");
            html.append("\t<meta charset='UTF-8'>\n");
            html.append("\t<meta name='author' content='John Doe'>\n");
            html.append("\t<meta name='keywords' content='documentation'>\n");
            html.append("\t<meta name='description' content='Sample description pertaining to the contents within this file'>\n");
            html.append("\t<meta name='generator' content='TEK-GUI'>\n");
        html.append("</head>\n");
        html.append("<body>\n");
            html.append("\t<!-- Content within object.getName() -->\n");
            html.append("\t<div class='object-ui'>\n");
                // loop through contents here
                for(String s : alteredContent){
                    html.append("\t\t").append(s).append("\n");
                }
            html.append("\t</div>\n");
        html.append("</body>\n");
        return html.toString();
    }
    /**
     * Method to create a text output file with the generated HTML
     * Only ever called when a file is not attached, meaning that the objectUI was created using VK_INSERT.
     */
    public void createHTMLTextFile() {
        String htmlContent = generateHTML();
        // warning that it may override an existing file, in such a case, should we allow it without question, or should we warn?
        try (FileWriter writer = new FileWriter(name)) {
            writer.write(htmlContent);
            System.out.println("HTML content successfully written to " + name);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    
    /**
     * Save the object as an HTML file immediately after creation
     */
    private void saveAsHTML() {
        if(file == null){
            createHTMLTextFile();  // Call the method to save the HTML file
        } else {
            // not created yet, will rely on both content and alteredContent and is overall much more complex?
        }
    }
    
    public void editContent(List<String> newContent) { //edits object content
        setContent(newContent);
        label.setText(TEKPanel.formatObjectDetails(this));
    }
}