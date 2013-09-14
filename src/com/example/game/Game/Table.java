package com.example.game.Game;

import android.graphics.Canvas;


public class Table
{
	private Card[] mCards;
	private int mCount;
	private int mGameWidth , mGameHeight;
	
	public Table(int numberofplayers)
	{
		mCards = new Card[4 * numberofplayers];
		mCount = 0;
	}
	
	public void clear()
	{
		mCount = 0;
	}
	
	public void setWidthHeight(int width , int height)
	{
		mGameWidth = width;
		mGameHeight = height;
	}
	
	public boolean addToTable(Card card)
	{
		if (mCount == 0)
		{
			mCards[mCount] = card;
			card.setCenterX(mGameWidth / 2);
			card.setCenterY(mGameHeight / 2);
			mCount++;
			return true;
		}
		else
		{
			//if it is a legal move then add the card to the table
			if (legalMove(card)){
				mCards[mCount] = card;
				card.setCenterX(mCards[mCount - 1].getCenterX() + card.getWidth() / 4);
				card.setCenterY(mGameHeight / 2);
				mCount++;
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	private boolean legalMove(Card card){
		//note that 2 is hard-coded -- it represents the number of cards that are played in one circle
		//ex for 2 is 2, for 3 is 3, for 4 is for
		if ( mCount != 0 && mCount % 2 == 0 &&
				!mCards[0].getNumber().contentEquals(card.getNumber()) && 
					!card.getNumber().contentEquals("7")) {
			return false;
		}
		
		return true;
	}
	
	public void drawCards(Canvas canvas)
	{
		for (int i = 0;i<mCount;i++)
			mCards[i].draw(canvas);
	}
	
	
	//returns an object that was 2 fields: handWinner and numberOfPoints
	public HandWinner checkHandWinner(int whichPlayerWasFirst){
		String firstCardName = mCards[0].getNumber();
		//maybe this variable will be global at some point.
		//for now is local
		int numberOfPlayers = 2;
		int whatCardWon = 0;
		int numberOfPoints = 0;
		
		for (int i=mCount - 1; i>=0; i--){
			if ( mCards[i].getNumber().contentEquals(firstCardName) ||
					mCards[i].getNumber().contentEquals("7") ){
				whatCardWon = i;
				break;
			}
		}
		
		for (int i=0; i<mCount; i++){
			if (mCards[i].getNumber().contentEquals("a") ||
					mCards[i].getNumber().contentEquals("10")){
				numberOfPoints++;
			}
		}
		
		return new HandWinner((whichPlayerWasFirst + whatCardWon % numberOfPlayers) % numberOfPlayers ,
				numberOfPoints);
	}
	
}
