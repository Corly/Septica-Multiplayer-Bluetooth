package com.example.game.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameSheet extends SurfaceView implements SurfaceHolder.Callback , OnTouchListener
{
	private SurfaceHolder mHolder;
	private Context mContext;
	private int mWidth;
	private int mHeight;
	private GameThread mThread;
	private final GestureDetector mGestureDetector;

	public GameSheet(Context context)
	{
		super(context);
		mContext = context;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mThread = new GameThread(context, mHolder);
		mGestureDetector = new GestureDetector(mContext, new GestureListener());
		setOnTouchListener(this);
		Log.d("Septica", "GameSheet constructor 1");
	}

	public GameSheet(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mThread = new GameThread(context, mHolder);
		mGestureDetector = new GestureDetector(mContext, new GestureListener());
		setOnTouchListener(this);
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

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return mGestureDetector.onTouchEvent(event);
	}
	
	public void pauseGame(boolean paused)
	{
		mThread.pauseGame(paused);
	}

	private final class GestureListener extends SimpleOnGestureListener
	{

		private static final int SWIPE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onDown(MotionEvent e)
		{
			Log.d("Septica", "Down");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			boolean result = false;
			try
			{
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY))
				{
					if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
					{
						Log.d("Septica", "Finished and Swiped");
						mThread.finishHand(1, true);
					}
				}
				else
				{
					if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD)
					{
						if (diffY > 0)
						{
							// swipe bottom
						}
						else
						{
							// swipe top
						}
					}
				}
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
			return result;
		}
	}

}
