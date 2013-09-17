package com.example.septica_multiplayer_bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.Bluetooth.AsyncServerComponent;
import com.example.Bluetooth.UILink;
import com.example.game.Game.GameSheet;

public class ServerGameActivity extends Activity
{
	private GameSheet mGameSheet;
	private AsyncServerComponent mServer;
	private AlertDialog mAlertDialog;
	private TextView mAlertDialogText;
	private UILink mUILink = new UILink()
	{

		@Override
		public void useData(String... args)
		{
			if (args[0].equals("!Start!"))
			{
				mAlertDialog.dismiss();
				mGameSheet.startGame();
				mServer.write("!Start!");
				Log.d("Septica" , "Game started!");
			}
		}
		
	};
	
	private void buildDialog()
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ServerGameActivity.this);
	    LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog, (ViewGroup) getCurrentFocus());
        
        alertBuilder.setView(dialoglayout);
	    alertBuilder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
	    mAlertDialog = alertBuilder.create();
	    mAlertDialog.show();
	    
	    mAlertDialogText = (TextView)mAlertDialog.findViewById(R.id.alertTextview);
	    mAlertDialogText.setText("Waiting for players ..");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mGameSheet = new GameSheet(this);
		setContentView(mGameSheet);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//gameSheet = (GameSheet)findViewById(R.id.serverGameSheet);
		
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
