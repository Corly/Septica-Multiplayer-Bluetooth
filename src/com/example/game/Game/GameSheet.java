package com.example.game.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSheet extends SurfaceView implements SurfaceHolder.Callback
{
	private SurfaceHolder mHolder;
	private Context mContext;
	private int mWidth;
	private int mHeight;
	private GameThread mThread;

	public GameSheet(Context context)
	{
		super(context);
		mContext = context;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mThread = new GameThread(context, mHolder);
		Log.d("Septica", "GameSheet constructor 1");
	}

	public GameSheet(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mThread = new GameThread(context, mHolder);
		Log.d("Septica", "GameSheet constructor 2");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		mWidth = width;
		mHeight = height;
		mThread.setWH(width, height);
		Log.d("Septica", "Surface was changed!");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		mThread.start();
		Log.d("Septica", "Surface was created!");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		mThread.stopRunning();
		mThread = null;
		Log.d("Septica", "Surface was destroyed!");
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		return mThread.handleTouch(event);
	}

}
