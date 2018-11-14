
import java.util.*;
import java.io.*;

/**
 * Read the students' info from a text file and write them to a random access file 
 * and then perform a few simple tasks using the random access file
 * Izhak Hamidi COSC 311, Winter 2018
 *
 */
public class Test {
	public static void main(String [] args)throws IOException{
		//method to call initial menu
		Menu();
	}

	/**
	 * Display the students' records neatly on the screen
	 * @param raf the random access file
	 * @param rec the student record
	 * @throws IOException
	 */
	public static void print (RandomAccessFile raf, Student rec) throws IOException
	{
		//counter for looping through records
		String cont="";
		int count=0;
		//starting the random access pointer at 0
		raf.seek(0);
		//try catch for IOException
		try {
			//loop for first 5 values
			while (count<5){
				
				rec.readFromFile(raf);
				//check to see if file is deleted
				if (rec.toString().contains("del")){
					//code to skip over deleted record
					raf.seek(rec.size()+raf.getFilePointer());;
					rec.readFromFile(raf);
				}
				//print and increment loop
				System.out.println(rec);
				count++;
			}
			//loop for rest of the values, includes nested loops for selections
			while(raf.length()-raf.getFilePointer()>0)
			{
				//ask user what to display
				System.out.println("Return to the main menu (M); Next screen (N); or Display all (A)");
				Scanner choose= new Scanner(System.in);
				cont=choose.nextLine();
				//back to menu
				if(cont.equals("m"))
				{
					Menu();
				}
				//shows 5 more values using same loop as above
				else if(cont.equals("n"))
				{
					count=0;
					while (count<5 &&raf.getFilePointer()<=raf.length()-2){
						rec.readFromFile(raf);
						if (rec.toString().contains("del")){
							raf.seek(rec.size()+raf.getFilePointer());;
							rec.readFromFile(raf);
						}
						System.out.println(rec);
						count++;
					}
					Menu();
				}
				//shows all the rest of values
				else if(cont.equals("a"))
				{
					//displays rest of records in file
					while (true){
						
						rec.readFromFile(raf);
						if (rec.toString().contains("del")){
							raf.seek(rec.size()+raf.getFilePointer());;
							rec.readFromFile(raf);
						}
						System.out.println(rec);
						
					}
					
				}
			}
		}
		//exception for End Of stream, leaves program
		catch (EOFException e){
			System.exit(0);
		}
	}
	/**
	 * mENU FOR PROGRAM, COINTAINS ALL FUNCTIONS
	 * @throws IOException 
	 * @throws IOException
	 */
	public static void Menu() throws IOException {
		String x1="";
		System.out.println(" Welcome Back to the Menu please (Re)Type the file to read from");
		Scanner keyIn = new Scanner(System.in);
		x1=keyIn.nextLine();
		
		//scanner for and input streams for file
		Scanner fin = null;
		//try catches to catch exceptions
		try {
			fin = new Scanner (new FileInputStream(x1));
		} catch (FileNotFoundException e) {
			System.out.println("That is not a valid file");
			Menu();
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("dbase", "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//creating student and linked list
		Student rec = new Student();
		LinkedList records=new LinkedList();

		//menu options, prompts user to choose
		System.out.println("Type the corresponding number");
		System.out.println("1- Create a random-access file");
		System.out.println("2- Display a random-access file");
		System.out.println("3- Build the index");
		System.out.println("4- Display the index");
		System.out.println("5- Retrieve a record");
		System.out.println("6- Modify a record");
		System.out.println("7- Add a new record");
		System.out.println("8- Delete a new record");	
	    System.out.println("9- Exit");
	    //creating scanner to choose
	    int choice=0;
		Scanner itemread= new Scanner(System.in);
		choice=itemread.nextInt();
		if(choice==1)
		{
			
			
			System.out.println("Please type in the file name to create");
			// Create input and output file streams
			
			Scanner fileToRead= new Scanner(System.in);
			x1=fileToRead.nextLine();
			RandomAccessFile rafnew = new RandomAccessFile(x1, "rw");
			
			Student rec1 = new Student();
			// Read data from the input file and write them to the random access file
			while (fin.hasNext()){
				rec1.readFromTextFile(fin);
				rec1.writeToFile(rafnew);
			}
			//close newly created random access file and return to menu
			rafnew.close();
			Menu();
		}
		else if(choice==2)
		{
			// Display the students' records neatly on the screen
			print (raf,rec);
			Menu();
					}
		else if(choice==3)
		{
			//get file name
			System.out.println("What file would you like to read from");
			keyIn= new Scanner(System.in);
			x1=keyIn.nextLine();
			Student list= new Student();
			String record;
			int counter=0;
			//input stream based on name
			Scanner fileinput = new Scanner (new FileInputStream(x1));
			RandomAccessFile rafnew = new RandomAccessFile("dbase", "rw");
			Scanner key = new Scanner(System.in);
			rafnew.seek(80);
			//to make linked list
			while(counter*list.size()<rafnew.length())
			{	
				//reads from the file
				list.readFromFile(rafnew);
				
				//data of key and address
				Pair keyAdd= new Pair(rafnew.readInt(),counter*list.size());
				//adding new node if the file doesnt show up as deleted (equals 0)
				if(keyAdd.getKey()!=0){
					records.addToEnd(keyAdd);
				}
				rafnew.seek(counter*list.size()+80);
				
				counter++;
			}
			//go to more advanced list
			LinkedMenu(records);
		}
		else if(choice==4)
		{
			//need to build index to use this choice, takes you back to menu
			System.out.println("Build Index First!");
			Menu();
		
		}
		else if(choice==5)
		{
			System.out.println("Build Index First!");
			Menu();
		}
		else if (choice==6)
		{
			System.out.println("Build Index First!");
			Menu();
		}
		
		else if (choice==7)
		{
			
			System.out.println("Build Index First!");
			Menu();
		}
		else if (choice==8)
		{
			System.out.println("Build Index First!");
			Menu();
			
		}
		else if (choice==9)
		{
			//closing streams and files and exiting program
			raf.close();
			fin.close();
			System.exit(0);
		}
		
	}
	
	public static void LinkedMenu(LinkedList records) throws IOException  {
		String x1="";
		System.out.println(" Welcome Back to the Menu please (Re)Type the file to read from");
		Scanner keyIn = new Scanner(System.in);
		x1=keyIn.nextLine();
		
		//scanner for and input streams for file
		Scanner fin = null;
		//try catches to catch exceptions
		try {
			fin = new Scanner (new FileInputStream(x1));
		} catch (FileNotFoundException e) {
			System.out.println("That is not a valid file");
			LinkedMenu(records);
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("dbase", "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Student rec = new Student();
		

		//menu options, prompts user to choose
		System.out.println("Type the corresponding number");
		System.out.println("1- Create a random-access file");
		System.out.println("2- Display a random-access file");
		System.out.println("3- Build the index");
		System.out.println("4- Display the index");
		System.out.println("5- Retrieve a record");
		System.out.println("6- Modify a record");
		System.out.println("7- Add a new record");
		System.out.println("8- Delete a new record");	
	    System.out.println("9- Exit");
	    int choice=0;
		Scanner itemread= new Scanner(System.in);
		choice=itemread.nextInt();
		if(choice==1)
		{
			
			
			System.out.println("Please type in the file name to create");
			// Create input and output file streams
			
			Scanner fileToRead= new Scanner(System.in);
			x1=fileToRead.nextLine();
			RandomAccessFile rafnew = new RandomAccessFile(x1, "rw");
			
			Student rec1 = new Student();
			// Read data from the input file and write them to the random access file
			while (fin.hasNext()){
				rec1.readFromTextFile(fin);
				rec1.writeToFile(rafnew);
			}
			//close newly created random access file and return to menu
			rafnew.close();
			LinkedMenu(records);
		}
		else if(choice==2)
		{
			// Display the students' records neatly on the screen
			print (raf,rec);
			LinkedMenu(records);
					}
		else if(choice==3)
		{
			//rebuilds the linked list
			System.out.println("What file would you like to read from");
			keyIn= new Scanner(System.in);
			x1=keyIn.nextLine();
			Student list= new Student();
			String record;
			int counter=0;
			Scanner fileinput = new Scanner (new FileInputStream(x1));
			RandomAccessFile rafnew = new RandomAccessFile("dbase", "rw");
			Scanner key = new Scanner(System.in);
			rafnew.seek(80);
			
			while(counter*list.size()<rafnew.length())
			{	
				
				list.readFromFile(rafnew);
				
				
				Pair keyAdd= new Pair(rafnew.readInt(),counter*list.size());
				if(keyAdd.getKey()!=0){
					records.addToEnd(keyAdd);
				}
				rafnew.seek(counter*list.size()+80);
				
				counter++;
			}
			
		}
		else if(choice==4)
		{
			//code to view index
			System.out.println(records.getSize());
			System.out.println("Would you like to view full index? Y/N");
			Scanner display= new Scanner(System.in);
			String s=display.nextLine();
			//lets you view index if it isnt empty
			if(records.getSize()>0){
			//f user chooses yes
			if(s.equals("Y")||s.equals("y"))
			{
				//loop to go through linked list and print keys
					for(int i=0;i<records.getSize();i++)
					{
						System.out.println(records. returnIndex(i));
					}
					LinkedMenu(records);
				}
			else {
				//ask user where to start viewing
				System.out.println("Where would you like to start viewing full index? Enter number:");
				int x=display.nextInt();
				//use number and loop till end of list
				while(x<records.getSize())
				{
					System.out.println(records. returnIndex(x));
					x++;
				}
				LinkedMenu(records);
			}
			}
			
			
			//code if list is empty
			else 
			{
				System.out.println("No Index present");
				LinkedMenu(records);
			}
		}
		else if(choice==5)
		{
			//choosing which record to view
			int record=0;
			System.out.println("Enter the Student ID number you would like");
			Scanner recordread= new Scanner(System.in);
			record=recordread.nextInt();
			// Display the nth student record
			raf.seek((record-1)*rec.size());
			//if statements to check if record has been tagged as deleted
			if (!rec.toString().contains("del")){
				///code to seek adn display corresponding record
				raf.seek(rec.size()*2+raf.getFilePointer());;
				rec.readFromFile(raf);
				System.out.println(rec);
			}
			else
			{
				//code to skip over deleted and view next record
				rec.readFromFile(raf);
				System.out.println(rec);
			}
			LinkedMenu(records);
		}
		else if (choice==6)
		{
			//choosing which record to modify
			int record=0;
			System.out.println("Which record would you like to Modify?Enter the Student ID number you would like");
			Scanner recordread= new Scanner(System.in);
			record=recordread.nextInt();


				// Display the nth student record and skip over deleted
				int i=0;
				while (i<records.getSize()){
				if(records. returnIndex(i)==record)
				{

					raf.seek(records.getPosition(i));
				
				//else block when not skipping deleted prints out corresponding record
				
						rec.readFromFile(raf);
						System.out.println(rec);
					
				//code to change the record
				raf.seek((record-1)*rec.size());
				System.out.println("Type new fields of data");
				rec.readFromTextFile(keyIn);
				rec.writeToFile(raf);
				}
				i++;
			}
				LinkedMenu(records);
		}
		
		else if (choice==7)
		{
			int ID=0;
			// Write a new record to the end of the random access file
			System.out.print("Enter your first name, last name, student ID, and GPA: ");
			//code to write to file. getpostion returns address
			rec.readFromTextFile(keyIn);
			raf.seek(raf.length());
			rec.writeToFile(raf);
			System.out.print("Reenter ID for confirmation");
			ID= keyIn.nextInt();
			records.addToEnd(new Pair(ID, raf.length()-92));
			
			LinkedMenu(records);
		}
		else if (choice==8)
		{
			//choosing which record to delete
			int record=0;
			System.out.println("Enter the Student ID number you would like to delte");
			Scanner recordread= new Scanner(System.in);
			record=recordread.nextInt();
			int counter=0;
			//boolean to check if list contains item
			boolean has= false;
			//loop to go through list
			while(counter<records.getSize())
			{
				
				if(records. returnIndex(counter)==record)
				{
					//code to delte record from list
					records.deleteFromIndex(counter);
					has=true;
					break;
				}
				counter++;
			}
			// if record isnt in list
			if (has=false)
			{
				System.out.println("No record found");
				LinkedMenu(records);
			}
			//code for lazy deletion
			Student del= new Student();
			String delete="del";
			int record1=0;
			
			//replacing fields with deleted tag
			
			raf.seek(records.getPosition(counter));
			rec.readFromTextFile(keyIn);
			rec.writeString(raf, delete, 20);
			LinkedMenu(records);
			
		}
		else if (choice==9)
		{
			//closing streams and files and exiting program
			raf.close();
			fin.close();
			System.exit(0);
		}
		
	}
}
