package Indexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

//import javax.lang.model.util.Elements;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import Database.DatabaseUtils;

public class IndexerThreads  implements Runnable{

    private static final int N = 4;      // Number of threads
    private static String stopWords;  					// A string containing all of the stopping words
    List<String> crawledLinksList;     // A list of all the links sent by the crawler
    public static DatabaseUtils db;  				  //Access the database


    public IndexerThreads(String stopWords, List<String> urlList, DatabaseUtils db) {
        this.stopWords = stopWords;
        this.crawledLinksList = urlList;
        this.db = db;
    }
    
    private static Elements getDoc(String url) throws IOException
	{

		try{
			Connection con= Jsoup.connect(url);
     		Document doc=con.get();
//			if (con.response().statusCode()==200) {
//				System.out.println(doc);
//			    return doc ;
//				}
			
			Connection.Response resp = con.execute();
	        if (resp.statusCode() == 200) {

//	            String HTMLSTring = doc.toString();
//	            Document html = Jsoup.parse(HTMLSTring);

	            Elements ParagraphsOnPage = doc.select("p, h0,h1,h2,h3,h4,h5,h6,title, body");

	            return ParagraphsOnPage;

	        }
			
			return null;
		}
		catch(IOException e){
			return null ;
		}

	}
    
    private static void whichTag(String tagName, WordIndexer w)
    {
    	if(tagName == "h1")
    		w.h1++;
    	else if(tagName == "h2")
    		w.h2++;
    	else if(tagName == "h3")
    		w.h3++;
    	else if(tagName == "h4")
    		w.h4++;
    	else if(tagName == "h5")
    		w.h5++;
    	else if(tagName == "h6")
    		w.h6++;
    	else if(tagName == "body")
    		w.body++;
    	else if(tagName == "title")
    		w.title++;
    }
    private static void stemming(Elements contentBlocks, String url) 
    {
		Hashtable<String, WordIndexer> hashDoc = new Hashtable<>();
		//PorterStemmer porterStemmer = new PorterStemmer();
	
			for (Element line : contentBlocks) {	
				String lineString = line.text();
				System.out.println("first line = "+ lineString);
				String lineTag = line.tagName();
				lineString = lineString.toLowerCase().replaceAll(stopWords, " ");
				lineString = lineString.replaceAll("\\p{Punct}", " ");
				String[] words = lineString.replaceAll("[^a-zA-Z]", " ").split("\\W+");
				List<String> list = new ArrayList<String>(Arrays.asList(words));
				list.remove(" ");
				words = list.toArray(new String[0]);
				for (String word : words) {
					if (word == "" || word == " ")
                    continue;
					String stem =   word; // porterStemmer.stem(word);
					if (hashDoc.containsKey(stem)) {
						WordIndexer w = hashDoc.get(stem);
						w.TF++;
						w.countPerDoc++;
						whichTag(lineTag, w);
						hashDoc.put(stem, w);
					} 
					else {
						WordIndexer w = new WordIndexer();
						try {
							db.insertWord(stem);
							int id = db.getWordId(stem);
							w.wordId = id;
							System.out.println(id);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						w.TF = 1;
						whichTag(lineTag, w);
						w.countPerDoc++;
						//w.doc_ID = fileIndex;
						w.docURL = url;
						hashDoc.put(stem, w);
					}
				}
			}
			hashDoc.entrySet().forEach(entry -> {
	            System.out.println(entry.getKey() + "->" + entry.getValue().docURL+" , count= "+entry.getValue().countPerDoc);
	            try {
					db.insertWordIndexer(entry.getValue());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			
			
    }
    
    
    @Override
    public void run(){
    	
    	int threadName = Integer.parseInt(Thread.currentThread().getName());
        int docPerThread = crawledLinksList.size() / N;

        try {
            int startIndex = docPerThread * (threadName - 1);
            int endIndex = threadName == N ? crawledLinksList.size() : startIndex + docPerThread;
            for (int i = startIndex; i < endIndex; i++) {
               Elements contentBlocks = getDoc(crawledLinksList.get(i));
               if (contentBlocks != null) {
                   stemming(contentBlocks,crawledLinksList.get(i) );
               }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
   }
	
	public static void main(String[] args) {
 
		db = new DatabaseUtils();
		try {
			db.connectToDB();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> readStopwords = null;
        try {
        	readStopwords = Files.readAllLines(Paths.get("src/stopWords.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWords = readStopwords.stream()
                .collect(Collectors.joining(" "));
        
		Elements ParagraphsOnPage;
		try {
			ParagraphsOnPage = getDoc(" https://abcnews.go.com/");
			System.out.println(ParagraphsOnPage.text());
			
			for (Element e : ParagraphsOnPage) {
				
				System.out.println(e.text());
				System.out.println(e.tagName());
			}
			stemming(ParagraphsOnPage," https://abcnews.go.com/" );

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}

}
