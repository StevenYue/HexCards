package sixcards.servlet;

import static sixcards.datatype.SixCardsConstant.PLAYERSET;
import static sixcards.datatype.SixCardsConstant.ROOMLIST;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logoff
 */
@WebServlet("/logoff")
public class Logoff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logoff() {
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
		List<String> roomList = (List<String>) brain.getAttribute(ROOMLIST);
		
		String userId = request.getParameter("ID");
		int roomIndex = Integer.parseInt(request.getParameter("ROOMID")) - 1;
		userSet.remove(userId);
		
		if(roomIndex > -1){
			String str = roomList.get(roomIndex);
			if(str.contains(userId)){// means you already in a room
				String ss[] = str.split("-");
				String strToPutBack = "";
				if(ss[0].equals(userId)){
					if(ss.length == 2)
						strToPutBack = ss[1] + "-" ;
				}else{
					strToPutBack = ss[0] + "-" ;
				}
				roomList.set(roomIndex,strToPutBack);
			}
		}
	}

	
}
