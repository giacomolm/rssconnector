package postboardIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import data.Feedback;
import data.Post;
import data.Title;

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
				Iterator<String> ic = post.getCategory().iterator();
				if(ic.hasNext()){
					String category = ic.next();
					urlString+="&category="+category;
					while(ic.hasNext()){
						category = ic.next();
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
								Feedback feedback = trust(feedbacks);
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
	
	public Feedback trust(ArrayList<Feedback> feedbacks){
		int [] average = new int[4]; // array contenente 4 contatori, uno per ogni possibile valore di Title
		
		if(feedbacks.isEmpty()) return new Feedback("", null, 0);
		
		// Inizializza un oggetto Feedback con il primo (potenzialmente unico) Feedback presente nella collection
		Feedback fRes = new Feedback("Feedback_autogenerated", 
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
			int agree = 10;
			int partially_agree= 7;
			int disagree = 4;
			int detractor = 1;
			
			float result = ((average[0] * agree) + (average[1] * partially_agree) + (average[2] * disagree) + (average[3] * detractor)) / (average[0]+average[1]+average[2]+average[3]);
			
			if(result <= 10 && result > 7) fRes.setTitle(Title.AGREE);
			else if(result <= 7 && result > 4) fRes.setTitle(Title.PARTIALLY_AGREE);
			else if(result <= 4 && result > 1) fRes.setTitle(Title.DISAGREE);
			else if(result <= 1 ) fRes.setTitle(Title.DETRACTOR);
			
			
			return fRes;
	}
}
