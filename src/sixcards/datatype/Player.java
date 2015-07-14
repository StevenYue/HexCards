package sixcards.datatype;

import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;

/*
 * 06/24/2015
 * client can't receive response when using Asynccontext,
 * try PrintWriter
 */

public class Player {
	private String id;
//	private final AsyncContext async;
	private PrintWriter pw;
	 
	public Player(String id,  PrintWriter pw){
		this.id = id;
		this.pw = pw;
//		this.async = asc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public AsyncContext getAsync() {
//		return async;
//	}
	
	public PrintWriter getPW(){
		return this.pw;
	}
	
	
}
