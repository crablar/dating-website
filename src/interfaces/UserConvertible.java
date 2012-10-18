package interfaces;

import user_components.User;

/**
 * An interface which provides a means for the implementing object to be interpreted as a User.
 * @author jeffreymeyerson
 *
 */

public interface UserConvertible {
	
	/**
	 * Return the implementing object as a User.
	 * 
	 * @param userName
	 * @return
	 */
	public User asUser(String userName);
	
}
