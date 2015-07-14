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
import sixcards.datatype.Round;
import sixcards.utility.SixCardsExceptions.RoundDiedException;

/**
 * Servlet implementation class CardsRequested
 */
@WebServlet("/cardsrequested")
public class CardsRequested extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CardsRequested() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String gameID = null, requesterID = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		gameID = request.getParameter("GAMEID");
		requesterID = request.getParameter("ID");

		// brain here means the central unit, memorizing everything within this application
		ServletContext brain = request.getServletContext();
		Map<String, Game> gameMap = (Map<String, Game>) brain.getAttribute(GAMEMAP);
		Game game = gameMap.get(gameID);
		
		Round round = null;
		PrintWriter pw = response.getWriter();
		
		JSONObject jMsg = new JSONObject();
		JSONArray jArray = new JSONArray();
		boolean bothHandIn = false;

		round = game.getCardDeck().getARound();
		
		if (requesterID.equals(game.getPlayer1ID())) {// This is player1
			game.setP1Round(round);
			if (game.getP2Round() != null)
				bothHandIn = true;
		} else { // This is player2
			game.setP2Round(round);
			if (game.getP1Round() != null)
				bothHandIn = true;
		}
		
		
		if(round.isDead()){
			//Died Round
			try {
				jMsg.put(MSG_TYPE_DEAD_ROUND, MSG_ROUND_DIED);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if(bothHandIn){
				JSONObject json = new JSONObject();
				try {
					String whoDead = "died";
					if(game.getP1Round().isDead()) whoDead = whoDead+"-"+game.getPlayer1ID();
					if(game.getP2Round().isDead()) whoDead = whoDead+"-"+game.getPlayer2ID();
					json.put(MSG_TYPE_GAME_DATA, whoDead);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.setGameMsg(json.toString());
			}
		}

		try{
			for(Card c:round.getRound())
				jArray.put(c.toString());
			jMsg.put(MSG_TYPE_GAME_DATA, jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pw.println(jMsg.toString());
	}

}
