package sixcards.datatype;

import static sixcards.datatype.SixCardsConstant.*;

public class Card implements Comparable<Card>{
	private int rank;
	private char suit;
	private boolean isJoker;
	private boolean isFaceCard;
	public Card(int r,char s){
		if(r == BIGJOKER) isJoker = true;
		if(r == KING || r == QUEEN || r == JACK) isFaceCard = true;
		this.rank = r;
		this.suit = s;
	}
	public int getRank() {return rank;}
	public void setRank(int rank) {this.rank = rank;}
	public char getSuit() {return suit;}
	public void setSuit(char suit) {this.suit = suit;}
	public boolean isJoker() {return isJoker;}
	public void setJoker(boolean isJoker) {this.isJoker = isJoker;}
	public boolean isFaceCard() {return isFaceCard;}
	public void setFaceCard(boolean isFaceCard) {this.isFaceCard = isFaceCard;}
	
	public String toString(){
		return rank + "" + suit;
	}
	
	@Override
	// Sort IN decending order
	public int compareTo(Card o) {
		if(this.getRank() > o.getRank()) return -1;
		else if(this.getRank() < o.getRank()) return 1;
		else{
			if(this.getSuit() > o.getSuit()) return -1;
			else if(this.getSuit() < o.getSuit()) return 1;
			else	return 0; 
		}
	}
}
