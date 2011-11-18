package Connector.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Connettore;
import data.Post;

public class WriteWrongPostTest {

	@Test
	public void test() {
		Connettore c=new Connettore("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/",
				"http://pc-ericlab11.isti.cnr.it:8080/virtualNoticeBoard/",
				"author1","author2","alias1","alias2",new ArrayList<String>());
		Post p = new Post(0, null, "link", "description", null, null, null, null, null,null);
		/*Post p = new Post(0, null, null, "description", null, null, null, null, null, null);
		Post p = new Post(0, null, null, null, null, null, null, null, null, null);
		Post p = new Post(0, "titolo", null, null, null, null, null, null, null, null);
		Post p = new Post(0, "titolo", "link", null, null, null, null, null, null, null);
		Post p = new Post(0, "titolo", null, "Description", null, null, null, null, null, null);
		Post p = new Post(0, null, "link", null, null, null, null, null, null, null);*/
		c.writePost(1, p);
		ArrayList<Post> lista=c.readPosts(1);
		assertFalse(lista.contains(p));
	}

}
