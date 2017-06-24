package net.ddns.onderisin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;

public class ServerMain {
	

	static //This is the Port the Server Listens too
	int port = 9000;

	static //This saves the usernames already in use
	HashSet<String> names = new HashSet<String>();
	
	//The printers of the clients for mass broadcasting
	static HashSet<PrintWriter> writer = new HashSet<PrintWriter>();

	public static void main(String[] args) throws Exception {

		System.out.println("The Chat Server is Running!");
		ServerSocket listener = new ServerSocket(port);
		
		try {
			
			while(true){
				new Handler(listener.accept(), names, writer).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			listener.close();
		}
		
		
		

	}
	
	public synchronized static void broadcast(String message){
		
		for(PrintWriter wrt : writer){
			wrt.println(message);
			wrt.flush();
		}
		
	}

}
