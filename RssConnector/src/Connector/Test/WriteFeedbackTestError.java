package Connector.Test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import postboardIO.RssReader;
import postboardIO.RssWriter;

import data.Feedback;
import data.Post;
import data.Title;


public class WriteFeedbackTestError {

	@Test
	public void testWriteFeedback(){
		Post p = new Post(0, "titolo", "http://link.it", "description", null, null, null, null, null, null);
		RssWriter w = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "alias", "author");
		boolean res = false;
		RssReader r = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "");
		w.writePost(p, r);
		long feedbackname = r.findFeedbackName(p);
		//abbiamo creato un post che non contiene il campo Title
		Feedback f = new Feedback("descrizione", null, feedbackname);
		f.setAuthor("Tester");
		//chiamiamo il metodo write, anche se l'effettiva scrittura dovrebbe non avvenire
		w.writeFeedback(f);
		//verifichiamo se il feedback è stato effettivamente scritto
		Collection<Feedback> set =  r.readFeedbacks(feedbackname);
		for(Iterator<Feedback> i = set.iterator(); i.hasNext()&&!res;){
			Feedback feedback = i.next();
			if(f.getDescription()!=null && f.getDescription().equals(feedback.getDescription())&&
			   f.getTitle()!=null && f.getTitle().equals(feedback.getTitle())) res = true;  
		}
		assertFalse(res);
	}
}
