package application;
import java.util.ArrayList;
import java.util.List;

public class Log {
	private String firstName;
	private String lastName;
	private String date;
	private String time;
	private List<String> timeStamps;
	
	public Log(String f, String l)
	{
		this.firstName = f;
		this.lastName = l;
		timeStamps = new ArrayList<>();
	}
	

    public void addTimeStamp(String time, String date) 
    {
        String temp = time + ", " + date;
        timeStamps.add(temp);
    }
	
    
	public String getFirstName() 
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public List<String> getTimeStamps()
	{
		return timeStamps;
	}
	
	public String getDate(int index)
	{
		if(index < 0 || index > timeStamps.size())
			System.out.print("out of bounds");
		
		String timestamp = timeStamps.get(index);
	    String[] parts = timestamp.split(",\\s+");
	    return parts[1];
		
	}
	
	public String getTime(int index)
	{
		if(index < 0 || index > timeStamps.size())
			System.out.print("out of bounds");
		
		String timestamp = timeStamps.get(index);
	    String[] parts = timestamp.split(",\\s+");
	    return parts[0];
	}
	
	public String toString() 
	{
		String str = lastName + ", " + firstName + " ";
	    for (String dateTime : timeStamps) 
	    {
	        str += dateTime + ", ";
	    }
	    // remove the last comma and space
	    str = str.substring(0, str.length() - 2);
	    return str;
    }
	
	

}
