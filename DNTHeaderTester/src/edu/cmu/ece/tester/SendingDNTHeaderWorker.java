package edu.cmu.ece.tester;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

public class SendingDNTHeaderWorker implements Runnable{
	private String url;
	private SendingDNTHeaderFileWriter writer;
	public SendingDNTHeaderWorker(String url, SendingDNTHeaderFileWriter writer) {
		this.url = url;
		this.writer = writer;
	}

	@Override
	public void run() {
		writer.output(url, this.sendGet(this.url));
		
	}
	public synchronized String sendGet(String url) {
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
//		for (Header header : get.getAllHeaders()) {
//			// System.out.println(header.toString());
//		}

		// System.out.println("Send request ============================>");
		try {
			HttpResponse response = client.execute(get);
			int status = response.getStatusLine().getStatusCode();
			if (status != 200) {
				return "STATUS " + status;
			}
//			Header[] headers = response.getAllHeaders();
//			for (Header header : headers) {
//				// System.out.println(header.toString());
//			}
			Header dntHeader = response.getFirstHeader("DNT");
			if (dntHeader == null || dntHeader.getValue().length() == 0) {
				return "NOT SUPPORT";
			} else {
				return dntHeader.getValue() + " SUPPORT";
			}
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(42);
			return "ERROR";
		}
	}

}
