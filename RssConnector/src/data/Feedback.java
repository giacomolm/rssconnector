package data;
import java.util.Date;


public class Feedback {
	private String description;
	private String author;
	private Title title;
	private Date pubDate;
	private long feedbackName = 0;

	public Feedback(String description, Title title, Date pubDate) {
		super();
		this.description = description;
		this.title = title;
		this.pubDate = pubDate;
	}
	
	public Feedback(String description, Title title, long feedbackname) {
		super();
		this.description = description;
		this.title = title;
		this.setFeedbackname(feedbackname);
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

	public void setTitle(Title title) {
		this.title = title;
	}

	public Title getTitle() {
		return title;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setFeedbackname(long feedbackname) {
		this.feedbackName = feedbackname;
	}

	public long getFeedbackname() {
		return feedbackName;
	}
	
	@Override
	public String toString() {
		return "Descrizione= "+description+", Titolo= "+title.toString();
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Feedback){
			Feedback confront=(Feedback)arg0;
			if (this.getDescription()!=null && this.getDescription().equals(confront.getDescription())&&
					   this.getTitle()!=null && this.getTitle().equals(confront.getTitle()))
				return true;
			else
				return false;
		}
		else
			return super.equals(arg0);
	}
	
}


