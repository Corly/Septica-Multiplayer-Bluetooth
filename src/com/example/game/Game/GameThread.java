package com.example.game.Game;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
	/* Holder and Context */
	private SurfaceHolder mHolder;
	private Context mContext;

	/* Running and pausing boolean */
	private boolean mRunning;
	private boolean mPaused;

	/* Width and height of the game */
	private int mHeight, mWidth;

	/* The two players */
	private Player player1;
	private Player player2;

	/* The table where the cards are */
	private Table table;

	// Bot Engine Thread
	private BotPlay botPlay;

	public GameThread(Context context, SurfaceHolder holder)
	{
		mContext = context;
		mHolder = holder;

		// initialise botPlay
		botPlay = new BotPlay();

		// Shuffle the cards
		DeckVector.init(context);
		DeckVector.shuffle();

		// instantiate players
		player1 = new Player(1);
		player2 = new Player(2);

		// player1's turn
		player1.setTurn(true);

		// player2 not turn
		player2.setTurn(false);

		// table dimension
		table = new Table(2);
		Log.d("Septica", "Cards are now created!");
	}

	public void setWH(int width, int height)
	{
		mHeight = height;
		mWidth = width;
		player1.setupCards(mWidth, mHeight);
		player2.setupCards(mWidth, mHeight);
		table.setWidthHeight(mWidth, mHeight);
	}

	public void run()
	{
		mRunning = true;
		mPaused = false;

		// Start botPlay
		botPlay.start();

		while (mRunning)
		{
			try
			{
				if (!mPaused)
				{
					Canvas canvas = mHolder.lockCanvas();
					synchronized (mHolder)
					{
						canvas.drawColor(Color.BLACK);
						player1.drawCards(canvas);
						player2.drawCards(canvas);
						table.drawCards(canvas);
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

		// join the botPlay thread at the end of the game
		try
		{
			botPlay.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
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

	public boolean handleTouch(MotionEvent event)
	{

		if (player1.isTurn())
		{
			int result = player1.checkTouch(event);
			if (result != -1)
			{
				table.addToTable(player1.getCard(result));
				player1.setTurn(false);
				player2.setTurn(true);
			}
		}
		return true;
	}

	private int pseudoRandom()
	{
		return (int) (Math.random() * (player2.getCards().size()));
	}

	private class BotPlay extends Thread
	{

		@Override
		public void run()
		{
			while (mRunning)
			{

				try
				{
					Thread.sleep(20);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				if (player2.isTurn())
				{
					// change player 2 turn
					player2.setTurn(false);
					
					// choose a random card from player's 2 hand
					int whatCard = pseudoRandom();

					table.addToTable(player2.getCard(whatCard));

					player1.setTurn(true);
				}
			}
		}
	}

}
