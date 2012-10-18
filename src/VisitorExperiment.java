import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import user_components.Suggestions;
import user_components.Trait;
import user_components.UndisclosedPreferences;
import user_components.User;
import user_components.UserFeedback;

/**
 * @author jeffreymeyerson
 * 
 *         The VisitorExperiment. This class contains the main method. The
 *         experiment is described in detail in ReadMe.
 * 
 */
public class VisitorExperiment {

	/**
	 * Defines how many iterations of suggestion and ranking will be performed.
	 */
	final static int NUMBER_OF_ITERATIONS = 1000;

	/**
	 * At the beginning of each iteration in main, a number of suggestions are
	 * presented to the user, which the user ranks based on his undisclosed
	 * preferences.
	 */
	final static int NUMBER_OF_SUGGESTIONS_TO_PRESENT_TO_USER = 3;

	public static void main(String[] args) throws IOException {

		// Create dynamic instance of NUMBER_OF_ITERATIONS
		int numIterations = NUMBER_OF_ITERATIONS;

		// Initialize Users
		HashSet<User> users = initializeUsers();

		// Create map of average Trait values from among Users as a population
		HashSet<Trait> averageTraitValues = getAverageTraitValues(users);

		// Initialize the current iteration
		int iterationNumber = 0;

		// Initialize the UserFeedback object that will be modified over time
		final UserFeedback userFeedback = new UserFeedback();

		while (iterationNumber < numIterations) {
			
			for(User user : users){
				// Provide suggestions to each User
				makeSuggestions(user);
				// Have the User rank his suggestions
				user.rankSuggestions();
				// Derive UserFeedback based on how a User ranks his Suggestions
				deriveUserFeedback(user);
			}
			
			iterationNumber++;

		}
		
		userFeedback.print();

	}

	/**
	 * Parse users.txt and create users from it
	 * 
	 * @return a set of User objects
	 * @throws FileNotFoundException
	 */
	private static HashSet<User> initializeUsers() throws FileNotFoundException {

		HashSet<User> result = new HashSet<User>();
		
		// Create a scanner for both files
		Scanner traitScanner = new Scanner(new File("user_traits.txt"));
		Scanner preferenceScanner = new Scanner(new File("user_preferences.txt"));

		boolean endOfFile = false;
		
		String traitsLine = traitScanner.nextLine();
		System.out.println(traitsLine);
		String preferenceLine = preferenceScanner.nextLine();

		
		while (!endOfFile) {
			
			// Get the user name
			String userName = traitsLine.substring(1);

			// Initialize an empty set of Traits that will describe the User
			HashSet<Trait> userTraits = new HashSet<Trait>();

			traitsLine = traitScanner.nextLine();
			//System.out.println("TraitScanner: " + traitsLine);
			
			// Define User traits until you hit a new line
			while (!traitsLine.equals("-")) {
				//System.out.println("TraitScanner: " + traitsLine);
				// Break up the line and define its components
				String[] brokenLine = traitsLine.split(" ");
				String traitName = brokenLine[0];
				Double traitVal = new Double(brokenLine[1]);

				// Define a Trait for the line
				Trait trait = Trait.singleValueTrait(traitName, traitVal);

				// Add the Trait to the user's set of Traits
				userTraits.add(trait);
				
				traitsLine = traitScanner.nextLine();
			}
			
			traitsLine = traitScanner.nextLine();
			
			//System.out.println("TraitScanner: " + traitsLine);

			// Initialize an empty set of Traits that will describe UndisclosedPreferences
			HashSet<Trait> preferenceSet = new HashSet<Trait>();

			// Skip past a name and to the first preference in a set of preferences
			preferenceLine = preferenceScanner.nextLine();
			
			// Define User preferences until you hit a new line
			while (!preferenceLine.equals("-")) {
				//System.out.println("PreferenceScanner: " + preferenceLine);

				// Break up the line and define its components
				String[] brokenLine = preferenceLine.split(" ");
				String traitName = brokenLine[0];
				Double traitVal = new Double(brokenLine[1]);

				// Define a Trait for the line
				Trait trait = Trait.singleValueTrait(traitName, traitVal);

				// Add the Trait to the user's set of preferences
				preferenceSet.add(trait);
				
				preferenceLine = preferenceScanner.nextLine();


			}

			// Make preferences from traits
			UndisclosedPreferences undisclosedPreferences = new UndisclosedPreferences(preferenceSet);
			
			// Define the User
			User user = new User(userName, userTraits, undisclosedPreferences);
			
			// Add the User to the result
			result.add(user);

			// Skip past "-"
			preferenceLine = preferenceScanner.nextLine();
			//System.out.println("PreferenceScanner: " + preferenceLine);

			
			if(preferenceLine.equals("eof"))
				endOfFile = true;
		}

		return result;
	}

	/**
	 * Calculate the average value of that Trait from among the population.
	 * 
	 * @param users
	 * @return a Trait set with values equal to that of the average from within
	 *         the population
	 */
	private static HashSet<Trait> getAverageTraitValues(HashSet<User> users) {
		HashSet<Trait> result = new HashSet<Trait>();

		// A map that tracks the number of Users who have each trait
		HashMap<String, Integer> traitPresenceCounts = new HashMap<String, Integer>();

		// A map that tracks the sum of the Trait values across the population
		HashMap<String, Double> traitValueSums = new HashMap<String, Double>();

		for (User user : users) {

			// Get the set of Traits that describe the User
			HashSet<Trait> traitSet = user.getTraits();

			for (Trait trait : traitSet) {

				String traitName = trait.getName();

				if (traitPresenceCounts.containsKey(traitName)) {

					// Add one to the number of people in the population with
					// that Trait
					int currentCount = traitPresenceCounts.get(traitName);
					currentCount++;
					traitPresenceCounts.put(traitName, currentCount);

					// Get the User's value of the trait
					double userTraitValue = trait.getValue();

					// Get the population's running total of the Trait
					double populationTraitValue = traitValueSums.get(traitName);

					// Add the user's value to the running total and put it back
					// into the map
					populationTraitValue += userTraitValue;
					traitValueSums.put(traitName, populationTraitValue);

				}
			}
		}

		// Create the result
		for (String traitName : traitValueSums.keySet()) {

			// Get the summation of a Trait value across the population
			double totalSum = traitValueSums.get(traitName);

			// Get the number of people with that Trait from across the
			// population
			int numPeopleWithTrait = traitPresenceCounts.get(traitName);

			// Calculate the average and put it into result as a trait
			double average = totalSum / numPeopleWithTrait;
			Trait trait = Trait.singleValueTrait(traitName, average);
			result.add(trait);

		}
		return result;
	}

	private static UserFeedback deriveUserFeedback(User user) throws IOException {
		UserFeedback result = new UserFeedback();
		return result;
	}

	private static void makeSuggestions(User user) {
		// TODO Auto-generated method stub
	}

	private static void processFeedback(UserFeedback feedback) {
		// TODO Auto-generated method stub
		/**
		 * For each User, find the Traits that the user likes that are uncommon
		 */

	}

}
