package sixcards.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import sixcards.datatype.Game;
import static sixcards.datatype.SixCardsConstant.*;
/**
 * Servlet implementation class GameStartRequest
 */
@WebServlet("/gamestartrequest")
public class GameStartRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameStartRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String  gameID = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		gameID = request.getParameter("GAMEID");
		
		//brain here means the central unit, memorizing everything within this application
		ServletContext brain = request.getServletContext();
		Map<String,Game> gameMap= (Map<String, Game>) brain.getAttribute(GAMEMAP);
		Set<String> userSet = (Set<String>) brain.getAttribute(PLAYERSET);
		if(gameMap.containsKey(gameID)){
			JSONObject jMsg = new JSONObject();
			try {
				jMsg.put(MSG_TYPE_GAME_DATA, MSG_GAME_START);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			gameMap.get(gameID).setGameMsg(jMsg.toString());
			
			String ss[] = gameID.split("-");
			
			for (String s : ss) {
				if (userSet.contains(s)) {
					userSet.remove(s);
					userSet.add(s + " (In Game)");
				}
			}
			
			
			
		}else{
			Game game = new Game(gameID);
			gameMap.put(gameID, game);
		}
	}

}
