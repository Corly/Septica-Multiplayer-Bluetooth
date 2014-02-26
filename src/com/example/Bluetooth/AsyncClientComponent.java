package com.example.Bluetooth;

import java.io.IOException;
import java.util.UUID;

import com.example.septica_multiplayer_bluetooth.DevicesActivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncClientComponent extends AsyncTask<Void, String, Void>
{
	private BluetoothSocket mDataSocket;
	private final BluetoothDevice mDevice;
	private final UILink mUpdater;
	private ConnectionManager mManager;
	
	private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
	private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

	public AsyncClientComponent(BluetoothDevice device, UILink UIUpdater)
	{
		BluetoothSocket tmp = null;
		mUpdater = UIUpdater;
		mDevice = device;
		try
		{
			tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
		}
		catch (IOException er)
		{
		}
		mDataSocket = tmp;
	}

	protected Void doInBackground(Void... params)
	{
		int num_errors = 0;
		for (int i = 0; i < 10; i++)
		{
			try
			{
				mDataSocket.connect();
				break;
			}
			catch (Exception connectEr)
			{
				Log.d("BLT", connectEr.getMessage());
				this.publishProgress("Connection to " + mDataSocket.getRemoteDevice().getName() + " has failed!");
				num_errors++;
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;				
			}
		}
		
		
		if (num_errors == 10)
		{
			try
			{
				mDataSocket.close();
			}
			catch (Exception e)
			{}
			this.publishProgress("!Connection error!");
			
			Log.d("BLT","AsyncClientComponent failed to connect to the other side!");
		}
		else
		{
			mManager = new ConnectionManager(mDataSocket, mUpdater);
			mManager.execute();
			this.publishProgress("!Connection established!");
			Log.d("BLT", "Connection established to " + mDataSocket.getRemoteDevice().getName());
		}
		
		return null;
	}

	protected synchronized void onProgressUpdate(String... strings)
	{
		if (mUpdater != null)
			mUpdater.useData(strings);
	}

	public synchronized void stopEverything()
	{		
		try
		{
			mManager.stop();
			mManager = null;
		}
		catch (Exception err)
		{}
		
		try
		{
			mDataSocket.close();
		}
		catch(Exception err)
		{}
		
		Log.d("BLT","Released all bluetooth resources from AsyncClientComponent!");
	}

	public synchronized void write(String data)
	{
		if (mManager != null)
			mManager.write(data);
	}
}
