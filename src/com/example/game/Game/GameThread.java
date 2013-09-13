package com.example.game.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
	private SurfaceHolder mHolder;
	private Context mContext;
	private boolean mRunning;
	private boolean mPaused;
	private int mHeight , mWidth;
	private Player player1;
	private Player player2;

	public GameThread(Context context, SurfaceHolder holder)
	{
		mContext = context;
		mHolder = holder;
		DeckVector.init(context);
		DeckVector.shuffle();
		player1 = new Player(1);
		player2 = new Player(2);
		Log.d("Septica", "Cards are now created!");
	}
	
	public void setWH(int width , int height)
	{
		mHeight = height;
		mWidth = width;
		player1.setupCards(mWidth, mHeight);
		player2.setupCards(mWidth, mHeight);
	}

	public void run()
	{
		mRunning = true;
		mPaused = false;
		while (mRunning)
		{
			try
			{
				if (!mPaused)
				{
					Canvas canvas = mHolder.lockCanvas();
					synchronized (mHolder)
					{
						player1.drawCards(canvas);
						player2.drawCards(canvas);
						mHolder.unlockCanvasAndPost(canvas);
					}
				}
				Thread.sleep(20);
			}
			catch (Exception e)
			{
				break;
			}
		}
		Log.d("Septica", "Game has stopped! ");
	}

	public void stopRunning()
	{
		mRunning = false;
	}

	public void Pause()
	{
		mPaused = true;
	}

	public void Unpause()
	{
		mPaused = false;
	}

}
