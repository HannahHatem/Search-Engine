package Indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import Database.DatabaseUtils;

public class IndexerTest {

	// Number of threads
    private static final int N = 4;
	static DatabaseUtils db;
	public static String stopWords;
	
	static void getStopWords()
    {
        List<String> readStopwords = null;
        try {
        	readStopwords = Files.readAllLines(Paths.get("src/stopWords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWords = readStopwords.stream()
                .collect(Collectors.joining(" "));
    }
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		db = new DatabaseUtils();
		db.connectToDB();
		getStopWords();
		List crawledLinks = db.getCrawledLinks();
		Thread myThreads[] = new Thread[N];
        IndexerThreads n = new IndexerThreads(stopWords,crawledLinks,db);

        //Multiple threads Initialization (as ThreadedIndexer)and setting their names according to the index
        for(int i=0 ;i<N; i++){

            myThreads[i]= new Thread(n);
            myThreads[i].setName(Integer.toString(i+1));
            myThreads[i].start();
        }
        
        for (int i=0;i<N;i++)
        {
            myThreads[i].join();
        }
	}

}
