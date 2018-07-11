package com.example.shubham.wordgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WordManager {
    private final static int MIN_WORD_LENGTH = 4;
    private TrieUtil tu;

    public WordManager(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        tu = new TrieUtil();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                tu.add(line.trim());
        }
    }

    public boolean isWord(String word) {
        return tu.isWord(word);
    }

    public char getAnyWordStartingWith(String prefix) {
        return (tu.getAnyWordStartingWith(prefix));
    }
}
