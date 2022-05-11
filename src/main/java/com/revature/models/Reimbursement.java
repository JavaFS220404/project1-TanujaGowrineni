package com.revature.models;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 * This concrete Reimbursement class can include additional fields that can be
 * used for extended functionality of the ERS application.
 *
 * Example fields:
 * <ul>
 * <li>Description</li>
 * <li>Creation Date</li>
 * <li>Resolution Date</li>
 * <li>Receipt Image</li>
 * </ul>
 *
 */
public class Reimbursement extends AbstractReimbursement {

	private String description;
	private Timestamp creationDate;
	private Timestamp resolutionDate;
//	private Blob receiptImage;
	private ReimbType type;

	public Reimbursement() {
		super();
	}

	/**
	 * This includes the minimum parameters needed for the
	 * {@link com.revature.models.AbstractReimbursement} class. If other fields are
	 * needed, please create additional constructors.
	 */
	public Reimbursement(int id, Status status, User author, User resolver, double amount) {
		super(id, status, author, resolver, amount);
	}
//Blob receiptImage,
	public Reimbursement(int id, Status status, User author, User resolver, double amount, String description,
			Timestamp creationDate, Timestamp resolutionDate,  ReimbType type) {
		super(id, status, author, resolver, amount);
		this.description = description;
		this.creationDate = creationDate;
		this.resolutionDate = resolutionDate;
	//	this.receiptImage = receiptImage;
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Timestamp resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

//	public Blob getReceiptImage() {
//		return receiptImage;
//	}
//
//	public void setReceiptImage(Blob receiptImage) {
//		this.receiptImage = receiptImage;
//	}

	public ReimbType getType() {
		return type;
	}

	public void setType(ReimbType type) {
		this.type = type;
	}

}
