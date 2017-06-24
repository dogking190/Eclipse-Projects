package net.ddns.onderisin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatMain extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8454611400720786614L;

	public static void main(String[] args) throws UnknownHostException, IOException{
		
		//Creating a GUI
		JFrame frame = new JFrame("Chat Client");		//Creates a New frame to house all the parts
		JTextArea textarea = new JTextArea(10, 20);		//Creates a TextArea field for server responses
		JButton button = new JButton("Send");			//Creates a button to send the clients message
		JTextField textfield = new JTextField("",20);	//Creates a textfield for client to write their message
		JScrollPane scroll = new JScrollPane(textarea);	//Creates a scrolls panel for the textarea so they can scroll and view messages
		
		//Sets the TextArea editable false so the player can not edit it
		textarea.setEditable(false);
		
		//Sets the default close operation & adds the parts to the jframe
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.getContentPane().add(scroll, BorderLayout.NORTH);
		frame.getContentPane().add(button, BorderLayout.EAST);
		frame.getContentPane().add(textfield, BorderLayout.WEST);
		
		//Sets the Default size of the textarea
		textarea.setSize(300, 20);
		
		//Packs all the parts into the jframe
		frame.pack();
		
		//When the frame popups it will locate to the middle of the screen
		frame.setLocationRelativeTo(null);
		
		//Sets the default size of the Chat Box
		frame.setSize(400, 230);
		
		//Makes the Chat Box visuable
		frame.setVisible(true);
	
		//This connects to the server and prompts server address dialog
		Socket s = new Socket(getServerAddress(), 9000);
		
		//Initilize the BufferedReader and the PrintWriter
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		
		//Creates a new Thread for the client - This is where the loop for reading server input sits
		new ReadFromServer(s, in, textarea).start();
		
		//This ActionListener, listens for the button click and send the message to the server
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Sets 'line' equal to the input of the textfield
				String line = textfield.getText();
				
				//Sets the textfield to blank
				textfield.setText("");
				
				//Send the response to the server
				out.println(line);
				
			}
			
			
		});
	}

	//a pop up dialog bo asking for the server's address
	private static String getServerAddress() {
		
		return JOptionPane.showInputDialog("What is the Server Address?");
	}

}
