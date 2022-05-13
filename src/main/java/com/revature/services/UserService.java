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

	protected UserDAO userDAO = new UserDAO();

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

	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}


}
