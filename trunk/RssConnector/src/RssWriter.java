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
	
	public boolean writePosts(ArrayList<Post> posts){
		boolean response = true;
		for(Iterator it = posts.iterator(); it.hasNext();){
			Post post = (Post) it.next();
			String urlString = boardAddress+"?";
			urlString+="action=NEWPOST&";
			urlString+="title="+post.getTitle()+"&";
			urlString+="description="+post.getDescription()+"&";
			urlString+="link="+post.getLink()+"&";
			urlString+="author="+this.author+"&";
			urlString+="source="+this.alias;
			Iterator ic = post.getCategory().iterator();
			if(ic.hasNext()){
				String category = (String) ic.next();
				urlString+="&category="+category;
				while(ic.hasNext()){
					category = (String) ic.next();
					urlString+=","+category;
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
			
		}
		return response;
	}
	
	public String writePost(){
		String response=null;
		String urlString = "http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard?action=NEWPOST&title=151&description=descrizione_del_feed_151&link=http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/";
		
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
			response = sb.toString();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) {
		/*RssWriter writer = new RssWriter();
		String result = writer.writePost();*/
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
}
