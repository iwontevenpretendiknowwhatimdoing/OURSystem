
import java.awt.Component; 
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class StudentFrame {

	/**
	 * Displays a student frame
	 * allows student to view and edit schedule
	 * conditions hasPaid and numOfClasses are checked to see if student is able to register
	 * allows for viewing of the catalog
	 * allows for sign out to login 
	 */
	private JFrame studentFrame;
	private OnlineSystem sys;
	private int numOfClasses=0;
	private boolean firstRun=true;
	private ArrayList <String> classes=new ArrayList<String>();

	
	/**
	 * methods to hide and display the frame are used by OnlineSystem
	 */
	public void showScreen(){
		studentFrame.setVisible(true);
	}
	public void hideScreen(){
		studentFrame.setVisible(false);
	}
	
	/**
	 * checks student file for fee indicator,0 or 1
	 * all student files keep fee indicator in same specific position
	 * returns true if 1, false if 0
	 * 
	 */
	public boolean hasPaid(){
		//checks if fees are paid
		boolean checker=false;
		try {
			File currentStudent=new File(LoginFrame.give+".txt");
			//System.out.println("Working?");
			Scanner readIt=new Scanner(currentStudent);
		
		
			//	System.out.println("maybe");
				String following=readIt.nextLine();
				//will be indicated on second line
				following=readIt.nextLine();
				//System.out.println(following);
				//check to make sure it is the correct line
				//student files have an indicator in position (5)
				//ensure no exception is caused
				try{
					int showsPaid =Integer.parseInt(following.substring(5));
				//	System.out.println("showsPaid:"+showsPaid);
					if(showsPaid==1){
						///System.out.println("showsPaid is 1");
						checker=true;
					//System.out.println("checker is true");
					}
					
				}
				//only catches if file is not formatted correctly
				catch(NumberFormatException ex){
					ex.printStackTrace();
					
				}
				
			readIt.close();
		
			}
			catch (FileNotFoundException e2) {
				e2.printStackTrace();
				}
		if(checker){
			//there is a 1
		return true;
		}
		else{
			//there is a 0
			return false;
		}
	}
	
	/**
	 * constructor for student frames
	 * takes parameter of OnlineSystem b, also uses static String LoginFrame.give
	 * allows manipulation of student file and updating of the text areas
	 */
	public StudentFrame(OnlineSystem b){
		sys=b;
		
	
			
			//holds text areas, labels,buttons
			JPanel studentButtons=new JPanel();
			studentButtons.setLayout(new BoxLayout(studentButtons, BoxLayout.Y_AXIS));
			JButton logout=new JButton("Logout");
		//	JButton save=new JButton("Save");
			JButton add=new JButton("Add");
			JButton drop=new JButton("Drop");
			JButton print=new JButton("View Catalog");
			JButton update=new JButton("Update");
		
			//shows current schedule
			JLabel scheduleLabel=new JLabel("Your Schedule:");
			final JTextArea schedule=new JTextArea(6,15);
			schedule.setEditable(false);
			
			
			//allows for input
			JPanel inputPanel=new JPanel();
			JLabel enter=new JLabel("Here you can enter a class you would like to add or drop,\n Use the CRNs from the catalog");
			final JTextField input=new JTextField(20);
			inputPanel.add(enter);
			inputPanel.add(input);
			
			
			
			//add everything onto the panel
			
			studentButtons.add(scheduleLabel);
			studentButtons.add(schedule);
			
			studentButtons.add(inputPanel);
			
			studentButtons.add(logout);
			//studentButtons.add(save);
			studentButtons.add(add);
			studentButtons.add(drop);
			studentButtons.add(print);
			studentButtons.add(update);
			
			/**
			 * hides current frame and opens loginFrame
			 * 
			 */
			logout.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					sys.setState(0);
					
					
				}
			});
			
			/**
			 * checks numOfClasses, hasPaid to see if conditions are met 
			 * also checks ArrayLists ALLCLASSES and classes for conditions
			 * adds a class number to the end of the student file
			 * if conditions are not true, display error
			 */
			
			add.addActionListener(new ActionListener()
			{
				
				public void actionPerformed(ActionEvent e){
				
					//theInput is run through tests for eligibility
					String theInput=input.getText();
					
					//checks if class with theInput's CRN exists
					boolean exists=false;
					for(String k:CatalogFrame.ALLCLASSES){
						if(k.equals(theInput)){
							exists=true;
						}
					}
					
					//checks if classes being taken are not too high
					if(numOfClasses>=5){
						input.setText("Max of 5 classes");
						//do nothing, limit of 5 classes
					}
					
					else{
						//checks if the class is already in the schedule
						boolean notRepeat=true;
						String takeItOut="";
						for(String y:classes){
							if(y.equals(theInput)){
								notRepeat=false;
								takeItOut=y;
								//System.out.println("take "+takeItOut);
							}
							//System.out.println(y);
						}
						//System.out.println("cycle");
						
						//System.out.println(notRepeat);
						if(hasPaid()&&notRepeat&&exists){
						
					//String theInput=input.getText();
					File studentName=new File(LoginFrame.give+".txt");
					try{
					
						//adds a single line at the end of the file
						PrintWriter out= new PrintWriter(new BufferedWriter(new FileWriter(studentName,true)));
						//theInput is added at the file's end
						out.println(theInput+"\n");
						out.close();
						//increment numOfClasses
						numOfClasses++;
						classes.add(theInput);
						
					}	
					
					//exceptions are caught if a problem occurs with the file
					catch (FileNotFoundException e1) {
						// 
						e1.printStackTrace();
					}  catch (IOException e1) {	
						e1.printStackTrace();
					}
				
					
					}
						else if(!hasPaid()){
							input.setText("Pay your fees");
						}
						else if(!exists){
							input.setText("Wrong number");
						}
						else{
							input.setText("Only once");
							classes.remove(takeItOut);
						}
				}
					
				}
			});
			
			/**
			 * checks numOfClasses for minimum value before acting
			 * clones student file and copies everything but the CRN being dropped
			 * cloned file replaces original
			 */
			//removes a class number from the student file
			drop.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					
					if(numOfClasses<=3){
						input.setText("Minimum classes is 3");
						//do nothing, classes must be at least 3
					}
					else{
					String checkInput=input.getText();
					//tekes input from user
					File studentName=new File(LoginFrame.give+".txt");
					File tempfile=new File(studentName.getAbsolutePath()+".tmp");
					//create a file and a temp file
					//reads from file, copies all into temp except piece to be removed
					try{
						BufferedReader in=new BufferedReader(new FileReader(studentName));
						//read file
						PrintWriter write=new PrintWriter(new FileWriter(tempfile));
						//write to temp file
						String line=null;
						while ((line= in.readLine()) !=null){
							//read to end of file
							if (!line.trim().equals(checkInput)){
								//rewrite all of file but input line
								write.println(line);
								write.flush();
							}
						}
						in.close();
						write.close();
						//close read/write
						//tempfile is now original but without input
						studentName.delete();
						tempfile.renameTo(studentName);
						//tempfile overwrites original
						
						//increment numOfClasses
						numOfClasses--;
						classes.remove(checkInput);
					}
					
                      catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}
			}});
			
			/**
			 * changes state and opens catalogFrame
			 */
			
			print.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					sys.setState(4);
					
					
				}
			});
			
			
/**
 * reads student file and copies CRNs
 * displays CRNs in the schedule visible on the frame
 */
			update.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
				//	System.out.println("updating");
					File studentName=new File(LoginFrame.give+".txt");
					String showSchedule="";
					try {
					//	System.out.println("Working?");
						Scanner read=new Scanner(studentName);
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
								//add the CRN to the schedule
								//classes.add(nextLine);
								showSchedule=showSchedule+nextLine+"\n";
							//	System.out.println("Show: "+showSchedule);
								//this gets the num of initial classes only
								if(firstRun){
									classes.add(nextLine);
								numOfClasses++;
								//System.out.println("num: "+numOfClasses);
								}
							}
						}
					
						read.close();
						firstRun=false;
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
					schedule.setText(showSchedule);

					//System.out.println(hasPaid());
					
					
					
				}
			});
			
			
			studentFrame=new JFrame();
			
			
			
		/*	studentFrame.add(available);
			studentFrame.add(scheduleLabel);
			studentFrame.add(schedule);
			
			studentFrame.add(inputPanel);
			*/
			studentFrame.add(studentButtons);
			
			
			studentFrame.setLocationRelativeTo(null); 
			studentFrame.pack();
			studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//	studentFrame.setVisible(true);
	}

}
