package data;
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
			String source, Date pubDate, ArrayList<Feedback> feedbacks) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.description = description;
		this.author = author;
		this.category = category;
		this.enclosure = enclosure;
		this.source = source;
		this.pubDate = pubDate;
		this.feedbacks = feedbacks;
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
	
	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
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
	
	public String printFeedbacks(){
		Iterator<Feedback> it=this.feedbacks.iterator();
		String str="";
		boolean primo=true;
		while(it.hasNext()){
			if (primo){
				str+=it.next().getTitle();
				primo=false;
			}
			else str+=", "+it.next().getTitle();
		}
		return str;
	}
	
	public String toString(){
		return "id="+this.id+", title="+this.title+", description="+this.description+
				", link="+this.link+", autore="+this.author+", category="+printCategoriesToString()+
				", enclosure="+this.enclosure+", source="+this.source+", feedbacks="+printFeedbacks();
	}

	@Override
	public boolean equals(Object arg0) {

		if (arg0 instanceof Post){
			Post confront=(Post)arg0;
			if (this.getTitle()!=null && confront.getTitle().equals(this.getTitle())&&
					   this.getLink()!=null && confront.getLink().equals(this.getLink())&&
					   this.getDescription()!=null && confront.getDescription().equals(this.getDescription()))
				return true;
			else
				return false;
		}
		else
			return super.equals(arg0);
	}
	
	

}
