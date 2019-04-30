import java.awt.Component;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class LoginFrame {
/**
 * displays login frame
 * takes input from user and determines outcome based on input
 * can open student, department, or instructor frame
 * takes user and password
 * keeps file containing all users, passwords, and what frame they are associated with
 * compares input with file
 * checks if user and password are correct
 * if user and password are correct, determines which frame to open
 * opens designated frame
 * also saves a list of all students in the system, as well as the current user's name
 */
	
		
	 private JFrame frame;
	 
	 OnlineSystem on;
	
	 //stores user name to access files
	 public static String give;
	 //storeslist of all students
	 public static ArrayList<String> theList=new ArrayList<String>();
	 private boolean firstRunThrough=true;
	 
	 /**
	  * methods to enable/disable visibility
	  * used by OnlineSystem
	  */
		public void showScreen(){
			frame.setVisible(true);
		}
		
		public void hideScreen(){
			frame.setVisible(false);
		}
		
		/**
		 * constructor for login frame
		 * takes parameter OnlineSystem b
		 * determines which frame, if any, to open dependent on input
		 * contains labels and test areas for user and pass
		 */
		public LoginFrame(OnlineSystem b){
			on=b;
	
	
	
	JPanel buttons=new JPanel();
	buttons.setLayout(new FlowLayout());
	// panel for labels, text fields, buttons
	//tired with two panels, first was not showing, so one panel
	
JLabel userText=new JLabel("Username:");
	
	final JTextField userField=new JTextField(10);
	//create label and field
	
	buttons.add(userText);
	buttons.add(userField);
	//add label and field to panel
	
	
	//create password with same process
	
	JLabel passText=new JLabel("Password:");
	
	final JTextField passField=new JTextField(10);
	
	buttons.add(passText);
	buttons.add(passField);
	
	//create and label buttons
	JButton login=new JButton("Login");
	JButton cancel=new JButton("Cancel");
	
	buttons.add(login);
	buttons.add(cancel);
	
	
	
	
	
	/**
	 * closes the program and all frames
	 */
	cancel.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	});
	//this closes the frame when cancel is pressed
	
	/**
	 * takes text from the two text areas
	 * checks if the input is equal to the user and pass stored in systemUsers.txt
	 * users are stored with frame to open on the same line
	 * opens designated frame for designated user
	 * saves a list of all students (assumes students are above all others in the list)
	 * saves the name of the user who has loggen in
	 */
	login.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e){
			
			String user=userField.getText();
		//	System.out.println(user);
			String password=passField.getText();
			give=user;
			//static give;used to determine which file to open
			//takes input from text fields
			
			
			
			File Users=new File("systemUsers.txt");
			//file contains list of users and values
			
			
			Scanner scan;
			try {
				scan = new Scanner(Users);
				//scans a file full of user information
				//includes login names, passwords, and frame types
				while (scan.hasNextLine()){
					boolean isUser=false;
					boolean isPass=false;
					String lineSearch=scan.nextLine();
					//takes one line at a time
				//	System.out.println(lineSearch);
					int first=lineSearch.indexOf(',');
					int last= lineSearch.lastIndexOf(',');
					//commas seperate fields in the text file
					//store values of positions of commas
					String userPart=lineSearch.substring(0,first);
				//	System.out.println("user: "+userPart);
					//System.out.println(first);
					String passPart=lineSearch.substring(first+1,last);
				//	System.out.println("pass: "+passPart);
					String type=lineSearch.substring(last+1);
				//	System.out.println("type: "+type);
					
					//these lines take the user, password, and type substrings from a line
					
					//checks to see if the entered username exists in the system
					if(user.equals(userPart)){
						isUser=true;
					//	System.out.println("User correct");
					}
					//checks if password is correct
					if(password.equals(passPart)){
						isPass=true;
						//System.out.println("Pass correct");
					}
					if(type.equals("student")&&firstRunThrough){
						theList.add(userPart);
					}
					//if statements use the type String t odetermine which frame to open
					if(isPass&&isUser){
						if (type.equals("student")){
							//System.out.println("student");
							firstRunThrough=false;
							on.setState(1);
							break;
						}
						if (type.equals("department")){
							//System.out.println("department");
							firstRunThrough=false;
							on.setState(2);
							break;
						}
						if(type.equals("instructor")){
							//System.out.println("instructor");
							firstRunThrough=false;
							on.setState(3);
							break;
						}
						
					}
					
				}
			} catch (FileNotFoundException e1) {
				// 
				e1.printStackTrace();
			}
			catch (StringIndexOutOfBoundsException gb){
				userField.setText("Wrong");
			}
			
		
		}
	});

	
	
		//create the frame
		frame=new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//	add the panel 
		
		frame.add(buttons);
		
	frame.setLocationRelativeTo(null); 
    frame.pack();
    
   // frame.setVisible(true);
	
	
    
} }
	
	

	

