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
		JFrame frame = new JFrame("Chat Client");
		JTextArea textarea = new JTextArea(10, 20);
		JButton button = new JButton("Send");
		JTextField textfield = new JTextField("",20);
		JScrollPane scroll = new JScrollPane(textarea);
		
		textfield.setHorizontalAlignment(JTextField.HORIZONTAL);
		textarea.setEditable(false);
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.getContentPane().add(scroll, BorderLayout.NORTH);
		frame.getContentPane().add(button, BorderLayout.EAST);
		frame.getContentPane().add(textfield, BorderLayout.WEST);
		textarea.setSize(300, 20);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(400, 230);
		frame.setVisible(true);
	
		
		BufferedReader in;
		PrintWriter out;
		
		Socket s = new Socket("192.168.1.144", 9000);
		
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
		
		new ReadFromServer(s, in, textarea).start();
		
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String line = textfield.getText();
				textfield.setText("");
				
				out.println(line);
				
			}
			
			
		});
		/**
		while(true){
			
			System.out.print("> ");
			Scanner scan = new Scanner(System.in);
			
			out.println(scan.nextLine());
			
		}
		*/
	}

}
