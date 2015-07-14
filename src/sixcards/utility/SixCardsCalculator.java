package sixcards.utility;

import static sixcards.datatype.SixCardsConstant.*;
import static sixcards.utility.SixCardsExceptions.*;
import sixcards.datatype.Card;
import sixcards.datatype.Hand;
import sixcards.utility.SixCardsExceptions.TypeTwoGameDiedException;

public class SixCardsCalculator {
	
	/**
	 * return value, res[0] : total value;
	 * 				 res[1] : bigger rank value;
	 * 				 res[2] : suit
	 * @param hand
	 * @return
	 * @throws TypeTwoGameDiedException 
	 */
	public static int[] calTypeTwoHand(Hand hand) throws TypeTwoGameDiedException{
		int res[] = new int[3];
		if(hand.getJokerCounts()>0){
			if(hand.getCards()[0].getRank() == BIGJOKER && hand.getCards()[1].getRank() == BIGJOKER){
				res[0] = TEN_HALF; res[1] = TEN*10; res[2] = SPADE;
			}else{
				Card temp = hand.getCards()[0].getRank() == BIGJOKER? hand.getCards()[1] : hand.getCards()[0];
				if(temp.isFaceCard()){
					res[0] = TEN_HALF; res[1] = TEN*10; res[2] = SPADE;
				}else if(temp.getRank() == TEN){
					res[0] = TEN_HALF; res[1] = TEN*10; res[2] = temp.getRank();
				}else{
					res[0] = TEN*10;
					res[1] = Math.max(temp.getRank(), TEN - temp.getRank())*10;
					res[2] = temp.getRank() > TEN - temp.getRank()?temp.getSuit(): SPADE;
				}
			}
		}else{
			if(hand.getCards()[0].isFaceCard()) res[0] += 5;
			else res[0] += hand.getCards()[0].getRank()*10;
			if(hand.getCards()[1].isFaceCard()) res[0] += 5;
			else res[0] += hand.getCards()[1].getRank()*10;
			if(res[0] > TEN_HALF) throw new TypeTwoGameDiedException();
			
			res[1] = Math.max(hand.getCards()[0].isFaceCard() ? 5 : hand.getCards()[0].getRank()*10,
				hand.getCards()[1].isFaceCard() ? 5 : hand.getCards()[0].getRank()*10);
			if(hand.getCards()[0].isFaceCard() && hand.getCards()[1].isFaceCard()){
				res[2] = DIAMOND;
			}else if(!hand.getCards()[0].isFaceCard() && !hand.getCards()[1].isFaceCard()){
				res[2] = Math.max(hand.getCards()[0].getSuit(), hand.getCards()[1].getSuit());
			}else{
				Card temp = hand.getCards()[0].isFaceCard()? hand.getCards()[1]:hand.getCards()[0];
				res[2] = temp.getSuit();
			}
		}
		return res;
	}


	
	public static int[] calTypeThreeHand(Hand hand) {
		int res[] = new int[4];
		int cat = calTypeThreeCat(hand);
		int flushType = isFlushedHand(hand);
		if(cat == BOMB){
			res[0] = BOMB;
			if(flushType != NONEFLUSHHAND){
				res[1] = FLUSHHAND;
				res[2] = flushType;
				if(hand.getJokerCounts() == 3 || hand.getCards()[2].getRank()==ACE) 
					res[3] = ACEINBOMBORSTRAIGHT;
				else res[3] = hand.getCards()[2].getRank();
			}else{
				res[1] = NONEFLUSHHAND;
				res[2] = hand.getCards()[2].getRank();
			}
			
		}else if(cat == STRAIGHT){
			res[0] = STRAIGHT;
			if(flushType != NONEFLUSHHAND){
				res[1] = FLUSHHAND;
				res[2] = flushType;
				if(hand.getJokerCounts() == 1){
					if(hand.getCards()[1].getRank() - hand.getCards()[2].getRank() == 2)
						res[3] = hand.getCards()[1].getRank();
					else if(hand.getCards()[1].getRank() - hand.getCards()[2].getRank() == 1){
						res[3] = hand.getCards()[1].getRank() + 1;
					}else res[3] = ACEINBOMBORSTRAIGHT;
				}else{
					if(hand.getCards()[0].getRank() == KING && hand.getCards()[2].getRank()==ACE)
						res[3] = ACEINBOMBORSTRAIGHT;
					else res[3] = hand.getCards()[0].getRank();
				}
			}else{
				res[1] = NONEFLUSHHAND;
				if(hand.getJokerCounts() == 1){
					if(hand.getCards()[1].getRank() - hand.getCards()[2].getRank() == 2)
						res[2] = hand.getCards()[1].getRank();
					else if(hand.getCards()[1].getRank() - hand.getCards()[2].getRank() == 1){
						res[2] = hand.getCards()[1].getRank() + 1;
					}else res[2] = ACEINBOMBORSTRAIGHT;
				}else{
					if(hand.getCards()[0].getRank() == KING && hand.getCards()[2].getRank()==ACE)
						res[2] = ACEINBOMBORSTRAIGHT;
					else res[2] = hand.getCards()[0].getRank();
				}
			}
			
		}else if(cat ==  FLUSH){
			res[0] = FLUSH;
			res[1] = flushType;
		}else if(cat == PAIR){
			res[0] = PAIR;
			res[1] = hand.getCards()[1].getRank();
			if(hand.getJokerCounts()==1) {
				res[2] = hand.getCards()[2].getRank();
			}else{
				if(hand.getCards()[1].getRank() == hand.getCards()[0].getRank()){
					res[2] = hand.getCards()[2].getRank();
				}else{
					res[2] = hand.getCards()[0].getRank();
				}
			}
			
		}else{
			res[0] = HIGH_CARD;
			res[1] = hand.getCards()[0].getRank();
			res[2] = hand.getCards()[0].getSuit();
		}
		return res;
	}
	
