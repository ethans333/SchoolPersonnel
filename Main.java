import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println(
				"\t\tWelcome to my Personal Management Program\n\n\n" +
				"Choose one of the options:\n\n"
		);
		
		String res;
		int mres = -1;
		
		ArrayList<Person> p = new ArrayList<Person>();
		
		while (mres != 7) {
			
			printMenu();
			
			res = s.nextLine();
			mres = res.matches(".*\\d.*") ? Integer.parseInt(res) : -1;
			
			switch (mres) {
				case 1: // Enter Info Faculty
					p.add(createFaculty(s, p));
					break;
				case 2: // Enter Info Student
					p.add(createStudent(s, p));
					break;
				case 3: // Print Invoice Student
					Student st = (Student)IdLookup(s, p, "Student");
					if (st != null) st.print(); else System.out.println("No Student found!");
					break;
				case 4: // Print Info Faculty
					Faculty f = (Faculty)IdLookup(s, p, "Faculty");
					if (f != null) f.print(); else System.out.println("No Faculty found!");
					break;
				case 5: // Enter Info Staff
					p.add(createStaff(s, p));
					break;
				case 6: // Print Info Staff
					Staff stf = (Staff)IdLookup(s, p, "Staff");
					if (stf != null) stf.print(); else System.out.println("No Staff found!");
					break;
				case 7: // Exit
					createReport(s, p, "C:\\Users\\games\\Desktop\\Report.txt");
					break;
				default:
					System.out.println("\nInvalid entry - Please try again");
					break;
			} 
		}
		
	}
	
	static void printMenu () {
		System.out.println(
				"\n"+
				"1-  Enter the information a faculty\n" +
				"2-  Enter the information of a student\n" +
				"3-  Print tuition invoice for a student\n" +
				"4-  Print faculty information\n" +
				"5-  Enter the information of a staff member\n" +
				"6-  Print the information of a staff member\n" +
				"7-  Exit Program\n\n" +
				"\tEnter your selection: "
		);
	}
	
	static Faculty createFaculty (Scanner s, ArrayList<Person> p) {
		
		Faculty f = new Faculty();
		
		String res;
		
		System.out.println("Enter the faculty info: \n");
		
		System.out.println("Name: ");
		res = s.nextLine();
		f.setName(res);
	
		f.setId(getValidID(s, p));
		
		String[] ranks = {"Professor", "Adjunct"};
		f.setRank(getValidRes(s, "Rank", ranks));
		
		String[] departments = {"Mathematics", "Engineering", "Sciences"};
		f.setDepartment(getValidRes(s, "Department", departments));
		
		System.out.println("Faculty added!");
		
		return f;
	}
	
	static Student createStudent (Scanner s, ArrayList<Person> p) {
		
		Student st = new Student();
		
		String res;
		
		System.out.println("Enter the Student info: \n");
		
		System.out.println("Name: ");
		res = s.nextLine();
		st.setName(res);
	
		st.setId(getValidID(s, p));
		
		do {
			System.out.println("GPA: ");
			
			res = s.nextLine();
			
			double gpa;
			
			try {
				gpa = Double.parseDouble(res);
				if (gpa > 4.0 || gpa < 0) throw new NumberFormatException();
				st.setGpa(gpa);
				break;
			} catch (NumberFormatException e) {
				System.out.println("\"" + res + "\" is invalid");
			}
		} while (true);
		
		do {
			System.out.println("Credit Hours: ");
			
			res = s.nextLine();
			
			int ch;
			
			try {
				ch = Integer.parseInt(res);
				if (ch < 0) throw new NumberFormatException();
				st.setCh(ch);
				break;
			} catch (NumberFormatException e) {
				System.out.println("\"" + res + "\" is invalid");
			}
		} while (true);
		
		System.out.println("Student added!");
		
		return st;
	}
	
	static Staff createStaff (Scanner s, ArrayList<Person> p) {
		
		Staff st = new Staff();
		
		String res;
		
		System.out.println("Enter the Student info: \n");
		
		System.out.println("Name: ");
		res = s.nextLine();
		st.setName(res);
	
		st.setId(getValidID(s, p));
		
		String[] departments = {"Mathematics", "Engineering", "Sciences"};
		st.setDepartment(getValidRes(s, "Department", departments));
		
		String[] status = {"P", "F"};
		st.setStatus(getValidRes(s, "Status, Enter P for Part Time, or Enter F for Full Time", status));
		if (st.getStatus().equalsIgnoreCase("P")) st.setStatus("Part Time");
		else if (st.getStatus().equalsIgnoreCase("F")) st.setStatus("Full Time");
		
		System.out.println("Staff member added!");
		
		return st;
	}
	
	static String getValidID (Scanner s, ArrayList<Person> p) {
		
		String res;
		
		do {
			System.out.println("ID: ");
			
			res = s.nextLine();
			
			try {
				
				if (!(
						res.length() == 6 &&
						Character.isLetter(res.charAt(0)) &&
						Character.isLetter(res.charAt(1)) &&
						Character.isDigit(res.charAt(2)) &&
						Character.isDigit(res.charAt(3)) &&
						Character.isDigit(res.charAt(4)) &&
						Character.isDigit(res.charAt(5))
						
				)) throw new IdException("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit\n");
				else {
					for (Person per: p) {
						if (res.equalsIgnoreCase(per.getId())) throw new IdException("Invalid: \"" + res + "\" already exists");
					}
					
					break;
				}
				
			} catch (IdException e) {
				continue;
			}
			
		} while (true);
		
		return res;
	}
	
	static String getValidRes (Scanner s, String label, String[] arr) {
		
		String res;
		
		do {
			System.out.println(label + ": ");
			res = s.nextLine();
			
			for (String word: arr) {
				if (res.equalsIgnoreCase(word) ) {
					return word;
				}
			}
			
			System.out.println("\t\"" + res + "\" is invalid");
			
		} while (true);
	}
	
	static Person IdLookup (Scanner s, ArrayList<Person> p, String type) {
		String res;
		
		System.out.println("Enter the " + type + "'s id: ");
		res = s.nextLine();
		
		for (Person per: p) {
			if (res.equalsIgnoreCase(per.getId())) return per;
		}
		
		return null;
	}
	
	static void createReport (Scanner s, ArrayList<Person> p, String path) {
		
		String res;
		
		String[] optYN = {"Y", "N"};
		res = getValidRes(s, "Would you like to create a report (Y/N)", optYN);
		
		if (res.equalsIgnoreCase("Y")) {
			
		    try {
		    	
		        FileWriter w = new FileWriter(path);
		        
		        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		        LocalDateTime now = LocalDateTime.now();  
		        
		        w.write("\n\nReport Created On "+ dtf.format(now) +"\n***********************\n");
		        
		        int i = 1;
		        
		        w.write("\n\nFaculty Members\n-------------------------\n");
		        
		        for (Person per: p) {
		        	if (per instanceof Faculty) {
		        		w.write(
			        			i+". " + per.getName() + "\n" +
			        			"ID: " + per.getId() + "\n" +
			        			((Faculty)per).getRank() + ", " + ((Faculty)per).getDepartment() + "\n\n"
			        	);
		        		
		        		i++;
		        	}
		        }
		        
		        w.write("\n\nStaff Members\n-------------------------\n");
		        
		        i = 1;
		        
		        for (Person per: p) {
		        	if (per instanceof Staff) {
		        		w.write(
			        			i+". " + per.getName() + "\n" +
			        			"ID: " + per.getId() + "\n" +
			        			((Staff)per).getDepartment() + ", " + ((Staff)per).getStatus() + "\n\n"
			        	);
		        		
		        		i++;
		        	}
		        }
		        
				String[] opt12 = {"1", "2"};
				res = getValidRes(s, "Would you like to sort your Students by (1) GPA or (2) Credit Hours", opt12);
				
				ArrayList<Student> S = new ArrayList<Student>();
				
				for (Person per: p) {
					if (per instanceof Student) S.add((Student)per);
				}
				
				if (res.equalsIgnoreCase("1")) {
					w.write("\n\nStudents (Sorted by GPA)\n-------------------------\n");
					Collections.sort(S, new StudentGpaComparator());
					
				} else {
					w.write("\n\nStudents (Sorted by Credit Hours)\n-------------------------\n");
					Collections.sort(S, new StudentCreditComparator());
				}
				
				i = 1;
				
				for (Student st: S) {
					w.write(
		        			i+". " + st.getName() + "\n" +
		        			"ID: " + st.getId() + "\n" +
		        			"GPA: " + st.getGpa() + "\n" + 
		        			"Credit Hours: " + st.getCh() + "\n\n"
		        	);
					
					i++;
				}
		        
		        w.close();
		        
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
		}
		
		System.out.println("Goodbye!");
	}
	
}

