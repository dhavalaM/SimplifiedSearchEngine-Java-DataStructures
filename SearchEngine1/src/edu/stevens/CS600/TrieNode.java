package edu.stevens.CS600;

import java.util.HashMap;

public class TrieNode {
	char c;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    boolean isLeaf;
    HashMap<String, Integer> url=new HashMap<String,Integer>();
    
    public TrieNode() {}
 
    public TrieNode(char c){
        this.c = c;
    }
	 

}
