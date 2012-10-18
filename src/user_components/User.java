package user_components;
import interfaces.TraitConvertible;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * The User class. Users have empirical features which are randomized by the
 * initializing Python program. Over time, Users are increasingly modeled in
 * terms of their responses to machine Suggestions.
 * 
 * @author jeffreymeyerson
 * 
 */

public class User implements TraitConvertible{

	private String userName;
	private static UndisclosedPreferences undisclosedPreferences;
	private DisclosedPreferences disclosedPreferences;
	private int numberOfTopMatches;
	
	// The most recent round of Suggestions that the system has offered to the User
	private Suggestions suggestions;
	
	// The set of traits that define this User
	private HashSet<Trait> traits;
	
	// The set of Users that have been seen by this User.
	private HashSet<User> examinedUsers;
	
	// The queue of top matches for this User; this size will never change
	private MatchQueue topMatches;
	
	// The most recently calculated prototype vector that represents a User's optimal match
	private User optimalMatch;
		
	public User(String userName, HashSet<Trait> traits, UndisclosedPreferences undisclosedPreferences){
		this.userName = userName;
		this.traits = traits;
		this.undisclosedPreferences = undisclosedPreferences;
		//TODO: modularity
		this.numberOfTopMatches = 3;
		String optMatchName = userName + "'s optimal match";
		if(undisclosedPreferences != null){
			optimalMatch = undisclosedPreferences.asUser(optMatchName); 
			topMatches = new MatchQueue(optimalMatch, numberOfTopMatches);
		}
	}

	@Override
	public Trait asSingleValueTrait() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserName() {
		return userName;
	}

	public HashSet<Trait> getTraits() {
		return traits;
	}

	public void setTraits(HashSet<Trait> traits) {
		this.traits = traits;
	}

	public Set<User> getExaminedUsers() {
		return examinedUsers;
	}

	public void setExaminedUsers(HashSet<User> examinedUsers) {
		this.examinedUsers = examinedUsers;
	}

	public MatchQueue getTopMatches() {
		return topMatches;
	}

	public User getOptimalMatch() {
		return optimalMatch;
	}

	public void setOptimalMatch() {
		this.optimalMatch = undisclosedPreferences.asUser(userName + "'s optimal match");
	}

	public void rankSuggestions() {
		// TODO Auto-generated method stub
		
	}

	public LinkedList<String> getTraitNames() {
		LinkedList<String> result = new LinkedList<String>();
		for(Trait trait : traits)
			result.add(trait.getName());
		return result;
	}
	
	
	
}
