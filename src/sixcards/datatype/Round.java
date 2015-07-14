package sixcards.datatype;

import java.util.ArrayList;
import java.util.HashSet;

import sixcards.utility.SixCardsCalculator;
import sixcards.utility.SixCardsExceptions.RoundDiedException;
import static sixcards.utility.SixCardsExceptions.*;

public class Round {
	private Card[] round;
	private boolean isDead = false;
	private HashSet<Card> impossibleTypeOneGameSelection = null;

	public Round(Card[] r){
		round = new Card[6];
		for(int i=0;i<6;i++){
			round[i] = new Card(r[i].getRank(),r[i].getSuit());
		}
		if(isDied(round)){
			isDead = true;
			
		}else{
			impossibleTypeOneGameSelection = new HashSet<Card>();
			for(Card c:round){
				Card temp[] = new Card[5];
				int i = 0;
				for(Card inc: round){
					if(inc != c){
						temp[i] = inc;
						i++;
					}
				}
				if(isDied(temp)){
					impossibleTypeOneGameSelection.add(c);
				}
			}
		}
	}

	public Card[] getRound() {return round;}
	public void setRound(Card[] round) {this.round = round;}
	public boolean isDead(){return this.isDead;}
	public HashSet<Card> getImpossibleTypeOneGameSelection() {return impossibleTypeOneGameSelection;}



	private boolean isDied(Card[] cards){
		ArrayList<Card[]> res = new ArrayList<Card[]>();
		Card[] typeTwoGame = new Card[2];
		isDiedAssist(cards,res, typeTwoGame, 0, 0);
		int totalCombinations=0, diedCombinations=0;
		for(Card[] cc:res){
			totalCombinations++;
			try {
				SixCardsCalculator.calTypeTwoHand(new Hand(cc));
			} catch (TypeTwoGameDiedException e) {
				diedCombinations++;
			}
		}
		return totalCombinations == diedCombinations;
	}
	
	private void isDiedAssist(Card[] cards,ArrayList<Card[]> list, Card[] typeTwoGame, int index, int n){
		if(n == 2){
			Card[] t = new Card[2];
			t[0] = typeTwoGame[0]; t[1] = typeTwoGame[1];
			list.add(t);
			return;
		}
		for(int i = index;i<cards.length;i++){
			typeTwoGame[n] = cards[i];
			isDiedAssist(cards, list, typeTwoGame, i+1, n+1);
		}
	}
	
	public String toString(){
		String res = "";
		for(Card c:round)
			res += c.toString();
		return res;
	}
}
