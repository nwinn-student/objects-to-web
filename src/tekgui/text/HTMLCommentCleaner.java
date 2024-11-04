package tekgui.text;
/**
 * The HTMLCommentCleaner class holds methods pertaining to cleaning HTML code of distractions that needn't be reviewed, comments.
 *
 * @author Noah Winn
 * @version Oct. 23, 2024
 */
public class HTMLCommentCleaner{
    private boolean isCommentOpen = false;
    private String commentOpener = "<!--"; // should work with any comment scheme
    private String commentCloser = "-->"; // should work with any comment scheme
    public HTMLCommentCleaner(){}
    /**
     * Retrieves the comment open that will be used to clean the input text.  For java it is /** for javadoc and /* for multiline and // for single line.
     * return comment opener
     */
    public String getCommentOpener(){return commentOpener;}
    /**
     * Retrieves the comment closer that will be used to clean the input text.  For java it is astericks/ for javadoc and multiline, with \n for single line.
     * @return comment closer
     */
    public String getCommentCloser(){return commentCloser;}
    /**
     * Sets the comment opener that will be used to clean the input text.  For java it is /** for javadoc and /* for multiline and // for single line.
     * @param comment opener
     */
    public void setCommentOpener(String commentOpener){this.commentOpener = commentOpener;}
    /**
     * Sets the comment closer that will be used to clean the input text.  For java it is astericks/ for javadoc and multiline, with \n for single line.
     * @param comment closer
     */
    public void setCommentCloser(String commentCloser){this.commentCloser = commentCloser;}
    /**
     * Cleans the line of any HTML comments, expected to take in default charset
     * 
     * @param line the string
     * @return the cleaned string
     */
    public String clean(String line){
        return clean(line, commentOpener, commentCloser, isCommentOpen);
    }
    /**
     * Redo this later, VERY inefficient
     */
    public static String clean(String line, String commentOpener, String commentCloser, boolean isOpen){
        // Look for <!-- all in a row, if so isCommentOpen = true until it -->
        if(line.contains(commentOpener) && !isOpen){
            // find that comment and beat it up
            String prior = line.substring(0,line.indexOf(commentOpener));
            String after = line.substring(line.indexOf(commentOpener)+commentOpener.length()-1, line.length());
            if(!after.contains(commentCloser)){
                isOpen = true;
                return prior;
            } else {
                after = line.substring(line.indexOf(commentCloser)+commentCloser.length(),line.length());
                if(after.contains(commentOpener)){
                    after = clean(after, commentOpener, commentCloser, isOpen);
                }
            }
            line = prior+after;
        } else if(isOpen && !line.contains(commentCloser)){
            return "";
        } else if(line.contains(commentCloser)){
            isOpen = false;
            line = line.substring(line.indexOf(commentCloser)+commentCloser.length(),line.length());
            if(line.contains(commentOpener)){
                line = clean(line, commentOpener, commentCloser, isOpen);
            }
        }
        return line;
    }
}
