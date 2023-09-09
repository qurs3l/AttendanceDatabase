package application;

public class Student {
	private String firstName;
	private String lastName;
	
	public Student(String f, String l)
	{
		this.firstName = f;
		this.lastName = l;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String toString() 
	{
		String str = lastName + ", " + firstName + " ";
		return str;
	}
}
