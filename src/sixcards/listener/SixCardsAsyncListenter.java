package sixcards.listener;

import java.io.IOException;
import java.util.List;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

import sixcards.datatype.Player;

public class SixCardsAsyncListenter implements AsyncListener{
	private String ID;
	private List<Player> list;
	
	public SixCardsAsyncListenter(String id, List<Player> playerList){
		this.ID = id;
		this.list = playerList;
	}
	

	@Override
	public void onComplete(AsyncEvent event) throws IOException {
		// TODO Auto-generated method stub
		removeFromList();
		System.out.println("Async Complete");
	}

	@Override
	public void onTimeout(AsyncEvent event) throws IOException {
		// TODO Auto-generated method stub
		removeFromList();
		System.out.println("Async Timeout");
	}

	@Override
	public void onError(AsyncEvent event) throws IOException {
		// TODO Auto-generated method stub
		removeFromList();
		System.out.println("Async Error");
	}

	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Async Start");
	}
	
	private void removeFromList(){
		list.remove(ID);
	}

}
