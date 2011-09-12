import com.sun.cnpi.rss.elements.Channel;
import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserException;
import com.sun.cnpi.rss.parser.RssParserFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
    
    public RssReader() {
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
    
    public ArrayList<Post> readPost() throws RssParserException, IOException{     //manca la funzione match, per ricavare le categorie ed estrarre la data
        RssParser parser = RssParserFactory.createDefault();
        Rss rss = parser.parse(new URL("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard?action=READ"));
        Item x = new Item();
        Channel c= rss.getChannel();
        ArrayList<Item> items=new ArrayList<Item>(c.getItems());
        Iterator iter = items.iterator();
        ArrayList<Post> res=new ArrayList<Post>();
        while (iter.hasNext()){
        	Item i=(Item)iter.next();
        	Post p=new Post(getFeedbackName(i),i.getTitle().getText(),			//Controllare creazione post NullPointerException
        			i.getLink().getText(),i.getDescription().getText(),
        			getDcCreator(i),new ArrayList<String>(),i.getEnclosure().getText(),
        			i.getSource().getText(),new Date());
        	res.add(p);
        }
        return res;
        }
    
    private String getDcCreator(Item item){
    	String testo = item.getText();
    	String res="";
    	int i=49;
    	char c;
    	boolean finito=false;
    	do{
    		c=testo.charAt(i);
    		i++;
    		if (c!='\n')
    			res+=c;
    		else
    			finito=true;
    	}while ((!finito)&&(i<testo.length()));
    	return res;
    }
    
    private long getFeedbackName(Item x){
    	String guid =x.getGuid().getText();
    	return Long.valueOf(guid.substring(guid.indexOf('=')+1)).longValue();
    }
    
    public static void main (String[] args){
    	RssReader x=new RssReader();
    	try {
			ArrayList <Post> c=x.readPost();
			Iterator <Post> it=c.iterator();
			System.out.println(c.size());
			while(it.hasNext()){
				Post p=it.next();
				System.out.println(p.toString());
			}
		} catch (RssParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

