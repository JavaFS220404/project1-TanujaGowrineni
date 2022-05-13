package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.CreateReimbursementUnsuccessulException;
import com.revature.exceptions.ReimbursementProcessingUnsuccessfulException;
import com.revature.exceptions.UserAuthenticationException;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

public class ReimbusementController {

	private ReimbursementService reimbService = new ReimbursementService();
	private ObjectMapper objectMapper = new ObjectMapper();

	public void getReimbusementList(HttpServletResponse resp, Status status) throws IOException {

		List<Reimbursement> reimbList = reimbService.getReimbursementsByStatus(status);

		if (reimbList.size() == 0) {
			resp.setStatus(204);
		} else {
			resp.setStatus(200);
			String json = objectMapper.writeValueAsString(reimbList);
			PrintWriter print = resp.getWriter();
			print.print(json);
		}
	}

	public void addReimbursement(HttpServletResponse resp, Reimbursement reimbursement,String username) throws IOException {

		try {
			Reimbursement newReimbursement = reimbService.createReimbursement(reimbursement);
			List<Reimbursement> reimbList = reimbService.getReimbursementsByUsername(username);
			resp.setStatus(201);
			String json = objectMapper.writeValueAsString(reimbList);
			PrintWriter print = resp.getWriter();
			print.print(json);
		} catch (CreateReimbursementUnsuccessulException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			resp.setStatus(400);
		}
	}

	public void updateReimbusement(HttpSession session, HttpServletResponse resp, Reimbursement reimbursement) throws IOException {
	
		User resolver = (User) session.getAttribute("user");
		Status finalStatus = reimbursement.getStatus();
		
		try {
			reimbursement = reimbService.process(reimbursement, finalStatus, resolver);		
			resp.setStatus(200);
			String json = objectMapper.writeValueAsString(reimbursement);
			PrintWriter print = resp.getWriter();
			print.print(json);
		} catch(ReimbursementProcessingUnsuccessfulException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage()); 
			resp.setStatus(400);
		}
		
	}

}
