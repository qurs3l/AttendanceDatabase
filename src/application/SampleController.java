package application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

import javafx.stage.Stage;

public class SampleController {
	private File scanTimesFile;
	private File studentRosterFile;
	
	
	
	private AttendanceLog attendanceLog;
	private StudentRoster studentRoster;
	private AttendanceApp attendanceApp;

	
	private List<String> late_students = new ArrayList<>();
	private List<String> all_times_checked_in = new ArrayList<>();
	private List<String> times_in_and_out = new ArrayList<>();
	private List<String> not_in_class = new ArrayList<>();
	private List<String> all_students_checked_in = new ArrayList<>();
	private List<String> all_students_checked_before = new ArrayList<>();
	private List<String> nums = new ArrayList<>();
	
	
	private Student first;
	private String str;
	private String firstDate;
	
	Stage stage;
	
	@FXML
	private Button chooseScannerButton;
	
	@FXML 
	private Button chooseRosterButton;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private Button submit;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Pane scenePane;
	
	@FXML
	private TextArea t;
	
	@FXML
	private TextField tf;
	
	public void setAttendanceApp(AttendanceApp attendanceApp) {
	    this.attendanceApp = attendanceApp;
	}
	@FXML
    private void handleChooseFileButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Scan Times File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            scanTimesFile = selectedFile;
            
        }
       
    }

    @FXML
    private void handleChooseFileButtonClick2(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Roster File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            studentRosterFile = selectedFile;
           
        }
    }

    private void initializeAttendance() 
    {
    	String filePath = scanTimesFile.getPath();
    	String filePath2 = studentRosterFile.getPath();
        attendanceLog = new AttendanceLog();
        studentRoster = new StudentRoster();
        attendanceApp = new AttendanceApp(filePath, filePath2, stage);
        	
    }
    
    @FXML
    private void handleExitButtonClick(ActionEvent event) {
        if (scenePane != null) 
        {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Exiting Program....");
            stage.close();
        }
    }
    
    @FXML
    private void handleLoadLogClick(ActionEvent event) {
    	 if (attendanceLog == null) {
    	        initializeAttendance();
    	    }
    	  String filePath = scanTimesFile.getPath();
    	  attendanceLog.load_log(filePath);
    	  Platform.runLater(() -> t.setText("Log successfully loaded\n"));
    }
    
   @FXML
   private void handlePrintCollectionLogClick(ActionEvent event)
   {
	   if (attendanceLog == null) {
	        initializeAttendance();
	        t.setText("You must load the log and roster files before using this method.");
	        return;
	    }
	   attendanceLog.print_collection(t);
	   
   }
   
   @FXML
   private void handlePrintCountLogClick(ActionEvent event)
   {
	   if (attendanceLog == null) {
	        initializeAttendance();
	        t.setText("You must load the log and roster files before using this method.");
	        return;
	    }
	   attendanceLog.print_count(t);
	   
   }
   
   @FXML
   private void handleLoadRosterClick(ActionEvent event) {
   	 if (studentRoster == null) {
   	        initializeAttendance();
   	    }
   	  String filePath2 = studentRosterFile.getPath();
   	  studentRoster.load_roster(filePath2);
   	  Platform.runLater(() -> t.setText("Roster successfully loaded\n"));
   }
   
   @FXML
   private void handlePrintCollectionRosterClick(ActionEvent event)
   {
	   if (studentRoster == null) {
	        initializeAttendance();
	        t.setText("You must load the log and roster files before using this method.");
	        return;
	    }
	   studentRoster.print_collection(t);
	   
   }
   
   
   @FXML
   private void handlePrintCountRosterClick(ActionEvent event)
   {
	   if (studentRoster == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   studentRoster.print_count(t);
	   
   }
   
   @FXML
   private void handleTimesNotInClass(ActionEvent event)
   {
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   not_in_class = AttendanceApp.list_students_not_in_class(attendanceLog, studentRoster);
	   str = "G";
	    Platform.runLater(() -> t.setText("Finished running list_students_not_in_class"));
	   
   }
   
 
   
   @FXML
   private void handleTimesInAndOut(ActionEvent event)
   {
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   String firstName = "";
	   String lastName = "";
	 
	   TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter Student Information");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Enter the student's first name:");

	    Optional<String> result = dialog.showAndWait();
	    if (result.isPresent()) 
	    {
	       firstName = result.get();
	
	        
	        dialog.setContentText("Enter the student's last name:");
	        result = dialog.showAndWait();
	        if (result.isPresent()) {
	            lastName = result.get();

	        }
	 
	   
	    }	
   
	    times_in_and_out = AttendanceApp.list_all_times_checking_in_and_out(firstName, lastName, attendanceLog);
	    str = "H";
	    Platform.runLater(() -> t.setText("Finished running list_all_times_checking_in_and_out"));
   
   }
   
   @FXML
   private void handleAllTimesCheckedIn(ActionEvent event)
   {
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   all_times_checked_in = AttendanceApp.list_all_times_checked_in(attendanceLog);
	   str = "I";
	   Platform.runLater(() -> t.setText("Finished running list_all_times_checked_in"));
	   
   }
   
   @FXML
   private void handleLateStudents(ActionEvent event)
   {
	   String time = "";
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	 
	   	TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter Class Time");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Enter Class Time in the format HR:MIN:SEC :");

	    Optional<String> result = dialog.showAndWait();
	    if (result.isPresent()) 
	    {
	       time = result.get();
	
	    }	
	   	str = "J";
	   	late_students = AttendanceApp.list_students_late_to_class(time, attendanceLog);
	    Platform.runLater(() -> t.setText("Finished running list_students_late_to_class"));
		   
   }
   
   @FXML
   private void handleFirstStudent(ActionEvent event)
   {
	   String date = "";
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
		 
	   	TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter Class Time");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Enter Class Date in the format MONTH/DAY/YEAR :");

	    Optional<String> result = dialog.showAndWait();
	    if (result.isPresent()) 
	    {
	       date = result.get();
	       System.out.println(date);
	       firstDate = date;
	
	    }	
       first = AttendanceApp.get_first_student_to_enter(date, attendanceLog);
       str = "K";
       t.setText("****** First student to enter on " + firstDate +" ********************\n");
       t.appendText(first.toString() + "\n");
       Platform.runLater(() -> t.appendText("Finished running get_first_student_to_enter"));
   }
   
   @FXML 
   private void handlePrintAttendanceData(ActionEvent event)
   {
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   
	   String firstName = "";
	   String lastName = "";
	   List<String> times = new ArrayList<>();
	   String input = "";
	   TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter Student Information");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Enter the student's first name:");

	    Optional<String> result = dialog.showAndWait();
	    if (result.isPresent()) 
	    {
	       firstName = result.get();
	
	        
	        dialog.setContentText("Enter the student's last name:");
	        result = dialog.showAndWait();
	        if (result.isPresent()) {
	            lastName = result.get();
	            while (true) {
	                dialog.setContentText("Enter the student's check-in time and date in the form HOUR:MIN:SEC, MONTH/DAY/YEAR (or enter 'q' to finish):");
	                result = dialog.showAndWait();
	                if (!result.isPresent() || result.get().equalsIgnoreCase("q")) {
	                    break;
	                }
	                times.add(result.get());
	            }

	        }

	    }
	    AttendanceApp.print_attendance_data_for_student(firstName, lastName, times, attendanceLog, t);

	   Platform.runLater(() -> t.appendText("Finished running print_attendance_data_for_student"));
   }
   
   @FXML
   private void handleIsPresent(ActionEvent event)
   {
	   
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   String firstName = "";
	   String lastName = "";
	   String date = "";
	   TextInputDialog dialog = new TextInputDialog();
	   
	   dialog.setContentText("Enter the student's first name:");

	    Optional<String> result = dialog.showAndWait();
	   if (result.isPresent()) 
	    {
	       firstName = result.get();
	
	        
	        dialog.setContentText("Enter the student's last name:");
	        result = dialog.showAndWait();
	        if (result.isPresent()) {
	            lastName = result.get();
	            
	            dialog.setContentText("Enter the date in the format MONTH/DAY/YEAR:");
		        result = dialog.showAndWait();
	            if(result.isPresent()) {
	            	date = result.get();
	            }
	        }
	    }
	   
	   AttendanceApp.is_present(firstName, lastName, date, attendanceLog, t);
	   Platform.runLater(() -> t.appendText("Finished running is_present"));
	   
   }
   
   @FXML
   private void handleAllCheckedIn(ActionEvent event)
   {
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   String date = "";
	   TextInputDialog dialog = new TextInputDialog();
	   
	   dialog.setContentText("Enter the date in the format MONTH/DAY/YEAR:");

	    Optional<String> result = dialog.showAndWait();
	   if (result.isPresent()) 
	    {
	       	date = result.get();
	   
	            
	    }
	   
	   all_students_checked_in = AttendanceApp.list_all_students_checked_in(date, attendanceLog);
	   str = "N";
	   Platform.runLater(() -> t.setText("Finished running list_all_students_checked_in"));
	   
   }
   
   
   @FXML
   private void handleAllCheckedInBefore(ActionEvent event) {
	   
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   String date = "";
	   String time = "";
	   TextInputDialog dialog = new TextInputDialog();
	   
	   dialog.setContentText("Enter the date in the format MONTH/DAY/YEAR :");

	   Optional<String> result = dialog.showAndWait();
	   if (result.isPresent()) 
	    {
	       	date = result.get();
	       	dialog.setContentText("Enter the time in the format HOUR:MIN:SEC :");
	       	result = dialog.showAndWait();
	       	if (result.isPresent()) 
		    {
		       	time = result.get();
		    }
	            
	    }
	   all_students_checked_before = AttendanceApp.list_all_students_checked_in_before(date, time, attendanceLog);
	   str = "O";
	   Platform.runLater(() -> t.setText("Finished running list_all_students_checked_in_before"));
	   
   }
   
   @FXML 
   private void handleAttendanceCount(ActionEvent event) {
	   
	   if (studentRoster == null || attendanceLog == null) {
		    t.setText("You must load the log and roster files before using this method.");
	        initializeAttendance();
	        return;
	    }
	   String num = "";
	   TextInputDialog dialog = new TextInputDialog();
	   
	   dialog.setContentText("Enter the number of classes: ");
	   Optional<String> result = dialog.showAndWait();
	   if(result.isPresent()) {
		   num = result.get();
	   }
	   nums = AttendanceApp.list_students_attendance_count(num, attendanceLog);
	   str = "P";
	   Platform.runLater(() -> t.setText("Finished running list_students_attendance_count"));
   }
   
   
   @FXML 
   private void handlePrintQuery(ActionEvent event)
   {
  
   	if(str == "G")
       {
   		t.setText("****** Students missing in class *************\n");
   		AttendanceApp.print_query_list(not_in_class,t);
       }
   	else if(str == "H")
   	{
   		t.setText("****** List all swipe in and out for a student *******\n");
   		AttendanceApp.print_query_list(times_in_and_out,t);
   	}
   	else if(str == "I")
   	{
   		t.setText("****** Check in times for all students who attended***\n");
   		AttendanceApp.print_query_list(all_times_checked_in,t);
   	}
   	else if(str == "J")
   	{
   		t.setText("****** Students that arrived late ********************\n");
   		AttendanceApp.print_query_list(late_students,t);
   	}
	else if(str == "N")
   	{
   		t.setText("**** Students present on this date ****\n");
   		AttendanceApp.print_query_list(all_students_checked_in, t);
   		
   	}
	else if(str == "O")
   	{
   		t.setText("**** Those present on date & before a time assigned ****\n");
   		AttendanceApp.print_query_list(all_students_checked_before, t);
   		
   	}
	else if(str == "P")
   	{
   		t.setText("**** Those who attended certain number of classes ****\n");
   		AttendanceApp.print_query_list(nums, t);
   		
   	}

   	
   }
   
   
   @FXML 
   private void handlePrintCount(ActionEvent event)
   {
  
   	if(str == "G")
    {
   		AttendanceApp.print_query_count(not_in_class,t);
    }
   	else if(str == "H")
   	{
   		AttendanceApp.print_query_count(times_in_and_out,t);
   	}
   	else if(str == "I")
   	{
   		AttendanceApp.print_query_count(all_times_checked_in,t);
   	}
   	else if(str == "J")
   	{
   		
   		AttendanceApp.print_query_count(late_students,t);
   	}
	else if(str == "N")
   	{
   		
   		AttendanceApp.print_query_count(all_students_checked_in, t);
   		
   	}
	else if(str == "O")
   	{
   		
   		AttendanceApp.print_query_count(all_students_checked_before, t);
   		
   	}
	else if(str == "P")
   	{
   		
   		AttendanceApp.print_query_count(nums, t);
   		
   	}

   	
   }
}
   
  
    
 

	
	
	
	

