package Connector.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Connector.Post;
import Connector.RssReader;

@RunWith(value=Suite.class)
@SuiteClasses(value={WritePostTest.class, WritePostTestError.class})

public class ForwardPostTest {
	
	@Before public void read(){
		RssReader testR = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "anonymus");
		int res = 0;
		ArrayList<Post> testA = testR.readPost();
		
		if(testA!=null) res = 1;
		assertEquals(0, res,0);
	}

}
