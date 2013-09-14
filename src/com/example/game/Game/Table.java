package com.example.game.Game;

import java.util.ArrayList;

import android.graphics.Canvas;


public class Table
{
	private Card[] mCards;
	private int mCount;
	private int mGameWidth , mGameHeight;
	private int mNumberOfPlayers;
	
	public Table(int numberofplayers)
	{
		mCards = new Card[4 * numberofplayers];
		mCount = 0;
		mNumberOfPlayers = numberofplayers;
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
	
	public int getNumberOfPlayers() {
		return mNumberOfPlayers;
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
		
		//clear the table
		this.clear();
		
		int winner = whichPlayerWasFirst + whatCardWon % mNumberOfPlayers;
		if ( winner % mNumberOfPlayers == 0){
			return new HandWinner(winner, numberOfPoints);
		}
		else {
			return new HandWinner(winner % mNumberOfPlayers, numberOfPoints);
		}
	}

	//return an arraylist with the winners
	public ArrayList<Integer> checkGameWinner(Player[] players){
		int maxPoints = 0;
		int nr = 0;
		
		for (int i=0; i<mNumberOfPlayers; i++){
			if ( players[i].getNumberOfPoints() > maxPoints ){
				maxPoints = players[i].getNumberOfPoints();
				nr = 1;
			}
			else if ( players[i].getNumberOfPoints() == maxPoints){
				nr++;
			}
		}
		
		ArrayList<Integer> winners = new ArrayList<Integer>(nr);
		
		for (int i=0; i< mNumberOfPlayers; i++){
			if (players[i].getNumberOfPoints() == maxPoints){
				winners.add(players[i].getPlayerIndex());
			}
		}
		
		return winners;
	}
}
