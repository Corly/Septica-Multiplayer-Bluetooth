package com.example.septica_multiplayer_bluetooth;

import com.example.vs.computer.Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button b_multiplayer = (Button) findViewById(R.id.b_multiplayer);
        Button b_start = (Button) findViewById(R.id.b_start);
        Button b_exit = (Button) findViewById(R.id.b_exit);
        
        b_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Game.class);
				startActivity(i);
			}
		});
        
        b_exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
