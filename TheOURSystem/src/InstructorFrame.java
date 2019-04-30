
import java.awt.Component; 
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InstructorFrame {
	
	/**
	 * displays instructor frame
	 * allows instructor to view classes taught and students in classes
	 * allows for sign out 
	 */
	private JFrame instructorFrame;
	
	private OnlineSystem sys;
	//sys will be used to change state
	
	//holds classes taught
	private ArrayList<String> comp=new ArrayList<String>(); 
	
	
	//methods to change visibility
	public void showScreen(){
		instructorFrame.setVisible(true);
	}

	public void hideScreen(){
		instructorFrame.setVisible(false);
	}
	
	
	
	/**
	 * constructor for instructor
	 * parameter of OnlineSystem b, also uses LoginFrame.give
	 * contains space for classes taught and students in classes
	 */
	
	public InstructorFrame(OnlineSystem s){
		sys=s;
		
		JPanel instructor=new JPanel();
		
		//labels and fields to show course list and student list
		//instructor.add(new JLabel("Departments are identified by the first number;1:CompSci 2:Humanities 3:Math"));
		instructor.add(new JLabel("Courses you are teaching:")); 
		//JTextArea classList=new JTextArea("Computer Science Intro \n Computer Science Intermidiate\n Computer Science Advanced\n");
		final JTextArea classList=new JTextArea(5,10);
		classList.setEditable(false);
		instructor.add(classList);
		instructor.add(new JLabel("Students in your classes:"));
		//JTextArea students=new JTextArea("Joe\nFred\nSally\nMark");
		final JTextArea students=new JTextArea(5,10);
		students.setEditable(false);
		instructor.add(students);
		
		//add buttons
		JButton view=new JButton("View");
		JButton logout=new JButton("Logout");
		instructor.add(view);
		instructor.add(logout);
		
		/**
		 * sets default state
		 */
		logout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				sys.setState(0);
				
				
			}
		});
		
		/**
		 * takes info about classes taught and students in classes
		 * instructor file contains classes they are teaching
		 * copies classes and displays them on frame
		 * opens student files to view their classes
		 * if student contains a class taught by instructor student if added to list
		 * list of students is displayed on frame
		 */
		
		view.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				//gets list of classes taught
//				System.out.println("updating");
				File InstructorName=new File(LoginFrame.give+".txt");
				String showSchedule="";
				try {
				//	System.out.println("Working?");
					Scanner read=new Scanner(InstructorName);
					//System.out.println("?");
					
					while (read.hasNextLine()){
						boolean checkNum;
						//System.out.println("maybe");
						String nextLine=read.nextLine();
						
						//take a line at a time
						//if the line is only a number, take the line
						try{
							Integer.parseInt(nextLine);
							checkNum=true;
							
						}
						catch(NumberFormatException ex){
							checkNum=false;
						}
						
						if(checkNum){
							showSchedule=showSchedule+nextLine+"\n";
						//	System.out.println("Show: "+showSchedule);
							//arraylist of classes instructor teaches
							comp.add(nextLine);
							
						}
					}
				
					read.close();
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				//outside the try
				
				//gets list of students in classes
				
				
				String []studentList=OnlineSystem.intoArray();
				//method gets classes from all students
				String info="";
				for (int k=0;k<studentList.length;k++){
					boolean point=false;
					//creates a file for the current student
					//searches their classes to see if they are taking one from the instructor
					//will loop through all students
					File currentStudent=new File(studentList[k]+".txt");
					//System.out.println("studentname "+studentList[k]+".txt");
					
					try {
						//System.out.println("Working?");
						Scanner readIt=new Scanner(currentStudent);
					//	System.out.println("?");
						while (readIt.hasNextLine()){
							boolean checkThisNum;
						//	System.out.println("maybe");
							String following=readIt.nextLine();
							
							//take a line at a time
							//if the line is only a number, take the line
							try{
								Integer.parseInt(following);
								checkThisNum=true;
							}
							catch(NumberFormatException ex){
								checkThisNum=false;
							}
							if(checkThisNum){
								//if line is a number
						//	System.out.println("line is a number");
								for(String g:comp){
									//compare to list of classes instructor is teaching
									if(following.equals(g)){
										info=info+studentList[k]+"\n";
										//if student is taking a class from instructor, save student name
									//	System.out.println("info updated"+info);
										point=true;
										break;
									}
								//	else{System.out.println("not desired");}
								}
								
							}
							//if student is in class their name has been saved 
							//break loop and check next student
							if(point){
								readIt.close();
							break;
							}
						}
				//extra close in case student is not taking any classes with instructor
						readIt.close();
					
						}
						catch (FileNotFoundException e2) {
							e2.printStackTrace();
							}
					
				}
				//outside of 
				
				//set the text areas with the obtained info
				classList.setText(showSchedule);
				
				students.setText(info);
			}
		});
		
		instructorFrame=new JFrame();
		instructorFrame.setLayout(new BoxLayout(instructorFrame.getContentPane(),BoxLayout.Y_AXIS));
		
		instructorFrame.add(instructor);
		
		
		instructorFrame.setLocationRelativeTo(null); 
		instructorFrame.pack();
		instructorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
