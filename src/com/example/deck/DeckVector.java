package com.example.deck;

import java.util.Random;

public class DeckVector
{
	public static String deck[];
	public static int size;

	public static void init()
	{
		deck = new String[32];
		size = 32;

		/*
		 * t = trefla c = caro r = rosie n = neagra
		 */

		deck[0] = "t7";
		deck[1] = "c7";
		deck[2] = "r7";
		deck[3] = "n7";

		deck[4] = "ta";
		deck[5] = "ca";
		deck[6] = "ra";
		deck[7] = "na";

		deck[8] = "t9";
		deck[9] = "c9";
		deck[10] = "r9";
		deck[11] = "n9";

		deck[12] = "t10";
		deck[13] = "c10";
		deck[14] = "r10";
		deck[15] = "n10";

		deck[16] = "tj";
		deck[17] = "cj";
		deck[18] = "rj";
		deck[19] = "nj";

		deck[20] = "tq";
		deck[21] = "cq";
		deck[22] = "rq";
		deck[23] = "nq";

		deck[24] = "tk";
		deck[25] = "ck";
		deck[26] = "rk";
		deck[27] = "nk";

		deck[28] = "t8";
		deck[29] = "c8";
		deck[30] = "r8";
		deck[31] = "n8";
	}

	public static void reinitSize()
	{
		size = 32;
	}

	public static void shuffle()
	{
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < size; i++)
		{
			int change = i + random.nextInt(size - i);
			swap(i, change);
		}
	}

	private static void swap(int i, int change)
	{
		String helper = deck[i];
		deck[i] = deck[change];
		deck[change] = helper;
	}

	public static String pop()
	{
		size--;
		return deck[size];
	}

	public static int isEmpty()
	{
		if (size == 0)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

}
