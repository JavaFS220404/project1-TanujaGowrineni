package com.revature.services;

import com.revature.exceptions.NewUserHasNonZeroIdException;
import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.exceptions.UserAuthenticationException;
import com.revature.exceptions.UsernameNotUniqueException;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

import java.util.Optional;

/**
 * The AuthService should handle login and registration for the ERS application.
 *
 * {@code login} and {@code register} are the minimum methods required; however,
 * additional methods can be added.
 *
 * Examples:
 * <ul>
 * <li>Retrieve Currently Logged-in User</li>
 * <li>Change Password</li>
 * <li>Logout</li>
 * </ul>
 */
public class AuthService {

	protected UserDAO userDAO = new UserDAO();

	/**
	 * <ul>
	 * <li>Needs to check for existing users with username/email provided.</li>
	 * <li>Must throw exception if user does not exist.</li>
	 * <li>Must compare password provided and stored password for that user.</li>
	 * <li>Should throw exception if the passwords do not match.</li>
	 * <li>Must return user object if the user logs in successfully.</li>
	 * </ul>
	 */
	public User login(String username, String password) throws UserAuthenticationException {

		// The user name or password is incorrect.
		Optional<User> userOptional = userDAO.getByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String userPassword = user.getPassword();
			if (userPassword != null && userPassword.equals(password)) {
				return user;
			} else {
				throw new UserAuthenticationException("Invalid username/password");
			}
		} else {
			throw new UserAuthenticationException("Invalid username/password");
		}
	}

	/**
	 * <ul>
	 * <li>Should ensure that the username/email provided is unique.</li>
	 * <li>Must throw exception if the username/email is not unique.</li>
	 * <li>Should persist the user object upon successful registration.</li>
	 * <li>Must throw exception if registration is unsuccessful.</li>
	 * <li>Must return user object if the user registers successfully.</li>
	 * <li>Must throw exception if provided user has a non-zero ID</li>
	 * </ul>
	 *
	 * Note: userToBeRegistered will have an id=0, additional fields may be null.
	 * After registration, the id will be a positive integer.
	 */
	public User register(User userToBeRegistered) {

		User user = null;

		if (userToBeRegistered.getId() == 0) { // Check if the provided user has a non-zero ID

			Optional<User> optUser = userDAO.getByUsername(userToBeRegistered.getUsername());

			if (optUser.isPresent()) {
				System.out.println(optUser.get().getId());
				throw new UsernameNotUniqueException(
						"Username is already exitst! Please try with new Username.");
			} else {
				
				user = userDAO.create(userToBeRegistered);

				if (user == null) {
					throw new RegistrationUnsuccessfulException("Could not register the User. Please try again!");
				}
			}

		} else {
			throw new NewUserHasNonZeroIdException("User-to-be-Registered has a non-zero ID!");
		}

		return user;
	}

	/**
	 * This is an example method signature for retrieving the currently logged-in
	 * user. It leverages the Optional type which is a useful interface to handle
	 * the possibility of a user being unavailable.
	 */
	public Optional<User> exampleRetrieveCurrentUser() {
		
		
		return Optional.empty();
	}
}
