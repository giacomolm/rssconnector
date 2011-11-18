package Connector.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import core.Connettore;
import data.Feedback;
import data.Post;
import data.Title;

public class ForwardFeedbackTest {

	@Test
	public void test() {
		Connettore c=new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/",
				"http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/",
				"author1","author2","alias1","alias2",new ArrayList<String>());
		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titolo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		c.writePost(1, p);
		Feedback f = new Feedback("descrizione"+ts, Title.AGREE, 0);
		c.writeFeedback(1, p, f);
		c.federate();
		ArrayList<Feedback> res=c.readFeedbacks(2, p);
		assertTrue(res.contains(f));
	}

}
