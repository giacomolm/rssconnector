package Connector.Test;

import java.util.ArrayList;
import java.util.Iterator;


import static org.junit.Assert.assertEquals;
import org.junit.Test;


import postboardIO.RssWriter;

import data.Feedback;
import data.Title;


public class TrustFeedback {

	@Test
	public void testTrustFeedback(){
		Feedback f1 = new Feedback("descrizione feedback 1", Title.AGREE, 130824975);
		Feedback f2 = new Feedback("descrizione feedback 2", Title.DISAGREE, 130824975);
		Feedback f3 = new Feedback("descrizione feedback 3", Title.DISAGREE, 130824975);
		Feedback f4 = new Feedback("descrizione feedback 4", Title.DETRACTOR, 130824975);
		
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		feedbacks.add(f1);
		feedbacks.add(f2);
		feedbacks.add(f3);
		feedbacks.add(f4);
		
		RssWriter w = new RssWriter("link", "alias", "author");
		Feedback feedTrust = w.trust(feedbacks);

		int average[] = new int[4];
		Feedback myfeedback = new Feedback("Feedback_autogenerated", null, 130824975);
		for(Iterator<Feedback> i = feedbacks.iterator(); i.hasNext();){
			Feedback f = (Feedback) i.next();
			//incremento contatori
			if (f.getTitle().equals(Title.AGREE)) average[0]++;
			else if (f.getTitle().equals(Title.PARTIALLY_AGREE)) average[1]++;
			else if (f.getTitle().equals(Title.DISAGREE)) average[2]++;
			else if (f.getTitle().equals(Title.DETRACTOR)) average[3]++;
		}
		int agree = 10;
		int partially_agree= 7;
		int disagree = 4;
		int detractor = 1;
		
		float result = ((average[0] * agree) + (average[1] * partially_agree) + (average[2] * disagree) + (average[3] * detractor)) / (average[0]+average[1]+average[2]+average[3]);
		
		if(result <= 10 && result > 7) myfeedback.setTitle(Title.AGREE);
		else if(result <= 7 && result > 4) myfeedback.setTitle(Title.PARTIALLY_AGREE);
		else if(result <= 4 && result > 1) myfeedback.setTitle(Title.DISAGREE);
		else if(result <= 1 ) myfeedback.setTitle(Title.DETRACTOR);
	
		assertEquals(myfeedback.toString(), feedTrust.toString());
	}
}
