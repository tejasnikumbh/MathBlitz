package com.musebrain.mathfun;

import java.util.Random;
import java.util.Timer;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class PlayActivity extends Activity {

	private static final int ADD = 0;
	private static final int SUBTRACT = 1;
	private static final int MIXED = 2;
	private static final int DIVIDE = 3;
	
	private String losingCause = "";
	private String mAnswer = "";
	private String mEquation = "";
	private int mExpectedAnswer = 3;
	private int mUserAnswer = 0;
	private Random rand;
	private int range = 10;
	private int mOperator = 0;
	private String[] operatorsStr = {" + "," - "," +- "," / "};
	private int firstNum = 1;
	private int secondNum = 2;
	private int mUserScore = 0;
	private int scoreAdd = 5;
	private int scoreSub = 3;
	private double leap = 0.04;
	private int wrongAns = 0;
	private int chancesLeft = 3;
	private double timeScore = 5;
	private boolean endGameFlag = false;
	private int[] pencil = {5000,25000,50000,100000,200000,500000,1000000};
	//View references
	private TextView time;
    private TextView score;
    private TextView equation;
    private TextView answer;
    
    private ImageView resultImg;
    private boolean isMixedSub;
    //private Button resultText;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Loading Persistent state
        super.onCreate(savedInstanceState);
        // Setting the content view
        setContentView(R.layout.gameplay);
        
        Bundle extras = getIntent().getExtras();
        mOperator = (Integer) extras.get("Operator");
        
        // Retrieving all input sources
        Button button0 = (Button) findViewById(R.id.button_0);
        Button button1 = (Button) findViewById(R.id.button_1);
        Button button2 = (Button) findViewById(R.id.button_2);
        Button button3 = (Button) findViewById(R.id.button_3);
        Button button4 = (Button) findViewById(R.id.button_4);
        Button button5 = (Button) findViewById(R.id.button_5);
        Button button6 = (Button) findViewById(R.id.button_6);
        Button button7 = (Button) findViewById(R.id.button_7);
        Button button8 = (Button) findViewById(R.id.button_8);
        Button button9 = (Button) findViewById(R.id.button_9);
        
        Button cancel = (Button) findViewById(R.id.cancel);
        Button enter = (Button) findViewById(R.id.enter);
        
        // Retrieving all output sources
        time = (TextView) findViewById(R.id.text_view);
        score = (TextView) findViewById(R.id.score_view);
        equation = (TextView) findViewById(R.id.equation);
        answer = (TextView) findViewById(R.id.answer);
        
        resultImg = (ImageView) findViewById(R.id.result_image);
        //resultText = (Button) findViewById(R.id.result_button);
        
        // Generating new equation and answer
		generateNewEquation();
		// Updating the equation and answer display
		updateNewEquationViews();
		  
        // Starting the timer
        final CountDownTimer timer = new CountDownTimer(7000, 1000) {    	
            public void onTick(long millisUntilFinished) {
                time.setText("Time Left : " + millisUntilFinished / 1000 + " sec");
                timeScore -= 0.5;
            }
            public void onFinish() {
            	if(!endGameFlag){
            		losingCause = "Sorry, you ran out of time.";
            		endGame();
            	}
            }
         }.start();
         
        // Listener for Cancel Button
        cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mAnswer = "";
				answer.setText(" ? ");
			}
		});
        
        // Listener for Enter Button
        enter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Checking the answer
				if(!answer.getText().equals(" ? ")){
					mUserAnswer = Integer.parseInt(String.valueOf(answer.getText()));
					if(mExpectedAnswer == mUserAnswer){
						resultImg.setBackgroundColor(getResources().getColor(R.color.greenIWant));
						//resultText.setBackgroundColor(getResources().getColor(R.color.greenIWant));
						resultImg.setImageResource(R.drawable.tick);
						//resultText.setText("Right!");
						mUserScore += scoreAdd;
						timeScore += leap*timeScore;
						scoreAdd += (int) (Math.floor(leap*mUserScore)) + timeScore;
						timeScore = 5 + range*leap*10;
						timer.start();
						
					}else{
						resultImg.setBackgroundColor(getResources().getColor(R.color.redIWant));
						//resultText.setBackgroundColor(getResources().getColor(R.color.redIWant));
						resultImg.setImageResource(R.drawable.wrong);
						//resultText.setText("Wrong!");
						if(mUserScore>=0){
							mUserScore -= scoreSub;
							if(mUserScore<0)
								mUserScore = 0;
						}
						wrongAns += 1;
						if(wrongAns == 3){
							losingCause = "Sorry, you miscalculated three problems.";
							endGame();
						}
						
						
						chancesLeft -= 1;
						if(chancesLeft>0){
							Toast.makeText(getApplicationContext(), String.valueOf(chancesLeft) + " chances left!",
								   Toast.LENGTH_SHORT).show();
						}else{
							
						}
						
						scoreSub += (int) Math.floor(leap*2*mUserScore);
						timeScore = 5 + range*leap*10;
						timer.start();
					}
					
					// Generating new equation and answer
					generateNewEquation();
					// Updating the equation and answer display
					updateNewEquationViews();
					// Updating the range
					updateRange();
					
					
				}else{
					Toast.makeText(PlayActivity.this, "Enter an answer!",
							   Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
        
        
        // Listeners for Numpad
        button0.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "0";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button1.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "1";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button2.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "2";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button3.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "3";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button4.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "4";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button5.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "5";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button6.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "6";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button7.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "7";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button8.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)
					mAnswer += "8";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        button9.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(mAnswer.length()<=6)	
					mAnswer += "9";
				if(mAnswer.length()>=5)
					answer.setTextSize(24);
				answer.setText(mAnswer);
			}
		});
        
        
    }
    
    private void generateNewEquation(){

		// Updating for new equation
		// Randomly generating new equation
		// Random Number Generation
		rand = new Random();
		firstNum = rand.nextInt(range);
		secondNum = rand.nextInt(range);
		int tempFirstNum = firstNum,tempSecondNum = secondNum;
		isMixedSub = false;
		switch(mOperator){
			case ADD:
				break;
			case SUBTRACT:
				tempFirstNum = Math.max(firstNum, secondNum);
				tempSecondNum = Math.min(firstNum, secondNum);
				break;
			case MIXED:
				if(rand.nextInt(2)%2 == 0){
					tempFirstNum = Math.max(firstNum, secondNum);
					tempSecondNum = Math.min(firstNum, secondNum);
					isMixedSub = true;}
				break;
			case DIVIDE:
				break;
			default:
				break;
		}
		
		firstNum = tempFirstNum;
		secondNum = tempSecondNum;
		// Calculation of expected result
		int result = -1;
		switch(mOperator){
			case ADD:
				result = firstNum + secondNum;
				break;
			case SUBTRACT:
				result = firstNum - secondNum;
				break;
			case MIXED:
				if(isMixedSub)
					result = firstNum - secondNum;
				else
					result = firstNum + secondNum;
				break;
			case DIVIDE:
				result = firstNum/secondNum;
				break;
			default:
				break;
		}
		
		mExpectedAnswer = result;
		
		// Resetting user variables
		mUserAnswer = 0;
		mAnswer = "";
		
    }
    
    private void endGame(){
    	boolean newPencilUnlocked = false;
    	endGameFlag = true;
    	//getting preferences
    	SharedPreferences prefs = this.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
    	int curHighScore = prefs.getInt("CurHighScore", 0); //0 is the default value
    	//Setting high score in case the score is more than the high score

    	int pencilCur = getPencil(curHighScore);
    	int pencilNew = getPencil(mUserScore);
    	if(pencilCur != pencilNew) newPencilUnlocked = true;
    	if(mUserScore>curHighScore){
    		// New High Score, Achievement Unlocked.
    		Editor editor = prefs.edit();
    		editor.putInt("CurHighScore", mUserScore);
    		editor.commit();
    		if(mUserScore>5000 && newPencilUnlocked){
    			Intent congratsEndGameIntent = new Intent(PlayActivity.this,CongratsActivity.class);
    			congratsEndGameIntent.putExtra("highScore",mUserScore);
    			congratsEndGameIntent.putExtra("losingCause",losingCause);
    			PlayActivity.this.startActivity(congratsEndGameIntent);
    		}else{
    			// Old High Score Page displayed
            	Intent endGameIntent = new Intent(PlayActivity.this,HighScoreActivity.class);
            	endGameIntent.putExtra("Score",mUserScore);
            	endGameIntent.putExtra("losingCause",losingCause);
            	endGameIntent.putExtra("newHighScore",true);
            	PlayActivity.this.startActivity(endGameIntent);
    		}
    		
    	}else{
    
    		// Old High Score Page displayed
        	Intent endGameIntent = new Intent(PlayActivity.this,HighScoreActivity.class);
        	endGameIntent.putExtra("Score",mUserScore);
        	endGameIntent.putExtra("losingCause",losingCause);
        	endGameIntent.putExtra("newHighScore",false);
        	PlayActivity.this.startActivity(endGameIntent);
        
    	}
    }
    
    private void updateNewEquationViews(){
    	// Resetting the equation and answer display
		String operatorStr;
		if(mOperator == 2){
			if(isMixedSub == true)
				operatorStr = operatorsStr[1];
			else
				operatorStr = operatorsStr[0];
		}else{
			operatorStr = operatorsStr[mOperator];
		}
		String firstNumStr = String.valueOf(firstNum);
		String secondNumStr = String.valueOf(secondNum);
		mEquation =  firstNumStr + operatorStr + secondNumStr;
		if(firstNumStr.length() + secondNumStr.length() >=5 )
			equation.setTextSize(24);
		if(firstNumStr.length() + secondNumStr.length() >=8 )
			equation.setTextSize(20);
		if(firstNumStr.length() + secondNumStr.length() >=10 )
			equation.setTextSize(18);
		if(firstNumStr.length() + secondNumStr.length() >=12 )
			equation.setTextSize(16);
		if(firstNumStr.length() + secondNumStr.length() >=14 )
			equation.setTextSize(14);				
		equation.setText(mEquation);
		answer.setText(" ? ");
		answer.setTextSize(28);
		score.setText(String.valueOf(mUserScore));
    }
    
    private int getPencil(int score){
    	
    	if(score<pencil[0]){
        	return 1;
        }else if(score<pencil[1]){
        	return 2;
        }else if(score<pencil[2]){
        	return 3;
        }else if(score<pencil[3]){
        	return 4;
        }else if(score<pencil[4]){
        	return 5;
        }else if(score<pencil[5]){
        	return 6;
        }else{
        	return 7;
        }   
    }
    
    // Range updater. Different for different operations [Useful later]
    private void updateRange(){
    	range = (int) Math.floor(10 + mUserScore*leap);
    }
    
    @Override
    public void onBackPressed(){
    	losingCause = "You abruptly quit the game.";
    	endGame();   	
    }

}
