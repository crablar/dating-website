package user_components;
import java.util.HashSet;



/**
 * 
 * A set of Users who have been calculated to be the top three choices for a
 * User based on their similarity to the OptimalMatch prototype vector.
 * 
 * @author jeffreymeyerson
 * 
 */
public class Suggestions {

	HashSet<User> suggestedUsers;
	/**
	 * Create a Suggestions object
	 * 
	 * @param user
	 * @param users
	 */
	public Suggestions(User user, HashSet<User> users) {
		
		this.suggestedUsers = new HashSet<User>();
		
		// Initialize a MatchQueue to serve as the repository for closest matches
		MatchQueue matchQueue = new MatchQueue(user, 3);
		
		
		for(User potentialSuggestion : users){
			matchQueue.enqueue(potentialSuggestion);
			
		}
		
	}

}
