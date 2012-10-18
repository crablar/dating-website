package user_components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import functions.UserComparison;

/**
 * A queue that keeps at the head the User closest in similarity to the optimal match of the User who
 * this queue belongs to.
 * 
 * @author jeffreymeyerson
 * 
 */

public class MatchQueue {

	private ArrayList<User> queue;
	private int size;
	private User owningUsersOptimalMatch;
	private User mostRecentlyEvicted;

	public MatchQueue(User owningUsersOptimalMatch, int size) {
		this.owningUsersOptimalMatch = owningUsersOptimalMatch;
		queue = new ArrayList<User>();
		this.size = size;
	}

	/**
	 * Attempts to enqueue the parameter user.
	 * 
	 * @param userToBeQueued
	 * @return Whether or not the User was queued successfully.
	 */
	public boolean enqueue(User userToBeQueued) {
		boolean queuedSuccessfully = false;

		// Find the disparity between the user to be queued and the owning user
		double disparityForParameterUser = disparityBetweenOwningUserAndOtherUser(userToBeQueued);

		// Iterate through the queue looking for a user with a higher disparity
		for (int i = 0; i < size; i++) {

			// Get the User at index i
			User userInQueue = queue.get(i);

			// Find the disparity between userInQueue and the userToBeQueued
			double disparityForUserInQueue = disparityBetweenOwningUserAndOtherUser(userInQueue);

			// If the disparity of the user already in the queue is greater than
			// that of the parameter user,
			// add the userToBeQueued to the queue
			if (disparityForUserInQueue > disparityForParameterUser){
				queue.add(i, userToBeQueued);
				
				// If the queue is too big, evict a User and shrink the queue
				if(queue.size() > size){
					mostRecentlyEvicted = queue.get(size);
					queue = (ArrayList<User>) queue.subList(0, size);
				}
				queuedSuccessfully = true;
				break;
			}

		}

		return queuedSuccessfully;
	}

	/**
	 * Find the disparity between the owningUser and another User.
	 * 
	 * @param otherUser
	 * @return The value of the disparity.
	 */
	public double disparityBetweenOwningUserAndOtherUser(User otherUser) {
		return UserComparison.getDisparity(owningUsersOptimalMatch, otherUser);
	}

	//TODO
	public MatchQueue mergeWithOtherQueue(MatchQueue other) {
		return null;
	}

}
