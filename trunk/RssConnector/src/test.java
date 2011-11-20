import java.util.ArrayList;
import java.util.Date;

import core.Connettore;
import data.Post;


public class test {
	
	public static void main (String[] args){
		Connettore testina=new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/",
				"http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/",
				"alessio","giacomo","alias1","alias2",new ArrayList<String>());

		Connettore c=new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/",
				"http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/",
				"author1","author2","alias1","alias2",new ArrayList<String>());
		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titoloAlessio", "http://link.it", "description"+ts, null, null, null, null, null, null);
		Post p2 = new Post(0, "titoloGiacomo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		testina.writePost(1, p);
		testina.writePost(2, p2);
		c.federate();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.federate();
		
	}
}
