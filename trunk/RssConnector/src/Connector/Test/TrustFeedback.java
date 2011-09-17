package Connector.Test;

import java.util.ArrayList;

import Connector.Connettore;
import Connector.Feedback;
import Connector.Title;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class TrustFeedback {

	@Test
	public void testTrustFeedback(){
		Feedback f1 = new Feedback("descrizione feedback 1", Title.AGREE, 130824975);
		Feedback f2 = new Feedback("descrizione feedback 2", Title.DISAGREE, 130824976);
		Feedback f3 = new Feedback("descrizione feedback 3", Title.DETRACTOR, 13082497);
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		feedbacks.add(f1);
		feedbacks.add(f2);
		feedbacks.add(f3);
		Connettore connettore = new Connettore("address1", "address2", "author1", "author2", "alias1", "alias2", new ArrayList<String>());
		int res = 0;
		if (connettore.trust(feedbacks) instanceof Feedback){
			res = 1;
		}
		assertEquals(1, res,0);
	}
}
