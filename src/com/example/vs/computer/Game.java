package com.example.vs.computer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.deck.DeckVector;
import com.example.game.bot.PlayedCardsTwo;
import com.example.players.Players;
import com.example.septica_multiplayer_bluetooth.R;

public class Game extends Activity {
	
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
	
	static ImageButton myCard1;
	static ImageButton myCard2;
	static ImageButton myCard3;
	static ImageButton myCard4;
	static ImageButton myCardAux1;
	static ImageButton myCardAux2;
	static ImageButton myCardAux3;
	
	static ImageView playedCard1;
	static ImageView playedCard2;
	static ImageView playedCard3;
	static ImageView playedCard4;
	static ImageView playedCard5;
	static ImageView playedCard6;
	static ImageView playedCard7;
	static ImageView playedCard8;
	static ImageView playedCard9;
	static ImageView playedCard10;
	static ImageView playedCard11;
	static ImageView playedCard12;
	static ImageView playedCard13;
	static ImageView playedCard14;
	static ImageView playedCard15;
	static ImageView playedCard16;
	
	Context cnt = this;
	
	static int numberOfPlayers;
	static int playersTurn = 1;
	static int anyCardPlayed = 0;
	static int whoIsFirst = 0;
	static int cardsInHand = 4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		//Gesture detector for finishing the hand
		gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
		
		numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 0);
		
		//This variables will be used when the player has 4 or 2 cards remaining
		myCard1 = (ImageButton) findViewById(R.id.mycard1);
		myCard2 = (ImageButton) findViewById(R.id.mycard2);
		myCard3 = (ImageButton) findViewById(R.id.mycard3);
		myCard4 = (ImageButton) findViewById(R.id.mycard4);
		
		//This variables will be used when the player has 3 or 1 card(s) remaining
		myCardAux1 = (ImageButton) findViewById(R.id.mycard1prim);
		myCardAux2 = (ImageButton) findViewById(R.id.mycard2prim);
		myCardAux3 = (ImageButton) findViewById(R.id.mycard3prim);
		
		//Played cards. We will choose which of them will be used according to the number of players
		playedCard1 = (ImageView) findViewById(R.id.playedcard1);
		playedCard2 = (ImageView) findViewById(R.id.playedcard2);
		playedCard3 = (ImageView) findViewById(R.id.playedcard3);
		playedCard4 = (ImageView) findViewById(R.id.playedcard4);
		playedCard5 = (ImageView) findViewById(R.id.playedcard5);
		playedCard6 = (ImageView) findViewById(R.id.playedcard6);
		playedCard7 = (ImageView) findViewById(R.id.playedcard7);
		playedCard8 = (ImageView) findViewById(R.id.playedcard8);
		playedCard9 = (ImageView) findViewById(R.id.playedcard9);
		playedCard10 = (ImageView) findViewById(R.id.playedcard10);
		playedCard11 = (ImageView) findViewById(R.id.playedcard11);
		playedCard12 = (ImageView) findViewById(R.id.playedcard12);
		playedCard13 = (ImageView) findViewById(R.id.playedcard13);
		playedCard14 = (ImageView) findViewById(R.id.playedcard14);
		playedCard15 = (ImageView) findViewById(R.id.playedcard15);
		playedCard16 = (ImageView) findViewById(R.id.playedcard16);
		
		switch (numberOfPlayers) {
		case 2:
			//Shuffle the cards :D
			DeckVector.init();
			DeckVector.shuffle();
			
			for (int i=0;i<4;i++){
				Players.addToPlayer1(DeckVector.pop());
				Players.addToPlayer2(DeckVector.pop());
			}
			
			myCard1.setBackgroundResource(nameToId(Players.player1[0]));
			myCard2.setBackgroundResource(nameToId(Players.player1[1]));
			myCard3.setBackgroundResource(nameToId(Players.player1[2]));
			myCard4.setBackgroundResource(nameToId(Players.player1[3]));
			myCard1.setVisibility(0);
			myCard2.setVisibility(0);
			myCard3.setVisibility(0);
			myCard4.setVisibility(0);
			
			findViewById(R.id.oppositecard1).setVisibility(0);
			findViewById(R.id.oppositecard2).setVisibility(0);
			findViewById(R.id.oppositecard3).setVisibility(0);
			findViewById(R.id.oppositecard4).setVisibility(0);
			
			
			//This is a test. Cover your ears! X_x
			myCard1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					myCard1.setVisibility(ImageButton.INVISIBLE);
					playedCard5.setBackgroundResource(nameToId(Players.player1[0]));
					playedCard5.setVisibility(ImageView.VISIBLE);
					
					TranslateAnimation ta = new TranslateAnimation( 0, -dpToPx(30), 0, 0);
					ta.setDuration(1000);
					ta.setFillAfter(true);
					myCard2.startAnimation(ta);
					myCard3.startAnimation(ta);
					myCard4.startAnimation(ta);
				}
			});
			break;
			
		case 3:
			//Shuffle the cards :D
			DeckVector.init();
			DeckVector.size = 30;
			DeckVector.shuffle();
			
			for (int i=0;i<4;i++){
				Players.addToPlayer1(DeckVector.pop());
				Players.addToPlayer2(DeckVector.pop());
				Players.addToPlayer3(DeckVector.pop());
			}
			
			myCard1.setBackgroundResource(nameToId(Players.player1[0]));
			myCard2.setBackgroundResource(nameToId(Players.player1[1]));
			myCard3.setBackgroundResource(nameToId(Players.player1[2]));
			myCard4.setBackgroundResource(nameToId(Players.player1[3]));
			myCard1.setVisibility(0);
			myCard2.setVisibility(0);
			myCard3.setVisibility(0);
			myCard4.setVisibility(0);
			
			findViewById(R.id.leftcard1).setVisibility(0);
			findViewById(R.id.leftcard2).setVisibility(0);
			findViewById(R.id.leftcard3).setVisibility(0);
			findViewById(R.id.leftcard4).setVisibility(0);
			
			findViewById(R.id.rightcard1).setVisibility(0);
			findViewById(R.id.rightcard2).setVisibility(0);
			findViewById(R.id.rightcard3).setVisibility(0);
			findViewById(R.id.rightcard4).setVisibility(0);
			break;
			
		case 4:
			//Shuffle the cards :D
			DeckVector.init();
			DeckVector.shuffle();
			
			for (int i=0;i<4;i++){
				Players.addToPlayer1(DeckVector.pop());
				Players.addToPlayer2(DeckVector.pop());
				Players.addToPlayer3(DeckVector.pop());
				Players.addToPlayer4(DeckVector.pop());
			}
			
			myCard1.setBackgroundResource(nameToId(Players.player1[0]));
			myCard2.setBackgroundResource(nameToId(Players.player1[1]));
			myCard3.setBackgroundResource(nameToId(Players.player1[2]));
			myCard4.setBackgroundResource(nameToId(Players.player1[3]));
			myCard1.setVisibility(0);
			myCard2.setVisibility(0);
			myCard3.setVisibility(0);
			myCard4.setVisibility(0);
		    
			findViewById(R.id.oppositecard1).setVisibility(0);
			findViewById(R.id.oppositecard2).setVisibility(0);
			findViewById(R.id.oppositecard3).setVisibility(0);
			findViewById(R.id.oppositecard4).setVisibility(0);
			
			findViewById(R.id.leftcard1).setVisibility(0);
			findViewById(R.id.leftcard2).setVisibility(0);
			findViewById(R.id.leftcard3).setVisibility(0);
			findViewById(R.id.leftcard4).setVisibility(0);
			
			findViewById(R.id.rightcard1).setVisibility(0);
			findViewById(R.id.rightcard2).setVisibility(0);
			findViewById(R.id.rightcard3).setVisibility(0);
			findViewById(R.id.rightcard4).setVisibility(0);
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		myCard1.setVisibility(1);
		myCard2.setVisibility(1);
		myCard3.setVisibility(1);
		myCard4.setVisibility(1);
		
		findViewById(R.id.oppositecard1).setVisibility(1);
		findViewById(R.id.oppositecard2).setVisibility(1);
		findViewById(R.id.oppositecard3).setVisibility(1);
		findViewById(R.id.oppositecard4).setVisibility(1);
		
		findViewById(R.id.leftcard1).setVisibility(1);
		findViewById(R.id.leftcard2).setVisibility(1);
		findViewById(R.id.leftcard3).setVisibility(1);
		findViewById(R.id.leftcard4).setVisibility(1);
		
		findViewById(R.id.rightcard1).setVisibility(1);
		findViewById(R.id.rightcard2).setVisibility(1);
		findViewById(R.id.rightcard3).setVisibility(1);
		findViewById(R.id.rightcard4).setVisibility(1);
		
		Players.size1 = 0;
		Players.size2 = 0;
		Players.size3 = 0;
		Players.size4 = 0;
		
	}
	
	public int nameToId(String name){
		return getResources().getIdentifier(name, "drawable", getPackageName());
	}
	
	public int dpToPx(int dp){
		return (int) ((dp * getApplicationContext().getResources().getDisplayMetrics().density) + 0.5);
	}
	
	 class MyGestureDetector extends SimpleOnGestureListener {
	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            try {
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                // right to left swipe
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && 
	                		Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                	//finish the hand
	                	if ( Game.playersTurn == 1 && Game.anyCardPlayed == 1){
	                		switch (Game.numberOfPlayers){
	                		case 2:
	                			Game.whoIsFirst = PlayedCardsTwo.checkWinner();
	                			PlayedCardsTwo.reset();
	                			Game.playedCard5.setVisibility(1);
	                			Game.playedCard6.setVisibility(1);
	                			Game.playedCard7.setVisibility(1);
	                			Game.playedCard8.setVisibility(1);
	                			Game.playedCard9.setVisibility(1);
	                			Game.playedCard10.setVisibility(1);
	                			Game.playedCard11.setVisibility(1);
	                			Game.playedCard12.setVisibility(1);
	                			//pop a card from the deck and put in the players hand
	                			break;
	                			
	                		case 3:
	                			
	                			break;
	                			
	                		case 4:
	                			
	                			break;
	                			
	                		default:
	                			break;
	                		}
	                	}
	                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && 
	                		Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                    //finish the hand
	                }
	            } catch (Exception e) {
	                // nothing
	            }
	            return false;
	        }

	    }
}
