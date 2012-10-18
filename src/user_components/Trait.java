package user_components;

import interfaces.TraitConvertible;

/**
 * The Trait class. In a single-value trait such as those defined at the
 * beginning of the experiment, a Trait is simply a name and a value. A
 * multi-value trait consists of a number of trait names whose correspondent
 * values form a sequential ratio.
 * 
 * @author jeffreymeyerson
 * 
 */
public class Trait implements TraitConvertible{

	/*
	 * ie:  skin_tone
	 * 	    (leg_length to tongue_symmetry)
	 * 		(skin_tone to (leg_length to tongue_symmetry))
	 */
	private static String name;
	private static Double value;

	public static Trait singleValueTrait(String traitName, Double val) {
		Trait trait = new Trait();
		trait.name = traitName;
		trait.value = 1.0;
		return trait;
	}

	public static Trait multiValueTrait(String[] traitNames, Double[] traitValues) {
		Trait trait = new Trait();
		if (traitNames.length != traitValues.length)
			throw new IllegalArgumentException();
		trait.name = '(' + traitNames[0];
		for (int i = 0; i < traitNames.length; i++) {
			name += " to " + traitNames[i];
			value /= traitValues[i];
		}
		trait.name += ')';
		return trait;
	}

	@Override
	public Trait asSingleValueTrait() {
		return singleValueTrait(name, value);
	}

	public Double getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

}
