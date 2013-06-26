package com.example.vs.computer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.septica_multiplayer_bluetooth.R;

public class Game extends Activity {
	
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
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
		
	}
}
