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

		// player1's turn
		whichPlayerWasFirst = 0;

		// player2 not turn

		// table dimension
		mTable = new Table(2);

		Log.d("Septica", "Cards are now created!");
	}

	public void setUILink(UILink linker)
	{
		mUpdater = linker;
	}

	public void setWH(int width, int height)
	{
		mHeight = height;
		mWidth = width;
		for (int i = 0; i < 2; i++)
		{
			mPlayers[i].setupCards(mWidth, mHeight);
		}
		mTable.setWidthHeight(mWidth, mHeight);
	}

	public void pauseGame(boolean paused)
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

		if (mPlayers[mMyIndex].isTurn())
		{
			int result = mPlayers[mMyIndex].checkTouch(event);
			if (result != -1)
			{
				if (mTable.addToTable(mPlayers[mMyIndex].getCard(result)))
				{
					mPlayers[mMyIndex].removeCard(result);
					mPlayers[mMyIndex].setTurn(false);
					mPlayers[(mMyIndex + 1) % 2].setTurn(true); // 2 is the number of players
					mUpdater.reportAction("!Updatecards " + mMyIndex + " " + result + "!");

				}
				else
				{
					Toast.makeText(mContext, "Illegal move", 1).show();
				}
			}
		}
		return true;
	}

	public void updateCards(int playerIndex, int cardIndex)
	{
		if (mTable.addToTable(mPlayers[playerIndex].getCard(cardIndex)))
		{
			mPlayers[playerIndex].removeCard(cardIndex);
			mPlayers[(playerIndex + 1) % 2].setTurn(true); // 2 is the number of players
			mPlayers[playerIndex].setTurn(false);
		}
	}
	
	public void sendFinishHand()
	{
		mPlayers[0].setTurn(false);
		mPlayers[1].setTurn(false);
		HandWinner winner = mTable.checkHandWinner(whichPlayerWasFirst);
		whichPlayerWasFirst = winner.getHandWinner();

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

		// set the player that won to start the hand
		// just for 2 players
		if (winner.getHandWinner() == 0)
		{
			mPlayers[0].setNumberOfPoints(mPlayers[0].getNumberOfPoints() + winner.getNumberOfPointsWon());
		}
		else
		{
			mPlayers[1].setNumberOfPoints(mPlayers[1].getNumberOfPoints() + winner.getNumberOfPointsWon());
		}

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
			if (winner.getHandWinner() == 0)
			{
				mPlayers[0].setTurn(true);
			}
			else
			{
				mPlayers[1].setTurn(true);
			}
		}
	}

	public void finishHand(boolean player1WantsToSwipe)
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
				mPlayers[0].setTurn(false);
				mPlayers[1].setTurn(false);
				HandWinner winner = mTable.checkHandWinner(whichPlayerWasFirst);
				whichPlayerWasFirst = winner.getHandWinner();
				Log.d("Septica", "Who won? " + whichPlayerWasFirst);

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

				// set the player that won to start the hand
				// just for 2 players
				if (winner.getHandWinner() == 0)
				{
					mPlayers[0].setNumberOfPoints(mPlayers[0].getNumberOfPoints() + winner.getNumberOfPointsWon());
				}
				else
				{
					mPlayers[1].setNumberOfPoints(mPlayers[1].getNumberOfPoints() + winner.getNumberOfPointsWon());
				}

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
					if (winner.getHandWinner() == 0)
					{
						mPlayers[0].setTurn(true);
					}
					else
					{
						mPlayers[1].setTurn(true);
					}
				}
				mUpdater.reportAction("!FinishHand!");
			}
		}
	}

	// just for 2 players
	private void dealCards(int whichPlayerHasWon)
	{
		if (whichPlayerHasWon == 0)
		{
			while (!DeckVector.isEmpty() && mPlayers[1].getCards().size() <= 3)
			{
				mPlayers[0].addCard(DeckVector.pop());
				mPlayers[1].addCard(DeckVector.pop());
			}
		}
		else
		{
			while (!DeckVector.isEmpty() && mPlayers[0].getCards().size() <= 3)
			{
				mPlayers[1].addCard(DeckVector.pop());
				mPlayers[0].addCard(DeckVector.pop());
			}
		}
	}

}
