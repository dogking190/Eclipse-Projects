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

	public void run(){
		while(true){
			try {
				
				ta.append(in.readLine() + "\n");
					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
