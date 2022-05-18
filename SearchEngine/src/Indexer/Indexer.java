package Indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import Database.DatabaseUtils;

public class Indexer {

	public static final int N = 2;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseUtils db = new DatabaseUtils();
		try {
			db.connectToDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		List<String> readStopwords = null;
		try {
			readStopwords = Files.readAllLines(Paths.get("src/stopWords.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String stopWords = readStopwords.stream().collect(Collectors.joining(" "));
		
		List<String> crawledLinks = null;
		try {
			crawledLinks = db.getCrawledLinks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread myThreads[] = new Thread[N];
        IndexerThreads n = new IndexerThreads(stopWords,crawledLinks,db);

        //Multiple threads Initialization (as ThreadedIndexer)and setting their names according to the index
        for(int i=0 ;i<N; i++){

            myThreads[i]=new Thread(n);
            myThreads[i].setName(Integer.toString(i+1));
            myThreads[i].start();
        }

        // Join all the threads in the thread array
        for (int i=0;i<N;i++)
        {
            try {
				myThreads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        System.out.println("Finished");
		
	}

}
