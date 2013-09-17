package com.example.septica_multiplayer_bluetooth;

import com.example.Bluetooth.AsyncClientComponent;
import com.example.Bluetooth.UILink;
import com.example.game.Game.GameSheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ClientGameActivity extends Activity
{
	private GameSheet mGameSheet;
	private int mDeviceIndex;
	private BluetoothDevice mDeviceToConnect;
	private AsyncClientComponent mClient;
	private AlertDialog mAlertDialog;
	
	private UILink mUpdater = new UILink()
	{
		@Override
		public void useData(String... args)
		{
			if (args[0].equals("!Connection established!"))
			{
				mAlertDialog.dismiss();
				mAlertDialog.setMessage("Connection established to " + mDeviceToConnect.getName() + "\r\n" + "Waiting for host..");
				mAlertDialog.show();
			}
			if (args[0].equals("!Connection error!"))
			{
				finish();
			}
		}
		
	};
	
	private void buildDialog()
	{
	    final ProgressBar sp = new ProgressBar(ClientGameActivity.this);
	    sp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    sp.setIndeterminate(true);
	    
	    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ClientGameActivity.this);
	    alertBuilder.setView(sp);
	    alertBuilder.setMessage("Connecting to " + mDeviceToConnect.getName() + "..");
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
		setContentView(R.layout.activity_client_game);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mGameSheet = (GameSheet)findViewById(R.id.GameSheet);
		Bundle extras = this.getIntent().getExtras();
		mDeviceIndex = extras.getInt("index");
		mDeviceToConnect = DevicesActivity.getDevice(mDeviceIndex);
		mClient = new AsyncClientComponent(mDeviceToConnect, mUpdater);
		mClient.execute();
		buildDialog();
	}
	
	public void onDestroy()
	{
		mClient.closeSockets();
		mClient.cancel(true);
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
