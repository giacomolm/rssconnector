import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserException;
import com.sun.cnpi.rss.parser.RssParserFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Vincenzo Barrea, Alessio Felicioni
 */
public class RssReader {

    private String boardAddress;
    private String[] tag;
    private Date timestamp;

    
    public RssReader(String boardAddress, String[] tag, Date timestamp) {
        this.boardAddress = boardAddress;
        this.tag = tag;
        this.timestamp = timestamp;
    }
    
    
    public String getBoardAddress() {
        return boardAddress;
    }

    public void setBoardAddress(String boardAddress) {
        this.boardAddress = boardAddress;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    private ArrayList readFeedbacks (long post){
         ArrayList feedbacks = new ArrayList();
         
         return feedbacks;
    }
    
    private boolean match(Post post){
        boolean res = false;
        
        return res;
    }
    
    public void readPost() throws RssParserException, IOException{
        
        String address = getBoardAddress();
        
        RssParser parser = RssParserFactory.createDefault();
        Rss rss = parser.parse(new URL("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard?action=READ"));
        Item x = new Item();
        }
    
}

