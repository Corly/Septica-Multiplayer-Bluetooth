package com.example.game.Game;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class Player
{
	private ArrayList<Card> mCards;
	private boolean mStarted;
	private boolean mIsTurn;
	private int mPlayerIndex;
	private int mNumberOfPoints;
	private int mScreenWidth, mScreenHeight;

	public Player(int playerindex)
	{
		mCards = new ArrayList<Card>();
		for (int i = 0; i < 4; i++)
		{
			mCards.add(DeckVector.pop());
		}
		mPlayerIndex = playerindex;
	}

	public void setupCards(int screenWidth, int screenHeight)
	{
		mScreenWidth = screenWidth;
		mScreenHeight = screenHeight;
		if (mPlayerIndex == 0)
		{
			float cardWidth = mCards.get(0).getWidth();
			float cardHeight = mCards.get(0).getHeight();
			float x = screenWidth / 2 - cardWidth - (cardWidth / 2);
			float y = screenHeight - cardHeight / 2 - 10;
			for (int i = 0; i < mCards.size(); i++)
			{
				mCards.get(i).setCenterX(x + cardWidth * i);
				mCards.get(i).setCenterY(y);
			}
		}

		if (mPlayerIndex == 1)
		{
			float cardWidth = mCards.get(0).getWidth();
			float cardHeight = mCards.get(0).getHeight();
			float x = screenWidth / 2 - cardWidth - (cardWidth / 2);
			float y = cardHeight / 2 + 10;
			for (int i = 0; i < mCards.size(); i++)
			{
				mCards.get(i).setCenterX(x + cardWidth * i);
				mCards.get(i).setCenterY(y);
			}
		}
	}

	public void drawCards(Canvas canvas , int myIndex)
	{
		if (mPlayerIndex == myIndex)
		{
			for (int i = 0; i < mCards.size(); i++)
				mCards.get(i).draw(canvas);
		}
		else
		{
			for (int i = 0; i < mCards.size(); i++)
			{
				Card card = mCards.get(i);
				canvas.drawBitmap(Images.getCardBackImage(), card.getCenterX() - card.getWidth() / 2 , card.getCenterY() - card.getHeight() / 2, null);
			}
		}
	}

	public boolean hasStarted()
	{
		return mStarted;
	}

	public void setStarted(boolean mStarted)
	{
		this.mStarted = mStarted;
	}

	public boolean isTurn()
	{
		return mIsTurn;
	}

	public void setTurn(boolean mIsTurn)
	{
		this.mIsTurn = mIsTurn;
	}

	public ArrayList<Card> getCards()
	{
		return mCards;
	}

	public int checkTouch(MotionEvent event)
	{
		for (int i = 0; i < mCards.size(); i++)
		{
			if (mCards.get(i).isTouched(event))
			{
				return i;
			}
		}
		return -1;
	}

	public Card getCard(int index)
	{
		if (index < 4 && index >= 0)
		{
			Card card = mCards.get(index);
			return card;
		}
		else return null;
	}

	public void addCard(Card card)
	{
		mCards.add(card);
		setupCards(mScreenWidth, mScreenHeight);
	}

	public void removeCard(int index)
	{
		if (index < 4 && index >= 0)
		{
			mCards.remove(index);
		}
	}

	public int getNumberOfPoints()
	{
		return mNumberOfPoints;
	}

	public void setNumberOfPoints(int mNumberOfPoints)
	{
		this.mNumberOfPoints = mNumberOfPoints;
	}

	public int getPlayerIndex()
	{
		return mPlayerIndex;
	}

}
