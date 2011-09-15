
package Connector.Test;

import static org.junit.Assert.*;
import org.junit.Test;
import Connector.*; 

public class WritePostTestError {
	
	@Test
	public void testWritePost(){
		RssWriter testW = new RssWriter("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard", "alias", "author");
		int res;
		Post testP = new Post(0, null, "link", "description", null, null, null, null, null);
		
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testP = new Post(0, null, null, "description", null, null, null, null, null);
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testP = new Post(0, null, null, null, null, null, null, null, null);
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testP = new Post(0, "titolo", null, null, null, null, null, null, null);
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testP = new Post(0, "titolo", "link", null, null, null, null, null, null);
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testP = new Post(0, "titolo", null, "Description", null, null, null, null, null);
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
		
		testP = new Post(0, null, "link", null, null, null, null, null, null);
		if(testW.checkPost(testP)) res =1;
		else res=0;
		assertEquals(0, res, 0);
	
	}
	
}