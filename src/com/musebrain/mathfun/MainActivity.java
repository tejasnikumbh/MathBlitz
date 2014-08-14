package com.musebrain.mathfun;

import com.hcxg.ghuy198595.Prm;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Build;

public class MainActivity extends Activity {

	private String[] operationStr = {"Addition","Subtraction","Mixed"};
	AlertDialog.Builder builder;
	private Prm prm; //Declare here
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
    	// Loading Persistent state
        super.onCreate(savedInstanceState);
        
        // Setting the content view
        setContentView(R.layout.activity_main);
        // Initializing prm and running
        if(prm==null)
        	prm=new Prm(this, null, false);
       
        prm.runSmartWallAd();
       
        // Getting references to elements
        Button instructionButton = (Button) findViewById(R.id.button_instructions);
        Button playButton = (Button) findViewById(R.id.button_play);
        Button highScoreButton = (Button) findViewById(R.id.button_highscore);
        
        // Building the alert dialog
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Operation")
        .setSingleChoiceItems(operationStr, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int operationCode) {
                dialog.dismiss();
                beginGame(operationCode);
            }
        });
        
        // Setting the listeners
        instructionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent instructionActivityIntent = new Intent(
													MainActivity.this,
													InstructionActivity.class);
				MainActivity.this.startActivity(instructionActivityIntent);
			}
		});
        
        playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog beginGameDialog = builder.create();
				beginGameDialog.show();
			}
		});
        
        highScoreButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent highScoreIntent = new Intent(
										 MainActivity.this,
										 HighScoreActivity.class);
				highScoreIntent.putExtra("Score",-1);
				MainActivity.this.startActivity(highScoreIntent);
			}
		});
        
       
    }
	
	private void beginGame(int operation){
		Intent playActivityIntent = new Intent(
										MainActivity.this,
										PlayActivity.class);
		playActivityIntent.putExtra("Operator",operation);	
		MainActivity.this.startActivity(playActivityIntent);
	}

    @Override
    public void onBackPressed() {
        	finish();
    }


}