class StudentGpaComparator implements Comparator<Student> {

	public int compare(Student a, Student b) {
		if (a.getGpa() < b.getGpa()) return 1;
		if (a.getGpa() == b.getGpa()) return 0;
		if (a.getGpa() > b.getGpa()) return -1;
		
		return 0;
	}
}

class StudentCreditComparator implements Comparator<Student> {
	public int compare(Student a, Student b) {
		if (a.getCh() < b.getCh()) return 1;
		if (a.getCh() == b.getCh()) return 0;
		if (a.getCh() > b.getCh()) return -1;
		
		return 0;
	}
}

class IdException extends Exception {
	public IdException (String err) {
		super();
		System.out.println(err);
	}
}

class Person {
	private String name, id;
	
	public Person () {
		this.name = null;
		this.id = null;
	}

	public Person(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

class Student extends Person {
	private double gpa;
	private int ch;
	
	public Student() {
		super();
		this.gpa = -1;
		this.ch = -1;
	}
	
	public Student(String name, String id, double gpa, int ch) {
		super(name, id);
		this.gpa = gpa;
		this.ch = ch;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}
	
	public void print() {
		
		double total = 236.45 * this.getCh() + 52;
		
		double discount = (this.gpa >= 3.85) ? total * 0.25 : 0;
		
		total = Math.round((total-discount) * 100.0) / 100.0;
		
		System.out.println(
				"Here is the tuition invoice for " + this.getName() + " :\n\n"
				+ "---------------------------------------------------------------------------\n"
				+ this.getName() + "\t" + this.getId() + "\n"
				+ "Credit Hours: "  + this.getCh() + " ($236.45/credit hour)\n"
				+ "Fees: $52\n\n"
				+ "Total payment: " + total + "\t($" + discount + " discount applied)\n"
				+ "---------------------------------------------------------------------------\n"
		);
		
	}
	
}

class Employee extends Person {
	private String department;

	public Employee() {
		super();
		this.department = null;
	}
	
	public Employee(String name, String id, String department) {
		super(name, id);
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}

class Faculty extends Employee {
	private String rank;

	public Faculty() {
		super();
		this.rank = null;
	}

	public Faculty(String name, String id, String department, String rank) {
		super(name, id, department);
		this.rank = rank;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public void print() {
		System.out.println(
				"---------------------------------------------------------------------------\n"
				+ this.getName() + "\t" + this.getId() + "\n"
				+ this.getDepartment() + " Department, " + this.getRank() + "\n"
				+ "---------------------------------------------------------------------------\n"
		);
	}
	
}

class Staff extends Employee {
	private String status;

	public Staff() {
		super();
		this.status = null;
	}
	
	public Staff(String name, String id, String department, String status) {
		super(name, id, department);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void print() {
		System.out.println(
				"---------------------------------------------------------------------------\n"
				+ this.getName() + "\t" + this.getId() + "\n"
				+ this.getDepartment() + " Department, " + this.getStatus() + "\n"
				+ "---------------------------------------------------------------------------\n"
		);
	}
	
}












































