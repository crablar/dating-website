package user_components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The UserFeedback class. A UserFeedback object is a means of communicating
 * information that is implicit in the User's processing of Suggestions.
 * 
 * @author jeffreymeyerson
 * 
 */
public class UserFeedback {

	private FileWriter out;
	private Scanner scanner;
	
	public UserFeedback() throws IOException {
			out = new FileWriter(new File("results.txt"));
			out.append("*******\nResults\n*******");
			out.flush();		
	}

	public void print() throws FileNotFoundException{
		scanner = new Scanner(new File("results.txt"));
		while(scanner.hasNext())
			System.out.println(scanner.next());
	}
	
}
