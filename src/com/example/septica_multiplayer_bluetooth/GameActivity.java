package com.example.septica_multiplayer_bluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.game.Game.GameSheet;

public class GameActivity extends Activity
{
	GameSheet gameSheet;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		gameSheet = new GameSheet(this);
		setContentView(gameSheet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
