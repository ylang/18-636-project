package edu.cmu.ece.tester;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

public class SendingDNTHeader {
	
	public static void main(String[] args) {
		List<String> urlList = getURLsFromFile();
		for (String url : urlList) {
			System.out.printf("%s\t%s\n", url, sendGet(url));
		}
	}
	
	public static List<String> getURLsFromFile(String fileName) {
		List<String> list= new ArrayList<String>(1000);
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("cannot find the file");
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static String sendGet(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		//Accept:text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
		//Accept-Charset:ISO-8859-1,utf-8;q=0.7,*;q=0.3
		//Accept-Encoding:gzip,deflate,sdch
		//Accept-Language:en-US,en;q=0.8
		
		get.addHeader(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		get.addHeader(new BasicHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3"));
		get.addHeader(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
		get.addHeader(new BasicHeader("Accept-Language", "en-US,en;q=0.8"));
		
		get.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22"));
		//DNT Header
		get.addHeader(new BasicHeader("DNT", "1"));
		
		try {
			HttpResponse response = client.execute(get);
			Header[] headers = response.getAllHeaders();
			for (Header header: headers) {
				System.out.println(header.toString());
			}
			String dnt = response.getFirstHeader("DNT").getValue();
			if (dnt == null || dnt.length() == 0) {
				return "NOT SUPPORT";
			} else {
				return dnt;
			}
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
			return "ERROR";
		}
	}
}

