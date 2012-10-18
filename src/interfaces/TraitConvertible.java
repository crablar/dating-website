package interfaces;

import user_components.Trait;

/**
 * An interface which provides a means of encoding all data of an implementing object as a Trait.
 * 
 * @author jeffreymeyerson
 *
 */
public interface TraitConvertible {

	public Trait asSingleValueTrait();
	
}
