package Connector.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import postboardIO.RssReader;
import postboardIO.RssWriter;

import data.Feedback;
import data.Post;
import data.Title;


public class ForwardFeedbackTest {
	
	@Test
	public void ForwardFeedbackTest(){
		boolean res = false;
		int ts = (int)(new Date()).getTime();

		Post p = new Post(0, "titolo", "http://link.it", "description"+ts, null,null,null,null,null,null);
		
		RssWriter w1 = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "alias1", "author1");
		RssReader r1 = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "");
		
		w1.writePost(p, r1);

		RssWriter w2 = new RssWriter("http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/", "alias2", "author2");
		RssReader r2 = new RssReader("http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/", "");
		w2.writePost(p, r2);
		
		Feedback f = new Feedback("Descrizione"+ts, Title.AGREE, 0);
		long feedbackname = r1.findFeedbackName(p);
		f.setFeedbackname(feedbackname);
		w1.writeFeedback(f);
		
		Feedback ftf=null;
		
		Collection<Feedback> feedbacks = r1.readFeedbacks(feedbackname);
		for(Iterator<Feedback> i = feedbacks.iterator(); i.hasNext();){
			Feedback feedback = i.next();
			if(feedback.getDescription().equals(f.getDescription())) ftf = new Feedback(feedback.getDescription(), feedback.getTitle(), 0);
		}
		
		long feedbackname2 = r2.findFeedbackName(p);
		ftf.setFeedbackname(feedbackname2);
		w2.writeFeedback(ftf);
		feedbacks = r2.readFeedbacks(feedbackname2);
		for(Iterator<Feedback> i = feedbacks.iterator(); i.hasNext();){
			Feedback feedback = i.next();
			if(feedback.getDescription().equals(ftf.getDescription())) res=true;
		}
		assertTrue(res);

	}

}