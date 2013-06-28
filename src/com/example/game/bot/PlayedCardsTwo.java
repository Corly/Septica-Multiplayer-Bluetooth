package com.example.game.bot;

import android.content.Context;
import android.widget.Toast;

public class PlayedCardsTwo {
	public static String playedCards[] = new String[16];
	public static int size = 0;
	public static int pointsPlayer1 = 0;
	public static int pointsPlayer2 = 0;
	
	public static void addCard(String arg1){
		playedCards[size++] = arg1;
	}
	
	public static void reset(){
		size = 0;
	}
	
	public static void exitingReset(){
		size = 0;
		pointsPlayer1 = 0;
		pointsPlayer2 = 0;
	}
	
	public static int checkWinner(){
		int numberOfPoints = 0;
		String c = playedCards[0].substring(playedCards[0].length() - 1);
		int winner = 0;
		
		for (int i=1; i<size; i++){
			if ( playedCards[i].endsWith(c) || playedCards[i].endsWith("7")){
				winner = i % 2;
			}
		}
		
		for (int i=0; i<size; i++){
			if ( playedCards[i].endsWith("a") || playedCards[i].endsWith("0")){
				numberOfPoints++;
			}
		}
		
		if ( winner == 0){
			pointsPlayer1 += numberOfPoints;
		}
		else {
			pointsPlayer2 += numberOfPoints;
		}
		
		return winner;
	}
	
	public static void checkFinalWinner(Context context){
		if (pointsPlayer1 > pointsPlayer2){
			Toast.makeText(context, "Congratulations! You won!", Toast.LENGTH_LONG).show();
		}
		else if ( pointsPlayer1 < pointsPlayer2){
			Toast.makeText(context, "Sorry! Player2 won!", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(context, "It is a tie!", Toast.LENGTH_LONG).show();
		}
	}

}
