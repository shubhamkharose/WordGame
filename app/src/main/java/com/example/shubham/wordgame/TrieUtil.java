package com.example.shubham.wordgame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class TrieUtil extends Trie {
    private static final int MAX_SCORE = 25;
    private static final int MIN_SCORE = -25;

    public TrieUtil() {
       super();
    }

    /**
     * Return best move character for computer
     */
    public char findBestMove(TrieNode t) {
        Set<Character> ss=t.getChildren().keySet();
        int value=Integer.MIN_VALUE;
        char ans='a';
        HashMap<Integer,ArrayList<Character>> hmap=new HashMap();
        ArrayList<Character> alist;
        for(char ch:ss)
        {
            TrieNode child=t.getChildren().get(ch);
            int temp=miniMax(child,1,false,Integer.MIN_VALUE,Integer.MAX_VALUE);
            if(temp>=value)
            {
                value=temp;
                ans=child.getChar();
                //Log.d("myTag"+cnt, ""+ans+" "+value);
                if(hmap.containsKey(value))
                {
                    alist=hmap.get(value);
                    alist.add(ans);
                }
                else
                {
                    alist=new ArrayList();
                    alist.add(ans);
                    hmap.put(value,alist);
                }
                cnt++;
            }
        }
        alist=hmap.get(value);
        Random random=new Random();
        int index=random.nextInt(alist.size());
        return alist.get(index);
    }


    /**
     *  Mini-Max Algorithm implementation
     */
    public int miniMax(TrieNode t,int depth,boolean isMaxPlayer,int alpha,int beta) {
        if(t.isEnd())
        {
            if(depth%2==0)  return MAX_SCORE-depth; //Computer Winning Condition
            return MIN_SCORE+depth;                 //Opponent Winning Condition
        }
        Set<Character> ss=t.getChildren().keySet();
        if(isMaxPlayer)
        {
            int value=Integer.MIN_VALUE;
            for(char ch:ss)
            {
                TrieNode child=t.getChildren().get(ch);
                int temp=miniMax(child,depth+1,false,alpha,beta);
                value=Math.max(value,temp);
                alpha=Math.max(alpha,value);
                if(beta<=alpha) break;
            }
            return value;
        }
        int value=Integer.MAX_VALUE;
        for(char ch:ss)
        {
            TrieNode child=t.getChildren().get(ch);
            int temp=miniMax(child,depth+1,true,alpha,beta);
            value=Math.min(value,temp);
            beta=Math.min(beta,value);
            if(beta<=alpha) break;
        }
        return value;
    }


    /**
     *  Returns next character if there is any word in the trie that starts with the given prefix otherwise 0
     */
    public char getAnyWordStartingWith(String prefix) {
        TrieNode t = searchNode(prefix);
        if(t == null)
            return 0;
        else
        {
            Set<Character> ss=t.getChildren().keySet();
            if(ss.isEmpty())    return 0;
            char ans=findBestMove(t);
            return ans;
        }
    }
}
