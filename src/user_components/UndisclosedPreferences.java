package user_components;
import java.util.HashSet;

/**
 * A wrapper for a set of Traits that the user desires, but has not defined to the system.
 * 
 * @author jeffreymeyerson
 * 
 */
public class UndisclosedPreferences extends Preferences {

	public UndisclosedPreferences(HashSet<Trait> traits){
		preferredTraits = traits;
		disclosed = true;
	}
	
	public User asUser(String userName){
		return new User(userName, preferredTraits, null);
	}

}
