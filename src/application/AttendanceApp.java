package application;
import java.util.List;


import javafx.scene.control.TextArea;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
public class AttendanceApp {
	private static String roster;
	private static String swipeData;
	private Stage stage;
	
	public AttendanceApp(String r, String s, Stage st)
	{
		AttendanceApp.roster = r;
		AttendanceApp.swipeData = s;
	}
	
	public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
            Parent root = loader.load();
            SampleController controller = loader.getController();
            controller.setAttendanceApp(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Attendance Tracker");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static List<String> list_students_not_in_class(AttendanceLog log, StudentRoster sr)
	{
		// compare roster to timestamps
    	
    	
    	List<Student> students = sr.getRoster();
    	
    	List<String> absent = new ArrayList<>();
    	
//    	 
//    	sr.print_count();
//    	log.print_count();
    	
    	List<Log> logList = log.getLogs();
    	for(Student s : students)
    	{
    		boolean found = false;
    		
    		for(Log l : logList)
    		{
    			// check if log list and students have the same
    			if(l.getFirstName().equals(s.getFirstName()) && l.getLastName().equals(s.getLastName()))
    			{
    				found = true;
    				break;
    			}
    		}
    		
    		if(!found)
    		{
    			absent.add(s.getLastName() + ", " + s.getFirstName());
    		}
    	}
    	
    return absent;
		
	}
	
	public static List<String> list_all_times_checking_in_and_out(String fName, String lName, AttendanceLog log)
	{
    	List<Log> logList = log.getLogs();
    	
    	List<String> check_in = new ArrayList<>();
    	
    	for(Log l: logList)
    	{
    		if(l.getFirstName().equals(fName) && l.getLastName().equals(lName))
    		{
    			List<String> times = l.getTimeStamps();
        		for(String t: times)
        		{
        			check_in.add(l.getLastName() + ", " + l.getFirstName() + ", " + t);
        		}
    		}
    		
    	}
    	
    	return check_in;
		
	}
	
	public static List<String> list_all_times_checked_in(AttendanceLog log)
	{
		// list only first check in time
    	List<Log> logList = log.getLogs();
    	List<String> first_check_in = new ArrayList<>();
    	//first_check_in.add("****** Check in times for all students who attended *****");
    	for(Log l: logList)
    	{
    		List<String> times = l.getTimeStamps();
    		 if (!times.isEmpty()) {
    	            first_check_in.add(l.getLastName() + ", " + l.getFirstName() + ", " + times.get(0));
    	        }
    	}
    	return first_check_in;
	}
	
	
	public static List<String> list_students_late_to_class(String time, AttendanceLog log)
	{
		// represents the ending line of the class
		List<String> late_students = new ArrayList<>();
		//late_students.add("****** Students that arrived late ********************");
		
		
    	List<Log> logList = log.getLogs();
    
    	String[] time_tokens = time.split(":");
    	int hour = Integer.parseInt(time_tokens[0]);
    	int min = Integer.parseInt(time_tokens[1]);
    	int second = Integer.parseInt(time_tokens[2]);
    	

    	for(Log l: logList)
    	{
    		//compare the students time to the timestamp given
    		// check if the hour matches the hour on the timestamp
    		boolean late = false;
    		boolean different_class = false;
    		List<String> s_times = l.getTimeStamps();
    		for(int i = 0; i < s_times.size(); i += 1)
    		{
    			String s_time_stamp = l.getTime(i);
    			String s_date_stamp = l.getDate(i);
    			String[] student_time_tokens = s_time_stamp.split(":");
    			
    			int s_hour = Integer.parseInt(student_time_tokens[0]);
    	    	int s_min = Integer.parseInt(student_time_tokens[1]);
    	    	int s_second = Integer.parseInt(student_time_tokens[2]);
    	    	
 
    	    	if(s_hour > hour)
    	    	{
    	    		different_class = true;
    	    		break;
    	    	}
    	    	else if(s_hour == hour)
    	    	{
    	    		// check if student is already in late students list
    	    		if(s_min > min)
    	    		{
    	    			late = true;
    	    			late_students.add(l.getLastName() + ", " + l.getFirstName() + ", " + s_time_stamp + ", " + s_date_stamp);
    	    		}
    	    		else if(s_min == min) {
    	    			if(s_second > second)
        	    		{
        	    			late = true;
        	    			late_students.add(l.getLastName() + ", " + l.getFirstName() + ", " + s_time_stamp + ", " + s_date_stamp);
        	    		}
    	    			
    	    		}
    	    	}
    		}
    		
    	}
		
		return late_students;
	}
	
	public static void print_attendance_data_for_student(String fName, String lName, List<String> time_stamps, AttendanceLog log, TextArea t)
	{
    	List<Log> logList = log.getLogs();
    	boolean found = false;
    	
    	String str = "";
    	for(Log l:logList)
    	{
    		if(l.getFirstName().equals(fName) && l.getLastName().equals(lName))
    		{
    			List<String> s_times = l.getTimeStamps();
    			for(int i = 0; i < Math.min(s_times.size(), time_stamps.size()); i += 1)
    			{
    				if(s_times.get(i).equals(time_stamps.get(i)))
    				{
    					found = true;
    				}
    				
    			}
    			if(found)
    			{
    				break;
    			}
    			
    		}
    	}
    	t.setText("********* Looking up Student Attendance Data ***********\n");
    	str = lName + ", " + fName + ", [ ";
    	for(int i = 0; i < time_stamps.size(); i+=1)
    	{
    		if(i == time_stamps.size()-1)
    		{
    			str += time_stamps.get(i) + " ]";
    		}
    		else
    		{
    			str += time_stamps.get(i) + ", ";
    		}
    	}
    	t.appendText(str + "\n");
    	if(!found)
    	{
    		t.appendText("No student of this name in the attendance log\n");
    	}
    	else
    	{
    		t.appendText("Student of this name was found in the attendance log\n");
    	}
    	
		
	}
	
	public static void is_present(String fName, String lName, String date, AttendanceLog log, TextArea t)
	{
		// add the message later for the query
		boolean present = false;
		
    	List<Log> logList = log.getLogs();
    	
    	for(Log l : logList)
    	{
    		if(l.getFirstName().equals(fName) && l.getLastName().equals(lName))
    		{
    			List<String> s_times = l.getTimeStamps();
    			for(int i = 0; i <  s_times.size(); i += 1)
    			{
    				String s_date= l.getDate(i);
    				if(s_date.equals(date))
    				{
    					present = true;

    				}
    					
    			}
    		}
    	}
		
		t.setText("**** Looking to see if Student was present on date ****\n");
		if(present) {
			t.appendText("True\n");
		}
		else {
			t.appendText("False\n");
		}
	}
	
	public static List<String> list_all_students_checked_in(String date, AttendanceLog log)
	{
		
    	List<Log> logList = log.getLogs();
    	
    	List<String> s = new ArrayList<>();
    	
    	List<String> repeats = new ArrayList<>();
    	for(Log l : logList)
    	{
    		
			List<String> s_times = l.getTimeStamps();
			for(int i = 0; i <  s_times.size(); i += 1)
			{
				String s_date= l.getDate(i);
				if(s_date.equals(date))
				{
					if(!repeats.contains(l.getLastName() + ", " + l.getFirstName())) {
						s.add(l.getLastName() + ", " + l.getFirstName());
						repeats.add(l.getLastName() + ", " + l.getFirstName());
						
					}
				}
					
			}
  
    	}
    	
    	return s;	
	}
	
	
	public static List<String> list_all_students_checked_in_before(String date, String time, AttendanceLog log)
	{
		
    	List<Log> logList = log.getLogs();
    	
    	List<String> s = new ArrayList<>();
    	 String[] dateTokens = date.split("/");

    	    // Check that the time string is in the expected format
    	    if (!time.matches("\\d{1,2}:\\d{2}:\\d{2}")) {
    	        System.err.println("Invalid time format: " + time);
    	        return s;
    	    }

	    // Parse time string into integers
	    String[] timeTokens = time.split(":");
	    int hour = Integer.parseInt(timeTokens[0]);
	    int min = Integer.parseInt(timeTokens[1]);
	    int second = Integer.parseInt(timeTokens[2]);
    	

    	for(Log l : logList)
    	{
    		
			List<String> s_times = l.getTimeStamps();
			for(int i = 0; i <  s_times.size(); i += 1)
			{
				String s_date= l.getDate(i);
				if(s_date.equals(date))
				{
					String s_time_stamp = l.getTime(i);
					String[] student_time_tokens = s_time_stamp.split(":");
	    			
	    			int s_hour = Integer.parseInt(student_time_tokens[0]);
	    	    	int s_min = Integer.parseInt(student_time_tokens[1]);
	    	    	int s_second = Integer.parseInt(student_time_tokens[2]);
	    	    	
	    	    	if(s_hour < hour)
	    	    	{
	    	    		s.add(l.getLastName() + ", " + l.getFirstName());
	    	    	}
	    	    	else if(s_min < min && s_hour == hour)
	    	    	{
	    	    		s.add(l.getLastName() + ", " + l.getFirstName());
	    	    	}
	    	    	else if(s_second < second && s_min == min && s_hour == hour)
	    	    	{
	    	    		s.add(l.getLastName() + ", " + l.getFirstName());
	    	    	}
				}
					
			}
  
    	}
    	
    	return s;
	}
	
	public static List<String> list_students_attendance_count(String numClasses, AttendanceLog log)
	{
		
    	List<Log> logList = log.getLogs();
    	
    	List<String> s = new ArrayList<>();
    	
    	int num = Integer.parseInt(numClasses);
    
    	for(Log l : logList)
    	{
    		
			List<String> s_times = l.getTimeStamps();
			if(s_times.size() >= num)
			{
				s.add(l.getLastName() + ", " + l.getFirstName());
			}
			
    	}
    	
    	return s;
		
	}
	
	public static Student get_first_student_to_enter(String date, AttendanceLog log)
	{
		// get first student who enters on a given date
		
    	List<Log> logList = log.getLogs();
    	
    	Student firstStudent = null;
	    int earliestHour = 24; // initialize with a large value to ensure any entry time will be earlier
	    int earliestMin = 60;
	    int earliestSec = 60;

    	
    	for(Log l: logList)
        {
            List<String> timeStamps = l.getTimeStamps();
            for(int i = 0; i < timeStamps.size(); i++)
            {
                if(l.getDate(i).equals(date))
                {
                	String s_time_stamp = l.getTime(i);
    				String[] student_time_tokens = s_time_stamp.split(":");
        			
        			int hour = Integer.parseInt(student_time_tokens[0]);
        	    	int min = Integer.parseInt(student_time_tokens[1]);
        	    	int sec = Integer.parseInt(student_time_tokens[2]);

                    if (hour < earliestHour ||
                        (hour == earliestHour && min < earliestMin) ||
                        (hour == earliestHour && min == earliestMin && sec < earliestSec)) {
                        earliestHour = hour;
                        earliestMin = min;
                        earliestSec = sec;
                        firstStudent = new Student(l.getFirstName(), l.getLastName());
                    }
                }
            }
        }
    	
    	
    	return firstStudent;
	}
	
	
	public static void print_query_list(List<String> l, TextArea t)
	{
		for(String s:l)
		{
			t.appendText(s + "\n");
		}
	}

	public static void print_query_count(List<String> l, TextArea t)
	{
		
		t.setText("There were " +l.size() + " records for this query\n");
		
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}
