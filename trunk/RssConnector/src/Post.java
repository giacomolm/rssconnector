import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.sun.cnpi.rss.elements.Category;


public class Post {

	private long id;
	private String title;
	private String link;
	private String description;
	private String author;
	private ArrayList<String> category;
	private String enclosure;
	private String source;
	private Date pubDate;
	private ArrayList<Feedback> feedbacks;
	
	public Post(long id, String title, String link, String description,
			String author, ArrayList<String> category, String enclosure,
			String source, Date pubDate) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.description = description;
		this.author = author;
		this.category = category;
		this.enclosure = enclosure;
		this.source = source;
		this.pubDate = pubDate;
		this.feedbacks=new ArrayList<Feedback>();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setCategory(ArrayList<String> category) {
		this.category = category;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}
	
	public String printCategoriesToString(){
		Iterator<String> it=this.category.iterator();
		String str="";
		boolean primo=true;
		while(it.hasNext()){
			if (primo){
				str+=it.next();
				primo=false;
			}
			else str+=", "+it.next();
		}
		return str;
	}
	
	public String toString(){
		String x= "id="+this.id+", title="+this.title+", description="+this.description+
				", link="+this.link+", autore="+this.author+", category="+printCategoriesToString()+
				", enclosure="+this.enclosure+", source="+this.source;
		Iterator <Feedback> it=feedbacks.iterator();
		int i=1;
		while (it.hasNext()){
			x+="\n\tFeedback "+i+": "+it.next().toString();
			i++;
		}
		return x;
	}

}