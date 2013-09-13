package com.example.game.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Card
{
	private float mCenterX;
	private float mCenterY;
	private String mNumber;
	private String mColor;
	private float mWidth;
	private float mHeight;
	private Bitmap mCardImage;
	private Context mContext;

	public Card(String cardCode, Context context)
	{
		mContext = context;
		mCardImage = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(cardCode, "drawable", context.getPackageName()));
		mColor = cardCode.charAt(0) + "";
		mNumber = cardCode.substring(1, cardCode.length());
		mCenterX = 0.0f;
		mCenterY = 0.0f;
		mWidth = mCardImage.getWidth();
		mHeight = mCardImage.getHeight();
	}

	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(mCardImage, mCenterX - mWidth / 2, mCenterY - mHeight / 2, null);
	}

	public float getCenterX()
	{
		return mCenterX;
	}

	public void setCenterX(float mCenterX)
	{
		this.mCenterX = mCenterX;
	}

	public float getCenterY()
	{
		return mCenterY;
	}

	public void setCenterY(float mCenterY)
	{
		this.mCenterY = mCenterY;
	}

	public String getNumber()
	{
		return mNumber;
	}

	public void setNumber(String mNumber)
	{
		this.mNumber = mNumber;
	}

	public String getColor()
	{
		return mColor;
	}

	public void setColor(String mColor)
	{
		this.mColor = mColor;
	}

	public float getWidth()
	{
		return mWidth;
	}

	public void setWidth(float mWidth)
	{
		this.mWidth = mWidth;
		mCardImage = BitmapFactory.decodeResource(mContext.getResources(),mContext.getResources().getIdentifier(mColor + mNumber, "drawable", mContext.getPackageName()));
		mCardImage = Bitmap.createScaledBitmap(mCardImage, (int)mWidth, (int)mHeight, true);		
	}

	public float getHeight()
	{
		return mHeight;
	}

	public void setHeight(float mHeight)
	{
		this.mHeight = mHeight;
		mCardImage = BitmapFactory.decodeResource(mContext.getResources(),mContext.getResources().getIdentifier(mColor + mNumber, "drawable", mContext.getPackageName()));
		mCardImage = Bitmap.createScaledBitmap(mCardImage, (int)mWidth, (int)mHeight, true);
		
	}
}
