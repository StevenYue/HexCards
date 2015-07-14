package sixcards.servlet;

import static sixcards.datatype.SixCardsConstant.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

/**
 * Servlet implementation class BroadCastRoomList
 */
@WebServlet("/broadcastroomlist")
public class BroadCastRoomList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BroadCastRoomList() {
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
		List<String> roomList = (List<String>) brain.getAttribute(ROOMLIST);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jMsg = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		for(String s:roomList){
			jArray.put(s);
		}
			
		
		try {
			jMsg.put(MSG_TYPE_ROOM_LIST_BROADCAST, jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		PrintWriter pw = response.getWriter();
//		System.out.println(jMsg.toString());
		pw.println(jMsg.toString());
	}

}
