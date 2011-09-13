import java.io.IOException;
import java.io.Reader;
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
		
		r1 = new RssReader(address1, tags, new Date(0));
		w1 = new RssWriter(address2, alias2, author1);
		
		r2 = new RssReader(address2, tags, new Date(0));
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
		
		return feedbacks.get(0);
	}
	
	public long getFeedbackName(Post post, RssReader r){
		
		long res = 0;
		ArrayList<Post> posts;
		try {
			posts = r.readPost();
			if(!posts.isEmpty()){
				for(Iterator<Post> it = posts.iterator(); it.hasNext();){
					Post p = (Post) it.next();
					if(post.getCategory().equals(p.getCategory()) &&post.getDescription().equals(p.getDescription()) &&
						post.getEnclosure().equals(p.getEnclosure()) &&	post.getLink().equals(p.getLink()) &&
						post.getTitle().equals(p.getTitle()))
						res = p.getId();
				}
			}
		} catch (RssParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	public boolean federate(){
		boolean res = true;
		try {
			ArrayList<Post> posts = r1.readPost();
			
			if(!posts.isEmpty()){
				for(Iterator<Post> it = posts.iterator(); it.hasNext();){
					Post post = (Post) it.next();
					boolean esito = w1.writePost(post);
					
					if(esito){
					
						//dobbiamo recuperare il feedbackname del post appena scritto
						long idPost = getFeedbackName(post, r1);
						if(idPost!=0){
							post.setId(idPost);
							ArrayList<Feedback> feedbacks = r1.readFeedbacks(post.getId());
							if(!feedbacks.isEmpty()){
								/*Feedback feedback = trust(feedbacks);
								ArrayList<Feedback> al = new ArrayList<Feedback>();
								al.add(feedback);
								post.setFeedbacks(al);
								w1.writeFeedback(feedback, idPost);*/
							}
						}
						else res = false;
					}
				}	
			}
			
		} catch (RssParserException e) {
			// TODO Auto-generated catch block
			res = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			res = false;
		}
		
		return res;
		
	}

	public static void main(String[] args) {
		
		Connettore c = new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/", "AL", "Vinci.88", "Atlantis", "Eric", null);
		c.federate();
	}
}
