package Connector.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Connector.Post;
import Connector.RssReader;
import Connector.RssWriter;



public class ForwardPostTest {
	@Test
	public void ForwardPostTest(){
		
		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titolo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		RssWriter w1 = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "alias", "author");
		RssReader r1 = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard/", "");
		//Scriviamo sulla bacheca sorgente il post che vogliamo inoltrare
		w1.writePost(p, r1);
		//Leggiamo dalla bacheca sorgente il post che abbiamo scritto
		Collection<Post> posts = r1.readPosts();
		//Prepariamoci per l'inoltro
		RssWriter w2 = new RssWriter("http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/", "alias", "author");
		RssReader r2 = new RssReader("http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/postboard/", "");
		for(Iterator<Post> i = posts.iterator(); i.hasNext();){
			Post post= i.next();
			if(p.getTitle()!=null && post.getTitle().equals(p.getTitle())&&
			   p.getLink()!=null && post.getLink().equals(p.getLink())&&
			   p.getDescription()!=null && post.getDescription().equals(p.getDescription()))
				//Una volta individuato il post appena scritto lo scriviamo sulla bacheca di destinazione
				w2.writePost(post, r2);
		}
		//Verifichiamo se i post presenti nella bacheca di destinazione
		//includono quelli appena inoltrati
		boolean res=false;
		Collection<Post> pc = r2.readPosts();
		for(Iterator<Post> i = posts.iterator(); i.hasNext()&&!res;){
			Post post = i.next();
			//confrontiamo il tutto con il post originario
			if(p.getTitle()!=null && post.getTitle().equals(p.getTitle())&&
			   p.getLink()!=null && post.getLink().equals(p.getLink())&&
			   p.getDescription()!=null && post.getDescription().equals(p.getDescription())) res = true;
		}

		assertTrue(res);
	}
}
