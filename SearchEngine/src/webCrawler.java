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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import Database.DatabaseUtils;

public class webCrawler {
	private static final int MAX_PAGES_TO_SEARCH = 5000;
	private static LinkedList<String> links = new LinkedList<String>();
	private static HashSet<String> visitedLinks = new HashSet<String>();
	private static HashSet<String> visitedStrings = new HashSet<String>();
	private static LinkedList<Integer> IDs = new LinkedList<Integer>();
	static DatabaseUtils db;

	private static void crawl(String url, Integer id) {
		Document doc = request(url);
		// System.out.println(doc);

		if (doc != null) {
			try {
				db.insertCrawledLink(url);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("webCrawler can't insert in db");
				e1.printStackTrace();
			}
			File input = new File("Documents\\" + ((id / 500) + 1) + "\\" + id.toString() + ".html");
			FileWriter myWriter;
			try {
				myWriter = new FileWriter(input);
				myWriter.write(doc.toString());
				myWriter.close();
				// System.out.println(myWriter);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				synchronized (IDs) {
					IDs.addFirst(id);
				}
				e.printStackTrace();
			}
			// Boolean robot;
			for (Element link : doc.select("a[href]")) {
				String next_link = link.absUrl("href");
				/*
				 * =URL url=new URL(next_link); try { robot=robotSafe(new URL(next_link)); }
				 * catch (Exception e) { robot=false; // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */

				if (visitedLinks.contains(next_link) == false) {
					links.add(next_link);

				}
			}
		} else {
			synchronized (IDs) {
				IDs.addFirst(id);
			}
		}

		File linksState = new File("State\\links.txt");
		synchronized (links) {
			try {
				FileWriter myWriter = new FileWriter(linksState);
				for (int i = 0; i < links.size(); i++) {
					try {
						myWriter.write(links.get(i) + "\n");
					} catch (Exception e1) {
						myWriter.close();
						break;
					}
				}
				myWriter.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		File visitedState = new File("State\\visitedLinks.txt");
		synchronized (visitedLinks) {
			try {
				FileWriter myWriter = new FileWriter(visitedState);
				Iterator<String> itr = visitedLinks.iterator();
				for (int i = 0; i < visitedLinks.size(); i++) {
					myWriter.write(itr.next() + "\n");
				}
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		visitedState = new File("Documents\\visitedLinks.txt");
		synchronized (visitedLinks) {
			try {
				FileWriter myWriter = new FileWriter(visitedState);
				Iterator<String> itr = visitedLinks.iterator();
				for (int i = 0; i < visitedLinks.size(); i++) {
					myWriter.write(itr.next() + "\n");
				}
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		File stringsState = new File("State\\visitedStrings.txt");
		synchronized (visitedStrings) {
			try {
				FileWriter myWriter = new FileWriter(stringsState);
				Iterator<String> itr = visitedStrings.iterator();
				for (int i = 0; i < visitedStrings.size(); i++) {
					myWriter.write(itr.next() + "\n");
				}
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static Document request(String url) {

		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if (con.response().statusCode() == 200) {
				System.out.println(
						"\n** Thread ID : " + Thread.currentThread().getName() + " Received  webpage at " + url);
				String title = doc.title();
				System.out.println(title);
				String URLString = getURLString(doc);
				if (visitedStrings.contains(URLString) == false && visitedLinks.contains(url) == false) {
					visitedStrings.add(URLString);
					visitedLinks.add(url);
					return doc;
				}
			}
			return null;

		} catch (IOException e) {
			return null;
		}

	}

	private static String getURLString(Document doc) {

		String toAppend = "";
		String URLString = "";
		Integer c = 0;

		for (Element link : doc.select("div")) {
			c++;
			// System.out.println(c);

		}

		toAppend = c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("p")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("ul")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("h1")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("h2")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("h3")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("br")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("ol")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("dl")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);
		c = 0;

		for (Element link : doc.select("article")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("button")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("input")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		c = 0;

		for (Element link : doc.select("select")) {
			c++;
			// System.out.println(c);

		}
		toAppend = toAppend + c.toString();
		// System.out.println(toAppend);

		return toAppend;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		db = new DatabaseUtils();
		try {
			db.connectToDB();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int threadsNum = sc.nextInt();
		File seedFile = new File("State\\links.txt");
		if (!seedFile.exists()) {
			seedFile = new File("Links.txt");
		}

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

		File visited = new File("State\\visitedLinks.txt");
		if (visited.exists()) {
			try {
				URLScanner = new Scanner(visited);
				while (URLScanner.hasNextLine()) {
					String currentURL = URLScanner.nextLine();
					visitedLinks.add(currentURL);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		visited = new File("State\\visitedStrings.txt");
		if (visited.exists()) {
			try {
				URLScanner = new Scanner(visited);
				while (URLScanner.hasNextLine()) {
					String currentURL = URLScanner.nextLine();
					visitedStrings.add(currentURL);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 5000; i++) {
			IDs.add(i);
		}
		Thread[] crawlerThreads = new Thread[threadsNum];
		for (Integer i = 0; i < threadsNum; i++) {

			crawlerThreads[i] = new Thread(() -> {
				while (!links.isEmpty() && !IDs.isEmpty() && visitedLinks.size() <= MAX_PAGES_TO_SEARCH) {
					int number;
					String link;
					synchronized (IDs) {
						number = IDs.getFirst();
						IDs.removeFirst();
					}
					synchronized (links) {
						link = links.getFirst();
						links.removeFirst();
					}
					crawl(link, number);
				}
			});
			// crawlerThreads[i].setName(i.toString());

			crawlerThreads[i].start();
		}
		for (Integer i = 0; i < threadsNum; i++) {
			try {
				crawlerThreads[i].join();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File linksState = new File("State\\links.txt");
		linksState.delete();
		File visitedState = new File("State\\visitedLinks.txt");
		visitedState.delete();
		File stringsState = new File("State\\visitedStrings.txt");
		stringsState.delete();

	}
}