
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;
/**
 * extract the parsed html to a string list
 *
 * @author Coby Zhong
 * @date 9.21.2024
 */
public class HtmlTextExtractor {

    public HtmlTextExtractor() {
    }
    // Create an empty constructor

  
    public List<String> extractTextToStringList(Document doc) {
    // method that extract parsed html documnet and convert it to a list of strings
    //This method takes a Document object as input and returns a List<String>
        List<String> stringList = new ArrayList<>();
        //stringList will store a list of String objects
       
        String bodyText = doc.body().text();
        // This code put all the body text it read into the string called fullText

        String[] seperatedText = bodyText.split("\\."); 
        //This code seperate bodyText when there is a new line(\n) or there is a period in the parsed html(.)and put the after seperate text to the string seperatedtext

        for (String text : seperatedText) {
        	//for loop that iterate every piece of seperatedText, and everytime it goes through the loop it implement the String text
            if (!text.isBlank()) {
                stringList.add(text.trim()); 
                //If the text is not blank it will be added to the stringList, and .trim() will make sure there is no unnecessary whitespace 
            }
        }

        return stringList;
        //return the output of stringList
    }
}
