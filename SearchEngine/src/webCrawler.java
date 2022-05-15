import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class webCrawler {

	private static LinkedList <String> links= new LinkedList <String>();
	private static HashSet <String> visitedLinks= new HashSet <String>();
	private static LinkedList <Integer> IDs= new LinkedList <Integer>();

	
	private static void crawl(String url ,Integer id)
	{	
			
			Document doc=request(url);
			if(doc!=null)
			{
				File input = new File("Documents\\"+ (id / 500 + 1) +"\\"+id.toString()+".html");
				FileWriter myWriter;
				try {
					myWriter = new FileWriter(input);
		            myWriter.write(doc.toString());
		            myWriter.close();
				} catch (IOException e) {
					
					// TODO Auto-generated catch block
					synchronized (IDs) {
						IDs.addFirst(id);
					}
					e.printStackTrace();
				}
				Boolean robot;
				for(Element link: doc.select("a[href]")) 
				{
					
					String next_link=link.absUrl("href");
					//=URL url=new URL(next_link);
					try {
						//robot=robotSafe(new URL(next_link));
					} catch (Exception e) {
						robot=false;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(visitedLinks.contains(next_link)==false )
					{
						links.add(next_link);
					}
				}
			}
			else
			{
				synchronized (IDs) {
					IDs.addFirst(id);
				}
			}
	}
	
	private static Document request(String url)
	{

		try{
			Connection con= Jsoup.connect(url);
			Document doc=con.get();
			if (con.response().statusCode()==200) {
				System.out.println("\n** Thread ID : "+Thread.currentThread().getName()+ " Received  webpage at " +url);
				String title=doc.title();
				System.out.println(title);
				visitedLinks.add(url);
				
				return doc ;
			}
			return null;
			
		}
		catch(IOException e){
			return null ;
		}

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);  

		int threadsNum=sc.nextInt();
		File seedFile = new File("Links.txt");
		Scanner URLScanner;
		try {
			
			URLScanner = new Scanner(seedFile);
			while (URLScanner.hasNextLine()) {
	            String currentURL = URLScanner.nextLine();
	            links.add(currentURL);
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
		for (int i = 0; i < 5500; i++) {
			IDs.add(i);
		}
		Thread[] crawlerThreads = new Thread[threadsNum];
			for (Integer i = 0; i < threadsNum; i++)
			{
				
				crawlerThreads[i] = new Thread(() -> 
				{
					
					while(!links.isEmpty()&&!IDs.isEmpty()&& visitedLinks.size()<=10)
					{
						int number;
						String link;
						synchronized (IDs){
							number=IDs.getFirst();
							IDs.removeFirst();
						}
						synchronized (links) {
							link=links.getFirst();
							links.removeFirst();
						}
						crawl(link,number);
					}
				});
				crawlerThreads[i].setName(i.toString());

				crawlerThreads[i].start();
			}
			for (Integer i = 0; i < threadsNum; i++)
			{				
				try {
					crawlerThreads[i].join();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
/*	
	
	public static class RobotRule {
        public String userAgent;
        public String rule;

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            String NEW_LINE = System.getProperty("line.separator");
            result.append(this.getClass().getName()).append(" Object {").append(NEW_LINE);
            result.append("   userAgent: ").append(this.userAgent).append(NEW_LINE);
            result.append("   rule: ").append(this.rule).append(NEW_LINE);
            result.append("}");
            return result.toString();
        }
    }

    public static boolean robotSafe(URL url) {
        String strHost = url.getHost();

        String strRobot = "http://" + strHost + "/robots.txt";
        URL urlRobot;
        try {
            urlRobot = new URL(strRobot);
        } catch (MalformedURLException e) {
            // something weird is happening, so don't trust it
            return false;
        }

        StringBuilder strCommands;
        try {
            InputStream urlRobotStream = urlRobot.openStream();
            byte[] b = new byte[1000];
            int numRead;
            strCommands = new StringBuilder("");
            while (true) {
                numRead = urlRobotStream.read(b);
                if (numRead != -1) {
                    String newCommands = new String(b, 0, numRead);
                    strCommands.append(newCommands);
                } else break;
            }
            urlRobotStream.close();
        } catch (IOException e) {
            return true; // if there is no robots.txt file, it is OK to search
        }

        if (strCommands.toString().toLowerCase().contains("disallow")) // if there are no "disallow" values, then they are not blocking anything.
        {
            String[] split = strCommands.toString().split("\n");
            ArrayList<RobotRule> robotRules = new ArrayList<>();
            String mostRecentUserAgent = null;
            for (String s : split) {
                String line = s.trim();
                if (line.toLowerCase().startsWith("user-agent")) {
                    int start = line.indexOf(":") + 1;
                    int end = line.length();
                    mostRecentUserAgent = line.substring(start, end).trim();
                } else if (line.toLowerCase().startsWith("disallow")) {
                    if (mostRecentUserAgent != null) {
                        RobotRule r = new RobotRule();
                        r.userAgent = mostRecentUserAgent;
                        int start = line.indexOf(":") + 1;
                        int end = line.length();
                        r.rule = line.substring(start, end).trim();
                        robotRules.add(r);
                    }
                }
            }

            for (RobotRule robotRule : robotRules) {
                String path = url.getPath();
                if (robotRule.rule.length() == 0) return true; // allows everything if BLANK
                if (robotRule.rule.equals("/")) return false;       // allows nothing if /

                if (robotRule.rule.length() <= path.length()) {
                    String pathCompare = path.substring(0, robotRule.rule.length());
                    if (pathCompare.equals(robotRule.rule)) return false;
                }
            }
        }
        return true;
    }	*/

}