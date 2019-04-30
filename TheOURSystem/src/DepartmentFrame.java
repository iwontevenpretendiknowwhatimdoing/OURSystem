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
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class DepartmentFrame {

	/**
	 * Displays a department frame
	 * allows department to view all students as well as those with unpaid fees
	 * allows for sign out and catalog viewing
	 */
	
		private JFrame departmentFrame;
		
		private OnlineSystem sy;
		//sy used to change state
		
		//keeps track of state when opening catalog
		public static boolean wasDepartment=false;
		
		private ArrayList<String> unpaid=new ArrayList<String>();
		
		private boolean firstRun=true;
		
		/**
		 * methods to set visibility
		 * used by OnlineSystem
		 */
		
		public void showScreen(){
			departmentFrame.setVisible(true);
		}
		public void hideScreen(){
			departmentFrame.setVisible(false);
		}
		
		/**
		 * constructor for department frame
		 * takes parameter of OnlineSystem b, also uses static String LoginFrame.give
		 * allows viewing of all students and students with unpaid fees
		 * 
		 */
		public DepartmentFrame(OnlineSystem b){
			
			sy=b;
			
			//strange issue, only loads text on one panel
		
		JLabel registeredStudents=new JLabel("These students are registered:");
		final JTextArea enrolledStudents=new JTextArea(6,10);

		
	//	final JTextArea enrolledStudents=new JTextArea(useThis);
	
		//shows students
		enrolledStudents.setEditable(false);
		JLabel feesInfoLabel=new JLabel("Following students have unpaid fees:");
		final JTextArea feesPaidInfo=new JTextArea(3,10);
			
		//shows fees info
		feesPaidInfo.setEditable(false);
		
		
		JLabel warning=new JLabel("Input here will edit the catalog; Enter CRN,Title");
		final JTextField modify=new JTextField(10);
		final JTextArea addDescription=new JTextArea(3,10);
		JLabel aboutIt=new JLabel("Description for new class;-for each line");
		JPanel departmentButtons=new JPanel();
		departmentButtons.setLayout(new FlowLayout());
		
		
		//departmentButtons.add(catalogLabel);
		//departmentButtons.add(catalogList);
		departmentButtons.add(registeredStudents);
		departmentButtons.add(enrolledStudents);
		departmentButtons.add(feesInfoLabel);
		departmentButtons.add(feesPaidInfo);
		departmentButtons.add(warning);
		departmentButtons.add(modify);
		departmentButtons.add(aboutIt);
		departmentButtons.add(addDescription);
		
		
		JButton show=new JButton("Show");
		JButton viewDep=new JButton("View Catalog");
		JButton logoutDep=new JButton("Logout");
		JButton change=new JButton("Edit Catalog");
		//JButton edit=new JButton("Edit Descriptions");
		
		departmentButtons.add(show);
		departmentButtons.add(viewDep);
		departmentButtons.add(change);
		departmentButtons.add(logoutDep);
		
		
		
		/**
		 * sets default state
		 */
		logoutDep.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				sy.setState(0);
				
				
			}
		});
		
		/**
		 * sets state, shows department and catalog frames at once
		 */
		viewDep.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				//shows to revert back to department if closed
				wasDepartment=true;
				sy.setState(5);
				
				
			}
		});
		
		/**
		 * displays student list and unpaid list
		 * uses LoginFrame.theList to copy all students in the system
		 * opens up all student files to check fees info
		 * fees info is stored in each student's personal file
		 * if unpaid info is found it is displayed
		 */
		
		show.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
			//displays all students in the panel
				if(firstRun){
				String useThis="";
				for(String h:LoginFrame.theList){
					useThis=useThis+h+"\n";
				//	System.out.println(h);
					//System.out.println(useThis);
				}
				enrolledStudents.setText(useThis);
				
			}
				//display students with unpaid fees
				
				String [] allStudents=OnlineSystem.intoArray();
				//method that places all students into an array
				for (int f=0;f<allStudents.length;f++){
					//start here,scan all student files for paid info
				
				
					//creates a file for the current student
					//searches their classes to see if they are taking one from the instructor
					//will loop through all students
					File currentStudent=new File(allStudents[f]+".txt");
					
					try {
						//System.out.println("Working?");
						Scanner readIt=new Scanner(currentStudent);
					//	System.out.println("?");
					
						//	System.out.println("maybe");
							String following=readIt.nextLine();
							//will be indicated on second line
							following=readIt.nextLine();
							//check to make sure it is the correct line
							//student files have an indicator in position (5)
							//ensure no exception is caused
							try{
								int showsPaid =Integer.parseInt(following.substring(5));
								if(showsPaid==0){
									unpaid.add(allStudents[f]);
								}
								
							}
							catch(NumberFormatException ex){
								ex.printStackTrace();
							}
							
								//if line is a number
						//	System.out.println("line is a number");
								
								
							
						
						
						readIt.close();
					
						}
						catch (FileNotFoundException e2) {
							e2.printStackTrace();
							}
				
			}
