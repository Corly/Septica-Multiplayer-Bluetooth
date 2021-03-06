package com.example.septica_multiplayer_bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
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

import com.example.Bluetooth.AsyncClientComponent;
import com.example.Bluetooth.UILink;
import com.example.game.Game.DeckVector;
import com.example.game.Game.GameSheet;

public class ClientGameActivity extends Activity
{
	private GameSheet mGameSheet;
	private int mDeviceIndex;
	private BluetoothDevice mDeviceToConnect;
	private AsyncClientComponent mClient;
	private AlertDialog mAlertDialog;
	private TextView mAlertDialogText;
	private Context mContext;

	private UILink mUpdater = new UILink()
	{
		@Override
		public void useData(String... args)
		{
			if (args[0].equals("!Connection established!"))
			{
				mAlertDialogText.setText("Connection established to " + mDeviceToConnect.getName() + "\r\n" + "Waiting for host..");
			}
			if (args[0].contains("Start"))
			{
				if (mAlertDialog.isShowing())
				{
					try
					{
						mAlertDialog.dismiss();
					}
					catch(Exception er)
					{
						
					}
				}
				String[] arguments = args[0].substring(0, args[0].length() - 1).split(" ");
				String[] cardArray = arguments[2].split(",");
				Log.d("BLT", cardArray.length + "");
				DeckVector.initFromNames(cardArray, mContext);
				mGameSheet.startGame(Integer.parseInt(arguments[1]), this);
			}
			if (args[0].equals("!Connection error!"))
			{
				finish();
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
			mClient.write(args[0]);
		}

	};

	private void buildDialog()
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ClientGameActivity.this);
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
		mAlertDialogText.setText("Connecting to " + mDeviceToConnect.getName() + "..");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mGameSheet = new GameSheet(this);
		setContentView(mGameSheet);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mContext = this;
		Bundle extras = this.getIntent().getExtras();
		mDeviceIndex = extras.getInt("index");
		mDeviceToConnect = DevicesActivity.getDevice(mDeviceIndex);
		mClient = new AsyncClientComponent(mDeviceToConnect, mUpdater);
		mClient.execute();
		buildDialog();
	}

	public void onDestroy()
	{
		mClient.stopEverything();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_game, menu);
		return true;
	}

}
