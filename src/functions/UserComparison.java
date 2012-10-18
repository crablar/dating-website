package functions;

import java.util.HashMap;
import java.util.HashSet;

import user_components.Trait;
import user_components.User;

/**
 * A collection of functions which compare users.
 * 
 * @author jeffreymeyerson
 *
 */
public class UserComparison {

	/**
	 * Returns a similarity measurement from 0 to 10.  This is determined by calculating the disparity between the users 
	 * across each trait that they both have enumerated, averaging those differences, and subtracting it from 10.
	 */
	public static double getDisparity(User userA, User userB){
		
		// Get the disparities between Trait values of each Trait that is in the intersection of the two Trait sets
		HashSet<Trait> traitDisparities = getDisparities(userA, userB);
		double total = 0;
		for(Trait trait : traitDisparities)
			total += trait.getValue();
		return total / (Math.max(1, traitDisparities.size()));
	}

	/**
	 * Calculates the absolute values of Trait differences between the two Users.
	 * 
	 * @param userA
	 * @param userB
	 * @return A Set of Traits that represent the absolute values of Trait disparities.
	 */
	private static HashSet<Trait> getDisparities(User userA, User userB) {
		HashSet<Trait> result = new HashSet<Trait>();
		
		// Create maps of the traits
		HashMap<String, Double> traitMapA = new HashMap<String, Double>();
		HashMap<String, Double> traitMapB = new HashMap<String, Double>();
		for(Trait trait : userA.getTraits())
			traitMapA.put(trait.getName(), trait.getValue());
		for(Trait trait : userB.getTraits())
			traitMapB.put(trait.getName(), trait.getValue());
		
		// Find Traits enumerated by both
		for(String traitName : traitMapA.keySet()){
			if(traitMapB.containsKey(traitName)){
				// If enumerated by both, calculate the disparity 
				double valA = traitMapA.get(traitName);
				double valB = traitMapB.get(traitName);
				Double disparity = Math.abs(valA - valB);
				Trait trait = Trait.singleValueTrait(traitName, disparity);
				result.add(trait);
			}
		}
		
		return result;
	}

}
