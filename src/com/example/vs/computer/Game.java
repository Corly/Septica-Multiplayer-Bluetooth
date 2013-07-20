package com.example.vs.computer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

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
	
    //Player's Cards
	static ImageButton myCard1;
	static ImageButton myCard2;
	static ImageButton myCard3;
	static ImageButton myCard4;
	
	//An array of ImageViews that holds the "playedCards" that are on the table
	static ImageView playedCards[] = new ImageView[17];
	
	//Context
	Context cnt = this;
	
	static int numberOfPlayers;
	static int playersTurn;
	static int anyCardPlayed = 0;
	static int whoIsFirst = 0;
	static int cardsInHand = 4;
	static int playedCardPosition;
	
	//X Coordinates for player's cards
	static float XmyCard1;
	static float XmyCard2;
	static float XmyCard3;
	static float XmyCard4;
	
	static int reper1;
	static int reper2;
	static int reper3;
	static int reper4;
	
	
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
		
		//It is player's turn
		playersTurn = 1;
		
		//This variables will be used for the cards.
		myCard1 = (ImageButton) findViewById(R.id.mycard1);
		myCard2 = (ImageButton) findViewById(R.id.mycard2);
		myCard3 = (ImageButton) findViewById(R.id.mycard3);
		myCard4 = (ImageButton) findViewById(R.id.mycard4);
		
		//Played cards. We will choose which of them will be used according to the number of players
		playedCards[1] = (ImageView) findViewById(R.id.playedcard1);
		playedCards[2] = (ImageView) findViewById(R.id.playedcard2);
		playedCards[3] = (ImageView) findViewById(R.id.playedcard3);
		playedCards[4] = (ImageView) findViewById(R.id.playedcard4);
		playedCards[5] = (ImageView) findViewById(R.id.playedcard5);
		playedCards[6] = (ImageView) findViewById(R.id.playedcard6);
		playedCards[7] = (ImageView) findViewById(R.id.playedcard7);
		playedCards[8] = (ImageView) findViewById(R.id.playedcard8);
		playedCards[9] = (ImageView) findViewById(R.id.playedcard9);
		playedCards[10] = (ImageView) findViewById(R.id.playedcard10);
		playedCards[11] = (ImageView) findViewById(R.id.playedcard11);
		playedCards[12] = (ImageView) findViewById(R.id.playedcard12);
		playedCards[13] = (ImageView) findViewById(R.id.playedcard13);
		playedCards[14] = (ImageView) findViewById(R.id.playedcard14);
		playedCards[15] = (ImageView) findViewById(R.id.playedcard15);
		playedCards[16] = (ImageView) findViewById(R.id.playedcard16);
		
		//Repers coresponding to position
		reper1 = 1;
		reper2 = 3;
		reper3 = 5;
		reper4 = 7;
		
		switch (numberOfPlayers) {
		case 2:
			//Shuffle the cards :D
			DeckVector.init();
			DeckVector.shuffle();
			
			for (int i=0;i<4;i++){
				Players.addToPlayer1(DeckVector.pop());
				Players.addToPlayer2(DeckVector.pop());
			}
			
			playedCardPosition = 5;
			
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
			
			//X coordinates of the cards
			XmyCard1 = myCard1.getX();
			XmyCard2 = myCard2.getX();
			XmyCard3 = myCard3.getX();
			XmyCard4 = myCard4.getX();
			
			myCard1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if ( playersTurn == 1 ){
						myCard1.clearAnimation();
						myCard1.setVisibility(ImageButton.INVISIBLE);
						playersTurn = 0;
						cardsInHand--;

						playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[0]));
						playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
						playedCardPosition++;

						if (myCard2.getVisibility() == ImageButton.VISIBLE){
							TranslateAnimation ta2 = new TranslateAnimation(XmyCard2, XmyCard2 - dpToPx(30), 0, 0);
							ta2.setDuration(1000);
							ta2.setFillEnabled(true);
							ta2.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onAnimationRepeat(Animation animation) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									XmyCard2 -= dpToPx(30);
									reper2--;
									final int id = stringToId("myreper" + "" + reper2);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard2.layout(left, top, right, bottom);
								}
							});

							myCard2.startAnimation(ta2);
						}

						if (myCard3.getVisibility() == ImageButton.VISIBLE){
							TranslateAnimation ta3 = new TranslateAnimation( XmyCard3, XmyCard3 - dpToPx(30), 0, 0);
							ta3.setDuration(1000);
							ta3.setFillEnabled(true);
							ta3.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onAnimationRepeat(Animation animation) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									XmyCard3 -= dpToPx(30);
									reper3--;
									final int id = stringToId("myreper" + "" + reper3);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard3.layout(left, top, right , bottom);
								}
							});

							myCard3.startAnimation(ta3);
						}

						if (myCard4.getVisibility() == ImageButton.VISIBLE){
							TranslateAnimation ta4 = new TranslateAnimation( XmyCard4, XmyCard4 - dpToPx(30), 0, 0);
							ta4.setDuration(1000);
							ta4.setFillEnabled(true);
							ta4.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// 	TODO Auto-generated method stub
								}

								@Override
								public void onAnimationRepeat(Animation animation) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									XmyCard4 -= dpToPx(30);
									reper4--;
									final int id = stringToId("myreper" + "" + reper4);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard4.layout(left, top, right , bottom);
								}
							});

							myCard4.startAnimation(ta4);
						}
					}
				}
			});
			
			myCard2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					myCard2.setVisibility(ImageButton.INVISIBLE);
			
					playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[1]));
					playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
					playedCardPosition++;
					
					
						
					if (myCard1.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta1 = new TranslateAnimation( XmyCard1, XmyCard1 + dpToPx(30), 0, 0);
						ta1.setDuration(1000);
						ta1.setFillEnabled(true);
						ta1.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard1 += dpToPx(30);
								reper1++;
								final int id = stringToId("myreper" + "" + reper1);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard1.layout(left, top, right , bottom);
							}
						});
						myCard1.startAnimation(ta1);
					}
					
					if (myCard3.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta3 = new TranslateAnimation( XmyCard3 , XmyCard3 -dpToPx(30), 0, 0);
						ta3.setDuration(1000);
						ta3.setFillEnabled(true);
						ta3.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard3 -= dpToPx(30);
								reper3--;
								final int id = stringToId("myreper" + "" + reper3);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard3.layout(left, top, right , bottom);	
							}
						});
						myCard3.startAnimation(ta3);
					}
					
					if (myCard4.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta4 = new TranslateAnimation( XmyCard4 , XmyCard4 - dpToPx(30), 0, 0);
						ta4.setDuration(1000);
						ta4.setFillEnabled(true);
						ta4.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard4 -= dpToPx(30);
								reper4--;
								final int id = stringToId("myreper" + "" + reper4);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard4.layout(left, top, right , bottom);	
							}
						});
						myCard4.startAnimation(ta4);
					}

				}
			});
			
			myCard3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					myCard3.setVisibility(ImageButton.INVISIBLE);
					
					playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[2]));
					playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
					playedCardPosition++;
					
					if (myCard1.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta1 = new TranslateAnimation( XmyCard1, XmyCard1 + dpToPx(30), 0, 0);
						ta1.setDuration(1000);
						ta1.setFillEnabled(true);
						ta1.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard1 += dpToPx(30);
								reper1++;
								final int id = stringToId("myreper" + "" + reper1);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard1.layout(left, top, right , bottom);
							}
						});
						myCard1.startAnimation(ta1);
					}
					
					if (myCard2.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta2 = new TranslateAnimation( XmyCard2, XmyCard2 + dpToPx(30), 0, 0);
						ta2.setDuration(1000);
						ta2.setFillEnabled(true);
						ta2.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard2 += dpToPx(30);
								reper2++;
								Log.d("TAG", "" + reper2);
								final int id = stringToId("myreper" + "" + reper2);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard2.layout(left, top, right , bottom);
							}
						});
						myCard2.startAnimation(ta2);
					}
					
					if (myCard4.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta4 = new TranslateAnimation( XmyCard4, XmyCard4 - dpToPx(30), 0, 0);
						ta4.setDuration(1000);
						ta4.setFillEnabled(true);
						ta4.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard4 -= dpToPx(30);
								reper4--;
								final int id = stringToId("myreper" + "" + reper4);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard4.layout(left, top, right , bottom);	
							}
						});
						myCard4.startAnimation(ta4);
					}
				}
			});
			
			myCard4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					myCard4.setVisibility(ImageButton.INVISIBLE);
					
					playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[3]));
					playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
					playedCardPosition++;
					
					if (myCard3.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta3 = new TranslateAnimation( XmyCard3, XmyCard3 + dpToPx(30), 0, 0);
						ta3.setDuration(1000);
						ta3.setFillEnabled(true);
						ta3.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard3 += dpToPx(30);
								reper3++;
								final int id = stringToId("myreper" + "" + reper3);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard3.layout(left, top, right , bottom);	
							}
						});
						myCard3.startAnimation(ta3);
					}
					
					if (myCard2.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta2 = new TranslateAnimation( XmyCard2, XmyCard2 + dpToPx(30), 0, 0);
						ta2.setDuration(1000);
						ta2.setFillEnabled(true);
						ta2.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard2 += dpToPx(30);
								reper2++;
								final int id = stringToId("myreper" + "" + reper2);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard2.layout(left, top, right , bottom);
							}
						});
						myCard2.startAnimation(ta2);
					}
					
					if (myCard1.getVisibility() == ImageButton.VISIBLE){
						TranslateAnimation ta1 = new TranslateAnimation( XmyCard1, XmyCard1 + dpToPx(30), 0, 0);
						ta1.setDuration(1000);
						ta1.setFillEnabled(true);
						ta1.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								XmyCard1 += dpToPx(30);
								reper1++;
								final int id = stringToId("myreper" + "" + reper1);
								final int left = findViewById(id).getLeft();
								final int right = findViewById(id).getRight();
								final int top = findViewById(id).getTop();
								final int bottom = findViewById(id).getBottom();
								myCard1.layout(left, top, right , bottom);
							}
						});
						myCard1.startAnimation(ta1);
					}
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
	
	public int stringToId(String str){
		return getResources().getIdentifier(str , "id", getPackageName());
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
	                			//use the vector now
	                			/*Game.playedCard5.setVisibility(1);
	                			Game.playedCard6.setVisibility(1);
	                			Game.playedCard7.setVisibility(1);
	                			Game.playedCard8.setVisibility(1);
	                			Game.playedCard9.setVisibility(1);
	                			Game.playedCard10.setVisibility(1);
	                			Game.playedCard11.setVisibility(1);
	                			Game.playedCard12.setVisibility(1);*/
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
