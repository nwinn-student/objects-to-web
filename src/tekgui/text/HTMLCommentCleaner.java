package tekgui.text;
/**
 * The HTMLCommentCleaner class holds methods pertaining to cleaning HTML code of distractions that needn't be reviewed, comments.
 *
 * @author Noah Winn
 * @version Sept. 23, 2024
 */
public class HTMLCommentCleaner{
    private boolean isCommentOpen = false;
    private String commentOpener = "<!--"; // should work with any comment scheme
    private String commentCloser = "-->"; // should work with any comment scheme
    protected boolean activatePrintTestMode = false;
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
     * Used whilst testing to ensure it works properly.
     */
    private void print(String s){
        if(activatePrintTestMode){
            System.out.println(s);
        }
    }
    
    /**
     * Cleans the line of any HTML comments, expected to take in default charset
     * 
     * @param line the string
     * @return the cleaned string
     */
    public String clean(String line){
        // Look for <!-- all in a row, if so isCommentOpen = true until it -->
        print(isCommentOpen+" INPUT: "+line);
        if(line.contains(commentOpener.subSequence(0,commentOpener.length()-1)) && !isCommentOpen){
            // find that comment and beat it up
            String prior = line.substring(0,line.indexOf(commentOpener));
            String after = line.substring(line.indexOf(commentOpener)+commentOpener.length()-1, line.length());
            if(!after.contains(commentCloser.subSequence(0,commentCloser.length()-1))){
                isCommentOpen = true;
                print(isCommentOpen+" OUTTED: "+prior);
                return prior;
            } else {
                after = line.substring(line.indexOf(commentCloser)+commentCloser.length(),line.length());
                if(after.contains(commentOpener.subSequence(0,commentOpener.length()-1))){
                    print(isCommentOpen+" ROUND: "+after);
                    after = clean(after);
                }
            }
            line = prior+after;
        } else if(isCommentOpen && !line.contains(commentCloser.subSequence(0,commentCloser.length()-1))){
            return "";
        } else if(line.contains(commentCloser.subSequence(0,commentCloser.length()-1))){
            isCommentOpen = false;
            line = line.substring(line.indexOf(commentCloser)+commentCloser.length(),line.length());
            if(line.contains(commentOpener.subSequence(0,commentOpener.length()-1))){
                line = clean(line);
            }
        }
        print(isCommentOpen+" OUTPUT: "+line);
        return line;
    }
    public static void main(String[] args){
        
        HTMLCommentCleaner commentCleaner = new HTMLCommentCleaner();
        commentCleaner.activatePrintTestMode = true;
        String a = "Hello, <!-- heyooooo -->it is <!-- WOAAAT-->pretty cool to be abl<!-- WOAAAT-->e to cl<!-- WOAAAT-->ean co<!-- WOAAAT-->mme<!-- WOAAAT-->nts from your co<!-- WOAAAT-->de right?";
        String b = "I though that this is rather needed for cleaning since <!-- abcdefghijklmnopqrstuvwxyz";
        String c = "I mean like what do you expect it should be useful";
        String d = ", so very useful.  If only I could read what I am sending now, oh yeah --> See this?";
        String e = "Yeah, so what if I do this <!--";
        String f = "ender-->thing, do<!-- hi --> you see this?";
        //commentCleaner.clean(b);
        System.out.println(commentCleaner.clean(a));
        System.out.println(commentCleaner.clean(b));
        System.out.println(commentCleaner.clean(c));
        System.out.println(commentCleaner.clean(d));
        System.out.println(commentCleaner.clean(e));
        System.out.println(commentCleaner.clean(f));
    }
}
