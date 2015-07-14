package sixcards.servlet;

import static sixcards.datatype.SixCardsConstant.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sixcards.datatype.Game;

/**
 * Servlet implementation class EnterRoomServlet
 */
@WebServlet("/enterroom")
public class EnterRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnterRoomServlet() {
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
		String yourID = null;
		int roomIndex = 0;
		roomIndex = Integer.parseInt(request.getParameter("ROOMID")) - 1;
		yourID = request.getParameter("ID");
		
		//brain here means the central unit, memorizing everything within this application
		ServletContext brain = request.getServletContext();
		List<String> roomList = (List<String>) brain.getAttribute(ROOMLIST);
		Map<String,Game> gameMap= (Map<String, Game>) brain.getAttribute(GAMEMAP);
		
		for(int i=0;i<roomList.size();i++){
			String str = roomList.get(i);
			if(str.contains(yourID)){// means you already in a room
				String ss[] = str.split("-");
				String strToPutBack = "";
				if(ss[0].equals(yourID)){
					
					//there has been 2 players in this room, if the game has been
					//created, now it's time to remove it from the map
					if(ss.length == 2){
						strToPutBack = ss[1] + "-" ;
						Arrays.sort(ss);
						String gameId = ss[1] + "-" + ss[0];
						if(gameMap.containsKey(gameId)){
							gameMap.remove(gameId);
						}
					}
						
				}else{
					strToPutBack = ss[0] + "-" ;
				}
				System.out.println("1111"+strToPutBack + "1111");
				roomList.set(i,strToPutBack);
				if(i == roomIndex) return;
				else break;
			}
		}
		
		String str = roomList.get(roomIndex);
		String ss[] = str.split("-");
		if(ss.length == 1){
			if(ss[0] == ""){
				roomList.set(roomIndex, yourID+"-");
			}else{
				roomList.set(roomIndex, roomList.get(roomIndex)+yourID);
			}
		}
		
	}

}
