import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class CatalogFrame {
	
	/**
	 * displays the catalog frame
	 * displays all courses available
	 * allows closing of catalog and return to previous panel
	 */
 private JFrame frame;
 public static ArrayList<String> ALLCLASSES=new ArrayList<String>();
	 
	 OnlineSystem on;
	
	 //methods to enable/disable visibility
		public void showScreen(){
			frame.setVisible(true);
			
		}
		
		public void hideScreen(){
			frame.setVisible(false);
		}
		
		
		/**
		 * constructor for catalog frame
		 * takes parameter of OnlineSystem sys
		 * shows all courses in the system
		 * uses courseCatalog.txt to list all courses
		 * copies all titles and CRNs and displays in panel
		 */
		public CatalogFrame(final OnlineSystem sys){
			on=sys;
			JPanel display=new JPanel();
			//JLabel info= new JLabel("Departments are identified by the first number;1:CompSci 2:Humanities 3:Math");
			//info.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel label=new JLabel("Courses are listed with a CRN");
		//	label.setAlignmentX(Component.LEFT_ALIGNMENT);
			final JTextArea show=new JTextArea(10,20);
			show.setEditable(false);
			String forShow="";
			try{
				File cat=new File("courseCatalog.txt");
				Scanner scan=new Scanner(cat);
				//this will open the file and take only the CRNs and titles
				while(scan.hasNext()){
					String line=scan.nextLine();
					//System.out.println(line);
					String CRN=(line.substring(0, line.indexOf(",")));
					//System.out.println(CRN);
					String title=line.substring(line.indexOf(",")+1);
					//System.out.println(title);
					ALLCLASSES.add(CRN);
					forShow=forShow+CRN+" "+title+"\n";
				//System.out.println(forShow);
				}
				scan.close();
			}
			catch (FileNotFoundException e1) {
				// 
				e1.printStackTrace();
			}
			//fills the area with the text
			show.setText(forShow);
			JLabel describe=new JLabel("Type a CRN and press Get Description");
			final JTextArea more=new JTextArea(3,10);
			JButton close =new JButton("Close");
			JButton getInfo=new JButton("Get Description");
			JButton refresh=new JButton("Refresh");
			//final JTextArea swapOut=new JTextArea(3,10);
			//final JTextField swapClass=new JTextField(5);
			//editing descriptions only available for department
			/*
			if(DepartmentFrame.wasDepartment){
				swapOut.setVisible(true);
				swapClass.setVisible(true);
			}
			else{
			swapOut.setVisible(false);
			swapClass.setVisible(false);
			}
			
			
			final JButton edit=new JButton("Edit Description");
			edit.setVisible(false);
			*/
			
			/**
			 * closes catalog panel
			 * returns to panel that was in use before catalog was opened
			 */
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					if(DepartmentFrame.wasDepartment){
						DepartmentFrame.wasDepartment=false;
						sys.setState(2);
					}
					else{
					sys.setState(1);
					}
					
				}
			});
			
			/**
			 * refreshes the catalog
			 * same process as constructing panel
			 */
			refresh.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					String forShow="";
					try{
						File cat=new File("courseCatalog.txt");
						Scanner scan=new Scanner(cat);
						//this will open the file and take only the CRNs and titles
						while(scan.hasNext()){
							String line=scan.nextLine();
							//System.out.println(line);
							String CRN=(line.substring(0, line.indexOf(",")));
							//System.out.println(CRN);
							String title=line.substring(line.indexOf(",")+1);
							//System.out.println(title);
							ALLCLASSES.add(CRN);
							forShow=forShow+CRN+" "+title+"\n";
						//System.out.println(forShow);
						}
						scan.close();
					}
					catch (FileNotFoundException e1) {
						// 
						e1.printStackTrace();
					}
					//fills the area with the text
					show.setText(forShow);
					
				}
			});
			
			
			
			/**
			 * shows course info
			 * reads file Description.txt and copies course description to frame
			 */
			getInfo.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					String compare=more.getText();
					String about="";
					try{
						File descriptions=new File("Descriptions.txt");
						Scanner in= new Scanner(descriptions);
						while (in.hasNextLine()){
							String theNextLine=in.nextLine();
							if(compare.equals(theNextLine)){
								 about=in.nextLine();
								 while(true){
								 String checkNext=in.nextLine();
								 //checks to see if description has more lines
								 if(checkNext.substring(0,1).equals("-")){
									 about=about+"\n"+checkNext;
									// checkNext=in.nextLine();
								 }
								 else{
									 break;
								 }
								 }
								// more.setText(about);
								break;
							}
						}
						more.setText(about);
					}
					catch(FileNotFoundException ex){
						
					}
					
				}
			});
			
			/**
			 * edits course descriptions
			 * 
			 */
			/*
			edit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e){
					if(DepartmentFrame.wasDepartment){
						
						String swap=more.getText();
						
						
					}
					else{
						more.setText("Not Authorized");
					}
					
					
				}
			});
			*/
			
			//display.add(info);
			display.add(label);
			display.add(show);
			display.add(describe);
			display.add(more);
			display.add(close);
			display.add(getInfo);
			display.add(refresh);
			//display.add(edit);
			
			frame=new JFrame();
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		//	add the panel 
			
			frame.add(display);
			
		frame.setLocationRelativeTo(null); 
	    frame.pack();
		}
		

}
