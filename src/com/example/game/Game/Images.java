package com.example.game.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Images
{
	private static Bitmap mGameBackground;
	private static Bitmap mCardBack;
	
	public static void init(Context context)
	{
		/* no need to call this right now */
		mGameBackground = BitmapFactory.decodeResource(context.getResources(), com.example.septica_multiplayer_bluetooth.R.drawable.background);
		mCardBack = BitmapFactory.decodeResource(context.getResources(), com.example.septica_multiplayer_bluetooth.R.drawable.cardback);
	}
	
	public static Bitmap getBackgroundImage()
	{
		return mGameBackground;
	}
	
	public static Bitmap getCardBackImage()
	{
		return mCardBack;
	}
	
	public static void resizeCardBackImage(Context context , int newWidth , int newHeight)
	{
		
		mCardBack = BitmapFactory.decodeResource(context.getResources(), com.example.septica_multiplayer_bluetooth.R.drawable.cardback);
		mCardBack = Bitmap.createScaledBitmap(mCardBack, newWidth, newHeight, true);
	}
	
	public static void resizeBackgroundImage(Context context , int newWidth , int newHeight)
	{
		mGameBackground = BitmapFactory.decodeResource(context.getResources(), com.example.septica_multiplayer_bluetooth.R.drawable.background);
		mGameBackground = Bitmap.createScaledBitmap(mGameBackground, newWidth, newHeight, true);
	}
}