	private static int isFlushedHand(Hand hand){
		if(hand.getJokerCounts() == 3) return SPADE;
		if(hand.getJokerCounts() == 2) return hand.getCards()[2].getSuit();
		if(hand.getJokerCounts() == 1){
			if(hand.getCards()[1].getSuit() == hand.getCards()[2].getSuit()) 
				return hand.getCards()[2].getSuit();
			else return NONEFLUSHHAND;
		}else{
			if(hand.getCards()[1].getSuit() == hand.getCards()[2].getSuit() &&
					hand.getCards()[1].getSuit() == hand.getCards()[0].getSuit()) 
				return hand.getCards()[0].getSuit();
			else return NONEFLUSHHAND;
		}
	}
	
	private static int calTypeThreeCat(Hand hand){
		if(hand.getJokerCounts() >= 2) return BOMB;
		else if(hand.getJokerCounts() == 1){
			if(hand.getCards()[1].getRank() == hand.getCards()[2].getRank()) return BOMB;
			if((hand.getCards()[1].getRank() - hand.getCards()[2].getRank()) <= 2 ||
				(hand.getCards()[1].getRank()==QUEEN && hand.getCards()[2].getRank()==ACE)||
				(hand.getCards()[1].getRank()==KING && hand.getCards()[2].getRank()==ACE)) return STRAIGHT;
			if(hand.getCards()[1].getSuit() == hand.getCards()[2].getSuit()) return FLUSH;
			return PAIR;
		}else{
			if(hand.getCards()[1].getRank() == hand.getCards()[2].getRank() &&
					hand.getCards()[1].getRank() == hand.getCards()[0].getRank()) return BOMB;
			if((hand.getCards()[0].getRank() - hand.getCards()[1].getRank()) == 1 &&
					(hand.getCards()[1].getRank() - hand.getCards()[2].getRank()) == 1) return STRAIGHT;
			if(hand.getCards()[0].getRank()==KING && hand.getCards()[1].getRank() == QUEEN
					&&hand.getCards()[2].getRank() == ACE) return STRAIGHT;
			if(hand.getCards()[1].getSuit() == hand.getCards()[2].getSuit() &&
					hand.getCards()[1].getSuit() == hand.getCards()[0].getSuit()) return FLUSH;
			if(hand.getCards()[1].getRank() == hand.getCards()[2].getRank() ^
					hand.getCards()[1].getRank() == hand.getCards()[0].getRank()) return PAIR;
			return HIGH_CARD;
		}
	}
	
	
	
}
