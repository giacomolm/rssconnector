package Connector.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Connector.RssReader;

@RunWith(value=Suite.class)
@SuiteClasses(value={WritePostTest.class, WritePostTestError.class})

public class ForwardPostTest {
	
	@Before public void read(){
		RssReader testR = new RssReader("http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/", "anonymus");
		
		//ArrayList testA = 
	}

}
