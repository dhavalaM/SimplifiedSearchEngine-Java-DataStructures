package edu.stevens.CS600;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupParser {
	
	public JSoupParser(){
	}
	
	/**
	 * This method takes a string text and splits it into arraylist 
	 * of strings using regular expressions.
	 * It also parses these words by removing a list of predefined stop words
	 * available in res folder
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> splitText(String text) throws IOException
    {
    	int k=0;
    	String sCurrentLine;
//    	FileReader fr=new FileReader("C:\\Dhavala\\stopwordslist.txt.txt");
    	FileReader fr=new FileReader("src/res/stopwordslist.txt");
	    BufferedReader br= new BufferedReader(fr);
	    //reading number of stop words
	    while ((br.readLine()) != null){
	         k++;
	    }
	    System.out.println("No of stop words:: "+k);
	    String[] stopwords = new String[k];
	    br.close();
	    fr.close();
	    k=0;
	    
	    fr=new FileReader("C:\\Dhavala\\stopwordslist.txt.txt");
	    br= new BufferedReader(fr);
    	
	    //Read the stop words
	    while ((sCurrentLine = br.readLine()) != null){
	         stopwords[k]=sCurrentLine;
	         k++;
	    }
	        
	    br.close();
	    fr.close();
    	Pattern pattern = Pattern.compile("\\w+");
    	Matcher matcher = pattern.matcher(text);
    	ArrayList<String> wordList = new ArrayList<String>();
    	
    	while (matcher.find()) {
    		/*
    		 * Check if current word is present in list of stop words using binary search
    		 * if present, skip the word
    		 * else add the word to wordlist
    		 */
            	if(Arrays.binarySearch(stopwords, matcher.group().toLowerCase())<0)
            	{
            		wordList.add(matcher.group());
            	}
        }
    	System.out.println("wordList size:: "+wordList.size()+" :: wordlist ::"+wordList.toString());
    	return wordList;
    }
    
    
    /**
     * This method connects to the url and returns the text of the web page
     * @param url
     * @return
     * @throws IOException
     */
    public String fetchText(String url) throws IOException{
    	Document doc;
    	String text;
    	doc=Jsoup.connect(url).get();
    	text=doc.body().text();
    	return text;
    }

}
