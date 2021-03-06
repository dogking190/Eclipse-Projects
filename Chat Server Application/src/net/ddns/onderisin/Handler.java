package net.ddns.onderisin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

public class Handler extends Thread{
	
	private String name;
	private Socket socket;
	private HashSet<String> clients;
	private HashSet<PrintWriter> writers;
	private BufferedReader in;
	private PrintWriter out;
	
	boolean keepgoing2 = true;

	public Handler(Socket accept, HashSet<String> list, HashSet<PrintWriter> writers) {
		
		this.socket = accept;
		this.clients = list;
		this.writers = writers;
	}
	
	public void run(){
		
		try{
			
			//The Input Goes to the Server 
			//Client > Server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Server > Client
			out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println("Welcome to the Chat Server!");
			System.out.println(socket.toString() + " Has Joined the Chat");
			boolean keepgoing = true;
			
			//Request a username from the client
			/***************************************************/
			while(keepgoing){
				
				//Prompts the CLient to Select a Username
				out.println("Submit a Username!");
				name = in.readLine();
				
				if(name == null){
					return;
				}
				
				synchronized (name){
					if(!clients.contains(name)){
						clients.add(name);
						out.println("Username has been Accepted!");
						writers.add(out);
						keepgoing = false;
						break;
					}
				}
			}
			/***************************************************/
			
			
			
			
			//Broadcasts a Message from one cleint to all the other clients
			
			while(keepgoing2){
				String messages = in.readLine();
				
				
				if(messages == null){
					return;
				}
				
				
				System.out.println(name + ":" + messages);
				
				//Checks to see of the use wants to logout
				if(messages.equalsIgnoreCase("LOGOUT")){
					
					//Checks to make sure the username is in the list and if so remove it
					if(clients.contains(name)){
						synchronized(clients){
							clients.remove(name);
						}
					}
					
					//Checks to make sure the output to each client is closed
					if(writers.contains(out)){
						synchronized(writers){
							writers.remove(out);
						}
					}
					
					keepgoing2 = false;
					
					this.interrupt();
					
				}
				else{
					
				//Send a message to all the clients
				ServerMain.broadcast(name + ":" + messages);
				
				}
				
			}
			
		}
		catch(IOException e){
			
			//Prints the Problem out to the server
			System.out.println(e);
			
		}
		finally{
			
			if(name != null){
				clients.remove(name);
			}
			
			if(out != null){
				writers.remove(out);
			}
			
			
			
			try{
				out.close();
				in.close();
				
				socket.close();
			}
			catch(IOException e){
				
			}
		}
	}

}
