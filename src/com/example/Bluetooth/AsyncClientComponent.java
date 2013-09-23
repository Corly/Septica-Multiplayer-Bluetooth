package com.example.Bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

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

	public AsyncClientComponent(BluetoothDevice device, UILink UIUpdater)
	{
		BluetoothSocket tmp = null;
		mUpdater = UIUpdater;
		mDevice = device;
		try
		{
			tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
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
				continue;
			}
		}
		if (num_errors == 10)
		{
			this.publishProgress("!Connection error!");
			try
			{
				mDataSocket.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		mManager = new ConnectionManager(mDataSocket, mUpdater);
		mManager.execute();
		this.publishProgress("!Connection established!");
		Log.d("BLT", "Connection established to " + mDataSocket.getRemoteDevice().getName());

		return null;
	}

	public void onProgressUpdate(String... strings)
	{
		if (mUpdater != null)
			mUpdater.useData(strings);
	}

	public void stopEverything()
	{
		if (mManager != null)
		{
			mManager.stop();
			mManager = null;
		}
		this.cancel(true);
	}

	public void write(String data)
	{
		mManager.write(data);
	}
}
