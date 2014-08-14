package com.musebrain.mathfun;


import com.hcxg.ghuy198595.AdListener;  //Add import statements
import com.hcxg.ghuy198595.AdListener.AdType;
import com.hcxg.ghuy198595.Prm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class HighScoreActivity extends Activity {

	private int[] pencilScore = {5000,25000,50000,100000,200000,500000,1000000};
	private int scoreVal = 0;
	private Prm prm; //Declare here
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Loading Persistent state
        super.onCreate(savedInstanceState);
        // Setting the content view
        setContentView(R.layout.highscore);
        // Initializing prm and running
        if(prm==null)
        	prm=new Prm(this, null, false);
       
        prm.runSmartWallAd();
        
        // Getting reference to view to be updated
        TextView scoreView = (TextView) findViewById(R.id.gameScore);
        ImageView pencilView = (ImageView) findViewById(R.id.highscore_img);
        TextView pencilMsg = (TextView) findViewById(R.id.pencil_msg);
        TextView highScoreMsgView = (TextView) findViewById(R.id.highscore_msg_score);
        // Getiting information in intent passed in
        Bundle extras = getIntent().getExtras();
        scoreVal = (Integer) extras.get("Score");
        String score = String.valueOf(scoreVal);
        String losingCause = String.valueOf(extras.get("losingCause"));	
        // Updating views
        String msg;
        if(scoreVal != -1)
        	msg = losingCause + " Your score this time around was " + score;
        else
        	msg = "";
        if(extras.getBoolean("newHighScore"))
        	msg += " Congrats! New high score!";
        
        String pencilMsg1 = "Based on the high score and your performance in the game, you currently own a ";
        String pencilMsg2 = " pencil!";
        scoreView.setText(msg);
       
    
        // Setting the HighScore
        // Getting Preferences
    	SharedPreferences prefs = this.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
    	int curHighScore = prefs.getInt("CurHighScore", 0); //0 is the default value
    	String highScoreMsg = "HighScore : " + String.valueOf(curHighScore);
    	highScoreMsgView.setText(highScoreMsg);
    	 if(curHighScore<pencilScore[0]){
         	pencilView.setImageResource(R.drawable.pencil1_v);
         	pencilMsg.setText(pencilMsg1 + "YELLOW" + pencilMsg2);
         }else if(curHighScore<pencilScore[1]){
         	pencilView.setImageResource(R.drawable.pencil2_v);
         	pencilMsg.setText(pencilMsg1 + "GREEN" + pencilMsg2);
         }else if(curHighScore<pencilScore[2]){
         	pencilView.setImageResource(R.drawable.pencil3_v);
         	pencilMsg.setText(pencilMsg1 + "BLUE" + pencilMsg2);
         }else if(curHighScore<pencilScore[3]){
         	pencilView.setImageResource(R.drawable.pencil4_v);
         	pencilMsg.setText(pencilMsg1 + "RED" + pencilMsg2); 
         }else if(curHighScore<pencilScore[4]){
         	pencilView.setImageResource(R.drawable.pencil5_v);
         	pencilMsg.setText(pencilMsg1 + "BROWN" + pencilMsg2);
         }else if(curHighScore<pencilScore[5]){
         	pencilView.setImageResource(R.drawable.pencil6_v);
         	pencilMsg.setText(pencilMsg1 + "DARK GRAY" + pencilMsg2);
         }else{
        	TextView nextLevelMsg = (TextView) findViewById(R.id.try_next_level);
        	nextLevelMsg.setText("");
         	pencilView.setImageResource(R.drawable.pencil7_v);
         	pencilMsg.setText(pencilMsg1 + "BLACK" + pencilMsg2 + "Expert! Game Completed!");
         }   
    
    }
    
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
	
} 