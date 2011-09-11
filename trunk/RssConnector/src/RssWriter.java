import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class RssWriter {
	
	/*public static String writePost(Post posts){
		
	}*/
	
	public String writePost(){
		String response=null;
		String urlString = "http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/postboard?action=NEWPOST&title=151&description=descrizione_del_feed_151&link=http://atlantis.isti.cnr.it:8080/virtualNoticeBoard/";
		
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = bf.readLine())!=null){
				sb.append(line);
			}
			bf.close();
			response = sb.toString();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) {
		RssWriter writer = new RssWriter();
		String result = writer.writePost();
	}
}
