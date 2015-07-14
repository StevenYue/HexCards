package sixcards.datatype;

import sixcards.utility.SixCardsExceptions.RoundDiedException;
import sixcards.utility.SixCardsExceptions.TypeTwoGameDiedException;
import static sixcards.datatype.SixCardsConstant.*;

public class Tester {
	
	public static void main(String[] args) {	
		String s = "1-";
		String ss[] = s.split("-");
		for(String str:ss)
			System.out.println("aaa" +str+"bbb");
	}
	
	private static void testIfRoundDied(CardDeck deck){
		int num = 1;
		while(true){
			deck.shuffle();
			System.out.println("-----------------Round:"+num+"----------------");
			for (int i = 0; i < deck.getAllCards().length - 5; i += 6) {
				Card[] cards = new Card[6];
				for (int j = 0; j < 6; j++) {
					cards[j] = deck.getAllCards()[i + j];
				}
				Round round = null;

				
				System.out.println("Round OK!");
				System.out.println(round.toString());
				System.out.println();
			}
		num++;
		}
	}
	
	
	
	
	private static void testGameTypeTwo(CardDeck deck){
		for(int i=0;i<deck.getAllCards().length-3;i+=4){
			Hand h1 = null, h2 = null;
			try {
				h1 = new Hand(deck.getAllCards()[i], deck.getAllCards()[i+1]);
				h2 = new Hand(deck.getAllCards()[i+2], deck.getAllCards()[i+3]);
			} catch (TypeTwoGameDiedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("Died Occurred!!");
				System.out.println("hand1:" + deck.getAllCards()[i].toString() + deck.getAllCards()[i+1].toString());
				System.out.println("hand2:" + deck.getAllCards()[i+2].toString()+ deck.getAllCards()[i+3].toString());
				System.out.println();
				continue;
			}
			
			System.out.println("hand1:" + h1);
			System.out.println("hand2:" + h2);
			if(h1.compareTo(h2) > 0){
				System.out.println("hand1 wins!!");
			}else if(h1.compareTo(h2) < 0){
				System.out.println("hand2 wins!!");
			}else{
				System.out.println("Tie!!");
			}
			System.out.println();
		}
	}
	
	private static void testGameTypeThree(CardDeck deck){
		int gameNum = 1;
		for(int i=0;i<deck.getAllCards().length-5;i+=6){
			Hand h1 = null, h2 = null;
			try {
				h1 = new Hand(deck.getAllCards()[i], deck.getAllCards()[i+1], deck.getAllCards()[i+2]);
				h2 = new Hand(deck.getAllCards()[i+3],deck.getAllCards()[i+4],deck.getAllCards()[i+5]);
			} catch (TypeTwoGameDiedException e) {
				// for type three game, there will never be this exception
			}		
			System.out.println("hand1:" + h1);
			System.out.println("hand2:" + h2);
			if(h1.compareTo(h2) > 0){
				System.out.println("hand1 wins Game# "+gameNum+" !!");
			}else if(h1.compareTo(h2) < 0){
				System.out.println("hand2 wins Game# "+gameNum+" !!");
			}else{
				System.out.println("Tie!!");
			}
			System.out.println();
			gameNum++;
		}
	}
}
