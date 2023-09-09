package application;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.scene.control.TextArea;
public class StudentRoster {
	private List<Student> roster;
	
	public StudentRoster()
	{
		roster = new ArrayList<>();
	}
	
	public List<Student> getRoster()
	{
		return roster;
	}
	
	public void load_roster(String filename)
	{
		Scanner infile = null;
		 try {
		        infile = new Scanner(new FileReader(filename));
		        // read in each individual line and set the values of the log class
		        while(infile.hasNextLine())
		        {
		            String line = infile.nextLine();
		            String[] tokens = line.split(",");
		            String fName = tokens[1].trim();
		            String lName = tokens[0].trim();
		            // check if first and last name already exist
		            boolean found = false;
		            for(Student s: roster)
		            {
		                if(s.getFirstName().equals(fName) && s.getLastName().equals(lName))
		                {
		                    found = true;
		                }
		            }
		            if (!found) 
		            {
		                // create a new log object and add it to the list
		                Student s = new Student(fName, lName);
		                
		                roster.add(s);
		            }
		        }
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
	}
	
	public void print_collection(TextArea t) {
       t.setText("** This is the entire Collection Data currently stored **\n");
        for (Student s : roster) 
        {
           t.appendText(s.toString() + "\n");
        }
    }
	
	public void print_count(TextArea t)
	{
		t.setText("Total number of students: "+roster.size()+"\n");
	}

}
