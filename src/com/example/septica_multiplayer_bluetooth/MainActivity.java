package com.example.septica_multiplayer_bluetooth;

import com.example.vs.computer.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	final Context cnt = this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button b_multiplayer = (Button) findViewById(R.id.b_multiplayer);
		Button b_start = (Button) findViewById(R.id.b_start);
		Button b_exit = (Button) findViewById(R.id.b_exit);

		b_start.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(cnt);

				dialog.setMessage("Choose number of players:");
				dialog.setTitle("Number of players");
				dialog.setNegativeButton("2 players", new Dialog.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						Intent i = new Intent(cnt, Game.class);
						i.putExtra("numberOfPlayers", 2);
						dialog.dismiss();
						startActivity(i);
					}
				});

				dialog.setNeutralButton("3 players", new Dialog.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						Intent i = new Intent(cnt, Game.class);
						i.putExtra("numberOfPlayers", 3);
						dialog.dismiss();
						startActivity(i);
					}
				});

				dialog.setPositiveButton("4 players", new Dialog.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						Intent i = new Intent(cnt, Game.class);
						i.putExtra("numberOfPlayers", 4);
						dialog.dismiss();
						startActivity(i);
					}
				});

				dialog.show();
			}
		});

		b_exit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
