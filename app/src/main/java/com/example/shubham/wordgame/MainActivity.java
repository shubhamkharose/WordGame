package com.example.shubham.wordgame;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private boolean userTurn = false;
    private Random random = new Random();
    private WordManager wm;
    private String sword;
    private boolean isOver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();

        try {
            wm = new WordManager(assetManager.open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn
     */
    public boolean onStart(View view) {
        isOver=false;
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.statusText);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }


    public void onChallenge(View view) {
        if(isOver)  return;
        TextView text = (TextView) findViewById(R.id.ghostText);
        sword = text.getText().toString();
        TextView label = (TextView) findViewById(R.id.statusText);
        char ss = wm.getAnyWordStartingWith(sword);
        if(sword.length()==0)   return;
        if (ss == 0 || ((sword.length() >= 4 && wm.isWord(sword)))) {
            String sres = "You won!!! ";
            if (ss == 0) {
                sres += "No more words possible by this prefix..";
            } else {
                sres += "Computer spelled meaningful word..";
            }
            label.setText(sres);
        } else
            label.setText("Computer Won!!! You challenged Wrong...");
        isOver=true;
    }

    private void computerTurn() {
        if(isOver)  return;
        TextView label = (TextView) findViewById(R.id.statusText);
        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * returns whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(isOver)  return super.onKeyUp(keyCode, event);
        final TextView text = (TextView) findViewById(R.id.ghostText);
        final TextView label = (TextView) findViewById(R.id.statusText);
        label.setText(COMPUTER_TURN);
        sword=text.getText().toString();
        char ch=(char) event.getUnicodeChar();
        if((ch>=65&&ch<=90)||(ch>=97&&ch<=122))
        {
            sword += ch;
            text.setText(sword);
            final char ss=wm.getAnyWordStartingWith(sword);
            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if  (ss == 0 ||(wm.isWord(sword)))
                    {
                        String sres="Computer won!!! ";
                        if(ss==0)
                        {
                            sres+="No more words possible by this prefix..";
                        }
                        else
                        {
                            sres+="You spelled meaningful word..";
                        }
                        label.setText(sres);
                        isOver=true;
                    }
                    else
                    {
                        sword+=ss;
                        text.setText(sword);
                        label.setText(USER_TURN);
                    }

                }
            },1000);
        }
        return super.onKeyUp(keyCode, event);
    }
}
