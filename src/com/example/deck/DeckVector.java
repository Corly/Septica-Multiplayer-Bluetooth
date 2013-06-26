package com.example.deck;

import java.util.Random;

public class DeckVector {
	public static String deck[];
	public static int size;
	
	public void init(){
		deck = new String[32];
		size = 32;
		
		/*	t = trefla		
		*	c = caro
		*	r = rosie
		*	n = neagra
		*/
		
		deck[0] = "7t";
		deck[1] = "7c";
		deck[2] = "7r";
		deck[3] = "7n";
		
		deck[4] = "8t";
		deck[5] = "8c";
		deck[6] = "8r";
		deck[7] = "8n";
		
		deck[8] = "9t";
		deck[9] = "9c";
		deck[10] = "9r";
		deck[11] = "9n";
		
		deck[12] = "10t";
		deck[13] = "10c";
		deck[14] = "10r";
		deck[15] = "10n";
		
		deck[16] = "Jt";
		deck[17] = "Jc";
		deck[18] = "Jr";
		deck[19] = "Jn";
		
		deck[20] = "Qt";
		deck[21] = "Qc";
		deck[22] = "Qr";
		deck[23] = "Qn";
		
		deck[24] = "Kt";
		deck[25] = "Kc";
		deck[26] = "Kr";
		deck[27] = "Kn";
		
		deck[28] = "At";
		deck[29] = "Ac";
		deck[30] = "Ar";
		deck[31] = "An";
	}
	
	public static void reinitSize(){
		size = 32;
	}
	
	public static void shuffle(){
		Random random = new Random();
	    random.nextInt();
	    for (int i = 0; i < size; i++) {
	      int change = i + random.nextInt(size - i);
	      swap(i, change);
	    }
	 }

	 private static void swap(int i, int change) {
	    String helper = deck[i];
	    deck[i] = deck[change];
	    deck[change] = helper;
	 }
	 
	 public String pop(){
		 size--;
		 return deck[size];
	 }

}
