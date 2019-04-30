
public class OnlineSystem {

/**
 * contains mechanism to switch between states
 * also contains static method intoArray()
 */

	private LoginFrame loginScreen; //0 - start state
	private StudentFrame studentScreen; //1
	private DepartmentFrame departmentScreen; //2
	private InstructorFrame instructorScreen; //3
	private CatalogFrame catalogScreen; //4, shows with student
	
	
	/*
	 0 - loginScreen shows - start state
	 1 - studentScreen shows and loginScreen hides
	 2 - departmentScreen shows and loginScreen hides
	 3 - instructorScreen shows and loginScreen hides
	 4 - studentScreen and catalogScreen show
	 5 - departmentScreen and catalogScreen show
	 ...
	 */
	
	private int state = 0;
	
	public OnlineSystem() {
		loginScreen = new LoginFrame(this);
		studentScreen = new StudentFrame(this);
		departmentScreen= new DepartmentFrame(this);
		instructorScreen=new InstructorFrame(this);
		catalogScreen=new CatalogFrame(this);
	}
	
	public void setState(int s) {
		state = s;
		//show screens appropriately
		switch(s) {
		//default
		case 0: 
			loginScreen.showScreen();
			studentScreen.hideScreen();
			departmentScreen.hideScreen();
			instructorScreen.hideScreen();
			catalogScreen.hideScreen();
			break;
			//student
		case 1:
			loginScreen.hideScreen();
			studentScreen.showScreen();
			departmentScreen.hideScreen();
			instructorScreen.hideScreen();
			catalogScreen.hideScreen();
			break;
			//department
		case 2:
			departmentScreen.showScreen();
			studentScreen.hideScreen();
			loginScreen.hideScreen();
			instructorScreen.hideScreen();
			catalogScreen.hideScreen();
			break;
			//instructor
		case 3:
			studentScreen.hideScreen();
			departmentScreen.hideScreen();
			loginScreen.hideScreen();
			instructorScreen.showScreen();
			catalogScreen.hideScreen();
			break;
			//student and catalog
		case 4:
			loginScreen.hideScreen();
			studentScreen.showScreen();
			departmentScreen.hideScreen();
			instructorScreen.hideScreen();
			catalogScreen.showScreen();
			break;
			//department and catalog
		case 5:
			loginScreen.hideScreen();
			studentScreen.hideScreen();
			departmentScreen.showScreen();
			instructorScreen.hideScreen();
			catalogScreen.showScreen();
			break;
			
		}
	}
	
	/**
	 * takes a list of all students and puts it into an array
	 * returns array of Strings with student names
	 * used by departmentFrame and instructorFrame
	 */
	
	public static String[] intoArray(){
		String [] studentList=new String[LoginFrame.theList.size()];
		//	System.out.println("LoginFrame.theList");
			
			
			/* used for testing
			for(String l:LoginFrame.theList){
				System.out.println("inTheList "+l);
			}
			
			*/
			//array of students from theList
			int counter=0;
			for(String student:LoginFrame.theList){
				studentList[counter]=student;
				//System.out.println("studentList at counter "+counter+studentList[counter]);
			counter++;
			}
		return studentList;
	}
	
	
	/**
	 * runs the entire system
	 * shows the login frame to start
	 */
	public void run() {
		loginScreen.showScreen();
		//studentScreen.showScreen();
	//	departmentScreen.showScreen();
		//instructorScreen.showScreen();
		
	}
	

}


