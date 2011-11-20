import java.util.ArrayList;
import java.util.Date;

import core.Connettore;
import data.Post;


public class test {
	
	public static void main (String[] args){
		Connettore c=new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/",
				"http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/",
				"author1","author2","alias1","alias2",new ArrayList<String>());
		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titoloAlessio", "http://link.it", "description"+ts, null, null, null, null, null, null);
		Post p2 = new Post(0, "titoloGiacomo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		c.writePost(1, p);
		c.writePost(2, p2);
		c.federate();
		c.federate();
		c.federate();
		Post p3 = new Post(0, "NEXT", "http://link.it", "description"+ts, null, null, null, null, null, null);
		c.writePost(1, p3);
		c.federate();
		c.federate();
		c.federate();
	}
}
