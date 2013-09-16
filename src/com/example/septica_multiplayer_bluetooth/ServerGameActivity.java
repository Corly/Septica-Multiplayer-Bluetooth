package com.example.septica_multiplayer_bluetooth;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.Bluetooth.AsyncServerComponent;
import com.example.Bluetooth.UILink;
import com.example.game.Game.GameSheet;

public class ServerGameActivity extends Activity
{
	private GameSheet gameSheet;
	private AsyncServerComponent mServer;
	private AlertDialog mAlertDialog;
	private UILink mUILink = new UILink()
	{

		@Override
		public void useData(String... args)
		{
			if (args[0].equals("!Start!"))
			{
				mAlertDialog.dismiss();
				gameSheet.pauseGame(false);
			}
		}
		
	};
	
	private void buildDialog()
	{
	    final ProgressBar sp = new ProgressBar(ServerGameActivity.this);
	    sp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    sp.setIndeterminate(true);
	    
	    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ServerGameActivity.this);
	    alertBuilder.setView(sp);
	    alertBuilder.setMessage("Waiting for players..");
	    alertBuilder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
	    mAlertDialog = alertBuilder.create();
	    mAlertDialog.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		gameSheet = (GameSheet)findViewById(R.id.GameSheet);
		//this.setContentView(gameSheet);
		mServer = new AsyncServerComponent(this, mUILink);
		buildDialog();
		mServer.execute();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	@Override
	public void onDestroy()
	{
		mServer.closeSockets();
		mServer.cancel(true);
		super.onDestroy();
	}

}
