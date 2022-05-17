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
            String CreateSql = "Create Table Dummy(id int primary key, nameH varchar, address text) ";
            stmt.executeUpdate(CreateSql);
            stmt.close();
            c.close();

        }catch (Exception e){
        	 System.out.println("Table failed to be Created.");

        }
    }
    
    public static void main(String[] args) throws Exception {
        // Connect to the database
    	DatabaseUtils db = new DatabaseUtils();
        db.connectToDB();
        db.createTestTable();
    }
}
