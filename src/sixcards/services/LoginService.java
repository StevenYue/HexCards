package sixcards.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sixcards.datatype.Player;


public class LoginService{
	private static LoginService ls = null;
	private HashMap<String, String> map;
	
	private LoginService(){
			map = new HashMap<String, String>();
			map.put("Steve", "qq327");
			map.put("Flora", "yx1215");
			for(int i=1;i<7;i++)
				map.put("Tester"+i, "Tester"+i);
			
//			map.put("Juve", "best");
//			map.put("Milan", "good");
	}
	
	public static LoginService getLoginService(){
		if(ls == null){
			ls = new LoginService();
		}
		return ls;
	}
	
	/**
	 * 0  == no such user
	 * 1  == perfect match
	 * -1 == password is wrong
	 * -2 == userID existed
	 * @param id
	 * @param password
	 * @return
	 */
	public int varify(String id, String password, Set<String> playerSet){
		if(map.containsKey(id)){
			if(map.get(id).equals(password)) {
				if(playerSet.contains(id)) return -2;
				else return 1;
			}
			else return -1;
		}else return 0;
	}

}
 