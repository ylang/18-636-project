package edu.cmu.ece.tester;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SendingDNTHeaderFileWriter {
	private PrintWriter pw;
	private long counter;
	public SendingDNTHeaderFileWriter(String fileName) throws IOException {
		File outputFile = new File(fileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}
			outputFile.createNewFile();
			pw = new PrintWriter(outputFile);
			this.counter = 0;
	}
	
	public synchronized void output(String url, String result) {
		pw.printf("%s\t%s\n", url, result);
		pw.flush();
		System.out.printf("%d\t%s\t%s\n", counter++, url, result);
	}
	
	public void close() {
		this.pw.close();
	}
}
