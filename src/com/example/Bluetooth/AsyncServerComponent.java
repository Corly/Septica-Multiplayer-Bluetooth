package com.example.Bluetooth;

import java.io.IOException;
import java.util.UUID;

import com.example.septica_multiplayer_bluetooth.R;
import com.example.septica_multiplayer_bluetooth.ServerGameActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

public class AsyncServerComponent extends AsyncTask<Void, String, Void>
{
	private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
	private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	private static final int MAX_CONNECTIONS = 1;

	private BluetoothServerSocket mServerSocket;
	private final BluetoothAdapter mBltAdapter;
	private final Context mContext;
	private final UILink mUpdater;
	private ConnectionManager mManager;
	private boolean mIsRunning;
	

	public AsyncServerComponent(Context context, UILink UIUpdater)
	{
		mContext = context;
		mUpdater = UIUpdater;
		BluetoothServerSocket tmp = null;
		mBltAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBltAdapter == null)
			return;

		if (mBltAdapter.isEnabled())
		{
			Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			mContext.startActivity(discoverable);
		}
		try
		{
			tmp = mBltAdapter.listenUsingRfcommWithServiceRecord("BLT", MY_UUID_SECURE);

		} catch (IOException er)
		{

		}

		mServerSocket = tmp;
	}

	@Override
	protected Void doInBackground(Void... arg0)
	{
		BluetoothSocket socket = null;

		if (mServerSocket == null)
			return null;
		mIsRunning = true;
		while (mIsRunning)
		{
			try
			{
				socket = mServerSocket.accept();
			} catch (IOException e)
			{
			}
			if (socket != null)
			{
				try	
				{
					mManager = new ConnectionManager(socket, mUpdater);
					mManager.execute();
					mManager.write("!Ping!");
					socket = null;
					((ServerGameActivity) mContext).runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							Button button = (Button) ((Activity) mContext).findViewById(R.id.button_server_start);
							button.setEnabled(true);
						}
					});
				} catch (Exception e)
				{
					
				}
			}
			try
			{
				Thread.sleep(20);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			mServerSocket.close();
		} catch (Exception er)
		{
		}

		Log.d("BLT", "AsyncServerComponent Task stopped succesfully!");

		return null;
	}

	protected synchronized void onProgressUpdate(String... strings)
	{
		if (mUpdater != null)
			mUpdater.useData(strings);
	}
	
	public synchronized void stopListeningForConnections()
	{
		mIsRunning = false;
		try
		{
			mServerSocket.close();
		} catch (Exception er)
		{
			Log.d("BLT","Error when closing the serverSocket");
		}
	}
	
	public synchronized void closeAllConnections()
	{
		try
		{
			mManager.closeConnection();
		} catch (Exception er)
		{
			Log.d("BLT","Error when closing the Connection Manager");
		}
	}

	public synchronized void write(String data)
	{
		if (mManager != null)
			mManager.write(data);
	}

	public synchronized void startGame()
	{
		this.publishProgress("!Start!");
	}
}
