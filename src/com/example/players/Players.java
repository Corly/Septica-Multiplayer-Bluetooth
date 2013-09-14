package com.example.players;

public class Players
{

	public static String player1[] = new String[4];
	public static String player2[] = new String[4];
	public static String player3[] = new String[4];
	public static String player4[] = new String[4];
	public static int size1 = 0;
	public static int size2 = 0;
	public static int size3 = 0;
	public static int size4 = 0;

	public static void addToPlayer1(String arg1)
	{
		player1[size1++] = arg1;
	}

	public static void addToPlayer2(String arg1)
	{
		player2[size2++] = arg1;
	}

	public static void addToPlayer3(String arg1)
	{
		player3[size3++] = arg1;
	}

	public static void addToPlayer4(String arg1)
	{
		player4[size4++] = arg1;
	}

	public static boolean checkSizes(int numberOfPlayers)
	{
		switch (numberOfPlayers)
		{
		case 2:
			if (size1 == size2)
			{
				return true;
			}
			else
			{
				return false;
			}

		case 3:
			if (size1 == size2 && size1 == size3)
			{
				return true;
			}
			else
			{
				return false;
			}

		case 4:
			if (size1 == size2 && size1 == size3 && size1 == size4)
			{
				return true;
			}
			else
			{
				return false;
			}

		default:
			return false;
		}
	}

}
