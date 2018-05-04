package edu.stevens.CS600;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Trie {
	private TrieNode root;
	HashMap<String, ArrayList<String>> searchMap=new HashMap<String,ArrayList<String>>();
	 
    public Trie() {
        root = new TrieNode();
    }
 
    /**
     * Inserts a word into the trie.
     * @param word
     * @param url
     */
    public void insert(String word,String url) {
    	HashMap<Character, TrieNode> children = root.children;
    	
    	/*
    	 * Traverse the tire till matching characters. Once a non matching character is found, create a new node 
    	 * and insert the character there
    	 */
    	
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            TrieNode t;
            if(children.containsKey(c)){
            	//character match, just move one level lower
            	t = children.get(c);
            }else{
            	//character mismatch, create a node and insert the character
            	t = new TrieNode(c);
                children.put(c, t);
            }
 
            children = t.children;
 
            //set leaf node
            if(i==word.length()-1)
                {
            		System.out.println("adding url here "+url);
            		t.isLeaf = true;
            		/*
            		 * at the leaf node we store the urls where the owrd was found.
            		 * Urls are stored in a hashmap with url as key and number of occurances as value.
            		 * For each occurence in a url, the count is increased. This is to rank the pages according to 
            		 * relevance. The number of occurances is used to rank the page.
            		 */
            		t.url.put(url, t.url.getOrDefault(url, 0)+ 1);
                }
        }
    }
    
    /**
     * Search for words
     */
    public void search(String[] searchWords){
    	ArrayList<String> fetchedUrls;
    	ArrayList<String> presentUrls;
    	List<List<String>> lists = new ArrayList<List<String>>();
    	
    	for(int i=0;i<searchWords.length;i++){
    		//search for each word and get the urls it was found in order of ranking
    		fetchedUrls=search(searchWords[i]);
    		
    		if(fetchedUrls!=null){
    			//word exists in the pages
    			presentUrls=searchMap.get(searchWords[i]);
    			
    			/*
    			 * create a reverse index with the word as the key and 
    			 * the associated list of urls as the value. This will be used to 
    			 * fetch the intersection of all the list of urls
    			 */
    			if(presentUrls==null)
    				presentUrls=new ArrayList<String>();
    			presentUrls.addAll(fetchedUrls);
    			
    			//creating the reverse index
    			searchMap.put(searchWords[i], presentUrls);
    		}
    		else{
    			//word not found, insert empty list of url
    			searchMap.put(searchWords[i], new ArrayList<String>());
    		}
    		
    	}
    	
    	System.out.println("Sfter search");
    	/*
    	 * After creating the reverse index, iterate through it
    	 * and fetch the all the url lists for all the words.
    	 * Each word has one url list associated with it. Hence for 
    	 * n words we will have n url lists
    	 */
    	Set<Entry<String, ArrayList<String>>> urls = searchMap.entrySet();
    	for(Entry<String,  ArrayList<String>> mapping : urls)
    	{
    		ArrayList<String> test=mapping.getValue();
    		System.out.println(mapping.getKey() + " ==> ");
    		lists.add(mapping.getValue());
    		for(int i=0;i<test.size();i++)
    			System.out.println(test.get(i));
    		
    	}

    	//find the intersection of all the lists
    	findIntersection(lists);
    	
    }
 
    /**
     * Method to search for a particular word. returns a list of urls that
     * the word was found in
     * @param word
     * @return
     */
    private ArrayList<String> search(String word) {
        TrieNode t=null;
//        = searchNode(word);
        ArrayList<String> matchingUrls=new ArrayList<String>();
        Map<Character, TrieNode> currentChildren = root.children; 
        //Iterate through the trie for length of the word
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(currentChildren.containsKey(c)){
            	//character match, proceed to next level
                t = currentChildren.get(c);
                currentChildren = t.children;
            }
        }
 
        if(t != null && t.isLeaf) 
        {
        	//all the characters have matched hence word found
        	System.out.println("associated urls:: "+word);
        	Set<Entry<String, Integer>> urls = t.url.entrySet();
        	//create a list of the urls and return
        	for(Entry<String, Integer> mapping : urls)
        	{
        		System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        		matchingUrls.add(mapping.getKey());
        	}
        	return matchingUrls;
        }
        else
        {
        	//word not found return null
        	 return null;
        }
           
    }
 
    
    private void findIntersection(List<List<String>> list){
    	
    	    /*
    	     * create a set and find intersection of the first list with the second list.
    	     * Then use this result to find the intersection with third list and so on
    	     */
    		Set<String> result = new HashSet<String>(list.get(0));
    	    for (List<String> l : list) {
    	        result.retainAll(l);
    	        
    	    }
    	    System.out.println("\n \n");
    	    System.out.println("****************************************************************");
    	    System.out.println("                       SEARCH RESULT");
    	    System.out.println("****************************************************************");
    	    //Intersection was null
    	    if(result.isEmpty())
    	    	System.out.println("No links found with all the input words. Please try again.");
    	    else{
    	    	//links are printed in order of relevance
    	    	System.out.println("The search words are present in the following order of relevance at links:: ");
        	    result.forEach(System.out::println);
    	    }
    	    
    }

}
