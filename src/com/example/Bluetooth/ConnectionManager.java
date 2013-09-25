package com.example.Bluetooth;

import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectionManager extends AsyncTask<Void, String, Void>
{
	private BluetoothSocket mSocket;
	private InputStream mInput;
	private OutputStream mOutput;
	private final UILink mLink;

	public ConnectionManager(BluetoothSocket socket, UILink updater)
	{
		mSocket = socket;
		mLink = updater;
		InputStream tmpInput = null;
		OutputStream tmpOutput = null;
		try
		{
			tmpInput = mSocket.getInputStream();
			tmpOutput = mSocket.getOutputStream();
		}
		catch (Exception er)
		{
			Log.d("BLT", "Couldn't obtain the streams from socket!");
		}
		mInput = tmpInput;
		mOutput = tmpOutput;
	}

	public Void doInBackground(Void... params)
	{

		while (!isCancelled())
		{
			Log.d("BLT", "Looking for data..");
			try
			{
				byte[] buf = new byte[1000];
				int bytes = mInput.read(buf);
				String string = new String(buf);
				int index = string.lastIndexOf("!");
				if (index == -1)
				{
					Thread.sleep(20);
					continue;
				}
				string = string.substring(0, index + 1);
				Log.d("BLT", string);
				this.publishProgress(string);
				Thread.sleep(20);
			}
			catch (Exception er)
			{
				Log.d("BLT", er.getMessage());
				break;
			}
		}
		return null;
	}

	protected void onProgressUpdate(String... strings)
	{
		if (mLink != null)
			mLink.useData(strings);
	}

	public void write(String data)
	{
		try
		{
			mOutput.write(data.getBytes());
			mOutput.flush();
		}
		catch (Exception er)
		{

		}
	}

	public void stop()
	{
		if (mInput != null)
		{
			try
			{
				mInput.close();
			}
			catch (Exception e)
			{
			}
			mInput = null;
		}

		if (mOutput != null)
		{
			try
			{
				mOutput.close();
			}
			catch (Exception e)
			{
			}
			mOutput = null;
		}

		if (mSocket != null)
		{
			try
			{
				mSocket.close();
			}
			catch (Exception e)
			{
			}
			mSocket = null;
		}
	}
}