//copies unpaid students into the feesPaidInfo area on the frame
				if(firstRun){
				String displayFees="";
				for(String v:unpaid){
					
					displayFees=displayFees+v;
				}
				feesPaidInfo.setText(displayFees);
				firstRun=false;
			
				}
				}
		});
		
		/**
		 * allows for editing of the catalog
		 * takes input from panel
		 * reads file courseCatalog.txt
		 * compares input to file
		 * determines to add or remove class
		 */
		change.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				
				File catalog=new File("courseCatalog.txt");
				

				String input=modify.getText();
				boolean flag=false;
				try{
					Scanner in=new Scanner(catalog);
					while(in.hasNextLine()){
						//search file for the class
						String toBeChecked=in.nextLine();
						//System.out.println(toBeChecked);
						//System.out.println(input);
						
						if(toBeChecked.equals(input)){
							//System.out.println("worked");
							//if class exists, remove it
							
							File tempfile=new File(catalog.getAbsolutePath()+".tmp");
							//create a file and a temp file
							//reads from file, copies all into temp except piece to be removed
							
								BufferedReader read=new BufferedReader(new FileReader(catalog));
								//read file
								PrintWriter write=new PrintWriter(new FileWriter(tempfile));
								//write to temp file
								String line=null;
								while ((line= read.readLine()) !=null){
									//read to end of file
									if (!line.trim().equals(input)){
										//rewrite all of file but input line
										write.println(line);
										write.flush();
									}
								}
								read.close();
								write.close();
								//close read/write
								//tempfile is now original but without input
								catalog.delete();
								tempfile.renameTo(catalog);
								
								//this removes descriptions from the file using the same process
								File work=new File("Descriptions.txt");
								File temp=new File(work.getAbsolutePath() +".tmp");
								
								BufferedReader readDes=new BufferedReader(new FileReader(work));
								//read file
								PrintWriter writeDes=new PrintWriter(new FileWriter(temp));
								//write to temp file
								String numInput=input.substring(0,input.indexOf(','));
								//check for CRN
								boolean found=false;
								int keep=3;
								while ((line= readDes.readLine()) !=null){
									//read to end of file
									if (line.trim().equals(numInput)){
										found=true;
										//class is found in descriptions
									}
									else if(found&&keep>0){
										//skip over description lines
										keep--;
									}
										
									else{
											//copy all but CRN line and 2 description lines
											writeDes.println(line);
											writeDes.flush();
										}
								}
								readDes.close();
								writeDes.close();
								
								//create text file without descriptions
								work.delete();
								temp.renameTo(work);
								
								
								
								flag=true;
								
								
								
						
						break;
						}
						
					}
				in.close();
				//if class is being added
					
				//set off if there is a duplicate
				if(!flag){
					//adds a single line at the end of the file
					PrintWriter out= new PrintWriter(new BufferedWriter(new FileWriter(catalog,true)));
					//theInput is added at the file's end
					out.println(input);
					out.close();
					
					//this adds new descriptions
				String newDescription=addDescription.getText();
				File fileDescribe=new File("Descriptions.txt");
				
				PrintWriter goOut= new PrintWriter(new BufferedWriter(new FileWriter(fileDescribe,true)));
				//theInput is added at the file's end
				goOut.println();
				goOut.println(input.substring(0,input.indexOf(',')));
				goOut.println(newDescription);
				//adds number because of strange bug when reading descriptions
				goOut.println("999");
				goOut.close();
				
				}
				}
				catch(FileNotFoundException g){
					g.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		
		
		departmentFrame=new JFrame();
		departmentFrame.setLayout(new BoxLayout(departmentFrame.getContentPane(),BoxLayout.Y_AXIS));
		
		
	//	departmentFrame.add(department);
		departmentFrame.add(departmentButtons);
		
		
		
		departmentFrame.setLocationRelativeTo(null); 
		departmentFrame.pack();
		departmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//departmentFrame.setVisible(true);
		
	}
}
