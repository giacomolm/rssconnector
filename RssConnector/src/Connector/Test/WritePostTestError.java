
package Connector.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import postboardIO.RssReader;
import postboardIO.RssWriter;

import data.Post;
import Connector.*; 

public class WritePostTestError {
	
	@Test
	public void testWritePost(){
		boolean res=false;
		Post p = new Post(0, null, "link", "description", null, null, null, null, null,null);
		/*testP.add(new Post(0, null, null, "description", null, null, null, null, null, null));
		testP.add(new Post(0, null, null, null, null, null, null, null, null, null));
		testP.add(new Post(0, "titolo", null, null, null, null, null, null, null, null));
		testP.add(new Post(0, "titolo", "link", null, null, null, null, null, null, null));
		testP.add(new Post(0, "titolo", null, "Description", null, null, null, null, null, null));
		testP.add(new Post(0, null, "link", null, null, null, null, null, null, null));*/
		RssWriter w = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard", "alias", "author");
		RssReader r = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard/", "");
		w.writePost(p, r);
		Collection<Post> pc = r.readPosts();
		for(Iterator<Post> i = pc.iterator(); i.hasNext()&&!res;){
			Post post = i.next();
			if(p.getTitle()!=null && p.getTitle().equals(post.getTitle())&&
			   p.getLink()!=null && p.getLink().equals(post.getLink())&&
			   p.getDescription()!=null && p.getDescription().equals(post.getDescription())) res = true;
		}
		assertFalse(res);
	}
}