package user_components;
import interfaces.TraitConvertible;
import interfaces.UserConvertible;

import java.util.HashSet;

/**
 * A wrapper for a set of Traits that can be converted into a single trait.
 * 
 * @author jeffreymeyerson
 *
 */
public abstract class Preferences implements TraitConvertible, UserConvertible{
	
	protected HashSet<Trait> preferredTraits;
	protected boolean disclosed;
	
	public Trait asSingleValueTrait(){
		Trait[] traits = (Trait[]) preferredTraits.toArray();
		Double[] traitValues = new Double[traits.length];
		String[] traitNames = new String[traits.length];
		for(int i = 0; i < traits.length; i++){
			traitNames[i] = traits[i].getName();
			traitValues[i] = traits[i].getValue();
		}
		Trait result = Trait.multiValueTrait(traitNames, traitValues);
		return result.asSingleValueTrait();
	}
	

}
