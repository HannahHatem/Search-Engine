package Indexer;

import java.util.List;

import Database.DatabaseUtils;

public class IndexerThreads  implements Runnable{

	// Number of threads
    private static final int N = 4;

    // A string containing all of the stopping words
    String stopWords;
    // A list of all the links sent by the crawler
    List<String> crawledLinksList;
    //Access the database
    DatabaseUtils db;


    public IndexerThreads(String stopWords, List<String> urlList, DatabaseUtils db) {
        this.stopWords = stopWords;
        this.crawledLinksList = urlList;
        this.db = db;
    }
    
    @Override
    public void run() {}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
