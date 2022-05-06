package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.models.Role;
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

	/*
	 * Should retrieve a User with the corresponding username or an empty optional
	 * if there is no match.
	 */
	public Optional<User> getByUsername(String username) {
		return userDAO.getByUsername(username);
	}

	/*
	 * Should retrieve a User with the corresponding UserID or an empty optional if
	 * there is no match.
	 */
	public Optional<User> getById(int userid) {
		return userDAO.getById(userid);
	}

	/*
	 * Should retrieve a User with the corresponding Email or an empty optional if
	 * there is no match.
	 */
	public Optional<User> getByEmail(String email) {
		return userDAO.getByEmail(email);
	}

	// Returns list of all Users
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	// Update an existing User information
	public boolean updateUser(User user) {
		if (userDAO.updateUser(user)) {
			return true;
		}
		return false;
	}

	/*
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
	public boolean createUser(String username, String password, String userrole, String firstname, String lastname,
			String email) {
		int userId = 0;

		User user = null;
		if (userrole.equals("EMPLOYEE"))
			user = new User(userId, username, password, Role.EMPLOYEE, firstname, lastname, email);
		else if (userrole.equals("FINANCE MANAGER"))
			user = new User(userId, username, password, Role.FINANCE_MANAGER, firstname, lastname, email);

		user = userDAO.create(user);
		if (user != null) {
			return true;
		}
		return false;
	}

	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
}
