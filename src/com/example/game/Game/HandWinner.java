package com.example.game.Game;

public class HandWinner
{
	private int handWinner;
	private int numberOfPointsWon;

	public HandWinner(int handWinner, int numberOfPlayers)
	{
		this.handWinner = handWinner;
		this.numberOfPointsWon = numberOfPlayers;
	}

	public int getHandWinner()
	{
		return handWinner;
	}

	public int getNumberOfPointsWon()
	{
		return numberOfPointsWon;
	}

	public void setHandWinner(int handWinner)
	{
		this.handWinner = handWinner;
	}

	public void setNumberOfPointsWon(int numberOfPointsWon)
	{
		this.numberOfPointsWon = numberOfPointsWon;
	}

}
