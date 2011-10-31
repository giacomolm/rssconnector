package Connector.Test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import Connector.Feedback;
import Connector.Post;
import Connector.RssReader;
import Connector.RssWriter;
import Connector.Title;

public class WriteFeedbackTest {
	
	@Test
	public void testWriteFeedback(){
		Post p = new Post(0, "titolo", "http://link.it", "description", null, null, null, null, null, null);
		RssWriter w = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "alias", "author");
		boolean res = false;
		RssReader r = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "");
		w.writePost(p, r);
		long feedbackname = r.findFeedbackName(p);
		Feedback f = new Feedback("descrizione", Title.AGREE, feedbackname);
		f.setAuthor("Tester");
		w.writeFeedback(f);
		Collection<Feedback> set =  r.readFeedbacks(feedbackname);
		for(Iterator<Feedback> i = set.iterator(); i.hasNext()&&!res;){
			Feedback feedback = i.next();
			if(f.getDescription()!=null && f.getDescription().equals(feedback.getDescription())&&
			   f.getTitle()!=null && f.getTitle().equals(feedback.getTitle())) res = true;  
		}
		
		assertTrue(res);		
	}

}
