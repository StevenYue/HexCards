package sixcards.utility;

public class SixCardsExceptions {
	public static class GameTypeMisMatchException extends Exception{
		public GameTypeMisMatchException(){
			super("Game Type Mismatch Exception");
		}
	}
	
	public static class TypeTwoGameDiedException extends Exception{
		public TypeTwoGameDiedException(){
			super("Type Two Game Died Exception");
		}
	}
	
	public static class RoundDiedException extends Exception{
		public RoundDiedException(){
			super("Round Died Exception");
		}
	}
}
