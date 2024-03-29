package postboardIO.Test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import postboardIO.RssReader;
import postboardIO.RssWriter;

import data.Post;

public class WritePostTest {
	
	@Test
	public void testWritePost(){

		int ts = (int)(new Date()).getTime();
		Post p = new Post(0, "titolo", "http://link.it", "description"+ts, null, null, null, null, null, null);
		RssWriter w = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard", "alias", "author");
		boolean res=false;
		RssReader r = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard/", "");
		w.writePost(p, r,true);
		Collection<Post> pc = r.readPosts(true);
		for(Iterator<Post> i = pc.iterator(); i.hasNext()&&!res;){
			Post post = i.next();
			System.out.println(post.toString());
			if(p.getTitle()!=null && post.getTitle().equals(p.getTitle())&&
			   p.getLink()!=null && post.getLink().equals(p.getLink())&&
			   p.getDescription()!=null && post.getDescription().equals(p.getDescription())) res = true;
		}
		assertTrue(res);
	}
	
	
	
}
