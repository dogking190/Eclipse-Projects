package net.ddns.onderisin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;

public class ReadFromServer extends Thread{
	
	BufferedReader in;
	Socket s;
	PrintWriter out;
	JTextArea ta = new JTextArea();
	
	public ReadFromServer(Socket s, BufferedReader in, JTextArea textarea) {
		
		this.in = in;
		this.s = s;
		this.ta = textarea;
	}

	//Method that runs when start() is called
	public void run(){
		
		//This loops and looks for server response
		while(true){
			try {
				
				//Appends the server response to the textarea
				ta.append(in.readLine() + "\n");
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
