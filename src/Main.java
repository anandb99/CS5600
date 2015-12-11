import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;


public class Main 
{
	static float sc;
	static float maxSc;
	static String maxScURL=null;
	static String myUrl=null;
	static String googleFirstResult=null;
	
	public static void main(String[] args) throws Exception 
	{
		String search = null;
		
		/*
		 * Accept search query from the user from the input box
		 */
		JFrame frame = new JFrame();
		search = JOptionPane.showInputDialog(frame, "Enter the query :");

		/*
		 * Remove stop words and extract keywords from the user search query
		 */
		Map map = StopWords.removeStopWords(search);
		System.out.println("********** MAP CONTENT AFTER STOP WORD REMOVAL AND KEYWORD EXTRACTION **********");
		System.out.println(map);


		sc=0;
		maxSc=0;
		maxScURL=null;
		myUrl=null;
		int NumberOfResults=8;
		
		for(int i=0;i<NumberOfResults;i+=4)
		{

			String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start="+i+"&q=";
			//String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
			String charset = "UTF-8";

			// query builder
			URL url = new URL(google + URLEncoder.encode(search, charset));
			Reader reader = new InputStreamReader(url.openStream(), charset);
			GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

			int m=0;
			for (m = 0; m < 4; m++) 
			{
				// Show title and URL of the results.
				String title = results.getResponseData().getResults().get(m).getTitle();
				if(title!=null)
				{
					System.out.println("\n ********** "+(i+m)+" TITLE & URL **********");
					System.out.println(title);
					myUrl = results.getResponseData().getResults().get(m).getUrl();
					System.out.println(myUrl);

					if(i==0 && m==0)
					{
						googleFirstResult=myUrl;
					}
		
					String data = deTag.readAndRetunData(myUrl);
					if(data!=null)
					{	
						/*
						 * calulate score for each webpage
						 */
						Score s = new Score();
						sc=s.calculateScore(i+m,data,map);
						System.out.println("score : "+sc);
						if(sc>maxSc)
						{
							maxSc=sc;
							maxScURL=myUrl;
						}
					}
					else
					{
						System.out.println("null found");
					}
				}
				else
				{
					System.out.println("null found");
				}
			}
		}

		show1(googleFirstResult);
		System.out.println("\n\n Chosen URL by Google : "+googleFirstResult);
		// show1(maxScURL) pops up the default browser and show the wabpage there
		show1(maxScURL);
		System.out.println("\n\n Chosen URL by our system : "+maxScURL+"  & with score : "+maxSc);

		System.exit(1);
	}


	public static void show1(String myUrl) 
	{
		try 
		{
			URI myURI = null;
			try 
			{
				myURI = new URI(myUrl);
			} 
			catch (URISyntaxException e) 
			{
				e.printStackTrace();
			}
			java.awt.Desktop.getDesktop().browse(myURI);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
