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

	public GameThread(Context context, SurfaceHolder holder)
	{
		mContext = context;
		mHolder = holder;
		DeckVector.init(context);
		Log.d("Septica", "Cards are now created!");
	}

	public void run()
	{
		mRunning = true;
		mPaused = false;
		while (mRunning)
		{
			if (!mPaused)
			{
				Canvas canvas = mHolder.lockCanvas();
				synchronized (mHolder)
				{
					DeckVector.deck[10].draw(canvas);
					mHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
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
