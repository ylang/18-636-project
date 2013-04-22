package edu.cmu.ece.tester;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendingDNTHeaderManager implements Runnable {
	private final int MAX_THREAD_NUMBER = 10;
	private ExecutorService pool;
	private String fileName;
	private SendingDNTHeaderFileWriter writer;
	private long numberOfUrls;
	
	public SendingDNTHeaderManager(int numberOfUrls, String fileName, SendingDNTHeaderFileWriter writer) {
		this.pool = Executors.newFixedThreadPool(this.MAX_THREAD_NUMBER);
		this.fileName = fileName;
		this.writer = writer;
		this.numberOfUrls = numberOfUrls;
	}
	
	@Override
	public void run() {
			FileReader fr = null;
			try {
				fr = new FileReader(fileName);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			for (long i = 0L; i < numberOfUrls; i ++) {
				try {
					if (i != 0L) {
						pool.execute(new SendingDNTHeaderWorker(line, writer));
					}
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					//i --;
				}
			}
			pool.shutdown();
			//writer.close();
	}
	
	public static void main (String args[]) throws IOException {
		SendingDNTHeaderFileWriter wr = new SendingDNTHeaderFileWriter("output.txt");
		(new Thread(new SendingDNTHeaderManager(100000, "../popular.txt", wr))).start();
	}
}