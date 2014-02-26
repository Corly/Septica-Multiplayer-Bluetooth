package com.example.septica_multiplayer_bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity
{

	private Context mContext;
	private BluetoothAdapter mBltAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mContext = this;
		mBltAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBltAdapter == null)
		{
			return;
		}

		if (!mBltAdapter.isEnabled())
		{
			// check if the bluetooth is enabled
			Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			mContext.startActivity(enableBluetooth);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void HostClick(View view)
	{
		Intent intent = new Intent(MainActivity.this, ServerGameActivity.class);
		this.startActivity(intent);
	}

	public void ClientClick(View view)
	{
		Intent intent = new Intent(MainActivity.this, DevicesActivity.class);
		this.startActivity(intent);
	}

}
