package Connector.Test;

import static org.junit.Assert.*;
import org.junit.Test;
import Connector.*; 

public class WritePostTest {
	
	@Test
	public void testWritePost(){
		Post testP = new Post(0, "titolo", "link", "description", null, null, null, null, null);
		RssWriter testW = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard", "alias", "author");
		int res;
		
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(1, res, 0);
	
	}
	
	
	
}
