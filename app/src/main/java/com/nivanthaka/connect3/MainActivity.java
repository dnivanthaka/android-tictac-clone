package com.nivanthaka.connect3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int player = 0;
    private int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[][] winningStates = {{0, 1, 2}, {3, 4, 5}, {6 ,7 ,8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4 ,8}, {2, 4, 6}};
    boolean gameIsRunning = true;
    private TextView nowPlaying;
    private TextView wonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        nowPlaying = (TextView)findViewById(R.id.txt_nowplaying);
        nowPlaying.setText("Player 1");

        wonText = (TextView)findViewById(R.id.txt_won);
    }

    public void dropIn(View view){
        ImageView piece = (ImageView)view;
        int tag = Integer.parseInt(piece.getTag().toString());
        int thisPlayer = player;

        if(gameState[tag - 1] == 0 && gameIsRunning) {
            piece.setTranslationY(-1000f);
            if (player == 0) {
                piece.setImageResource(R.drawable.red);
                player = 1;
                gameState[tag - 1] = 1;
            } else {
                piece.setImageResource(R.drawable.yellow);
                player = 0;
                gameState[tag - 1] = 2;
            }
            piece.animate().translationYBy(1000f).setDuration(300);

            //Check for winning here
            for(int i=0;i<winningStates.length;i++) {
                if (gameState[winningStates[i][0]] == gameState[winningStates[i][1]] &&
                        gameState[winningStates[i][1]] == gameState[winningStates[i][2]] && gameState[winningStates[i][0]] != 0) {
                    Log.d("CONNECT 3", "Player " + thisPlayer + " Won!!!");

                    wonText.setText("Player " + (thisPlayer + 1) + " Had Won!!!");

                    LinearLayout wonMessage = (LinearLayout)findViewById(R.id.wonMessage);
                    wonMessage.setVisibility(LinearLayout.VISIBLE);

                    gameIsRunning = false;
                    break;
                }else{
                    //Log.d("CONNECT 3", "Checking for draws");
                    boolean isDraw = true;

                    for(int state : gameState){
                        if(state == 0) isDraw = false;
                    }

                    if(isDraw) {
                        wonText.setText("It's a draw!!!");

                        LinearLayout wonMessage = (LinearLayout) findViewById(R.id.wonMessage);
                        wonMessage.setVisibility(LinearLayout.VISIBLE);

                        gameIsRunning = false;
                        break;
                    }

                }
            }

            if(gameIsRunning) {
                nowPlaying.setText("Player " + (player + 1));
            }

        } else{
            piece.animate().rotationYBy(360).setDuration(300);
        }
    }

    public void playAgain(View view){
        player = 0;

        GridLayout layout = (GridLayout)findViewById(R.id.gridLayout);

        for(int i=0;i<gameState.length;i++){
            gameState[i] = 0;
            ((ImageView)layout.getChildAt(i)).setImageResource(0);
        }
        nowPlaying.setText("Player 1");

        LinearLayout wonMessage = (LinearLayout)findViewById(R.id.wonMessage);
        wonMessage.setVisibility(LinearLayout.INVISIBLE);

        gameIsRunning = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
