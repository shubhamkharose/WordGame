package com.example.shubham.wordgame;

import java.util.HashMap;



public class Trie {
    private TrieNode root;
    int cnt=0;

    public Trie() {
        root = new TrieNode();
    }


    /**
     * Inserts a word into the trie.
      */
    public void add(String word) {
        HashMap<Character, TrieNode> children = root.getChildren();

        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.getChildren();

            //set End of Word
            if(i==word.length()-1)
                t.setEnd(true);
        }
    }


    /**
     * Returns if the word is in the trie
     */
    public boolean isWord(String word) {
        TrieNode t = searchNode(word);
        if(t != null && t.isEnd())
            return true;
        return false;
    }


    /**
     *  searches a word in trie
     */
    public TrieNode searchNode(String str){
        HashMap<Character, TrieNode> children = root.getChildren();
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.getChildren();
            }else{
                return null;
            }
        }
        return t;
    }
}
