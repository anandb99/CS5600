import java.io.*;
import java.net.*;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class deTag extends HTMLEditorKit.ParserCallback 
{
	StringBuffer txt;
	Reader reader;

	// empty default constructor
	public deTag() 
	{}

	// more convienient constructor
	public deTag(Reader r) 
	{
		setReader(r);
	}

	public void setReader(Reader r) 
	{ reader = r; }

	public void parse() throws IOException 
	{
		txt = new StringBuffer();
		ParserDelegator parserDelegator = new ParserDelegator();
		parserDelegator.parse(reader, this, true);
	}

	public void handleText(char[] text, int pos) 
	{
		txt.append(text);
	}

	public String toString() 
	{
		return txt.toString();
	}

	public static void readData (String myUrl) 
	{
		try {
			// the HTML to convert
			URL toRead;
			//toRead = new URL("http://p2p.wrox.com");
			toRead = new URL(myUrl);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(toRead.openStream()));
			deTag d = new deTag(in);
			d.parse();
			in.close();
			System.out.println(d.toString());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static String readAndRetunData (String myUrl) 
	{
		try {
			// the HTML to convert
			URL toRead;
			//toRead = new URL("http://p2p.wrox.com");
			toRead = new URL(myUrl);

			try{
				BufferedReader in = new BufferedReader(
						new InputStreamReader(toRead.openStream()));
				deTag d = new deTag(in);
				d.parse();
				in.close();
				//System.out.println(d.toString());
				return d.toString();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;

	}
}