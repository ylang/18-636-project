package edu.cmu.ece.tester;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendingDNTHeaderManager implements Runnable {
	private ExecutorService pool;
	private String fileName;
	private SendingDNTHeaderFileWriter writer;
	private int maxThread;
	
	public SendingDNTHeaderManager(int maxThread, String fileName, SendingDNTHeaderFileWriter writer) {
		this.pool = Executors.newFixedThreadPool(maxThread);
		this.fileName = fileName;
		this.writer = writer;
		this.maxThread = maxThread;
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
			for (long i = 0; i < 1000; i ++) {
				try {
					if (i != 0) {
						pool.execute(new SendingDNTHeaderWorker(line, writer));
					}
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					i --;
				}
			}
			pool.shutdown();
			//writer.close();
	}
	
	public static void main (String args[]) throws IOException {
		SendingDNTHeaderFileWriter wr = new SendingDNTHeaderFileWriter("output.txt");
		SendingDNTHeaderManager manager = new SendingDNTHeaderManager(1000, "../popular.txt", wr);
		manager.run();
	}
}
