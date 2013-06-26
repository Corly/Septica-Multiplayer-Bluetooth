package com.example.vs.computer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.septica_multiplayer_bluetooth.R;

public class Game extends Activity {
	
	ImageButton mycard1;
	ImageButton mycard2;
	ImageButton mycard3;
	ImageButton mycard4;
	
	ImageView playedCard1;
	ImageView playedCard2;
	ImageView playedCard3;
	ImageView playedCard4;
	ImageView playedCard5;
	ImageView playedCard6;
	ImageView playedCard7;
	ImageView playedCard8;
	ImageView playedCard9;
	ImageView playedCard10;
	ImageView playedCard11;
	ImageView playedCard12;
	ImageView playedCard13;
	ImageView playedCard14;
	ImageView playedCard15;
	ImageView playedCard16;
	
	Context cnt = this;
	
	static int typeOfGame;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(cnt);
		//final AlertDialog.Builder auxDialog = dialog;
		
		dialog.setMessage("Choose number of players:");
		dialog.setTitle("Number of players");
		dialog.setNegativeButton("2 players", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				typeOfGame = 2;
				dialog.dismiss();
			}
		});
		
		dialog.setNeutralButton("3 players", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				typeOfGame = 3;
				dialog.dismiss();
			}
		});
		
		dialog.setPositiveButton("4 players", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				typeOfGame = 4;
				dialog.dismiss();
			}
		});
		
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		dialog.show();
	
	}
}
