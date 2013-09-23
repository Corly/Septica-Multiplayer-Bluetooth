package com.example.septica_multiplayer_bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Bluetooth.AsyncServerComponent;
import com.example.Bluetooth.UILink;
import com.example.game.Game.DeckVector;
import com.example.game.Game.GameSheet;

public class ServerGameActivity extends Activity
{
	private GameSheet mGameSheet;
	private AsyncServerComponent[] mServer = new AsyncServerComponent[1];
	private AlertDialog mAlertDialog;
	private TextView mAlertDialogText;
	private Context mContext;

	private UILink mUILink = new UILink()
	{

		@Override
		public void useData(String... args)
		{
			if (args[0].equals("!Start!"))
			{
				mAlertDialog.dismiss();
				DeckVector.init(mContext);
				DeckVector.shuffle();
				for (int i = 0; i < mServer.length; i++)
				{
					mServer[i].write("!Start " + (i + 1) + " " + DeckVector.getCardsInOrder() + "!");
				}
				mGameSheet.startGame(0, this);
				Log.d("Septica", "Game started!");
			}
			if (args[0].contains("Updatecards"))
			{
				String arguments = args[0].replace("!", "");
				String[] data = arguments.split(" ");
				mGameSheet.updateCards(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
			}
			if (args[0].equals("!FinishHand!"))
			{
				mGameSheet.sendFinishHand();
			}
		}

		@Override
		public void reportAction(String... args)
		{
			for (int i = 0; i < mServer.length; i++)
			{
				mServer[i].write(args[0]);
			}
		}

	};

	private void buildDialog()
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ServerGameActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.dialog, (ViewGroup) getCurrentFocus());

		alertBuilder.setView(dialoglayout);
		alertBuilder.setOnCancelListener(new OnCancelListener()
		{

			@Override
			public void onCancel(DialogInterface dialog)
			{
				finish();
			}
		});
		mAlertDialog = alertBuilder.create();
		mAlertDialog.show();

		mAlertDialogText = (TextView) mAlertDialog.findViewById(R.id.alertTextview);
		mAlertDialogText.setText("Waiting for players ..");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//onRestoreInstanceState(savedInstanceState);
		mGameSheet = new GameSheet(this);
		setContentView(mGameSheet);
		mContext = this;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		for (int i = 0; i < mServer.length; i++)
		{
			mServer[0] = new AsyncServerComponent(this, mUILink);
			mServer[0].execute();
		}
		buildDialog();

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
		for (int i = 0; i < mServer.length; i++)
		{
			mServer[0].closeSockets();
			mServer[0].cancel(true);
		}
		//onSaveInstanceState(null);
		super.onDestroy();
	}

}
