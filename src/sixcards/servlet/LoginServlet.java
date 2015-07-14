package sixcards.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import sixcards.datatype.Player;
import sixcards.listener.SixCardsAsyncListenter;
import sixcards.services.*;
import static sixcards.datatype.SixCardsConstant.*;
/**
 * Servlet implementation class LoginServlet
 */

@WebServlet(urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		String userID = null, password = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//ServletOutputStream out = response.getOutputStream();
		
		userID = request.getParameter("ID");
		password = request.getParameter("password");
		
		//brain here means the central unit, memorizing everything within this application
		ServletContext brain = request.getServletContext();
	
		PrintWriter pw = response.getWriter();
		Set<String> playerSet = (Set<String>) brain.getAttribute(PLAYERSET);
		
		LoginService loginService = LoginService.getLoginService();
		int res = loginService.varify(userID, password, playerSet);
		JSONObject jMSG = new JSONObject();
		try{
			if (res > 0) {
				// perfect match
//				Player player = new Player(userID, pw);
				synchronized (playerSet) {
					playerSet.add(userID);
//					System.out.println("PlayerSet Ref#:" + playerSet);
				}
				jMSG.put(MSG_TYPE_ACTION, MSG_RELOAD_PAGE_ON_SUCCESS);
			} else if (res == -1) {
				// password is wrong
				jMSG.put(MSG_TYPE_ACTION, MSG_PASSWORDINCORRECT);
			} else if (res == -2) {
				// id existed
				jMSG.put(MSG_TYPE_ACTION, MSG_USERNAMEUSED);
			} else {
				// no such user
				jMSG.put(MSG_TYPE_ACTION, MSG_NOSUCHUSER);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		pw.println(jMSG.toString());
		
		//Now update the online user info in the playerMsgMap.
	}
}
