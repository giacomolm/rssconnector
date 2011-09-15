package Connector.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Connector.Feedback;
import Connector.RssWriter;
import Connector.Title;

public class WriteFeedbackTestError {

	@Test
	public void testWriteFeedback(){
		Feedback testF = new Feedback("desc", null, 130824975);
		RssWriter testW = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard", "alias", "author");
		
		int res;

		if(testW.checkFeedback(testF)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testF = new Feedback("desc", Title.AGREE, 0);
		if(testW.checkFeedback(testF)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testF = new Feedback("desc", null, 0);
		if(testW.checkFeedback(testF)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
	}
}
