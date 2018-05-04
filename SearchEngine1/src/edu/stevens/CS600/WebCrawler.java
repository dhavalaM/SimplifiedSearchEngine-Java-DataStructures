package edu.stevens.CS600;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;;

public class WebCrawler {
	
	private static final int LIMIT=5;
	
	/**
	 * Contains the urls that have been parsed
	 */
	private ArrayList<String> visited;
	
	/**
	 * Contains the urls that are yet to be parsed
	 */
	private ArrayList<String> toVisit;
	
	public WebCrawler() {
		visited=new ArrayList<String>();
		toVisit=new ArrayList<String>();
	}

	/**
	 * Fetch the url from toVisit list that is not in visited list
	 * @return
	 */
	private String fetchNextUrl(){
		String url=null;
		int i=0;
		while(i<toVisit.size())
		{
			url=toVisit.get(i);
			if(!visited.contains(url))
				break;
			i++;
		}
		if(url!=null)
		{
			visited.add(url);
		}
		return url;
	}
	
	private void removeUrl(String url) {
		if(visited.contains(url)){
			visited.remove(url);
		}
		if(toVisit.contains(url)){
			toVisit.remove(url);
		}
		
	}
	
	/**
	 * THis method fetches the relevant links. It parses the page for links,
	 * adds them to the toVisit list if it is not already present.
	 * The next url is fetced from the to Visit list if it not already
	 * traversed in an earlier loop
	 * @param url
	 * @return
	 */
	public ArrayList<String> fetchRelevantLinks(String url) {
		
		String curUrl;
		while(visited.size()<LIMIT)
		{
			System.out.println("toVisit.size(): "+toVisit.size()+"  visited.size(): "+visited.size());
			if(toVisit.size()==0){
				//first url, add it to the list
				visited.add(url);
					curUrl=url;
			}
			else{
				//fetch the next url that is unique and not yet visited
				curUrl=fetchNextUrl();
			}
			System.out.println("curUrl: "+curUrl);
			Document doc=null;
			try {
				doc = Jsoup.connect(curUrl).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error fetching url:: "+curUrl);
				doc=null;
				
			}
			if(doc!=null)
			{
				Elements links=getLinks(doc);
				if(!links.isEmpty())
				{
					//Get all the links, check for all the links not present already and add them
					System.out.println("adding :: "+links.size());
					for(Element link : links) {
						String newUrl=link.attr("abs:href");
						if(!visited.contains( newUrl)&& !toVisit.contains(newUrl))
							toVisit.add( newUrl);
					}
				}
					
			}
			else{
				//unable to connect to url, remove this from the list
				removeUrl(curUrl);
			}
		}
		System.out.println("fetchRelevantLinks :: "+visited.toString());
		return visited;
	}
	
	private Elements getLinks(Document doc){
		Elements links = doc.select("a[href]");
        return links;
	}
}
