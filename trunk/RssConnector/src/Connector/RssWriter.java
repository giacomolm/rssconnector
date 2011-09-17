package Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.io.ObjectInputStream.GetField;
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
	
	public boolean checkPost(Post post){
		if((post.getTitle()==null || (post.getTitle()).equals(""))||
		   (post.getDescription()==null || (post.getDescription()).equals(""))||
		   (post.getLink()==null || (post.getLink()).equals("")))
			return false;
		else return true;
	}
	
	public boolean checkFeedback(Feedback feedback){
		if((feedback.getTitle()==null || (feedback.getTitle()).equals(""))||
		   (feedback.getFeedbackname()==0))
			return false;
		else return true;
	}
	
	public boolean writeFeedback(Feedback feedback){
		boolean response=true;
		long idPost = feedback.getFeedbackname();
		if(checkFeedback(feedback)){
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
		}
		else response = false;
		return response;
	}

	
	
	public boolean writePost(Post post){
		boolean response = true;
		if(checkPost(post)){
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
				RssReader r = new RssReader(boardAddress, "");
				long idPost = r.findFeedbackName(post);
				post.setId(idPost);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				response = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response = false;
			}
			System.out.println("Post Sending Successful: "+response);
		}
		else response = false;
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
		Post p = new Post(1,"titolo2","http://www.google.it","Descrizione", "Giacomo",al,"http://www.yahoo.it","alias",new Date());
		//Feedback f = new Feedback("prova", "titolo", new Date());
		
		writer.writePost(p);
	}
}
