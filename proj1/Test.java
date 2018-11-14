
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
				//check to see if file is delted
				if (rec.toString().contains("del")){
					//code to skip over delted record
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
					while (count<5){
						rec.readFromFile(raf);
						if (rec.toString().contains("del")){
							raf.seek(rec.size()+raf.getFilePointer());;
							rec.readFromFile(raf);
						}
						System.out.println(rec);
						count++;
					}
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
	 */
	public static void Menu() throws IOException{
		String x1="";
		//scanner for and input streams for file
		Scanner fin = new Scanner (new FileInputStream("students.txt"));
		RandomAccessFile raf = new RandomAccessFile("dbase", "rw");
		Scanner keyIn = new Scanner(System.in);
		Student rec = new Student();

		//menu options, prompts user to choose
		System.out.println("Type the corresponding number");
		System.out.println("1- Create a random-access file");
		System.out.println("2- Display a random-access file");
		System.out.println("3- Retrieve a record");
		System.out.println("4- Modify a record");
		System.out.println("5- Add a new record");
		System.out.println("6- Delete a new record");	
	    System.out.println("7- Exit");
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
			//close newly created random acces ile and return to menu
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
			//choosing which record to view
			int record=0;
			System.out.println("Enter the record number you would like");
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
			Menu();
		}
		else if (choice==4)
		{
			//choosing which record to modify
			System.out.println("Which record would you like to Modify?");
			int record=0;
			Scanner recordread= new Scanner(System.in);
			record=recordread.nextInt();
			//if statement to check if number is valid
			if(record>(raf.length()/92)||record<0)
			{
				//error message for incorrect number
				System.out.println("That is not a valid record number");
			}
			else 
			{
				// Display the nth student record and skip over deleted
				raf.seek((record)*rec.size());
				if (!rec.toString().contains("del")){
					raf.seek(rec.size()+raf.getFilePointer());;
					rec.readFromFile(raf);
					System.out.println(rec);
				}
				//else block when not skipping deleted prints out corresponding record
				else
				{
					rec.readFromFile(raf);
					System.out.println(rec);
				}
				//code to change the record
				raf.seek((record-1)*rec.size());
				System.out.println("Type new fields of data");
				rec.readFromTextFile(keyIn);
				rec.writeToFile(raf);
			}
			Menu();
		}
		
		else if (choice==5)
		{
			// Write a new record to the end of the random access file
			System.out.print("Enter your first name, last name, student ID, and GPA: ");
			rec.readFromTextFile(keyIn);
			raf.seek(raf.length());
			rec.writeToFile(raf);
			Menu();
		}
		else if (choice==6)
		{
			//code for lazy deletion
			Student del= new Student();
			String delete="";
			//ask user what record they would like to delete
			System.out.println("Which record would you like to delete?");
			int record=0;
			Scanner recordread= new Scanner(System.in);
			record=recordread.nextInt();
			//replacing fields with delted tag
			System.out.println("Type \"del del 000 0.0\" to delete");
			raf.seek((record-1)*rec.size());
			rec.readFromTextFile(keyIn);
			rec.writeToFile(raf);
			Menu();
			
		}
		else if (choice==7)
		{
			//closing streams and files and exiting program
			raf.close();
			fin.close();
			System.exit(0);
		}
	}

}
