import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RssWriter {
	
	private String boardAddress;
	private String alias;
	private String author;
	
	public RssWriter(String boardAddress, String alias, String author){
		this.boardAddress = boardAddress;
		this.alias = alias;
		this.author = author;
	}
	
	private boolean writeFeedback(Feedback feedback, long idPost){
		boolean response=true;
		String urlString = boardAddress+"feedbacks?";
		urlString+="action=NEWCOMMENT&";
		urlString+="FeedbackName="+idPost+"&";
		urlString+="description="+feedback.getDescription()+"&";
		urlString+="author="+feedback.getAuthor()+"&";
		urlString+="title="+feedback.getTitle();
		
		System.out.println("Sending URL "+urlString+" ...");
		
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = bf.readLine())!=null){
				sb.append(line);
			}
			bf.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			response = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response = false;
		}
		System.out.println("Send Successful: "+response);
		return response;
	}
	
	public boolean writePosts(ArrayList<Post> posts){
		boolean response = true;
		for(Iterator it = posts.iterator(); it.hasNext();){
			Post post = (Post) it.next();
			String urlString = boardAddress+"postboard?";
			urlString+="action=NEWPOST&";
			urlString+="title="+post.getTitle()+"&";
			urlString+="description="+post.getDescription()+"&";
			urlString+="link="+post.getLink()+"&";
			urlString+="author="+this.author+"&";
			urlString+="source="+this.alias;
			if(post.getCategory()!=null){
				Iterator ic = post.getCategory().iterator();
				if(ic.hasNext()){
					String category = (String) ic.next();
					urlString+="&category="+category;
					while(ic.hasNext()){
						category = (String) ic.next();
						urlString+=","+category;
					} 
				}
			}
			if(post.getEnclosure()!=null)
				urlString+="&enclosure="+post.getEnclosure();
			
			System.out.println("Sending URL "+urlString+" ...");
			
			try {
				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();
				BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while((line = bf.readLine())!=null){
					sb.append(line);
				}
				bf.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				response = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response = false;
			}
			System.out.println("Post Sending Successful: "+response);
			//Now send feedback.
			//Come prima cosa dobbiamo prelevare il feedback name del post appena scritto
			//idPost = (new RssReader("bacheca di destinazione")).getFeedbackName(post); 
			//Dopo di che scriviamo il feedback
			for(Iterator itf = post.getFeedbacks().iterator(); itf.hasNext();){
				Feedback feedback = (Feedback) itf.next();
				//writeFeedback(feedback, idPost);
			}
			
		}
		return response;
	}

	public void setBoardAddress(String boardAddress) {
		this.boardAddress = boardAddress;
	}

	public String getBoardAddress() {
		return boardAddress;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}
	
	public static void main(String[] args) {
		RssWriter writer = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/","atlantis","vinny");
		ArrayList<String> al = new ArrayList<String>();
		al.add("red");
		al.add("white");
		al.add("green");
		Post p = new Post(1,"titolo","http://www.google.it","Descrizione", "Giacomo",al,"http://www.yahoo.it","alias",new Date());
		ArrayList<Post> posts = new ArrayList<Post>();
		posts.add(p);
		writer.writePosts(posts);
	}
}
