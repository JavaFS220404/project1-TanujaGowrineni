package com.revature.services;

import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;

import java.util.Date;    
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The ReimbursementService should handle the submission, processing, and
 * retrieval of Reimbursements for the ERS application.
 *
 * {@code process} and {@code getReimbursementsByStatus} are the minimum methods
 * required; however, additional methods can be added.
 *
 * Examples:
 * <ul>
 * <li>Create Reimbursement</li>
 * <li>Update Reimbursement</li>
 * <li>Get Reimbursements by ID</li>
 * <li>Get Reimbursements by Author</li>
 * <li>Get Reimbursements by Resolver</li>
 * <li>Get All Reimbursements</li>
 * </ul>
 */
public class ReimbursementService {

	protected ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
	protected UserDAO userDAO = new UserDAO();

	/*
	 * Inserts a new Reimbursement record into the DB with the provided information.
	 * Throws an exception if the creation is unsuccessful. Returns a Reimbursement
	 * object with an updated ID.
	 */
	public Reimbursement createReimbursement(Reimbursement reimbursement) {
		
		Reimbursement  newReimbursement = reimbursementDAO.createReimbursement(reimbursement);
		if (newReimbursement == null) {
			throw new RegistrationUnsuccessfulException("Could not register the User. Please try again!");
			//Change the exception 
			//Change the exception 
			//Change the exception 
		}else {
			return newReimbursement;
		}

	}

	/**
	 * <ul>
	 * <li>Should ensure that the user is logged in as a Finance Manager</li>
	 * <li>Must throw exception if user is not logged in as a Finance Manager</li>
	 * <li>Should ensure that the reimbursement request exists</li>
	 * <li>Must throw exception if the reimbursement request is not found</li>
	 * <li>Should persist the updated reimbursement status with resolver
	 * information</li>
	 * <li>Must throw exception if persistence is unsuccessful</li>
	 * </ul>
	 *
	 * Note: unprocessedReimbursement will have a status of PENDING, a non-zero ID
	 * and amount, and a non-null Author. The Resolver should be null. Additional
	 * fields may be null. After processing, the reimbursement will have its status
	 * changed to either APPROVED or DENIED.
	 */
	public Reimbursement process(Reimbursement unprocessedReimbursement, Status finalStatus, User resolver) {

		User reimbResolver = userDAO.getById(resolver.getId()).get();
		Role userRole = reimbResolver.getRole();
		if (userRole == Role.FINANCE_MANAGER) {

			Optional<Reimbursement> reimbursement = reimbursementDAO.getById(unprocessedReimbursement.getId());
			if (reimbursement.isPresent()) {
				
				Date date = new Date(); 			 
	            Timestamp resolvedDate = new Timestamp(date.getTime());  
	                
				unprocessedReimbursement.setStatus(finalStatus);
				unprocessedReimbursement.setResolver(resolver);
				unprocessedReimbursement.setResolutionDate(resolvedDate);
				reimbursementDAO.update(unprocessedReimbursement);
				return unprocessedReimbursement;
				
			} else {
				// throw exception
			}

		} else {
			// throw exception
		}

		return null;
	}

	/**
	 * Should retrieve all reimbursements with the correct status.
	 */
	public List<Reimbursement> getReimbursementsByStatus(Status status) {

		return reimbursementDAO.getByStatus(status);
	}
}
