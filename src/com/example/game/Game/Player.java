package com.example.game.Game;

import java.util.ArrayList;

import android.graphics.Canvas;

public class Player
{
	private ArrayList<Card> mCards;
	private boolean mStarted;
	private boolean mIsTurn;
	private int mPlayerIndex;
	
	public Player(int playerindex)
	{
		mCards = new ArrayList<Card>();
		for (int i = 0;i<4;i++) 
		{
			mCards.add(DeckVector.pop());
		}
		mPlayerIndex = playerindex;
	}
	
	public void setupCards(int screenWidth , int screenHeight)
	{
		if (mPlayerIndex == 1)
		{
			float cardWidth = mCards.get(0).getWidth();
			float cardHeight = mCards.get(0).getHeight();
			float x = screenWidth / 2 - cardWidth - (cardWidth / 2);
			float y = screenHeight - cardHeight / 2 - 10;
			for (int i = 0;i<mCards.size();i++)
			{
				mCards.get(i).setCenterX(x + cardWidth * i);
				mCards.get(i).setCenterY(y);
			}
		}
		
		if (mPlayerIndex == 2)
		{
			float cardWidth = mCards.get(0).getWidth();
			float cardHeight = mCards.get(0).getHeight();
			float x = screenWidth / 2 - cardWidth - (cardWidth / 2);
			float y = cardHeight / 2 + 10;
			for (int i = 0;i<mCards.size();i++)
			{
				mCards.get(i).setCenterX(x + cardWidth * i);
				mCards.get(i).setCenterY(y);
			}
		}
	}
	
	public void drawCards(Canvas canvas)
	{
		for (int i = 0;i<mCards.size();i++)
			mCards.get(i).draw(canvas);
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
}
