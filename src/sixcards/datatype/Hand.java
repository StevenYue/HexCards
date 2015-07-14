package sixcards.datatype;
import static sixcards.datatype.SixCardsConstant.*;
import static sixcards.utility.SixCardsExceptions.*;

import java.util.Arrays;

import sixcards.utility.SixCardsCalculator;
import sixcards.utility.SixCardsExceptions.TypeTwoGameDiedException;

public class Hand implements Comparable<Hand>{
	private Card[] cards;
	private int type;
	private int jokerCounts;
	private int value[];
	
	public Card[] getCards() {return cards;}
	public void setCards(Card[] cards) {this.cards = cards;}
	public int getType() {return type;}
	public void setType(int type) {this.type = type;}
	public int getJokerCounts() {return jokerCounts;}
	public void setJokerCounts(int hasJoker) {this.jokerCounts = hasJoker;}
	public int[] getValue() {return value;}
	public void setValue(int[] value) {this.value = value;}
	
	
	
	public Hand(Card[] h) throws TypeTwoGameDiedException{
		type = h.length;
		cards = new Card[type];
		value = new int[type+1];
		for(int i=0;i<type;i++){
			if(h[i].getRank() == BIGJOKER) jokerCounts++;
			cards[i] = new Card(h[i].getRank(),h[i].getSuit());
		}
		Arrays.sort(cards);
		if(type == TYPE_TWO_GAME)
			value = SixCardsCalculator.calTypeTwoHand(this);
		if(type == TYPE_THREE_GAME)
			value = SixCardsCalculator.calTypeThreeHand(this);
	}
	private void create(Card[] h) throws TypeTwoGameDiedException {
		cards = new Card[type];
		value = new int[type+1];
		for(int i=0;i<type;i++){
			if(h[i].getRank() == BIGJOKER) jokerCounts++;
			cards[i] = new Card(h[i].getRank(),h[i].getSuit());
		}
		Arrays.sort(cards);
		if(type == TYPE_TWO_GAME)
			value = SixCardsCalculator.calTypeTwoHand(this);
		if(type == TYPE_THREE_GAME)
			value = SixCardsCalculator.calTypeThreeHand(this);
	}
	public Hand(Card c1) throws TypeTwoGameDiedException{
		this.type = TYPE_ONE_GAME;
		Card[] temp = new Card[]{c1};
		this.create(temp);
	}
	public Hand(Card c1, Card c2) throws TypeTwoGameDiedException{
		this.type = TYPE_TWO_GAME;
		Card[] temp = new Card[]{c1,c2};
		this.create(temp);
	}
	public Hand(Card c1, Card c2, Card c3) throws TypeTwoGameDiedException{
		this.type = TYPE_THREE_GAME;
		Card[] temp = new Card[]{c1,c2,c3};
		this.create(temp);
	}

	@Override
	public int compareTo(Hand o) {
		// TODO Auto-generated method stub
		if(this.type != o.type)
			try {
				throw new GameTypeMisMatchException();
			} catch (GameTypeMisMatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(this.type == TYPE_ONE_GAME){
			return typeOneCompare(this, o);
		}
		if(this.type == TYPE_TWO_GAME){
			return typeTwoCompare(this, o);
		}
		if(this.type == TYPE_THREE_GAME){
			return typeThreeCompare(this, o);
		}
		return 0;
	}

	private int typeOneCompare(Hand hand1, Hand hand2) {
		// TODO Auto-generated method stub
		
		if(hand1.cards[0].getRank() > hand2.cards[0].getRank()) return 1;
		else if(hand1.cards[0].getRank() < hand2.cards[0].getRank()) return -1;
		else{
			if(hand1.cards[0].getRank() == BIGJOKER) return 0;
			else{
				if(hand1.cards[0].getSuit() > hand2.cards[0].getSuit()) return 1;
				else if(hand1.cards[0].getSuit() < hand2.cards[0].getSuit()) return -1;
				else return 0;
			}
		}
	}
	
	private int typeTwoCompare(Hand hand1, Hand hand2) {
		if(hand1.getValue()[0] > hand2.getValue()[0]) return 1;
		else if(hand1.getValue()[0] < hand2.getValue()[0]) return -1;
		else{
			if(hand1.getValue()[1] > hand2.getValue()[1]) return 1;
			else if(hand1.getValue()[1] < hand2.getValue()[1]) return -1;
			else{
				if(hand1.getValue()[2] > hand2.getValue()[2]) return 1;
				else if(hand1.getValue()[2] < hand2.getValue()[2]) return -1;
				else return 0;
			}
		}
	}	
	private int typeThreeCompare(Hand hand1, Hand hand2) {
		if(hand1.getValue()[0] > hand2.getValue()[0]) return 1;
		else if(hand1.getValue()[0] < hand2.getValue()[0]) return -1;
		else{
			if(hand1.getValue()[1] > hand2.getValue()[1]) return 1;
			else if(hand1.getValue()[1] < hand2.getValue()[1]) return -1;
			else{
				if(hand1.getValue()[2] > hand2.getValue()[2]) return 1;
				else if(hand1.getValue()[2] < hand2.getValue()[2]) return -1;
				else{
					if(hand1.getValue()[3] > hand2.getValue()[3]) return 1;
					else if(hand1.getValue()[3] < hand2.getValue()[3]) return -1;
					else{
						return 0;
					}
				}
			}
		}
	}
	
	
	public String toString(){
		String res = "";
		for(Card c:this.cards){
			res += c.toString();
		}
		return res;
	}
	
	
}
