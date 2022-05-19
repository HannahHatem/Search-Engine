package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Indexer.WordIndexer;

public class DatabaseUtils {

	private Connection c = null;
    private Statement  stmt = null;
    final private String password = "1234567890";
    //final private String database = "search_engine";

    public void connectToDB() throws Exception {
    	
    	try /*(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/search_engine", "postgres", "hannah@sk21"))*/ 
    	{
    		 
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                             "jdbc:postgresql://localhost:5432/search_engine",
                             "postgres", password);

           System.out.println("Database Connected ..");
           
 
        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    
    }
    
    public void createTestTable() throws SQLException {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Create Table Dummy2(id int primary key, nameH varchar, address text) ";
            stmt.executeUpdate(CreateSql);
            stmt.close();
            c.close();

        }catch (Exception e){
        	 System.out.println("Table failed to be Created.");

        }
    }

    public void createCrawledLinksTable() throws SQLException {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Create Table crawled_links(url_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,url text,doc_size int default 0,is_visited boolean default false, Unique(url));";
            stmt.executeUpdate(CreateSql);
            System.out.println("Table crawled_links successfully created");

        }catch (Exception e){
        	 System.out.println("Table failed crawled_links to be Created.");

        }
    }
    
    public void createWordIdTable() throws SQLException {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Create Table word_id(id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,word text , Unique(word));";
            stmt.executeUpdate(CreateSql);
            System.out.println("Table word_id successfully created");

        }catch (Exception e){
        	 System.out.println("Table failed word_id to be Created.");

        }
    }

    public void createWordIndexerTable() throws SQLException {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Create Table word_indexer(word_id int references word_id(id), url_id int references crawled_links(url_id), count int,doc_size int, tf int,h1 int, h2 int, h3 int, h4 int, h5 int, h6 int, title int, body int,score int, PRIMARY KEY (word_id, url_id));";
            stmt.executeUpdate(CreateSql);
            System.out.println("Table word_indexer successfully created");

        }catch (Exception e){
        	 System.out.println("Table failed word_indexer to be Created.");

        }
    }
    
    public void createWordIndecesTable() throws SQLException {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Create Table word_indices(word_id int references word_id(id), url_id int references crawled_links(url_id), index int, Primary Key(word_id,url_id, index));";
            stmt.executeUpdate(CreateSql);
            System.out.println("Table word_indicies successfully created");

        }catch (Exception e){
        	 System.out.println("Table failed word_indicies to be Created.");

        }
    }

    public void createPopularityTable() throws SQLException {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Create Table popularity(parent_url_id int references crawled_links(url_id), url_id int references crawled_links(url_id), PRIMARY KEY(parent_url_id, url_id));";
            stmt.executeUpdate(CreateSql);
            System.out.println("Table popularity successfully created");

        }catch (Exception e){
        	 System.out.println("Table failed popularity to be Created.");

        }
    }
   
    public void insertCrawledLink(String url) throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Insert INTO crawled_links(url) VALUES ('"+url+"');";
            stmt.executeUpdate(CreateSql);

        }catch (Exception e){
        	 System.out.println("Failed to insert link in Table");

        }
    }
    
    public void insertPopularity(int pId, int urlId) throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "INSERT INTO popularity(parent_url_id, url_id) VALUES ("+pId+","+ urlId+");";
            stmt.executeUpdate(CreateSql);

        }catch (Exception e){
        	 System.out.println("Failed to insert in Popularity Table");

        }
    }
    
    public int getWordId(String word)throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Select id from word_id where word = '"+word+"';";
            ResultSet rs = stmt.executeQuery(CreateSql);
            int id = 0;
            while (rs.next())
            {
//                System.out.print("Column 1 returned ");
//                System.out.println(rs.getInt(1));
                id = rs.getInt(1);
            }
            return id;
        }catch (Exception e){
        	 System.out.println("Failed to insert link in Table");
        	 return -1;
        }
    }
    
    public int getUrlId(String url)throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Select url_id from crawled_links where url = '"+url+"';";
            ResultSet rs = stmt.executeQuery(CreateSql);
            int id = 0;
            while (rs.next())
            {
//                System.out.print("Column 1 returned ");
//                System.out.println(rs.getInt(1));
                id = rs.getInt(1);
            }
            return id;
        }catch (Exception e){
        	 System.out.println("Failed to insert link in Table");
        	 return -1;
        }
    }
    
    public void insertWord(String word)throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Insert INTO word_id(word) VALUES ('"+word+"');";
            stmt.executeUpdate(CreateSql);

        }catch (Exception e){
        	 System.out.println("Failed to insert wordID in Table");
        }
    }
    
    public void insertWordIndexer(WordIndexer w, int i) throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "Insert INTO word_indexer(word_id, url_id, count, doc_size,tf, h1, h2, h3, h4, h5, h6, title, body, score) "
            		+ "VALUES ("+w.wordId+", "+ w.docID +" ,"+ w.countPerDoc+","+w.docSize+","+w.TF+","+ w.h1+","+ w.h2+","+w.h3+","+ w.h4+","+ w.h5+","+ w.h6+","+ w.title+","+ w.body+", 0);";
            stmt.executeUpdate(CreateSql);
        }catch (Exception e){
        	 System.out.println("Failed to insert in WordIndexer Table");

        }
    }
    
    public List<String> getCrawledLinks() throws Exception {

        	stmt = c.createStatement();    
            String CreateSql = "Select url from crawled_links where is_visited = false;";
            //stmt.executeUpdate(CreateSql);
            ResultSet rs = stmt.executeQuery(CreateSql);
            List<String> l = new ArrayList<String>();
            while (rs.next()) {
                l.add(rs.getString("url"));
                //System.out.println(rs.getString("url"));
            }
            System.out.println(l.size());
            return l;
    }

    public void setIsVisistedLink(int url_id)throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "UPDATE public.crawled_links SET is_visited= true WHERE url_id = "+url_id+";";
            stmt.executeUpdate(CreateSql);

        }catch (Exception e){
        	 System.out.println("Failed to set is_visited url");
        }
    }
    
    public void insertWordIndices(int word_id, int url_id, int index)throws Exception {
        try {
        	stmt = c.createStatement();    
            String CreateSql = "INSERT INTO public.word_indices("
            		+ "	word_id, url_id, index)"
            		+ "	VALUES ("+word_id+","+ url_id+","+ index+");";
            stmt.executeUpdate(CreateSql);

        }catch (Exception e){
        	 System.out.println("Failed to insert in worde_indices Table");
        }
    }
    
    public static void main(String[] args) throws Exception {
        // Connect to the database
    	DatabaseUtils db = new DatabaseUtils();
        db.connectToDB();
        db.createCrawledLinksTable();
        db.createWordIdTable();
        db.createWordIndexerTable();
        db.createWordIndecesTable();
        db.createPopularityTable();
        
    }
}
