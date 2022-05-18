package Indexer;

import java.util.Set;

public class WordIndexer {

	    public float TF;
	    public String docURL;
	    public int docID;
	    public int countPerDoc;
	    public int docSize;
	    public int h1, h2, h3, h4, h5, h6,title, body;
	    public int wordId;
	    //Set<String> tags;
	    
	    public WordIndexer()
	    {
	    	TF = 0;
	    	countPerDoc = 0;
	    	docSize = 0;
	    	h1 = 0;
	    	h2 = 0;
	    	h3 = 0;
	    	h4 = 0;
	    	h5 = 0;
	    	h6 = 0;
	    	title = 0;
	    	body = 0;
	    	wordId = -1;
	    }
}
