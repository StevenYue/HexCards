package sixcards.servlet;

import static sixcards.datatype.SixCardsConstant.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sixcards.datatype.Card;
import sixcards.datatype.Game;
import sixcards.datatype.Hand;
import sixcards.datatype.Round;
import sixcards.utility.SixCardsExceptions.TypeTwoGameDiedException;

/**
 * Servlet implementation class HandSubmittedServlet
 */
@WebServlet("/handsubmitted")
public class HandSubmittedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandSubmittedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String handInfo, gameID, yourID;
		handInfo = request.getParameter("HANDINFO");
		yourID = request.getParameter("ID");
		gameID = request.getParameter("GAMEID");
		
		PrintWriter pw = response.getWriter();
//		System.out.println(handInfo);
//		System.out.println(yourID);
//		System.out.println(gameID);
		
		//Create a hand based on handInfo
		String ss[] = handInfo.split("-");
		Card[] cards = new Card[ss.length];
		for(int i=0;i<ss.length;i++){
			char suit = ss[i].charAt(ss[i].length()-1);
			String tempS = ss[i].replaceAll("\\D+","");
			int rank = Integer.parseInt(tempS);
			cards[i] = new Card(rank, suit);
		}
		
		
		Hand hand = null;
		try {
			hand = new Hand(cards);
		} catch (TypeTwoGameDiedException e) {
			//Can't handin this type two game
			JSONObject jMsg = new JSONObject();
			try {
				jMsg.put(MSG_TYPE_GAME_DATA, MSG_GAME_TWO_DIED);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			pw.println(jMsg.toString());
			return;
		}
		
		ServletContext brain = request.getServletContext();
		Map<String, Game> gameMap = (Map<String, Game>) brain.getAttribute(GAMEMAP);
		Game game = gameMap.get(gameID);
		int res = 27; // the designed numbers are -1,0,1
		JSONObject jMsg = new JSONObject();
		if(cards.length == 1){//This is a type one game
			Round round = null;
			if(game.getPlayer1ID().equals(yourID)){//This is player 1
				round = game.getP1Round();
			}else{
				round = game.getP2Round();
			}
			for(Card c:round.getImpossibleTypeOneGameSelection()){
				if(c.getRank() == cards[0].getRank() && c.getSuit() == cards[0].getSuit()){
					try {
						jMsg.put(MSG_TYPE_GAME_DATA, MSG_GAME_TWO_DIED);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					pw.println(jMsg.toString());
					return;
				}
			}
		}
		
		
		
		if(yourID.equals(game.getPlayer1ID())){//This is player 1
			game.setPlayer1Handin(hand);
			if(game.getPlayer2Handin() != null){
				res = game.getPlayer2Handin().compareTo(hand);
	
			}
			
		}else{//This is player 2
			game.setPlayer2Handin(hand);
			if(game.getPlayer1Handin() != null){
				res = game.getPlayer2Handin().compareTo(game.getPlayer1Handin());// make sure always player2 compare to player1
			
			}
			
		}

		if(res != 27){
			try {
				if(res == 0){//tie
					jMsg.put(MSG_TYPE_GAME_DATA,MSG_GAME_RESULT + "-0");
				}else if(res == -1){//player 1 win
					jMsg.put(MSG_TYPE_GAME_DATA,MSG_GAME_RESULT + "-" + game.getPlayer1ID());
				}else if(res == 1){//player 2 win
					jMsg.put(MSG_TYPE_GAME_DATA,MSG_GAME_RESULT + "-" + game.getPlayer2ID());
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			game.setGameMsg(jMsg.toString());
		}
	}

}
