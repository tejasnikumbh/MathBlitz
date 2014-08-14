package com.musebrain.mathfun;


import com.hcxg.ghuy198595.Prm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CongratsActivity extends Activity {

	private int[] pencilScore = {5000,25000,50000,100000,200000,500000,1000000};
	private int scoreVal = 0;
	private String msg = "";
	private Prm prm;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Loading Persistent state
        super.onCreate(savedInstanceState);
        // Setting the content view
        setContentView(R.layout.congrats);
        // Initializing prm and running
        if(prm==null)
        	prm=new Prm(this, null, false);
       
        prm.runSmartWallAd();
     
       // Getting reference to view to be updated
        ImageView pencilViewV = (ImageView) findViewById(R.id.congrats_img);
        TextView pencilMsg = (TextView) findViewById(R.id.congrats_msg);
        // Getiting information in intent passed in
        Bundle extras = getIntent().getExtras();
        scoreVal = (Integer) extras.get("highScore");
        String score = String.valueOf(scoreVal);
        String losingCause = extras.getString("losingCause");
        // Setting the HighScore
        // Getting Preferences
        
         String pencilMsg1 = "Score : " + score +" Congrats! You unlocked the ";
         String pencilMsg2 = " Pencil! Great! Try unlocking the next level. Take care not to run out of time the next time!";
         if(scoreVal<pencilScore[1]){
           	pencilViewV.setImageResource(R.drawable.pencil2);
          	pencilMsg.setText(pencilMsg1 + "GREEN" + pencilMsg2);
         }else if(scoreVal<pencilScore[2]){
           	pencilViewV.setImageResource(R.drawable.pencil3);
           	pencilMsg.setText(pencilMsg1 + "BLUE" + pencilMsg2);
         }else if(scoreVal<pencilScore[3]){
           	pencilViewV.setImageResource(R.drawable.pencil4);
           	pencilMsg.setText(pencilMsg1 + "RED" + pencilMsg2); 
         }else if(scoreVal<pencilScore[4]){
           	pencilViewV.setImageResource(R.drawable.pencil5);
           	pencilMsg.setText(pencilMsg1 + "BROWN" + pencilMsg2);
         }else if(scoreVal<pencilScore[5]){
           	pencilViewV.setImageResource(R.drawable.pencil6);
           	pencilMsg.setText(pencilMsg1 + "DARK GRAY" + pencilMsg2);
         }else{
           	pencilViewV.setImageResource(R.drawable.pencil7);
           	pencilMsg.setText(pencilMsg1 + "BLACK" + " Pencil!" + "Expert!");
         } 
         
         // For sound effect
         final MediaPlayer mp = MediaPlayer.create(this,R.raw.sfx);
         mp.start();
       
    
    }
    
    @Override
    public void onBackPressed() {
      	Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
	
	
} 