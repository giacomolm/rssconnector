import java.util.Date;


public class Feedback {
	private String description;
	private String author;
	private Title title;
	private Date pubDate;

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
}

enum Title{
	AGREE, DISAGREE, PARTIALLY_AGREE, DETRACTOR;
}