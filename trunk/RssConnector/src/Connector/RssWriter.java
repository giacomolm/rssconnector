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
		if (boardAddress.charAt(boardAddress.length()-1)!='/')
        	this.boardAddress = boardAddress+"/";
        else this.boardAddress = boardAddress;
		this.alias = alias;
		this.author = author;
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
	
	public boolean checkPost(Post post){
		if((post.getTitle()==null || (post.getTitle()).equals(""))||
		   (post.getDescription()==null || (post.getDescription()).equals(""))||
		   (post.getLink()==null || (post.getLink()).equals("")))
			return false;
		else return true;
	}
	
	public boolean checkFeedback(Feedback feedback){
		if((feedback.getTitle()==null || (feedback.getFeedbackname()==0)))
			return false;
		boolean res = false;
		if(feedback.getTitle()!=null){
			Title[] t = Title.values();
			for(int i=0; i<=t.length&!res; i++){
				if (t[i].equals(feedback.getTitle())) res = true;
			}
		}
		return res;
	}
	
	public boolean writeFeedback(Feedback feedback){
		boolean response=true;
		long idPost = feedback.getFeedbackname();
		if(checkFeedback(feedback)){
			String urlString = boardAddress+"feedbacks?";
			urlString+="action=NEWCOMMENT&";
			urlString+="FeedbackName="+feedback.getFeedbackname()+"&";
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
				e.printStackTrace();
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

	
	
	public boolean writePost(Post post, RssReader dest){
		boolean response = true;
		if(checkPost(post)){
			String urlString = boardAddress+"postboard/postboard?";
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
			if(response){
					//dobbiamo recuperare il feedbackname del post appena scritto
					long idPost = dest.findFeedbackName(post);
					if(idPost!=0){
	
						ArrayList<Feedback> feedbacks = post.getFeedbacks();
						if(feedbacks!=null){
							if(!feedbacks.isEmpty()){
								Feedback feedback = Feedback.trust(feedbacks);
								feedback.setFeedbackname(idPost);
								writeFeedback(feedback);
							}
						}
					}
			}
		}
		else response = false;
		return response;
	}
	
}
