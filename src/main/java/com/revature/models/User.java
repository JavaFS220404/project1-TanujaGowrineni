package com.revature.models;

/**
 * This concrete User class can include additional fields that can be used for
 * extended functionality of the ERS application.
 *
 * Example fields:
 * <ul>
 * <li>First Name</li>
 * <li>Last Name</li>
 * <li>Email</li>
 * <li>Phone Number</li>
 * <li>Address</li>
 * </ul>
 *
 */
public class User extends AbstractUser {

	private String firstname;
	private String lastname;
	private String email;

	public User() {
		super();
	}

	/**
	 * This includes the minimum parameters needed for the
	 * {@link com.revature.models.AbstractUser} class. If other fields are needed,
	 * please create additional constructors.
	 */
	public User(int id, String username, String password, Role role) {
		super(id, username, password, role);
	}
	
	public User(int id, String username, String password, Role role, String firstname, String lastname, String email ) {
		super(id, username, password, role);
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
