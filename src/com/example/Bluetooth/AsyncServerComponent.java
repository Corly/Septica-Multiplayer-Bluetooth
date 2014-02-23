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
	private BluetoothServerSocket mServerSocket;
	private final BluetoothAdapter mBltAdapter;
	private final Context mContext;
	private final UILink mUpdater;
	private ConnectionManager mManager;

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
			Intent discoverable = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
					300);
			mContext.startActivity(discoverable);
		}
		try
		{
			tmp = mBltAdapter.listenUsingRfcommWithServiceRecord("BLT",
					UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

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
		
		while (!isCancelled())
		{
			try
			{
				socket = mServerSocket.accept();
			} catch (IOException e)
			{
				e.printStackTrace();
				break;
			}
			if (socket != null)
			{
				try
				{
					mServerSocket.close();
					mManager = new ConnectionManager(socket, mUpdater);
					mManager.execute();
					((ServerGameActivity) mContext).runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Button button = (Button) ((Activity) mContext).findViewById(R.id.button_server_start);
							button.setEnabled(true);
						}
					});
					//this.publishProgress("!Start!");
					break;
				} catch (Exception e)
				{
					break;
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
		Log.d("BLT","AsyncServerComponent Task stopped succesfully!");
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
		} catch (Exception er)
		{}
		
		try
		{
			mServerSocket.close();
		} catch (Exception er)
		{}
		
		this.cancel(true);
	}

	public synchronized void write(String data)
	{
		if (mManager != null)
			mManager.write(data);
	}
	
	public synchronized void startGame() {
		this.publishProgress("!Start!");
	}
}
