package com.revature.services;

import java.util.Optional;

import com.revature.models.User;
import com.revature.repositories.UserDAO;

/**
 * The UserService should handle the processing and retrieval of Users for the
 * ERS application.
 *
 * {@code getByUsername} is the only method required; however, additional
 * methods can be added.
 *
 * Examples:
 * <ul>
 * <li>Create User</li>
 * <li>Update User Information</li>
 * <li>Get Users by ID</li>
 * <li>Get Users by Email</li>
 * <li>Get All Users</li>
 * </ul>
 */
public class UserService {

	UserDAO userDAO;
	
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * Should retrieve a User with the corresponding username or an empty optional 
	 * if there is no match.
	 */
	public Optional<User> getByUsername(String username) {
		return userDAO.getByUsername(username);
	}

	// * Tanuja 19/4/22 - begin

	/**
	 * Should retrieve a User with the corresponding UserID or an empty optional if
	 * there is no match.
	 */
	public Optional<User> getByUserID(int userid) {
		return Optional.empty();
	}

	/**
	 * Should retrieve a User with the corresponding Email or an empty optional if
	 * there is no match.
	 */
	public Optional<User> getByEmail(String email) {
		return Optional.empty();
	}

	/**
	 * <ul>
	 * <li>Should Insert a new User record into the DB with the provided
	 * information.</li>
	 * <li>Should throw an exception if the creation is unsuccessful.</li>
	 * <li>Should return a User object with an updated ID.</li>
	 * </ul>
	 *
	 * Note: The userToBeRegistered will have an id=0, and username and password
	 * will not be null. Additional fields may be null.
	 */
	public User create(User userToBeRegistered) {
		return userToBeRegistered;
	}

	// Update an existing User information
	public User update(User userToBeUpdated) {
		return userToBeUpdated;
	}

	// Returns list of all Users
	public User getAllUsers() {
		return null;
	}

	// * Tanuja 19/4/22 - end

}
