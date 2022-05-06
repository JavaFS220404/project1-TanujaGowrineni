package com.revature.repositories;


import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    public Optional<User> getByUsername(String username) {
    	
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM ers_users WHERE ers_username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				User user = new User();
				user.setId(result.getInt("ers_users_id"));
				user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				user.setFirstname(result.getString("first_name"));
				user.setLastname(result.getString("last_name"));
				user.setEmail(result.getString("user_email"));
				
				int role_id = result.getInt("user_role_id");
				if (role_id == 1)
					user.setRole(Role.EMPLOYEE);
				else 
					user.setRole(Role.FINANCE_MANAGER);
				
				return Optional.of(user);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return  Optional.empty();

    }
    
    public Optional<User> getById(int userid) {
    	
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM ers_users WHERE ers_users_id = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, userid);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				User user = new User();
				user.setId(result.getInt("ers_users_id"));
				user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				user.setFirstname(result.getString("first_name"));
				user.setLastname(result.getString("last_name"));
				user.setEmail(result.getString("user_email"));
				
				int role_id = result.getInt("user_role_id");
				if (role_id == 1)
					user.setRole(Role.EMPLOYEE);
				else 
					user.setRole(Role.FINANCE_MANAGER);
				
				return Optional.of(user);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();

    }

    public Optional<User> getByEmail(String email) {
    	
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM ers_users WHERE user_email = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, email);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				User user = new User();
				user.setId(result.getInt("ers_users_id"));
				user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				user.setFirstname(result.getString("first_name"));
				user.setLastname(result.getString("last_name"));
				user.setEmail(result.getString("user_email"));
				
				int role_id = result.getInt("user_role_id");
				if (role_id == 1)
					user.setRole(Role.EMPLOYEE);
				else 
					user.setRole(Role.FINANCE_MANAGER);
				
				return Optional.of(user);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();

    }



    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     
    public User create(User userToBeRegistered) {
        return userToBeRegistered;*/
    

    public User create(User userToBeRegistered) {
    
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "INSERT INTO ers_users (ers_username, ers_password, "
					+ "first_name, last_name, user_email, user_role_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int count = 0;		
			statement.setString(++count, userToBeRegistered.getUsername());
			statement.setString(++count, userToBeRegistered.getPassword());
			statement.setString(++count, userToBeRegistered.getFirstname());
			statement.setString(++count, userToBeRegistered.getLastname());
			statement.setString(++count, userToBeRegistered.getEmail());
			Role role = userToBeRegistered.getRole();
			if (role == Role.EMPLOYEE)
				statement.setInt(++count, 1);
			else if (role == Role.FINANCE_MANAGER)
				statement.setInt(++count, 2);
				
			statement.execute();
			
			return userToBeRegistered;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
    public List<User> getAllUsers() {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM ers_users;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<User> list = new ArrayList<>();
			
			while(result.next()) {
				User user = new User();
				user.setId(result.getInt("ers_users_id"));
				user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				user.setFirstname(result.getString("first_name"));
				user.setLastname(result.getString("last_name"));
				user.setEmail(result.getString("user_email"));
				
				int role_id = result.getInt("user_role_id");
				if (role_id == 1)
					user.setRole(Role.EMPLOYEE);
				else 
					user.setRole(Role.FINANCE_MANAGER);
				
				list.add(user);
			}
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public boolean updateUser(User user) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "UPDATE ers_users SET ers_username=?, ers_password=?, "
					+ "first_name=?, last_name=?, user_email=?, user_role_id=? "
					+ "WHERE ers_users_id = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int count = 0;		
			statement.setString(++count, user.getUsername());
			statement.setString(++count, user.getPassword());
			statement.setString(++count, user.getFirstname());
			statement.setString(++count, user.getLastname());
			statement.setString(++count, user.getEmail());
			Role role = user.getRole();
			if (role == Role.EMPLOYEE)
				statement.setInt(++count, 1);
			else if (role == Role.FINANCE_MANAGER)
				statement.setInt(++count, 2);		
			statement.setInt(++count, user.getId());
			
			statement.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
