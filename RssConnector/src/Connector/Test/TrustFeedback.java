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
		int res;
		Feedback f1 = new Feedback("descrizione feedback 1", Title.AGREE, 130824975);
		Feedback f2 = new Feedback("descrizione feedback 2", Title.DISAGREE, 130824976);
		Feedback f3 = new Feedback("descrizione feedback 3", Title.DISAGREE, 130824977);
		Feedback f4 = new Feedback("descrizione feedback 4", Title.DETRACTOR, 130824978);
		
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		feedbacks.add(f1);
		feedbacks.add(f2);
		feedbacks.add(f3);
		feedbacks.add(f4);
		Connettore connettore = new Connettore("address1", "address2", "author1", "author2", "alias1", "alias2", new ArrayList<String>());
		
		Feedback f = connettore.trust(feedbacks);
		if (f.getTitle() == Title.DISAGREE) res = 1;
		else res =0;
		assertEquals(1, res,0);

		
		feedbacks.get(1).setTitle(Title.AGREE);
		feedbacks.get(2).setTitle(Title.AGREE);
		feedbacks.get(3).setTitle(Title.AGREE);
		
		
		f = connettore.trust(feedbacks);
		if (f.getTitle() == Title.AGREE) res = 1;
		else res =0;
		assertEquals(1, res,0);
		
		feedbacks.get(0).setTitle(Title.AGREE);
		feedbacks.get(1).setTitle(Title.DISAGREE);
		feedbacks.get(2).setTitle(Title.PARTIALLY_AGREE);
		feedbacks.get(3).setTitle(Title.DETRACTOR);
		
		f = connettore.trust(feedbacks);
		if (f.getTitle() == Title.PARTIALLY_AGREE) res = 1;
		else res =0;
		assertEquals(1, res,0);
		
	
	}
}
