package com.example.Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectionManager extends AsyncTask<Void, String, Void>
{
	private BluetoothSocket mSocket;
	private DataInputStream mInput;
	private DataOutputStream mOutput;
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
		mInput = new DataInputStream(tmpInput);
		mOutput = new DataOutputStream(tmpOutput);
	}

	public Void doInBackground(Void... params)
	{

		while (!isCancelled())
		{
			Log.d("BLT", "Looking for data..");
			try
			{
				String readString = mInput.readUTF();
				if (readString != null)
					this.publishProgress(readString);
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

	protected synchronized void onProgressUpdate(String... strings)
	{
		if (mLink != null)
			mLink.useData(strings);
	}

	public synchronized void write(String data)
	{
		try
		{
			mOutput.writeUTF(data);
			mOutput.flush();
		}
		catch (Exception er)
		{
			
		}
	}

	public synchronized void stop()
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
