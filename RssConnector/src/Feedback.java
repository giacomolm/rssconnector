import java.util.Date;


public class Feedback {
	private String description;
	private String author;
	private Title title;
	private Date pubDate;

	public Feedback(String description, Title title, Date pubDate) {
		super();
		this.description = description;
		this.title = title;
		this.pubDate = pubDate;
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

	@Override
	public String toString() {
		return "Descrizione= "+description+", Titolo= "+title.toString()+", Data di pubblicazione= "+pubDate.toString();
	}
}

enum Title{
	AGREE, DISAGREE, PARTIALLY_AGREE, DETRACTOR;
}

