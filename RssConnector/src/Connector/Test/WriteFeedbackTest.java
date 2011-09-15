package Connector.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Connector.Feedback;
import Connector.RssWriter;
import Connector.Title;

public class WriteFeedbackTest {
	
	@Test
	public void testWriteFeedback(){
		Feedback testF = new Feedback("desc", Title.AGREE, 130824975);
		RssWriter testW = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard", "alias", "author");
		
		int res;
		if(testW.checkFeedback(testF)) res =1;
		else res=0;
		assertEquals(1, res, 0);
	}

}
