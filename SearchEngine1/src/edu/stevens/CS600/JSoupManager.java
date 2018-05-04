package edu.stevens.CS600;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * JSOup Manager is used to interface with all other classes
 * @author Dhavala
 *
 */
public class JSoupManager {
	JSoupParser parser;
	WebCrawler crawler;
	Trie t;
	private String url;
	private static String text;
	
	private static ArrayList<String> relevantLinks;
	
	private static ArrayList<String> wordsList;
	
	private String[] searchWords;
	
	public JSoupManager(String url, String[] args){
		//Initialize all the variables
		this.url=url;
		
		//Class containing JSoup parser logic
		parser=new JSoupParser();
		
		//Web crawler instance
		crawler=new WebCrawler();
	    t=new Trie();
	    
	    //user provided serach parameters. Use them
	    if(args.length>1)
	    {
	    	searchWords=new String[args.length-1];
	    	for(int i=1;i<args.length;i++){
	    		System.out.println("args:: "+args[i]);
		    	searchWords[i-1]=args[i];
		    }
	    }
	    else
	    {
	    	//user did not provide search parameters. Using default values
	    	searchWords=new String[2];
	    	searchWords[0]="baldwin";
	    	searchWords[1]="girls";
	    }
	}
	
	public void parse() throws IOException{
		//fetch the links related to the current url
		relevantLinks=crawler.fetchRelevantLinks(url);
		
		//sort the links
//		relevantLinks.sort(new Comparator<String>() {
//
//			@Override
//			public int compare(String o1, String o2) {
//				// TODO Auto-generated method stub
//				return o1.compareToIgnoreCase(o2);
//			}
//		});
		
		//For each link, fetch the text and parse it
		for(int i=0;i<relevantLinks.size();i++){
			//fetch the text
			text=parser.fetchText(relevantLinks.get(i));
			
			//split the text and remove the stop words 
			wordsList=parser.splitText(text);
			System.out.println("JSOUP Manager link:: "+relevantLinks.get(i));
			System.out.println("JSOUP Manager:: "+wordsList.size()+"  "+wordsList.toString());
			
			if(wordsList!=null)
			{
				//Sort the word list
				sortWordList();
				System.out.println("JSOUP Manager2 :: "+wordsList.size()+"  "+wordsList.toString());
				
				//traverse through the words and insert each into the trie structure
				for(int j=0;j<wordsList.size();j++){
					System.out.println("JSOUP Manager2 ::inserting "+wordsList.get(j));
					t.insert(wordsList.get(j).toLowerCase(), relevantLinks.get(i));
				}
			}
		}
		
		//Once all the words are inserted, search for the words
		t.search(searchWords);
		
	}
	
	/**
	 * Sorts the word list
	 */
	public void sortWordList(){
    	// sort the word list 
    	wordsList.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareToIgnoreCase(o2);
			}
		});
    }

}
