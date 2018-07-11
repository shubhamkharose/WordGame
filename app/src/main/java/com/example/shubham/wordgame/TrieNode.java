package com.example.shubham.wordgame;

import java.util.HashMap;


public class TrieNode
{
    private char c;
    private HashMap<Character, TrieNode> children;
    private boolean isEnd; //true if node denotes end of any word

    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
    }

    public TrieNode(char c) {
        this();
        setChar(c);
        setEnd(false);
    }

    public void setChar(char c) {
        this.c=c;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd=isEnd;
    }

    public void setChildren( HashMap<Character, TrieNode> children) {
        this.children=children;
    }

    public char getChar() {
        return c;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }
}