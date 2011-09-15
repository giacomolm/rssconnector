package Connector.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Connector.Feedback;
import Connector.Post;
import Connector.RssReader;
import Connector.RssWriter;

@RunWith(value=Suite.class)
@SuiteClasses(value={WriteFeedbackTest.class, WriteFeedbackTestError.class})

public class ForwardFeedbackTest {
	
	@Test
	public void read(){
		RssWriter testW = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "alias", "anonymus");
		RssReader testR = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "anonymus");
		int res = 0;
		Post post = new Post(123456789, "title", "http://google.it", "description", "author", new ArrayList<String>(), "enclosure", "source", new Date());
		testW.writePost(post);
		ArrayList<Feedback> testF = testR.readFeedbacks(post.getId());
		if(testF!=null) res = 1;
		assertEquals(1, res, 0);
	}

}