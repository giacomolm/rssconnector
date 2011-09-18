package Connector;
import com.sun.cnpi.rss.elements.*;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserException;
import com.sun.cnpi.rss.parser.RssParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.xml.sax.SAXParseException;

public class RssReader {

    private String boardAddress;
    private ArrayList<String> tag;
    private Date timestamp;
    private String bannished; 

    
    public RssReader(String boardAddress, String bannished, ArrayList<String> tag, Date timestamp) {
        this.boardAddress = boardAddress;	//indirizzo bacheca
        this.bannished = bannished;			//autore bandito
        if (tag==null)																		//array di tag
        	this.tag=new ArrayList<String>();
        else
        	this.tag = tag;
        if (timestamp != null)
        	this.timestamp = timestamp;
        else{
        	SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);		//Timestamp del 1970
        	Date x=new Date();
        	try {
    			x=new Date(sdf.parse("01 01 1970").getTime());
    		} catch (ParseException e) {}
    		timestamp=x;
        }
    }
    
    public RssReader(String boardAddress, String bannished, Date timestamp) {
        this.boardAddress = boardAddress;													//indirizzo bacheca
        this.bannished = bannished;															//autore bandito
        this.tag=new ArrayList<String>();													//array_Post
        if (timestamp != null)
        	this.timestamp = timestamp;
        else{
        	SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);		//Timestamp del 1970
        	Date x=new Date();
        	try {
    			x=new Date(sdf.parse("01 01 1970").getTime());
    		} catch (ParseException e) {}
    		timestamp=x;
        }
    }
    
    public RssReader(String boardAddress, String bannished, ArrayList<String> tag) {	
        this.boardAddress = boardAddress;													//indirizzo bacheca
        this.bannished = bannished;															//autore bandito
        if (tag==null)																		//array di tag
        	this.tag=new ArrayList<String>();
        else
        	this.tag = tag;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);			//data del 1970
    	Date x=new Date();
    	try {
			x=new Date(sdf.parse("01 01 1970").getTime());
		} catch (ParseException e) {}
		timestamp=x;
    }
    
    public RssReader(String boardAddress, String bannished) {
        this.boardAddress = boardAddress;   											//Indirizzo Bacheca
        this.bannished = bannished;														//autore bandito
        tag=new ArrayList<String>();													//Array di tag vuoto
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);		//Timestamp del 1970
    	Date x=new Date();
    	try {
			x=new Date(sdf.parse("01 01 1970").getTime());
		} catch (ParseException e) {}
		timestamp=x;
    }
    
    public String getBoardAddress() {
        return boardAddress;
    }

    public void setBoardAddress(String boardAddress) {
        this.boardAddress = boardAddress;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public ArrayList<Feedback> readFeedbacks (long idPost) {
    	RssParser parser = null;
    	Rss rss=null;
    	//System.out.println("right? "+post);
    	try{
    		parser=RssParserFactory.createDefault();
    		rss = parser.parse(new URL(boardAddress+"feedbacks?action=READ&FeedbackName="+idPost));
    	} 
    	catch  (RssParserException e){
    		System.out.println(boardAddress+"feedbacks?action=READ&FeedbackName="+idPost);
    		System.out.println("RssParserException");
    		return null;
    	}
    	catch  (MalformedURLException e){
    		System.out.println("MalformedURLException");
    		return null;
    	}
    	catch  (IOException e){
    		System.out.println("IOException");
    		return null;
    	}
        
        Channel c= rss.getChannel();
        if (c.getItems()==null){
        	System.out.println("Non ci sono Feedback");
        	return new ArrayList<Feedback>();
        }
        ArrayList<Item> items=new ArrayList<Item>(c.getItems());
        Iterator<Item> iter = items.iterator();
        ArrayList<Feedback> lista=new ArrayList<Feedback>();
        while(iter.hasNext()){
        	Item x=iter.next();
        	String description="";
        	Title titolo=Title.valueOf(x.getTitle().getText());
        	Date data=getPubDate(x);
        	try{
        		description=x.getDescription().getText();
        	} catch (NullPointerException e){}
        	lista.add(new Feedback(description,titolo,data));
        }
        return lista;
    }
    
    private boolean match(ArrayList<String> x){
    	if (tag.isEmpty()){
    		return true;
    	}
        Iterator<String> it=x.iterator();
        while(it.hasNext()){
        	String comp=it.next();
        	Iterator<String> itTag = tag.iterator();
        	while(itTag.hasNext()){
        		String comp2=itTag.next();
        		if ((comp.toLowerCase().equals(comp2.toLowerCase()))		||
        				((comp.toLowerCase().indexOf(comp2.toLowerCase())))!=-1){
        			return true;
        		}
        	}
        }
        
        return false;
    }
    
    public ArrayList<Post> readPosts() { 
    	RssParser parser = null;
    	Rss rss=null;
    	try{
    		
    		parser=RssParserFactory.createDefault();
    		System.out.println(boardAddress);
    		rss = parser.parse(new URL(boardAddress+"postboard?action=READ"));
    		
    	} 
    	catch  (RssParserException e){
    		System.out.println("RssParserException");
    		return null;
    	}
    	catch  (MalformedURLException e){
    		System.out.println("MalformedURLException");
    		return null;
    	}
    	catch  (IOException e){
    		System.out.println("IOException");
    		return null;
    	}
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
        	if ((!bannished.equals(""))&&(i.getText().indexOf(bannished)!=-1)) continue;
        	long id=getFeedbackName(i);   //id sempre presente
            String titolo="";
            String link="";
            String description="";
            ArrayList<String> categories=new ArrayList<String>();
            String enclosure="";
            String source="";
            Date date=getPubDate(i);    //data sempre presente
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
            if (!(match(categories)&&timestamp.before(date))) continue;
        	Post p=new Post(id, titolo, link, description, "", categories, enclosure, source, date);
        	res.add(p);
        }
        timestamp=new Date();
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
		} catch (ParseException e) {}
		return x;
    }
    
    private long getFeedbackName(Item x){
    	String guid =x.getGuid().getText();
    	return Long.valueOf(guid.substring(guid.indexOf('=')+1)).longValue();
    }
    
public long findFeedbackName(Post post){
		
		RssReader r = new RssReader(boardAddress, "noOne");
		ArrayList<Post> posts = r.readPosts();
		
		if(posts!=null){
			if(!posts.isEmpty()){
				for(Iterator<Post> it = posts.iterator(); it.hasNext();){
					Post p = (Post) it.next();
					/*&& post.getCategory().equals(p.getCategory()) &&
					post.getEnclosure().equals(p.getEnclosure()) */
					if(post.getDescription().equals(p.getDescription()) &&	post.getLink().equals(p.getLink()) &&
						post.getTitle().equals(p.getTitle())){						
						return p.getId();
					}
				}
			}
		}
		
		return 0;
		
	}
    
    public static void main (String[] args){
    	RssReader x=new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/","");
    	ArrayList<Post> list=new ArrayList<Post>();
    	list=x.readPosts();
		Iterator<Post> it=list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
    }
}

