package com.example.Bluetooth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.ArrayAdapter;

public class BluetoothManager
{
	private final BluetoothAdapter mBluetoothAdapter;
	private final Context mContext;
	private final ArrayAdapter<String> mList;
	private final ArrayList<BluetoothDevice> mFoundDevices;
	private final HashMap<String, Integer> mHash;
	private boolean mDiscover;
	private final IntentFilter mFilter1 , mFilter2;

	public BluetoothManager(Context context, ArrayAdapter<String> list)
	{
		this.mContext = context;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mFoundDevices = new ArrayList<BluetoothDevice>();
		mHash = new HashMap<String, Integer>();
		this.mList = list;
		mFilter1  = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mFilter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	}

	public boolean checkDevices()
	{
		if (mBluetoothAdapter == null)
		{
			// check if the device has Bluetooth
			return false;
		}
		if (!mBluetoothAdapter.isEnabled())
		{
			// check if the bluetooth is enabled
			Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			mContext.startActivity(enableBluetooth);
		}
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		if (pairedDevices.size() > 0)
		{
			for (BluetoothDevice device : pairedDevices)
			{
				mFoundDevices.add(device);
				mList.add("Name: " + device.getName() + "\n" + "Adress: " + device.getAddress() + "\nPaired and not in range");
				mHash.put(device.getAddress(), mHash.size());
			}
			mList.notifyDataSetChanged();
		}
		mBluetoothAdapter.startDiscovery();
		mDiscover = true;
		mContext.registerReceiver(mFoundReceiver, mFilter1);
		mContext.registerReceiver(mFoundReceiver, mFilter2);
		return true;
	}

	private final BroadcastReceiver mFoundReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (!mHash.containsKey(device.getAddress()))
				{
					mFoundDevices.add(device);
					mList.add("Name: " + device.getName() + "\n" + "Adress: " + device.getAddress() + "\nNot paired and in range");
					mList.notifyDataSetChanged();
					mHash.put(device.getAddress(), mHash.size());
					Log.d("BLT", "Name: " + device.getName() + "\n" + "Adress: " + device.getAddress());
				}
				else
				{
					int index = mHash.get(device.getAddress());
					Log.d("BLT" , "Already in the list  " + index);
					String value = mList.getItem(index);
					mList.remove(value);
					value = ("Name: " + device.getName() + "\n" + "Adress: " + device.getAddress() + "\nPaired and in range");
					mList.insert(value, index);
					mList.notifyDataSetChanged();
					
				}
			}
			if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
			{
				/* If the device finished the scanning it will start to rescan :) */
				if (mDiscover) mBluetoothAdapter.startDiscovery();
				Log.d("BLT", "Rescanning...");
			}
		}

	};

	public void stopDiscovery()
	{
		try
		{
			mBluetoothAdapter.cancelDiscovery();
			mDiscover = false;
			mContext.unregisterReceiver(mFoundReceiver);
		}
		catch(Exception er)
		{
			
		}
	}

	public void destroy()
	{
		try
		{
			mBluetoothAdapter.cancelDiscovery();
			mDiscover = false;
			mFoundDevices.clear();
			mList.clear();
			mHash.clear();
			mContext.unregisterReceiver(mFoundReceiver);
		}
		catch(Exception er)
		{
			
		}
		
	}

	public BluetoothDevice getDevice(int index)
	{
		return mFoundDevices.get(index);
	}

}
