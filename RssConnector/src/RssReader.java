import com.sun.cnpi.rss.elements.*;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserException;
import com.sun.cnpi.rss.parser.RssParserFactory;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if (c.getItems()==null){
        	System.out.println("Non ci sono Post");
        	return new ArrayList<Post>();
        }
        ArrayList<Item> items=new ArrayList<Item>(c.getItems());
        Iterator iter = items.iterator();
        ArrayList<Post> res=new ArrayList<Post>();
        while (iter.hasNext()){
        	Item i=(Item)iter.next();
        	long id=getFeedbackName(i);   //L'id è sempre presente
            String titolo="";
            String link="";
            String description="";
            ArrayList<String> categories=new ArrayList<String>();
            String enclosure="";
            String source="";
            Date date=getPubDate(i);    //La data è sempre presente
            try{
            	titolo=i.getTitle().getText();
            }
            catch (NullPointerException e){}
            try{
            	link=i.getLink().getText();
            }
            catch (NullPointerException e){}
            try{
            	description=i.getDescription().getText();
            }
            catch (NullPointerException e){}
            try{
            	categories=getCategories(i);
            }
            catch (NullPointerException e){}
            try{
            	enclosure=i.getEnclosure().getText();
            }
            catch (NullPointerException e){}
            try{
            	source=i.getSource().getText();
            }
            catch (NullPointerException e){}
        	Post p=new Post(id, titolo, link, description, "", categories, enclosure, source, date);
        	res.add(p);
        }
        return res;
        }
    
    private ArrayList <String> getCategories(Item item){
    	ArrayList <String> lista=new ArrayList<String>();
    	Iterator <Category> it = item.getCategories().iterator();
    	while (it.hasNext()){
    		lista.add(it.next().getText());
    	}
    	return lista;
    }
    
    private Date getPubDate(Item item){
    	String data=item.getPubDate().getText();
    	SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    	Date x=new Date();
    	try {
			x=new Date(sdf.parse(data).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
    }
    
    
    
    private long getFeedbackName(Item x){
    	String guid =x.getGuid().getText();
    	return Long.valueOf(guid.substring(guid.indexOf('=')+1)).longValue();
    }
    
    public static void main (String[] args){
    	RssReader x=new RssReader();
    	ArrayList<Post> list=new ArrayList<Post>();
    	try {
			list=x.readPost();
		} catch (RssParserException e) {
		} catch (IOException e) {
		}
		Iterator<Post> it=list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
    }
}

