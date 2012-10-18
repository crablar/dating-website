package user_components;
import java.util.HashSet;

/**
 * A wrapper for a set of preferences that collate the traits that a user prefers.
 * 
 * @author jeffreymeyerson
 * 
 */
public class DisclosedPreferences extends Preferences{
	
	public DisclosedPreferences(HashSet<Trait> traits){
		preferredTraits = traits;
		disclosed = false;
	}

	@Override
	public User asUser(String userName) {
		return new User(userName, preferredTraits, null);
	}
	
}
