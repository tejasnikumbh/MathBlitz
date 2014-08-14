package com.musebrain.mathfun;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class InstructionActivity extends Activity {
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Loading Persistent state
        super.onCreate(savedInstanceState);
        // Setting the content view
        setContentView(R.layout.instructions);
    }
    
}
