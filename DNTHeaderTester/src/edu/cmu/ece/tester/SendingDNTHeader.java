package edu.cmu.ece.tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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

		List<String> urlList = getURLsFromFile("../popular.txt");
		File outputFile = new File("output.txt");
		if (outputFile.exists()) {
			outputFile.delete();
		}
		try {
			outputFile.createNewFile();
			PrintWriter pw = new PrintWriter(outputFile);
			int counter = 1;
			for (String url : urlList) {
				pw.printf("%s\t%s\n", url, sendGet(url));
				pw.flush();
				System.out.printf("%d\t%s\t%s\n", counter++, url, sendGet(url));
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Cannot create an output file");
			System.exit(-1);
		}

		// String url = "http://www.twitter.com";
		// System.out.printf("%s\t%s\n", url, sendGet(url));
	}

	public static List<String> getURLsFromFile(String fileName) {
		List<String> list = new ArrayList<String>(1000);
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
		// Accept:text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
		// Accept-Charset:ISO-8859-1,utf-8;q=0.7,*;q=0.3
		// Accept-Encoding:gzip,deflate,sdch
		// Accept-Language:en-US,en;q=0.8

		get.addHeader(new BasicHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		get.addHeader(new BasicHeader("Accept-Charset",
				"ISO-8859-1,utf-8;q=0.7,*;q=0.3"));
		get.addHeader(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
		get.addHeader(new BasicHeader("Accept-Language", "en-US,en;q=0.8"));

		get.addHeader(new BasicHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22"));
		// DNT Header
		get.addHeader(new BasicHeader("DNT", "1"));
		for (Header header : get.getAllHeaders()) {
			// System.out.println(header.toString());
		}

		// System.out.println("Send request ============================>");
		try {
			HttpResponse response = client.execute(get);
			int status = response.getStatusLine().getStatusCode();
			if (status != 200) {
				return "STATUS " + status;
			}
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				// System.out.println(header.toString());
			}
			Header dntHeader = response.getFirstHeader("DNT");
			if (dntHeader == null || dntHeader.getValue().length() == 0) {
				return "NOT SUPPORT";
			} else {
				return dntHeader.getValue() + " SUPPORT";
			}
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
			return "ERROR";
		}
	}
}
