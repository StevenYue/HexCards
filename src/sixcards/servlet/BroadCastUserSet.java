package sixcards.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import static sixcards.datatype.SixCardsConstant.*;

/**
 * Servlet implementation class BroadCastUserSet
 */
@WebServlet(urlPatterns={"/broadcastuserset"})
public class BroadCastUserSet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BroadCastUserSet() {
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
		
		ServletContext brain = request.getServletContext();
		Set<String> userSet = (Set<String>) brain.getAttribute(PLAYERSET);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jMsg = new JSONObject();
		String userString = "";
		
		for (String s : userSet) {
			userString += s + "-";
		}
		
		
		//System.out.println("data pulling:" + userString);
		try {
			jMsg.put(MSG_TYPE_NEW_USER_BROADCAST, userString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		PrintWriter pw = response.getWriter();
		pw.println(jMsg.toString());
	}

}
