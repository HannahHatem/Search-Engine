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

public class DatabaseUtils {

	private Connection c = null;
    private Statement  stmt = null;
    final private String password = "hannah@sk21";
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
            String CreateSql = "Create Table crawled_links("
            		+ "id serial,"
            		+ "url text primary key, "
            		+ "is_visited boolean default false"
            		+ ");";
            stmt.executeUpdate(CreateSql);
            System.out.println("Table crawled_links successfully created");

        }catch (Exception e){
        	 System.out.println("Table failed crawled_links to be Created.");

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
    
    public List getCrawledLinks() throws Exception {

        	stmt = c.createStatement();    
            String CreateSql = "Select url from crawled_links where is_visited = false;";
            //stmt.executeUpdate(CreateSql);
            ResultSet rs = stmt.executeQuery(CreateSql);
            List l = new ArrayList();
            while (rs.next()) {
                l.add(rs.getString("url"));
                //System.out.println(rs.getString("url"));
            }
            System.out.println(l.size());
            return l;
    }

        
    public static void main(String[] args) throws Exception {
        // Connect to the database
    	DatabaseUtils db = new DatabaseUtils();
        db.connectToDB();
        //db.createCrawledLinksTable();
        //db.insertCrawledLink("https://abcnews.go.com/");
        db.getCrawledLinks();
    }
}
