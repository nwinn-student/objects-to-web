package tekgui.text;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The CleanedHTMLExtractor class is responsible for collecting and storing the body content
 * after the comments have been removed from the HTML using HTMLCommentCleaner.
 *  Altered to suit the needs of files.
 * @author Coby Zhong, Noah Winn
 * @version Nov. 17, 2024
 */
public class CleanedHTMLExtractor {
    private List<String> cleanedHTMLBody = new ArrayList<>();
    private boolean isInsideBody = false;
    private static final String bodyStart = "<body";
    private static final String bodyEnd = "</body";
    private static boolean addCleanedLine(String line, List<String> body, boolean inner){
        if (line.contains(bodyStart)) {
            inner = true;
            int bodyStartIndex = line.indexOf(bodyStart) + 5;
            line = line.substring(bodyStartIndex);
            bodyStartIndex = line.indexOf(">") + 1;
            line = line.substring(bodyStartIndex).trim();
        }
        if (line.contains(bodyEnd)) {
            inner = false;
            int bodyEndIndex = line.indexOf(bodyEnd);
            line = line.substring(0, bodyEndIndex).trim();
        }

        if (inner && !line.isEmpty()) {
             String[] splitByParagraphs = line.split("</p>");
             for (String part : splitByParagraphs) {
                 String innerText = part.replaceAll("<p>", "").trim();
                 if (!innerText.isEmpty()) {
                     body.add(innerText);
                 }
             }
        }
        return inner;
    }    
    public void addCleanedLine(String cleanedLine) {
        addCleanedLine(cleanedLine, cleanedHTMLBody, isInsideBody);
    }

    /**
     * Retrieves the list of cleaned HTML body content.
     *
     * @return A list of cleaned HTML body content lines.
     */
    public List<String> getCleanedHTMLBody() {
        return cleanedHTMLBody;
    }

    /**
     * Prints the cleaned HTML body content stored in the list.
     */
    public void printCleanedHTMLBody() {
        cleanedHTMLBody.stream().forEach((x)->{System.out.println(x);});
    }

    public static List<String> cleanFile(File file) throws IOException{
        if(!file.exists() || !file.canRead() || !file.canWrite() || file.isHidden()){
            throw new IOException();
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<String> cleanedHTMLBody = new ArrayList<>();
        boolean isInsideBody = false;
        boolean isCommentOpen = false;
        final String commentOpener = "<!--";
        final String commentCloser = "-->";
        while ((line = reader.readLine()) != null) {
            isInsideBody = addCleanedLine(
                HTMLCommentCleaner.clean(line, commentOpener, commentCloser,isCommentOpen),
                cleanedHTMLBody,
                isInsideBody
            ); // For some reason booleans input were not changing correctly, odd that Java doesn't support reference inputs for primitives
        }
        reader.close();
        return cleanedHTMLBody;
    }
}