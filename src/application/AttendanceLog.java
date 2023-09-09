package application;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.scene.control.TextArea;


public class AttendanceLog {
	private List<Log> logs;
	
	public AttendanceLog()
	{
		logs = new ArrayList<>();
	}
	
	public List<Log> getLogs() 
    {
        return logs;
    }
	
	public void load_log(String filename)
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
	            for(Log log: logs)
	            {
	                if(log.getFirstName().equals(fName) && log.getLastName().equals(lName))
	                {
	                    // add the time stamps to their time stamps array
	                    for(int i = 2; i < tokens.length; i+=2)
	                    {
	                        log.addTimeStamp(tokens[i].trim(), tokens[i+1].trim());
	                    }
	                    found = true;
	                    break;
	                }
	            }
	            if (!found) 
	            {
	                // create a new log object and add it to the list
	                Log newLog = new Log(fName, lName);
	                for(int i = 2; i < tokens.length; i+=2)
	                {
	                    newLog.addTimeStamp(tokens[i].trim(), tokens[i+1].trim());
	                }
	                logs.add(newLog);
	            }
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	
	public void print_collection(TextArea t) {
        t.setText("** This is the entire Collection Data currently stored **\n");
    
        for (Log log : logs) 
        {
        	t.appendText(log.toString() + "\n");
        }
    }
	
	public void print_count(TextArea t)
	{
		t.setText("Total number of logs: "+logs.size() + "\n");
	}
	
	public Log findLog(String fName, String lName)
	{
		for (Log log : logs) 
        {
            if(log.getFirstName().equals(fName) && log.getLastName().equals(lName))
            {
            	return log;
            }
        }
		return null;
	}
}
