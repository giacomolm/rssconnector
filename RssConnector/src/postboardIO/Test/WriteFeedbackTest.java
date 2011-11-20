package postboardIO.Test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import postboardIO.RssReader;
import postboardIO.RssWriter;

import data.Feedback;
import data.Post;
import data.Title;


public class WriteFeedbackTest {
	
	@Test
	public void testWriteFeedback(){
		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titolo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		RssWriter w = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "alias", "author");
		boolean res = false;
		RssReader r = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "");
		w.writePost(p, r,true);
		long feedbackname = r.findFeedbackName(p);
		Feedback f = new Feedback("descrizione"+ts, Title.AGREE, feedbackname);
		f.setAuthor("Tester");
		w.writeFeedback(f,true);
		Collection<Feedback> set =  r.readFeedbacks(feedbackname);
		for(Iterator<Feedback> i = set.iterator(); i.hasNext()&&!res;){
			Feedback feedback = i.next();
			if(f.getDescription()!=null && f.getDescription().equals(feedback.getDescription())&&
			   f.getTitle()!=null && f.getTitle().equals(feedback.getTitle())) res = true;  
		}
		
		assertTrue(res);		
	}

}
