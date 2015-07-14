package sixcards.servlet;

import static sixcards.datatype.SixCardsConstant.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

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

/**
 * Servlet implementation class GameDateRequest
 */
@WebServlet("/gamedaterequest")
public class GameDateRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GameDateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String gameID = null, requesterID = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		gameID = request.getParameter("GAMEID");
		requesterID = request.getParameter("ID");

		// brain here means the central unit, memorizing everything within this application
		ServletContext brain = request.getServletContext();
		Map<String, Game> gameMap = (Map<String, Game>) brain.getAttribute(GAMEMAP);
		Set<String> userSet = (Set<String>) brain.getAttribute(PLAYERSET);
		boolean isFinalMsg = false;
		
		PrintWriter pw = response.getWriter();
		if(gameMap.containsKey(gameID)){
			Game game = gameMap.get(gameID);
			String s = game.getGameMsg();
			if(s == null) return;
			
			JSONArray jArray = new JSONArray();
			JSONObject jMsg = null;
			try {
				jMsg = new JSONObject(s);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			if(requesterID.equals(game.getPlayer1ID())){ //This is player 1
				if(!game.isP1MsgReceived()) {
					if(s.contains("result")){
						for(Card c:game.getPlayer2Handin().getCards())
							jArray.put(c.toString());
						try {
							jMsg.put(MSG_TYPE_HAND_INFO, jArray);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if(game.getPlayer2Handin().getType() == TYPE_THREE_GAME
								&& game.getCardDeck().isNoMoreCard()){
							try {
								jMsg.put(MSG_TYPE_GAME_END,MSG_GAME_END);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							isFinalMsg = true;
						}
						game.setPlayer2Handin(null);
					}else if(s.contains("died")){
						for(Card c:game.getP2Round().getRound())
								jArray.put(c.toString());
						try {
							jMsg.put(MSG_TYPE_HAND_INFO, jArray);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if(game.getCardDeck().isNoMoreCard()){
							try {
								jMsg.put(MSG_TYPE_GAME_END,MSG_GAME_END);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							isFinalMsg = true;
						}
						game.setP2Round(null);
					}
					
					pw.println(jMsg.toString());
					
				}
				game.setP1MsgReceived(true);
			}else{//This is for player 2
				if(!game.isP2MsgReceived()) {
					if(s.contains("result")){
						for(Card c:game.getPlayer1Handin().getCards())
							jArray.put(c.toString());
						try {
							jMsg.put(MSG_TYPE_HAND_INFO, jArray);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if(game.getPlayer1Handin().getType() == TYPE_THREE_GAME
								&& game.getCardDeck().isNoMoreCard()){
							try {
								jMsg.put(MSG_TYPE_GAME_END,MSG_GAME_END);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							isFinalMsg = true;
						}
						
						game.setPlayer1Handin(null);
					}else if(s.contains("died")){
						for(Card c:game.getP1Round().getRound())
							jArray.put(c.toString());
						try {
							jMsg.put(MSG_TYPE_HAND_INFO, jArray);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
						if(game.getCardDeck().isNoMoreCard()){
							try {
								jMsg.put(MSG_TYPE_GAME_END,MSG_GAME_END);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							isFinalMsg = true;
						}
						game.setP1Round(null);
					}
					pw.println(jMsg.toString());
					
				}
				game.setP2MsgReceived(true);
			}
			System.out.println("jmsg("+requesterID+"):" + jMsg.toString());
			
			if(game.isP1MsgReceived() && game.isP2MsgReceived()){
				gameMap.get(gameID).setGameMsg(null);
				game.setP1MsgReceived(false);
				game.setP2MsgReceived(false);
				if(isFinalMsg){
					gameMap.remove(gameID);
					String ss[] = gameID.split("-");
					String sss[] = new String[ss.length];
					for(int i=0;i<ss.length;i++){
						sss[i] = ss[i] + " (In Game)";
					}
					
					for(int i=0; i<sss.length;i++) {
						if (userSet.contains(sss[i])) {
							userSet.remove(sss[i]);
							userSet.add(ss[i]);
						}
					}
					
					System.out.println("final msg");
				}
			}
				
		}
	}

}
