package com.revature.repositories;

import com.revature.models.ReimbType;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReimbursementDAO {

	UserDAO userDAO = new UserDAO();

	/**
	 * Should retrieve a Reimbursement from the DB with the corresponding id or an
	 * empty optional if there is no match.
	 */
	public Optional<Reimbursement> getById(int id) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Reimbursement reimbursement = new Reimbursement();

				reimbursement.setId(result.getInt("reimb_id"));
				reimbursement.setAmount(result.getDouble("reimb_amount"));
				reimbursement.setCreationDate(result.getTimestamp("reimb_submitted"));
				reimbursement.setResolutionDate(result.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(result.getString("reimb_description"));
			//	reimbursement.setReceiptImage(result.getBlob("reimb_receipt"));
				reimbursement.setAuthor(userDAO.getById(result.getInt("reimb_author")).get());
				if (result.getInt("reimb_resolver")!= 0) {
					reimbursement.setResolver(userDAO.getById(result.getInt("reimb_resolver")).get());
				}else {
					reimbursement.setResolver(null);
				}
				int status_id = result.getInt("reimb_status_id");
				if (status_id == 1)
					reimbursement.setStatus(Status.PENDING);
				else if (status_id == 2)
					reimbursement.setStatus(Status.APPROVED);
				else if (status_id == 3)
					reimbursement.setStatus(Status.DENIED);

				int type = result.getInt("reimb_type_id");
				if (type == 1)
					reimbursement.setType(ReimbType.LODGING);
				else if (type == 2)
					reimbursement.setType(ReimbType.TRAVEL);
				else if (type == 3)
					reimbursement.setType(ReimbType.FOOD);
				else
					reimbursement.setType(ReimbType.OTHER);

				return Optional.of(reimbursement);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * Should retrieve a List of Reimbursements from the DB with the corresponding
	 * Status or an empty List if there are no matches.
	 */
	public List<Reimbursement> getByStatus(Status status) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			int status_id = 0;
			if (status == Status.PENDING)
				status_id = 1;
			else if (status == Status.APPROVED)
				status_id = 2;
			else if (status == Status.DENIED)
				status_id = 3;

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, status_id);
			ResultSet result = statement.executeQuery();

			List<Reimbursement> list = new ArrayList<>();

			while (result.next()) {
				Reimbursement reimbursement = new Reimbursement();

				reimbursement.setId(result.getInt("reimb_id"));
				reimbursement.setAmount(result.getDouble("reimb_amount"));
				reimbursement.setCreationDate(result.getTimestamp("reimb_submitted"));
				reimbursement.setResolutionDate(result.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(result.getString("reimb_description"));
			//	reimbursement.setReceiptImage(result.getBlob("reimb_receipt"));
				reimbursement.setAuthor(userDAO.getById(result.getInt("reimb_author")).get());
				if (result.getInt("reimb_resolver")!= 0) {
					reimbursement.setResolver(userDAO.getById(result.getInt("reimb_resolver")).get());
				}else {
					reimbursement.setResolver(null);
				}
					
				status_id = result.getInt("reimb_status_id");
				if (status_id == 1)
					reimbursement.setStatus(Status.PENDING);
				else if (status_id == 2)
					reimbursement.setStatus(Status.APPROVED);
				else if (status_id == 3)
					reimbursement.setStatus(Status.DENIED);

				int type = result.getInt("reimb_type_id");
				if (type == 1)
					reimbursement.setType(ReimbType.LODGING);
				else if (type == 2)
					reimbursement.setType(ReimbType.TRAVEL);
				else if (type == 3)
					reimbursement.setType(ReimbType.FOOD);
				else
					reimbursement.setType(ReimbType.OTHER);

				list.add(reimbursement);
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return null;
		return Collections.emptyList();
	}

	/**
	 * <ul>
	 * <li>Should Update an existing Reimbursement record in the DB with the
	 * provided information.</li>
	 * <li>Should throw an exception if the update is unsuccessful.</li>
	 * <li>Should return a Reimbursement object with updated information.</li>
	 * </ul>
	 */
	public Reimbursement update(Reimbursement unprocessedReimbursement) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "UPDATE ers_reimbursement SET reimb_amount=?, reimb_submitted=?, "
					+ "reimb_resolved=?, reimb_description=?, reimb_author=?,"
					+ "reimb_resolver=?, reimb_status_id=?, reimb_type_id=? WHERE reimb_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);

			int count = 0;
			statement.setDouble(++count, unprocessedReimbursement.getAmount());
			statement.setTimestamp(++count, unprocessedReimbursement.getCreationDate());
			statement.setTimestamp(++count, unprocessedReimbursement.getResolutionDate());
			statement.setString(++count, unprocessedReimbursement.getDescription());
		//	statement.setBlob(++count, unprocessedReimbursement.getReceiptImage());
			statement.setInt(++count, unprocessedReimbursement.getAuthor().getId());
			statement.setInt(++count, unprocessedReimbursement.getResolver().getId());
			Status status = unprocessedReimbursement.getStatus();
			if (status == Status.PENDING)
				statement.setInt(++count, 1);
			else if (status == Status.APPROVED)
				statement.setInt(++count, 2);
			else if (status == Status.DENIED)
				statement.setInt(++count, 3);

			ReimbType type = unprocessedReimbursement.getType();
			if (type == ReimbType.LODGING)
				statement.setInt(++count, 1);
			else if (type == ReimbType.TRAVEL)
				statement.setInt(++count, 2);
			else if (type == ReimbType.FOOD)
				statement.setInt(++count, 3);
			else if (type == ReimbType.OTHER)
				statement.setInt(++count, 3);

			statement.setInt(++count, unprocessedReimbursement.getId());

			statement.execute();

			return getById(unprocessedReimbursement.getId()).get();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Reimbursement createReimbursement(Reimbursement newReimbursement) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, "
					+ "reimb_resolved, reimb_description, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = conn.prepareStatement(sql);

			int count = 0;
			statement.setDouble(++count, newReimbursement.getAmount());
			statement.setTimestamp(++count, newReimbursement.getCreationDate());
			statement.setTimestamp(++count, newReimbursement.getResolutionDate());
			statement.setString(++count, newReimbursement.getDescription());
//			statement.setBlob(++count, newReimbursement.getReceiptImage().getBytes());
			statement.setInt(++count, newReimbursement.getAuthor().getId());
			statement.setNull(++count, Types.INTEGER);
			Status status = newReimbursement.getStatus();
			if (status == Status.PENDING)
				statement.setInt(++count, 1);
			else if (status == Status.APPROVED)
				statement.setInt(++count, 2);
			else if (status == Status.DENIED)
				statement.setInt(++count, 3);

			ReimbType type = newReimbursement.getType();
			if (type == ReimbType.LODGING)
				statement.setInt(++count, 1);
			else if (type == ReimbType.TRAVEL)
				statement.setInt(++count, 2);
			else if (type == ReimbType.FOOD)
				statement.setInt(++count, 3);
			else if (type == ReimbType.OTHER)
				statement.setInt(++count, 3);

			statement.execute();

			return newReimbursement;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimbursement> getAllReimbursements() {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement;";

			Statement statement = conn.createStatement();

			ResultSet result = statement.executeQuery(sql);

			List<Reimbursement> list = new ArrayList<>();

			while (result.next()) {
				Reimbursement reimbursement = new Reimbursement();

				reimbursement.setId(result.getInt("reimb_id"));
				reimbursement.setAmount(result.getDouble("reimb_amount"));
				reimbursement.setCreationDate(result.getTimestamp("reimb_submitted"));
				reimbursement.setResolutionDate(result.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(result.getString("reimb_description"));
//				reimbursement.setReceiptImage(result.getBlob("reimb_receipt"));
				reimbursement.setAuthor(userDAO.getById(result.getInt("reimb_author")).get());
				if (result.getInt("reimb_resolver")!= 0) {
					reimbursement.setResolver(userDAO.getById(result.getInt("reimb_resolver")).get());
				}else {
					reimbursement.setResolver(null);
				}

				int status = result.getInt("reimb_status_id");
				if (status == 1)
					reimbursement.setStatus(Status.PENDING);
				else if (status == 2)
					reimbursement.setStatus(Status.APPROVED);
				else
					reimbursement.setStatus(Status.DENIED);

				int type = result.getInt("reimb_type_id");
				if (type == 1)
					reimbursement.setType(ReimbType.LODGING);
				else if (type == 2)
					reimbursement.setType(ReimbType.TRAVEL);
				else if (type == 3)
					reimbursement.setType(ReimbType.FOOD);
				else
					reimbursement.setType(ReimbType.OTHER);

				list.add(reimbursement);
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
