package com.example.septica_multiplayer_bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Bluetooth.BluetoothManager;

public class DevicesActivity extends Activity
{

	private ListView mList;
	private ArrayAdapter<String> mAdapter;
	private Context mContext;
	private static BluetoothManager mManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devices);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mContext = this;
		mList = (ListView) findViewById(R.id.devicesList);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(itemListener);
		mManager = new BluetoothManager(this, mAdapter);
		mManager.checkDevices();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.devices, menu);
		return true;
	}
	
	OnItemClickListener itemListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long ID)
		{
			Intent next = new Intent(DevicesActivity.this, ClientGameActivity.class);
			next.putExtra("index", position);
			mManager.stopDiscovery();
			mContext.startActivity(next);
		}

	};

	public static BluetoothDevice getDevice(int index)
	{
		return mManager.getDevice(index);
	}

	public void onDestroy()
	{
		mManager.stopDiscovery();
		mManager.destroy();
		mManager = null;
		super.onDestroy();
	}

}
