
import java.util.ArrayList;
import java.util.List;

/**
 * The CleanedHTMLExtractor class is responsible for collecting and storing the body content
 * after the comments have been removed from the HTML using HTMLCommentCleaner.
 * @author Coby Zhong
 * @version Oct, 4, 2024
 */
public class CleanedHTMLExtractor {
    private List<String> cleanedHTMLBody = new ArrayList<>();
    private boolean isInsideBody = false;  
    public void addCleanedLine(String cleanedLine) {
        if (cleanedLine.contains("<body>")) {
            isInsideBody = true;
            int bodyStartIndex = cleanedLine.indexOf("<body>") + "<body>".length();
            cleanedLine = cleanedLine.substring(bodyStartIndex).trim();
        }
        if (cleanedLine.contains("</body>")) {
            isInsideBody = false;
            int bodyEndIndex = cleanedLine.indexOf("</body>");
            cleanedLine = cleanedLine.substring(0, bodyEndIndex).trim();
        }

        if (isInsideBody && !cleanedLine.isEmpty()) {
        	 String[] splitByParagraphs = cleanedLine.split("</p>");
             for (String part : splitByParagraphs) {
                 String innerText = part.replaceAll("<p>", "").trim();
                 if (!innerText.isEmpty()) {
                     cleanedHTMLBody.add(innerText);
                 }
             }
        }
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
        for (String line : cleanedHTMLBody) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        HTMLCommentCleaner cleaner = new HTMLCommentCleaner();
        CleanedHTMLExtractor extractor = new CleanedHTMLExtractor();
        String[] sample = {
        		 "Hello, <!-- heyooooo -->it is <!-- WOAAAT-->pretty cool to be abl<!-- WOAAAT-->e to cl<!-- WOAAAT-->ean co<!-- WOAAAT-->mme<!-- WOAAAT-->nts from your co<!-- WOAAAT-->de right?",
                 "<head>",
                 "<title>Test Document</title>",
                 "</head>",
                 "<body>",
                 "<p>computer</p><p>science</p>",
                 "This is more body content blablabla.",
                 "<th>Table content</th>",
                 "</body>",
                 "</html>"
        };

        // Clean each line using HTMLCommentCleaner and add the result to CleanedHTMLExtractor
        for (String line : sample) {
            String cleanedLine = cleaner.clean(line); 
            extractor.addCleanedLine(cleanedLine);  
        }
        // Print it out
        extractor.printCleanedHTMLBody();
    }
}