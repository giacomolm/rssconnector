package Connector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


import com.sun.cnpi.rss.parser.RssParserException;


public class Connettore {

	private String address1;
	private String address2;
	private String author1;
	private String author2;
	private String alias1;
	private String alias2;
	private ArrayList<String> tags;
	
	private RssReader r1;
	private RssReader r2;
	private RssWriter w1;
	private RssWriter w2;
	
	public Connettore(String address1, String address2, String author1,
			String author2, String alias1, String alias2, ArrayList<String> tags) {
		super();
		
		this.address1 = address1;
		this.address2 = address2;
		this.author1 = author1;
		this.author2 = author2;
		this.alias1 = alias1;
		this.alias2 = alias2;
		this.tags = tags;
		
		r1 = new RssReader(address1, author2, tags);
		w1 = new RssWriter(address2, alias2, author1);
		
		r2 = new RssReader(address2, author1,tags);
		w2 = new RssWriter(address1, alias1, author2);
	}	
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAuthor1(String author1) {
		this.author1 = author1;
	}
	public String getAuthor1() {
		return author1;
	}
	public void setAuthor2(String author2) {
		this.author2 = author2;
	}
	public String getAuthor2() {
		return author2;
	}
	public void setAlias1(String alias1) {
		this.alias1 = alias1;
	}
	public String getAlias1() {
		return alias1;
	}
	public void setAlias2(String alias2) {
		this.alias2 = alias2;
	}
	public String getAlias2() {
		return alias2;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public Feedback trust(ArrayList<Feedback> feedbacks){
		int [] average = new int[3]; // array contenente 4 contatori, uno per ogni possibile valore di Title
		
		if(feedbacks.isEmpty()) return new Feedback("", null, 0);
		
		// Inizializza un oggetto Feedback con il primo (potenzialmente unico) Feedback presente nella collection
		Feedback fRes = new Feedback("Feedback from" + feedbacks.get(0).getFeedbackname(), 
									feedbacks.get(0).getTitle(), 
									feedbacks.get(0).getFeedbackname()
		);
		
		for(Iterator<Feedback> i = feedbacks.iterator(); i.hasNext();){
			Feedback f = (Feedback) i.next();
			
			//incremento contatori
			if (f.getTitle().equals(Title.AGREE)) average[0]++;
			else if (f.getTitle().equals(Title.PARTIALLY_AGREE)) average[1]++;
			else if (f.getTitle().equals(Title.DISAGREE)) average[2]++;
			else if (f.getTitle().equals(Title.DETRACTOR)) average[3]++;
		}
			
			// Politica dell'ottimismo: in caso di numero uguale di commenti di un certo valore, 
			// prevale quello piu' "positivo"
			
			if ((average[0] > average[1]) && (average[0] > average[2]) && (average[0] > average[3])) fRes.setTitle(Title.AGREE);
			else if ((average[1] > average[0]) && (average[1] > average[2]) && (average[1] > average[3])) fRes.setTitle(Title.PARTIALLY_AGREE);
			else if ((average[2] > average[0]) && (average[2] > average[1]) && (average[2] > average[3])) fRes.setTitle(Title.DISAGREE);
			else if ((average[3] > average[0]) && (average[3] > average[1]) && (average[3] > average[2])) fRes.setTitle(Title.DETRACTOR);
			else if ((average[0] == average[1]) && (average[0] != average[2]) && (average[0] != average[3])
					&& (average[1] != average[2]) && (average[1] != average[3]) && (average[2] != average[3])) fRes.setTitle(Title.AGREE);
			else if ((average[0] != average[1]) && (average[0] == average[2]) && (average[0] != average[3])
					&& (average[1] != average[2]) && (average[1] != average[3]) && (average[2] != average[3])) fRes.setTitle(Title.PARTIALLY_AGREE);
			else if ((average[0] != average[1]) && (average[0] != average[2]) && (average[0] == average[3])
					&& (average[1] != average[2]) && (average[1] != average[3]) && (average[2] != average[3])) fRes.setTitle(Title.DISAGREE);
			else if ((average[0] != average[1]) && (average[0] != average[2]) && (average[0] != average[3])
					&& (average[1] == average[2]) && (average[1] != average[3]) && (average[2] != average[3])) fRes.setTitle(Title.PARTIALLY_AGREE);
			else if ((average[0] != average[1]) && (average[0] != average[2]) && (average[0] != average[3])
					&& (average[1] != average[2]) && (average[1] == average[3]) && (average[2] != average[3])) fRes.setTitle(Title.DISAGREE);
			else if ((average[0] != average[1]) && (average[0] != average[2]) && (average[0] != average[3])
					&& (average[1] != average[2]) && (average[1] != average[3]) && (average[2] == average[3])) fRes.setTitle(Title.DISAGREE);			
			else if ((average[0] == average[1]) && (average[0] == average[2]) && (average[1] == average[2]) 
					&& (average[0] != average[3]) && (average[1] != average[3]) && (average[2] != average[3])) fRes.setTitle(Title.AGREE);			
			else if ((average[0] == average[1])  && (average[0] == average[3]) && (average[1] == average[3]) 
					&& (average[0] != average[2]) && (average[1] != average[2]) && (average[3] != average[2])) fRes.setTitle(Title.PARTIALLY_AGREE);			
			else if ((average[0] == average[2]) && (average[0] == average[3]) &&(average[2] == average[3]) 
					&& (average[0] != average[1]) && (average[2] != average[1]) && (average[3] != average[1])) fRes.setTitle(Title.DISAGREE);
			else if ((average[1] == average[2]) && (average[1] == average[3]) && (average[2] == average[3])
					&& (average[1] != average[0]) && (average[2] != average[0]) && (average[3] != average[0])) fRes.setTitle(Title.PARTIALLY_AGREE);
			else if ((average[0] == average[1]) && (average[0] == average[2]) && (average[0] == average[3])
					&& (average[1] == average[2]) && (average[1] == average[3]) && (average[2] == average[3])) fRes.setTitle(Title.AGREE);
			
			return fRes;
	}
		
	
	/*public long getFeedbackName(Post post, RssReader reader){
		
		long res = 0;
		ArrayList<Post> posts;
		RssReader r = new RssReader(reader.getBoardAddress(), "");
		posts = r.readPost();
		if(posts!=null){
			if(!posts.isEmpty()){
				for(Iterator<Post> it = posts.iterator(); it.hasNext();){
					Post p = (Post) it.next();
					if(post.getCategory().equals(p.getCategory()) &&post.getDescription().equals(p.getDescription()) &&
						post.getEnclosure().equals(p.getEnclosure()) &&	post.getLink().equals(p.getLink()) &&
						post.getTitle().equals(p.getTitle()))
						res = p.getId();
				}
			}
		}
		
		return res;
		
	}*/
	
	public boolean federate(){
		boolean res = true;
		ArrayList<Post> posts1 = r1.readPost();
		ArrayList<Post> posts2 = r2.readPost();
		
		if(posts1!=null){
			Iterator<Post> it = posts1.iterator();
			while(it.hasNext()){
				Post post = (Post) it.next();
				boolean esito = w1.writePost(post);
				
				if(esito){
				
					//dobbiamo recuperare il feedbackname del post appena scritto
					long idPost = w1.getFeedbackName(post, r2);
					
					if(idPost!=0){
						ArrayList<Feedback> feedbacks = r1.readFeedbacks(post.getId());
						if(feedbacks!=null){
							if(!feedbacks.isEmpty()){
								Feedback feedback = trust(feedbacks);
								feedback.setFeedbackname(idPost);
								w1.writeFeedback(feedback);
							}
						}
					}
					else res = false;
				}
			}
		}	
			//Aggiorna il timestamp su A
		if(posts2!=null){
			Iterator <Post> it = posts2.iterator();
			while(it.hasNext()){
				Post post = (Post) it.next();
				boolean esito = w2.writePost(post);
				
				if(esito){
					//dobbiamo recuperare il feedbackname del post appena scritto
					long idPost = w2.getFeedbackName(post, r1);
					if(idPost!=0){
	
						ArrayList<Feedback> feedbacks = r2.readFeedbacks(post.getId());
						if(feedbacks!=null){
							if(!feedbacks.isEmpty()){
								Feedback feedback = trust(feedbacks);
								feedback.setFeedbackname(idPost);
								w2.writeFeedback(feedback);
							}
						}
					}
					else res = false;
				}
			}
		}
		return res;
		
	}

	public static void main(String[] args) {
		
		Connettore c = new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/", "AL", "Vinci.88", "Atlantis", "Eric", new ArrayList<String>());
		c.federate();
	}
}
