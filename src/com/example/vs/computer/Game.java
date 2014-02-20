package com.example.vs.computer;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.game.Game.DeckVector;
import com.example.game.bot.PlayedCardsTwo;
import com.example.players.Players;
import com.example.septica_multiplayer_bluetooth.R;

public class Game extends Activity
{

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	// Player's Cards
	static ImageButton myCard1;
	static ImageButton myCard2;
	static ImageButton myCard3;
	static ImageButton myCard4;

	// Opposite player's cards
	static ImageView oppositeCard1;
	static ImageView oppositeCard2;
	static ImageView oppositeCard3;
	static ImageView oppositeCard4;

	// An array of ImageViews that holds the "playedCards" that are on the table
	static ImageView playedCards[];

	// Context
	Context cnt = this;

	static int numberOfPlayers;
	static int playersTurn;
	static int anyCardPlayed = 0;
	static int whoIsFirst = 0;
	static int cardsInHand;
	static int playedCardPosition;

	// X Coordinates for player's cards - relative to their current position I
	// think
	static float XmyCard1;
	static float XmyCard2;
	static float XmyCard3;
	static float XmyCard4;

	// X Coordinates for real
	static float XcoordCard1;
	static float XcoordCard2;
	static float XcoordCard3;
	static float XcoordCard4;

	// ints to know where a card is placed reported to a reper
	static int reper1;
	static int reper2;
	static int reper3;
	static int reper4;

	// X Coordinates for opposite player's cards
	static float XopCard1;
	static float XopCard2;
	static float XopCard3;
	static float XopCard4;

	// ints to know where a card is placed reported to a reper - opposite
	static int opReper1;
	static int opReper2;
	static int opReper3;
	static int opReper4;

	// Boolean to see if one of the animation is finished or not
	private boolean finishAnimation;

	// Boolean to continue the bot Thread
	private boolean keepGoing;

	// Thread for the bot
	private BotPlay botPlay;

