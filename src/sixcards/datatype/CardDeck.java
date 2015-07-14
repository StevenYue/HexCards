package sixcards.datatype;

import java.util.Random;

import sixcards.datatype.SixCardsConstant.*;
import sixcards.utility.SixCardsExceptions.RoundDiedException;


public class CardDeck {
	private Card[] allCards;
	private int size;
	private int index;
	
	public CardDeck(int s){
		this.index = 0;
		this.size = s;
		allCards = new Card[s*54];
		for(int i=0;i<s;i++){
			creatOneDeck(i);
		}
		shuffle();
	}
	
	public Card[] getAllCards() {return allCards;}
	public void setAllCards(Card[] allCards) {this.allCards = allCards;}
	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}
	public boolean isNoMoreCard() {return this.index == (this.size*54 - 1);}
	
	//for testing of game termination
//	public boolean isNoMoreCard() {return this.index == (12);}

	
	
	public Round getARound(){
		Card[] round = new Card[6];
//		Random random = new Random();
//		int n  = random.nextInt();
//		if(n %2 == 0){
//			round[0] = new Card(9,'s');
//			round[1] = new Card(9,'s');
//			round[2] = new Card(9,'s');
//			round[3] = new Card(9,'s');
//			round[4] = new Card(9,'s');
//			round[5] = new Card(9,'s');
//		}else{
//			round[0] = new Card(1,'s');
//			round[1] = new Card(1,'s');
//			round[2] = new Card(1,'s');
//			round[3] = new Card(1,'s');
//			round[4] = new Card(1,'s');
//			round[5] = new Card(1,'s');
//		}
//		
		for(int i=0;i<6;i++){
			round[i] = allCards[index++];
		}
		Round r = new Round(round);
		return r;
	}

	private void creatOneDeck(int nth){
		for(int i=0;i<52;i++){
			int rank = (i+1)%13;
			if(rank == 0) rank = SixCardsConstant.KING;
			char suit=0;
			switch(i/13){
			case 0: suit = SixCardsConstant.SPADE;break;
			case 1: suit = SixCardsConstant.HEART;break;
			case 2: suit = SixCardsConstant.CLUB;break;
			case 3: suit = SixCardsConstant.DIAMOND;break;
			}
			
			allCards[54*nth+i] = new Card(rank,suit);
		}
		allCards[54*nth+52] = new Card(SixCardsConstant.BIGJOKER, SixCardsConstant.JOKER);
		allCards[54*nth+53] = new Card(SixCardsConstant.BIGJOKER, SixCardsConstant.JOKER);
	}
	
	public void shuffle(){
		Random random = new Random();
		for(int i=0;i<54*this.size;i++){
			int randNum = random.nextInt(54*this.size);
			Card temp = this.allCards[i];
			this.allCards[i] = this.allCards[randNum];
			this.allCards[randNum] = temp;
		}
	}
	
	public void showAllCards(){
		System.out.println("There are "+54*this.size+" cards in total.");
		for(Card c:allCards){
			System.out.println("Rank is:"+c.getRank()+" Suit is:"+c.getSuit());
		}
	}
	
}
