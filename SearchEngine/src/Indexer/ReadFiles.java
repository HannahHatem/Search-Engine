package Indexer;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

import Database.DatabaseUtils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFiles {

	static Vector<String> arrFiles = new Vector<String>();
	static DatabaseUtils db;
	static void PrintFile(String fileName) throws Exception
    {         
           BufferedReader br = new BufferedReader(new FileReader("./input/"+fileName));
	       String st;  
	         while ((st = br.readLine()) != null)
	             System.out.println(st);
	         br.close();
    }
	
	static void ReadFile()
	{
		File visited = new File("Links.txt");
		if (visited.exists()) {
			try 
			{	
				Scanner URLScanner = new Scanner(visited);
				while (URLScanner.hasNextLine()) {
		            String currentURL = URLScanner.nextLine();
		            try {
						db.insertCrawledLink(currentURL);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static void ReadFolder()
	{
		try
		{
			String folderName = "./input/";
			File sourceFolder = new File(folderName);
			String fileTxt = "";
			for(File sourceFile : sourceFolder.listFiles())
			{
				String fileName = sourceFile.getName();
				fileTxt = fileName.substring(fileName.lastIndexOf(".")+1);
				
				if(fileTxt.equalsIgnoreCase("txt"))
					System.out.println("We have read " + fileName + " successfully");
				else
					System.out.println("Filename extension not supported");
				
				
				String content = Files.readString(Paths.get(folderName + fileName));
	            //System.out.println(content);
	            arrFiles.add(content);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public static void main(String args[])
	{
		//ReadFolder();
		db = new DatabaseUtils();
		try {
			db.connectToDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadFile();
		
	}
}
