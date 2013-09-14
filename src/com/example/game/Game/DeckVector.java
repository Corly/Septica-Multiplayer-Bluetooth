package com.example.game.Game;

import java.util.Random;

import android.content.Context;

public abstract class DeckVector
{
	private static Card[] deck;
	private static int size;

	public static Card getCard(int index)
	{
		if (index < 32 && index >= 0)
			return deck[index];
		else return null;
	}

	public static void init(Context context)
	{
		deck = new Card[32];
		size = 32;

		/*
		 * t c caro r rosie n neagra
		 */

		deck[0] = new Card("t7", context);
		deck[1] = new Card("c7", context);
		deck[2] = new Card("r7", context);
		deck[3] = new Card("n7", context);

		deck[4] = new Card("ta", context);
		deck[5] = new Card("ca", context);
		deck[6] = new Card("ra", context);
		deck[7] = new Card("na", context);

		deck[8] = new Card("t9", context);
		deck[9] = new Card("c9", context);
		deck[10] = new Card("r9", context);
		deck[11] = new Card("n9", context);

		deck[12] = new Card("t10", context);
		deck[13] = new Card("c10", context);
		deck[14] = new Card("r10", context);
		deck[15] = new Card("n10", context);

		deck[16] = new Card("tj", context);
		deck[17] = new Card("cj", context);
		deck[18] = new Card("rj", context);
		deck[19] = new Card("nj", context);

		deck[20] = new Card("tq", context);
		deck[21] = new Card("cq", context);
		deck[22] = new Card("rq", context);
		deck[23] = new Card("nq", context);

		deck[24] = new Card("tk", context);
		deck[25] = new Card("ck", context);
		deck[26] = new Card("rk", context);
		deck[27] = new Card("nk", context);

		deck[28] = new Card("t8", context);
		deck[29] = new Card("c8", context);
		deck[30] = new Card("r8", context);
		deck[31] = new Card("n8", context);
		for (int i = 0; i < size; i++)
		{
			deck[i].setHeight(130.0f);
			deck[i].setWidth(100.0f);
		}

	}

	public static void reinitSize()
	{
		size = 32;
	}

	public static void shuffle()
	{
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < 10000; i++)
		{
			int change1 = random.nextInt(size);
			int change2 = random.nextInt(size);
			swap(change1, change2);
		}
	}

	private static void swap(int i, int change)
	{
		Card helper = deck[i];
		deck[i] = deck[change];
		deck[change] = helper;
	}

	public static Card pop()
	{
		size--;
		return deck[size];
	}

	public static boolean isEmpty()
	{
		if (size == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
