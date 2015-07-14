package sixcards.datatype;

public class SixCardsConstant {
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 1;
	public static final int ACEINBOMBORSTRAIGHT = 14;
	public static final int BIGJOKER = 99;
//	public static final int LITTLEJOKER = 99;
	
	public static final char SPADE = 's';
	public static final char HEART = 'h';
	public static final char CLUB = 'e';
	public static final char DIAMOND = 'd';
	public static final char JOKER = 'j';
	
	
	public static final int TYPE_ONE_GAME = 1;
	public static final int TYPE_TWO_GAME = 2;
	public static final int TYPE_THREE_GAME = 3;
	
	public static final int TEN_HALF = 105;
	
	public static final int BOMB = 5;
	public static final int STRAIGHT = 4;
	public static final int FLUSH = 3;
	public static final int PAIR = 2;
	public static final int HIGH_CARD = 1;
	
	public static final int FLUSHHAND = 1;
	public static final int NONEFLUSHHAND = 0;
	
	
	/*
	 * Servlet Variables
	 */
	public static final String PLAYERSET = "playerset";
	public static final String PLAYERMSGMAP = "playermsgmap";
	public static final String GAMEMAP = "gamemap";
	public static final String ROOMLIST = "roomlist";
	
	
	//alert Msgs for login page
	public static final String MSG_NOSUCHUSER = "No Such User!!!";
	public static final String MSG_PASSWORDINCORRECT = "Password Incorrect!!!";
	public static final String MSG_USERNAMEUSED = "User Name existed!!!";
	public static final String MSG_RELOAD_PAGE_ON_SUCCESS = "reload page on success";
	
	//Msgs for gameplay page
	public static final String MSG_GAME_START = "gamestart";
	public static final String MSG_GAME_END = "gameend";
	public static final String MSG_ROUND_DIED = "rounddied";
	public static final String MSG_GAME_TWO_DIED = "typetwodied";
	public static final String MSG_GAME_RESULT = "gameresult";
	
	//JSON msg type
	public static final String MSG_TYPE_ACTION = "action";
	public static final String MSG_TYPE_GAME_DATA = "gamedata";
	public static final String MSG_TYPE_HAND_INFO = "handinfo";
	public static final String MSG_TYPE_GAME_END = "gameend";
	public static final String MSG_TYPE_DEAD_ROUND = "deadround";
	public static final String MSG_TYPE_NEW_USER_BROADCAST = "newuserbroadcast";
	public static final String MSG_TYPE_ROOM_LIST_BROADCAST = "roomlistbroadcast";
}
