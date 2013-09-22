package com.example.game.Game;

public class BotPlay extends Thread
{

	/*@Override
	public void run()
	{
		while (mRunning)
		{

			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			if (player2.isTurn())
			{
				// choose a random card from player's 2 hand
				int whatCard = pseudoRandom();
				Log.d("Septica", whatCard + "");
				
				if (player2.getCards().size() != 0){
					if (table.addToTable(player2.getCard(whatCard)))
					{
						player2.removeCard(whatCard);
						// change player 2 turn
						player2.setTurn(false);
						player1.setTurn(true);
					}
					else
					{
						if (whatCard == 0)
						{
							player1.setTurn(false);
							player2.setTurn(false);
							finishHand(2, false);
						}
					}
				}
				else {
					player1.setTurn(false);
					player2.setTurn(false);
					finishHand(2, false);
				}
			}
		}
	}*/
}
