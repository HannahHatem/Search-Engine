package Indexer;

import java.io.*;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFiles {

	static Vector<String> arrFiles = new Vector<String>();
	
	static void PrintFile(String fileName) throws Exception
    {         
           BufferedReader br = new BufferedReader(new FileReader("./input/"+fileName));
	       String st;  
	         while ((st = br.readLine()) != null)
	             System.out.println(st);
	         br.close();
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
		ReadFolder();
		System.out.println(arrFiles.get(1));
	}
}