	private Handler handler;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		// Gesture detector for finishing the hand
		gestureDetector = new GestureDetector(this, new MyGestureDetector());
		gestureListener = new View.OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				return gestureDetector.onTouchEvent(event);
			}
		};

		handler = new Handler();

		// played cards array of imageviews
		playedCards = new ImageView[17];

		keepGoing = true;
		botPlay = new BotPlay();
		botPlay.start();

		// Button for done
		Button done = (Button) findViewById(R.id.button_done);
		done.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (Game.playersTurn == 1 && Game.anyCardPlayed == 1)
				{
					switch (Game.numberOfPlayers)
					{
					case 2:
						// if ( Players.checkSizes(2)){
						// Game.whoIsFirst = PlayedCardsTwo.checkWinner();
						PlayedCardsTwo.reset();
						dealCardsAfterOneHandWasFinished();
						// use the vector now
						/*
						 * Game.playedCard5.setVisibility(1);
						 * Game.playedCard6.setVisibility(1);
						 * Game.playedCard7.setVisibility(1);
						 * Game.playedCard8.setVisibility(1);
						 * Game.playedCard9.setVisibility(1);
						 * Game.playedCard10.setVisibility(1);
						 * Game.playedCard11.setVisibility(1);
						 * Game.playedCard12.setVisibility(1);
						 */
						// pop a card from the deck and put in the players hand
						// }
						break;

					case 3:

						break;

					case 4:

						break;

					default:
						break;
					}
				}
			}
		});

		numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 0);

		// Which player is up:
		playersTurn = 1;

		// Cards in hand
		cardsInHand = 4;

		// This variables will be used for the cards.
		myCard1 = (ImageButton) findViewById(R.id.mycard1);
		myCard2 = (ImageButton) findViewById(R.id.mycard2);
		myCard3 = (ImageButton) findViewById(R.id.mycard3);
		myCard4 = (ImageButton) findViewById(R.id.mycard4);

		// Played cards. We will choose which of them will be used according to
		// the number of players
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

		// Repers coresponding to position
		reper1 = 1;
		reper2 = 3;
		reper3 = 5;
		reper4 = 7;

		// Opposite repers coresponding to position
		opReper1 = 1;
		opReper2 = 3;
		opReper3 = 5;
		opReper4 = 7;

		switch (numberOfPlayers)
		{
		case 2:
			// Shuffle the cards :D
			//DeckVector.init();
			//DeckVector.shuffle();

			for (int i = 0; i < 4; i++)
			{
				//Players.addToPlayer1(DeckVector.pop());
				//Players.addToPlayer2(DeckVector.pop());
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

			// Opposite player's cards
			oppositeCard1 = (ImageView) findViewById(R.id.oppositecard1);
			oppositeCard2 = (ImageView) findViewById(R.id.oppositecard2);
			oppositeCard3 = (ImageView) findViewById(R.id.oppositecard3);
			oppositeCard4 = (ImageView) findViewById(R.id.oppositecard4);
			oppositeCard1.setVisibility(0);
			oppositeCard2.setVisibility(0);
			oppositeCard3.setVisibility(0);
			oppositeCard4.setVisibility(0);

			// X coordinates of the cards
			XmyCard1 = myCard1.getX();
			XmyCard2 = myCard2.getX();
			XmyCard3 = myCard3.getX();
			XmyCard4 = myCard4.getX();

			// X coordinates of the opposite cards
			XopCard1 = oppositeCard1.getX();
			XopCard2 = oppositeCard2.getX();
			XopCard3 = oppositeCard3.getX();
			XopCard4 = oppositeCard4.getX();

			// If one card is played then the other cards start moving according
			// to the card that was played
			myCard1.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO

					if (playersTurn == 1)
					{
						myCard1.clearAnimation();
						myCard1.setVisibility(ImageButton.INVISIBLE);
						playersTurn = 2;
						cardsInHand--;
						Players.size1--;
						anyCardPlayed = 1;

						PlayedCardsTwo.addCard(Players.player1[0]);
						playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[0]));
						playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
						playedCardPosition++;

						if (myCard2.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta2;
							ta2 = new TranslateAnimation(XmyCard2, XmyCard2 - dpToPx(30), 0, 0);
							ta2.setDuration(1000);
							ta2.setFillEnabled(true);
							ta2.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard2 -= dpToPx(30);
									reper2--;
									final int id = stringToId("myreper" + "" + reper2);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard2.layout(left, top, right, bottom);
									XcoordCard2 = myCard2.getX();
								}
							});

							myCard2.startAnimation(ta2);
						}

						if (myCard3.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta3 = new TranslateAnimation(XmyCard3, XmyCard3 - dpToPx(30), 0, 0);
							ta3.setDuration(1000);
							ta3.setFillEnabled(true);
							ta3.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard3 -= dpToPx(30);
									reper3--;
									final int id = stringToId("myreper" + "" + reper3);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard3.layout(left, top, right, bottom);
									XcoordCard3 = myCard3.getX();
								}
							});

							myCard3.startAnimation(ta3);
						}

						if (myCard4.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta4 = new TranslateAnimation(XmyCard4, XmyCard4 - dpToPx(30), 0, 0);
							ta4.setDuration(1000);
							ta4.setFillEnabled(true);
							ta4.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard4 -= dpToPx(30);
									reper4--;
									final int id = stringToId("myreper" + "" + reper4);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard4.layout(left, top, right, bottom);
									XcoordCard4 = myCard4.getX();
								}
							});

							myCard4.startAnimation(ta4);
						}
					}
				}
			});

			myCard2.setOnClickListener(new OnClickListener()
			{
				// TODO

				@Override
				public void onClick(View v)
				{
					if (playersTurn == 1)
					{
						myCard2.clearAnimation();
						myCard2.setVisibility(ImageButton.INVISIBLE);
						playersTurn = 2;

						PlayedCardsTwo.addCard(Players.player1[1]);
						playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[1]));
						playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
						playedCardPosition++;
						anyCardPlayed = 1;
						cardsInHand--;
						Players.size1--;

						if (myCard1.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta1 = new TranslateAnimation(XmyCard1, XmyCard1 + dpToPx(30), 0, 0);
							ta1.setDuration(1000);
							ta1.setFillEnabled(true);
							ta1.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
									Log.d("DEBUG", myCard1.getLeft() + "," + myCard1.getRight());
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard1 += dpToPx(30);
									reper1++;
									final int id = stringToId("myreper" + "" + reper1);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard1.layout(left, top, right, bottom);
									Log.d("DEBUG", myCard1.getLeft() + "," + myCard1.getRight());
									XcoordCard1 = myCard1.getX();
								}
							});
							myCard1.startAnimation(ta1);
						}

						if (myCard3.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta3 = new TranslateAnimation(XmyCard3, XmyCard3 - dpToPx(30), 0, 0);
							ta3.setDuration(1000);
							ta3.setFillEnabled(true);
							ta3.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard3 -= dpToPx(30);
									reper3--;
									final int id = stringToId("myreper" + "" + reper3);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard3.layout(left, top, right, bottom);
									XcoordCard3 = myCard3.getX();
								}
							});
							myCard3.startAnimation(ta3);
						}

						if (myCard4.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta4 = new TranslateAnimation(XmyCard4, XmyCard4 - dpToPx(30), 0, 0);
							ta4.setDuration(1000);
							ta4.setFillEnabled(true);
							ta4.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard4 -= dpToPx(30);
									reper4--;
									final int id = stringToId("myreper" + "" + reper4);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard4.layout(left, top, right, bottom);
									XcoordCard4 = myCard4.getX();
								}
							});
							myCard4.startAnimation(ta4);
						}
					}
				}
			});

			myCard3.setOnClickListener(new OnClickListener()
			{
				// TODO

				@Override
				public void onClick(View v)
				{
					if (playersTurn == 1)
					{
						myCard3.clearAnimation();
						myCard3.setVisibility(ImageButton.INVISIBLE);
						playersTurn = 2;

						PlayedCardsTwo.addCard(Players.player1[2]);
						playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[2]));
						playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
						playedCardPosition++;
						anyCardPlayed = 1;
						cardsInHand--;
						Players.size1--;

						if (myCard1.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta1 = new TranslateAnimation(XmyCard1, XmyCard1 + dpToPx(30), 0, 0);
							ta1.setDuration(1000);
							ta1.setFillEnabled(true);
							ta1.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
									Log.d("DEBUG", myCard1.getLeft() + "," + myCard1.getRight());
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard1 += dpToPx(30);
									reper1++;
									final int id = stringToId("myreper" + "" + reper1);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard1.layout(left, top, right, bottom);
									XcoordCard1 = myCard1.getX();
									Log.d("DEBUG", myCard1.getLeft() + "," + myCard1.getRight() + "," + myCard1.getX() + "," + XmyCard1);
								}
							});
							myCard1.startAnimation(ta1);
						}

						if (myCard2.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta2 = new TranslateAnimation(XmyCard2, XmyCard2 + dpToPx(30), 0, 0);
							ta2.setDuration(1000);
							ta2.setFillEnabled(true);
							ta2.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard2 += dpToPx(30);
									reper2++;
									Log.d("TAG", "" + reper2);
									final int id = stringToId("myreper" + "" + reper2);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard2.layout(left, top, right, bottom);
									XcoordCard2 = myCard2.getX();
								}
							});
							myCard2.startAnimation(ta2);
						}

						if (myCard4.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta4 = new TranslateAnimation(XmyCard4, XmyCard4 - dpToPx(30), 0, 0);
							ta4.setDuration(1000);
							ta4.setFillEnabled(true);
							ta4.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard4 -= dpToPx(30);
									reper4--;
									final int id = stringToId("myreper" + "" + reper4);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard4.layout(left, top, right, bottom);
									XcoordCard4 = myCard4.getX();
								}
							});
							myCard4.startAnimation(ta4);
						}
					}
				}
			});

			myCard4.setOnClickListener(new OnClickListener()
			{
				// TODO

				@Override
				public void onClick(View v)
				{
					if (playersTurn == 1)
					{
						myCard4.clearAnimation();
						myCard4.setVisibility(ImageButton.INVISIBLE);
						playersTurn = 2;

						PlayedCardsTwo.addCard(Players.player1[0]);
						playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player1[3]));
						playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
						playedCardPosition++;
						anyCardPlayed = 1;
						cardsInHand--;
						Players.size1--;

						if (myCard3.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta3 = new TranslateAnimation(XmyCard3, XmyCard3 + dpToPx(30), 0, 0);
							ta3.setDuration(1000);
							ta3.setFillEnabled(true);
							ta3.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard3 += dpToPx(30);
									reper3++;
									final int id = stringToId("myreper" + "" + reper3);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard3.layout(left, top, right, bottom);
									XcoordCard3 = myCard3.getX();
								}
							});
							myCard3.startAnimation(ta3);
						}

						if (myCard2.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta2 = new TranslateAnimation(XmyCard2, XmyCard2 + dpToPx(30), 0, 0);
							ta2.setDuration(1000);
							ta2.setFillEnabled(true);
							ta2.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard2 += dpToPx(30);
									reper2++;
									final int id = stringToId("myreper" + "" + reper2);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard2.layout(left, top, right, bottom);
									XcoordCard2 = myCard2.getX();
								}
							});
							myCard2.startAnimation(ta2);
						}

						if (myCard1.getVisibility() == ImageButton.VISIBLE)
						{
							TranslateAnimation ta1 = new TranslateAnimation(XmyCard1, XmyCard1 + dpToPx(30), 0, 0);
							ta1.setDuration(1000);
							ta1.setFillEnabled(true);
							ta1.setAnimationListener(new AnimationListener()
							{

								@Override
								public void onAnimationStart(Animation animation)
								{
								}

								@Override
								public void onAnimationRepeat(Animation animation)
								{
								}

								@Override
								public void onAnimationEnd(Animation animation)
								{
									XmyCard1 += dpToPx(30);
									reper1++;
									final int id = stringToId("myreper" + "" + reper1);
									final int left = findViewById(id).getLeft();
									final int right = findViewById(id).getRight();
									final int top = findViewById(id).getTop();
									final int bottom = findViewById(id).getBottom();
									myCard1.layout(left, top, right, bottom);
									XcoordCard1 = myCard1.getX();
								}
							});
							myCard1.startAnimation(ta1);
						}
					}
				}
			});

			break;

		case 3:
			// Shuffle the cards :D
			//DeckVector.init();
		//	DeckVector.size = 30;
			//DeckVector.shuffle();

			for (int i = 0; i < 4; i++)
			{
			//	Players.addToPlayer1(DeckVector.pop());
				//Players.addToPlayer2(DeckVector.pop());
				//Players.addToPlayer3(DeckVector.pop());
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
			// Shuffle the cards :D
		//	DeckVector.init();
			//DeckVector.shuffle();

			for (int i = 0; i < 4; i++)
			{
				//Players.addToPlayer1(DeckVector.pop());
				//Players.addToPlayer2(DeckVector.pop());
				//Players.addToPlayer3(DeckVector.pop());
				//Players.addToPlayer4(DeckVector.pop());
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
	protected void onStop()
	{
		super.onStop();
		keepGoing = false;
		try
		{
			botPlay.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

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

	@Override
	protected void onPause()
	{
		super.onPause();
		keepGoing = false;
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		keepGoing = true;
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		keepGoing = true;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		keepGoing = false;
		try
		{
			botPlay.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public int nameToId(String name)
	{
		return getResources().getIdentifier(name, "drawable", getPackageName());
	}

	public int stringToId(String str)
	{
		return getResources().getIdentifier(str, "id", getPackageName());
	}

	public int dpToPx(double d)
	{
		return (int) ((d * getApplicationContext().getResources().getDisplayMetrics().density) + 0.5);
	}

	public void dealCardsAfterOneHandWasFinished()
	{
		int numberOfCardsMoved = 0;

		final ImageButton reper7 = (ImageButton) findViewById(R.id.myreper7);
		final ImageButton reper5 = (ImageButton) findViewById(R.id.myreper5);
		final ImageButton reper3 = (ImageButton) findViewById(R.id.myreper3);
		final ImageButton reper1 = (ImageButton) findViewById(R.id.myreper1);

		// Moving the cards that are still visible, to the right. As far as they
		// can.
		if (myCard4.getVisibility() == ImageButton.VISIBLE)
		{
			myCard4.clearAnimation();
			TranslateAnimation ta4;
			Log.d("DEBUG", 4 - cardsInHand + "");
			ta4 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, (4 - cardsInHand) * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
			ta4.setDuration(1000);
			ta4.setFillEnabled(true);
			ta4.setAnimationListener(new AnimationListener()
			{

				@Override
				public void onAnimationStart(Animation animation)
				{
					// finishAnimation = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					// TODO think what you should write here
					/*
					 * final int left = reper7.getLeft(); final int right =
					 * reper7.getRight(); final int top = reper7.getTop(); final
					 * int bottom = reper7.getBottom(); myCard4.layout(left,
					 * top, right , bottom);
					 */
					// finishAnimation = true;
				}
			});
			myCard4.startAnimation(ta4);
			numberOfCardsMoved++;
		}

		if (myCard3.getVisibility() == ImageButton.VISIBLE)
		{
			TranslateAnimation ta3;
			if (numberOfCardsMoved == 0)
			{
				ta3 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, (4 - cardsInHand) * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				// place the cards' value in the proper position in the player's
				// vector
				Players.player1[3] = Players.player1[2];
			}
			else
			{
				// numberOfCardsMoved == 1
				ta3 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, (4 - cardsInHand) * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
			}
			ta3.setDuration(1000);
			ta3.setFillEnabled(true);
			ta3.setAnimationListener(new AnimationListener()
			{

				@Override
				public void onAnimationStart(Animation animation)
				{
					// finishAnimation = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					// TODO think what you should write here
					/*
					 * XmyCard4 -= dpToPx(30); reper4--; final int id =
					 * stringToId("myreper" + "" + reper4); final int left =
					 * findViewById(id).getLeft(); final int right =
					 * findViewById(id).getRight(); final int top =
					 * findViewById(id).getTop(); final int bottom =
					 * findViewById(id).getBottom(); myCard4.layout(left, top,
					 * right , bottom);
					 */
					// finishAnimation = true;
				}
			});
			myCard3.startAnimation(ta3);
			numberOfCardsMoved++;
		}

		if (myCard2.getVisibility() == ImageButton.VISIBLE)
		{
			TranslateAnimation ta2;
			if (numberOfCardsMoved == 0)
			{
				if (cardsInHand == 2)
				{
					ta2 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 2 * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				}
				else
				{
					// cardsInHand == 1
					ta2 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 3 * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				}
				// place the cards' value in the proper position in the player's
				// vector
				Players.player1[3] = Players.player1[1];
			}
			else if (numberOfCardsMoved == 1)
			{
				ta2 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, (4 - cardsInHand) * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				// place the cards' value in the proper position in the player's
				// vector
				Players.player1[2] = Players.player1[1];
			}
			else
			{
				ta2 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, (4 - cardsInHand) * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
			}
			ta2.setDuration(1000);
			ta2.setFillEnabled(true);
			ta2.setAnimationListener(new AnimationListener()
			{

				@Override
				public void onAnimationStart(Animation animation)
				{
					// finishAnimation = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					// TODO think what you should write here
					/*
					 * XmyCard4 -= dpToPx(30); reper4--; final int id =
					 * stringToId("myreper" + "" + reper4); final int left =
					 * findViewById(id).getLeft(); final int right =
					 * findViewById(id).getRight(); final int top =
					 * findViewById(id).getTop(); final int bottom =
					 * findViewById(id).getBottom(); myCard4.layout(left, top,
					 * right , bottom);
					 */
					// finishAnimation = true;
				}
			});
			myCard2.startAnimation(ta2);
			numberOfCardsMoved++;
		}

		if (myCard1.getVisibility() == ImageButton.VISIBLE)
		{
			TranslateAnimation ta1;
			if (numberOfCardsMoved == 0)
			{
				ta1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 3 * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				// place the cards' value in the proper position in the player's
				// vector
				Players.player1[3] = Players.player1[0];
			}
			else if (numberOfCardsMoved == 1)
			{
				ta1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 2 * dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				// place the cards' value in the proper position in the player's
				// vector
				Players.player1[2] = Players.player1[0];
			}
			else
			{
				ta1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, dpToPx(30), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				// place the cards' value in the proper position in the player's
				// vector
				Players.player1[1] = Players.player1[0];
			}
			ta1.setDuration(1000);
			ta1.setFillEnabled(true);
			ta1.setAnimationListener(new AnimationListener()
			{

				@Override
				public void onAnimationStart(Animation animation)
				{
					// finishAnimation = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					// TODO think what you should write here
					/*
					 * XmyCard4 -= dpToPx(30); reper4--; final int id =
					 * stringToId("myreper" + "" + reper4); final int left =
					 * findViewById(id).getLeft(); final int right =
					 * findViewById(id).getRight(); final int top =
					 * findViewById(id).getTop(); final int bottom =
					 * findViewById(id).getBottom(); myCard4.layout(left, top,
					 * right , bottom);
					 */
					// finishAnimation = true;
				}
			});
			myCard1.startAnimation(ta1);
			numberOfCardsMoved++;
		}

		// Pop cards from the deck and place them in player's hand
		Handler h = new Handler();
		h.postDelayed(new Runnable()
		{

			@Override
			public void run()
			{
				for (int i = 3 - cardsInHand; i >= 0; i--)
				{
				//	Players.player1[i] = DeckVector.pop();
				}

				int left;
				int right;
				int top;
				int bottom;

				left = reper1.getLeft();
				right = reper1.getRight();
				top = reper1.getTop();
				bottom = reper1.getBottom();
				myCard1.layout(left, top, right, bottom);
				myCard1.setVisibility(0);

				left = reper3.getLeft();
				right = reper3.getRight();
				top = reper3.getTop();
				bottom = reper3.getBottom();
				myCard2.layout(left, top, right, bottom);
				myCard2.setVisibility(0);

				left = reper5.getLeft();
				right = reper5.getRight();
				top = reper5.getTop();
				bottom = reper5.getBottom();
				myCard3.layout(left, top, right, bottom);
				myCard3.setVisibility(0);

				left = reper7.getLeft();
				right = reper7.getRight();
				top = reper7.getTop();
				bottom = reper7.getBottom();
				myCard4.layout(left, top, right, bottom);
				myCard4.setVisibility(0);

				myCard1.setBackgroundResource(nameToId(Players.player1[0]));
				myCard2.setBackgroundResource(nameToId(Players.player1[1]));
				myCard3.setBackgroundResource(nameToId(Players.player1[2]));
				myCard4.setBackgroundResource(nameToId(Players.player1[3]));
			}
		}, 1000);

	}

	class MyGestureDetector extends SimpleOnGestureListener
	{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			try
			{
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
				{
					// finish the hand
					if (Game.playersTurn == 1 && Game.anyCardPlayed == 1)
					{
						switch (Game.numberOfPlayers)
						{
						case 2:
							// if ( Players.checkSizes(2)){
							Game.whoIsFirst = PlayedCardsTwo.checkWinner();
							PlayedCardsTwo.reset();
							dealCardsAfterOneHandWasFinished();
							// use the vector now
							/*
							 * Game.playedCard5.setVisibility(1);
							 * Game.playedCard6.setVisibility(1);
							 * Game.playedCard7.setVisibility(1);
							 * Game.playedCard8.setVisibility(1);
							 * Game.playedCard9.setVisibility(1);
							 * Game.playedCard10.setVisibility(1);
							 * Game.playedCard11.setVisibility(1);
							 * Game.playedCard12.setVisibility(1);
							 */
							// pop a card from the deck and put in the players
							// hand
							// }
							break;

						case 3:

							break;

						case 4:

							break;

						default:
							break;
						}
					}
				}
				else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
				{
					// finish the hand
				}
			}
			catch (Exception e)
			{
				// nothing
			}
			return false;
		}

	}

	private class BotPlay extends Thread
	{

		@Override
		public void run()
		{
			while (keepGoing)
			{
				try
				{
					Thread.sleep(20);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				if (Game.playersTurn == 2)
				{
					try
					{
						Thread.sleep(1500);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							int cardNumber = pseudoRandom();
							PlayedCardsTwo.addCard(Players.player2[cardNumber - 1]);

							playedCards[playedCardPosition].setVisibility(ImageView.VISIBLE);
							// TODO
							Log.d("DEBUG", XcoordCard1 + "," + XcoordCard2 + "," + XcoordCard3 + "," + XcoordCard4);
							myCard2.setVisibility(View.INVISIBLE);
							playedCards[playedCardPosition].setBackgroundResource(nameToId(Players.player2[cardNumber - 1]));
							playedCardPosition++;
							Log.d("DEBUG", XcoordCard1 + "," + XcoordCard2 + "," + XcoordCard3 + "," + XcoordCard4);

							/*
							 * switch (cardNumber){ case 1:
							 * oppositeCard1.clearAnimation();
							 * oppositeCard1.setVisibility(ImageView.INVISIBLE);
							 * if (oppositeCard2.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta2; ta2
							 * = new TranslateAnimation(XopCard2, XopCard2
							 * -dpToPx(22.5), 0, 0); ta2.setDuration(1000);
							 * ta2.setFillEnabled(true);
							 * ta2.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard2 -= dpToPx(22.5);
							 * opReper2--; final int id =
							 * stringToId("oppositereper" + "" + opReper2);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard2.layout(left, top, right, bottom); }
							 * });
							 * 
							 * oppositeCard2.startAnimation(ta2); }
							 * 
							 * if (oppositeCard3.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta3 =
							 * new TranslateAnimation( XopCard3, XopCard3 -
							 * dpToPx(22.5), 0, 0); ta3.setDuration(1000);
							 * ta3.setFillEnabled(true);
							 * ta3.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard3 -= dpToPx(22.5);
							 * opReper3--; final int id =
							 * stringToId("oppositereper" + "" + opReper3);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard3.layout(left, top, right , bottom);
							 * } });
							 * 
							 * oppositeCard3.startAnimation(ta3); }
							 * 
							 * if (oppositeCard4.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta4 =
							 * new TranslateAnimation( XopCard4, XopCard4 -
							 * dpToPx(22.5), 0, 0); ta4.setDuration(1000);
							 * ta4.setFillEnabled(true);
							 * ta4.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard4 -= dpToPx(22.5);
							 * opReper4--; final int id =
							 * stringToId("oppositereper" + "" + opReper4);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard4.layout(left, top, right , bottom);
							 * } });
							 * 
							 * oppositeCard4.startAnimation(ta4); } break;
							 * 
							 * case 2: oppositeCard2.clearAnimation();
							 * oppositeCard2.setVisibility(ImageView.INVISIBLE);
							 * if (oppositeCard1.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta1 =
							 * new TranslateAnimation( XopCard1, XopCard1 +
							 * dpToPx(22.5), 0, 0); ta1.setDuration(1000);
							 * ta1.setFillEnabled(true);
							 * ta1.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard1 += dpToPx(22.5);
							 * opReper1++; final int id =
							 * stringToId("oppositereper" + "" + opReper1);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard1.layout(left, top, right , bottom);
							 * } }); oppositeCard1.startAnimation(ta1); }
							 * 
							 * if (oppositeCard3.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta3 =
							 * new TranslateAnimation( XopCard3 , XopCard3
							 * -dpToPx(22.5), 0, 0); ta3.setDuration(1000);
							 * ta3.setFillEnabled(true);
							 * ta3.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard3 -= dpToPx(22.5);
							 * opReper3--; final int id =
							 * stringToId("oppositereper" + "" + opReper3);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard3.layout(left, top, right , bottom);
							 * } }); oppositeCard3.startAnimation(ta3); }
							 * 
							 * if (oppositeCard4.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta4 =
							 * new TranslateAnimation( XopCard4 , XopCard4 -
							 * dpToPx(22.5), 0, 0); ta4.setDuration(1000);
							 * ta4.setFillEnabled(true);
							 * ta4.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard4 -= dpToPx(22.5);
							 * opReper4--; final int id =
							 * stringToId("oppositereper" + "" + opReper4);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard4.layout(left, top, right , bottom);
							 * } }); oppositeCard4.startAnimation(ta4); } break;
							 * 
							 * case 3: oppositeCard3.clearAnimation();
							 * oppositeCard3.setVisibility(ImageView.INVISIBLE);
							 * if (oppositeCard1.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta1 =
							 * new TranslateAnimation( XopCard1, XopCard1 +
							 * dpToPx(22.5), 0, 0); ta1.setDuration(1000);
							 * ta1.setFillEnabled(true);
							 * ta1.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard1 += dpToPx(22.5);
							 * opReper1++; final int id =
							 * stringToId("oppositereper" + "" + opReper1);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard1.layout(left, top, right , bottom);
							 * } }); oppositeCard1.startAnimation(ta1); }
							 * 
							 * if (oppositeCard2.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta2 =
							 * new TranslateAnimation( XopCard2, XopCard2 +
							 * dpToPx(22.5), 0, 0); ta2.setDuration(1000);
							 * ta2.setFillEnabled(true);
							 * ta2.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard2 += dpToPx(22.5);
							 * opReper2++; Log.d("TAG", "" + opReper2); final
							 * int id = stringToId("oppositereper" + "" +
							 * opReper2); final int left =
							 * findViewById(id).getLeft(); final int right =
							 * findViewById(id).getRight(); final int top =
							 * findViewById(id).getTop(); final int bottom =
							 * findViewById(id).getBottom();
							 * oppositeCard2.layout(left, top, right , bottom);
							 * } }); oppositeCard2.startAnimation(ta2); }
							 * 
							 * if (oppositeCard4.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta4 =
							 * new TranslateAnimation( XopCard4, XopCard4 -
							 * dpToPx(22.5), 0, 0); ta4.setDuration(1000);
							 * ta4.setFillEnabled(true);
							 * ta4.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard4 -= dpToPx(22.5);
							 * opReper4--; final int id =
							 * stringToId("oppositereper" + "" + opReper4);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard4.layout(left, top, right , bottom);
							 * } }); oppositeCard4.startAnimation(ta4); } break;
							 * 
							 * case 4: oppositeCard4.clearAnimation();
							 * oppositeCard4.setVisibility(ImageView.INVISIBLE);
							 * if (oppositeCard3.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta3 =
							 * new TranslateAnimation( XopCard3, XopCard3 +
							 * dpToPx(22.5), 0, 0); ta3.setDuration(1000);
							 * ta3.setFillEnabled(true);
							 * ta3.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard3 += dpToPx(22.5);
							 * opReper3++; final int id =
							 * stringToId("oppositereper" + "" + opReper3);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard3.layout(left, top, right , bottom);
							 * } }); oppositeCard3.startAnimation(ta3); }
							 * 
							 * if (oppositeCard2.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta2 =
							 * new TranslateAnimation( XopCard2, XopCard2 +
							 * dpToPx(22.5), 0, 0); ta2.setDuration(1000);
							 * ta2.setFillEnabled(true);
							 * ta2.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard2 += dpToPx(22.5);
							 * opReper2++; final int id =
							 * stringToId("oppositereper" + "" + opReper2);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard2.layout(left, top, right , bottom);
							 * } }); oppositeCard2.startAnimation(ta2); }
							 * 
							 * if (oppositeCard1.getVisibility() ==
							 * ImageButton.VISIBLE){ TranslateAnimation ta1 =
							 * new TranslateAnimation( XopCard1, XopCard1 +
							 * dpToPx(22.5), 0, 0); ta1.setDuration(1000);
							 * ta1.setFillEnabled(true);
							 * ta1.setAnimationListener(new AnimationListener()
							 * {
							 * 
							 * @Override public void onAnimationStart(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationRepeat(Animation
							 * animation) { }
							 * 
							 * @Override public void onAnimationEnd(Animation
							 * animation) { XopCard1 += dpToPx(22.5);
							 * opReper1++; final int id =
							 * stringToId("oppositereper" + "" + opReper1);
							 * final int left = findViewById(id).getLeft();
							 * final int right = findViewById(id).getRight();
							 * final int top = findViewById(id).getTop(); final
							 * int bottom = findViewById(id).getBottom();
							 * oppositeCard1.layout(left, top, right , bottom);
							 * } }); oppositeCard1.startAnimation(ta1); } break;
							 * 
							 * default: break; }
							 */

							// Game stuff
							playersTurn = 1;
							Players.size2--;
							anyCardPlayed = 1;
						}

					});
				}
			}
		}

		private int pseudoRandom()
		{
			return 1 + (int) (Math.random() * (Players.size2 + 1));
		}
	}

}
