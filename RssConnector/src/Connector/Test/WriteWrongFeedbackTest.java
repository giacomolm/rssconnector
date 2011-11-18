package Connector.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import core.Connettore;
import data.Feedback;
import data.Post;

public class WriteWrongFeedbackTest {

	@Test
	public void test() {
		Connettore c=new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/",
				"http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/",
				"author1","author2","alias1","alias2",new ArrayList<String>());
		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titolo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		c.writePost(1, p);
		Feedback f = new Feedback("descrizione", null, 0);
		c.writeFeedback(1, p, f);
		ArrayList<Feedback> res=c.readFeedbacks(1, p);
		assertFalse(res.contains(f));
	}

}
