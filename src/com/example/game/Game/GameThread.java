package com.example.game.Game;

import com.example.Bluetooth.UILink;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

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
	private Player[] mPlayers;

	/* The table where the cards are */
	private Table mTable;
	private int mMyIndex;
	private UILink mUpdater;

	// Bot Engine Thread

	// Which player was first
	private int whichPlayerWasFirst;

	public GameThread(Context context, SurfaceHolder holder, int myPlayerIndex)
	{
		mContext = context;
		mHolder = holder;
		mMyIndex = myPlayerIndex;

		// instantiate players
		mPlayers = new Player[2];

		for (int i = 0; i < 2; i++)
		{
			mPlayers[i] = new Player(i);
			mPlayers[i].setTurn(false);
		}
		mPlayers[0].setTurn(true);

		// table dimension
		mTable = new Table(2);

		Log.d("Septica", "Cards are now created!");
	}

	public synchronized void setUILink(UILink linker)
	{
		mUpdater = linker;
	}

	public synchronized void setWH(int width, int height)
	{
		mHeight = height;
		mWidth = width;
		for (int i = 0; i < 2; i++)
		{
			mPlayers[i].setupCards(mWidth, mHeight);
		}
		mTable.setWidthHeight(mWidth, mHeight);
	}

	public synchronized void pauseGame(boolean paused)
	{
		mPaused = paused;
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
						canvas.drawBitmap(Images.getBackgroundImage(), 0, 0, null);
						for (int i = 0; i < 2; i++)
						{
							mPlayers[i].drawCards(canvas, mMyIndex);
						}
						mTable.drawCards(canvas);
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

	public synchronized void stopRunning()
	{
		mRunning = false;
	}

	public synchronized void Pause()
	{
		mPaused = true;
	}

	public synchronized void Unpause()
	{
		mPaused = false;
	}

	public synchronized boolean handleTouch(MotionEvent event)
	{

		if (mPlayers[mMyIndex].isTurn())
		{
			int result = mPlayers[mMyIndex].checkTouch(event);
			if (result != -1)
			{
				if (mTable.addToTable(mPlayers[mMyIndex].getCard(result)))
				{
					mPlayers[mMyIndex].removeCard(result);
					mPlayers[mMyIndex].setTurn(false);
					mPlayers[(mMyIndex + 1) % mPlayers.length].setTurn(true);
					mUpdater.reportAction("!Updatecards " + mMyIndex + " " + result + "!");
				}
				else
				{
					Toast.makeText(mContext, "Illegal move", Toast.LENGTH_SHORT).show();
				}
			}
		}
		return true;
	}

	public synchronized void updateCards(int playerIndex, int cardIndex)
	{
		if (mTable.addToTable(mPlayers[playerIndex].getCard(cardIndex)))
		{
			mPlayers[playerIndex].removeCard(cardIndex);
			mPlayers[(playerIndex + 1) % mPlayers.length].setTurn(true);
			mPlayers[playerIndex].setTurn(false);
		}
	}
	
	public synchronized void sendFinishHand()
	{
		mPlayers[0].setTurn(false);
		mPlayers[1].setTurn(false);
		HandWinner winner = mTable.checkHandWinner(whichPlayerWasFirst);
		whichPlayerWasFirst = winner.getHandWinner();
		Log.d("Septica", "Who won? " + whichPlayerWasFirst);
		int winnerIndex = winner.getHandWinner();
		// boolean for knowing if the game is finished
		boolean gameDone = false;

		// firstly we have to deal the cards if it is possible
		if (DeckVector.isEmpty() && mPlayers[0].getCards().size() == 0)
		{
			gameDone = true;
		}
		else
		{
			dealCards(winner.getHandWinner());
		}

		mPlayers[winnerIndex].setNumberOfPoints(mPlayers[winnerIndex].getNumberOfPoints() + winner.getNumberOfPointsWon());

		if (gameDone)
		{
			final int gameWinner = mTable.checkGameWinner(mPlayers).get(0);
			Log.d("Septica", gameWinner + " winner");
			((Activity) mContext).runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					Toast.makeText(mContext, "Player " + gameWinner + " has won!", Toast.LENGTH_SHORT).show();
				}
			});
		}
		else
		{
			mPlayers[winnerIndex].setTurn(true);
		}
	}

	public synchronized void finishHand(boolean player1WantsToSwipe)
	{
		Log.d("Septica", "Player 1 wants to swipe: " + player1WantsToSwipe);
		Log.d("Septica", "Player " + mMyIndex + " is turn? " + mPlayers[mMyIndex].isTurn());
		Log.d("Septica", "mMyIndex = " + mMyIndex + " and whichPlayerWasFirst = " + whichPlayerWasFirst);
		if (player1WantsToSwipe && (mPlayers[mMyIndex].isTurn() == false))
		{
			// player1 can not finish the hand
		}
		else
		{
			if (mMyIndex == whichPlayerWasFirst)
			{
				mUpdater.reportAction("!FinishHand!");
				mPlayers[0].setTurn(false);
				mPlayers[1].setTurn(false);
				HandWinner winner = mTable.checkHandWinner(whichPlayerWasFirst);
				whichPlayerWasFirst = winner.getHandWinner();
				Log.d("Septica", "Who won? " + whichPlayerWasFirst);
				int winnerIndex = winner.getHandWinner();
				// boolean for knowing if the game is finished
				boolean gameDone = false;

				// firstly we have to deal the cards if it is possible
				if (DeckVector.isEmpty() && mPlayers[0].getCards().size() == 0)
				{
					gameDone = true;
				}
				else
				{
					dealCards(winner.getHandWinner());
				}

				mPlayers[winnerIndex].setNumberOfPoints(mPlayers[winnerIndex].getNumberOfPoints() + winner.getNumberOfPointsWon());

				if (gameDone)
				{
					final int gameWinner = mTable.checkGameWinner(mPlayers).get(0);
					Log.d("Septica", gameWinner + " winner");
					((Activity) mContext).runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							Toast.makeText(mContext, "Player " + gameWinner + " has won!", Toast.LENGTH_SHORT).show();
						}
					});
				}
				else
				{
					mPlayers[winnerIndex].setTurn(true);
				}
			}
		}
	}

	// just for 2 players
	private void dealCards(int whichPlayerHasWon)
	{
		
		while(!DeckVector.isEmpty() && mPlayers[whichPlayerHasWon].getCards().size() <= 3)
		{
			int index = whichPlayerHasWon;
			for (int i = 0;i<mPlayers.length;i++,index++,index %= mPlayers.length)
			{
				mPlayers[index].addCard(DeckVector.pop());
			}
		}
		
		
	}

}
