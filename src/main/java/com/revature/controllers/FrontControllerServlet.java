package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;

public class FrontControllerServlet extends HttpServlet {

	private ObjectMapper mapper = new ObjectMapper();
	private AuthController authController = new AuthController();
	private LogoutController logoutController = new LogoutController();
	private ReimbusementController reimbController = new ReimbusementController();
	//private UserController userController = new UserController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");

		resp.setStatus(404);

		final String URL = req.getRequestURI().replace("/ERS/core/", "");

		String[] UrlSections = URL.split("/");

		switch (UrlSections[0]) {
		case "login":
			if (req.getMethod().equals("POST")) {
				authController.login(req, resp);
			}
			break;
//		case "user":
//			if (req.getMethod().equals("POST")) {
//				userController.login(req, resp);
//			}
//			break;
		case "register":
			if (req.getMethod().equals("POST")) {
				authController.newUser(req, resp);
			}
			break;
		case "logout":
			if (req.getMethod().equals("GET")) {
				logoutController.logout(req, resp);
			}
			break;
		case "reimb":
			HttpSession session = req.getSession(false);
			if (session != null) {
				if (req.getMethod().equals("GET")) {
					Status status = Status.PENDING;
					reimbController.getReimbusementList(resp,status);
				} else if (req.getMethod().equals("POST")) {	
					
					BufferedReader reader = req.getReader();
					StringBuilder stBuilder = new StringBuilder();
					String line = reader.readLine();

					while (line != null) {
						stBuilder.append(line);
						line = reader.readLine();
					}

					String body = new String(stBuilder);
					System.out.println(body);
					
					if(body.contains("statustype")) {
						
						Status status = null;
						if (body.contains("PENDING"))
							status = Status.PENDING;
						else if (body.contains("APPROVED"))
							status = Status.APPROVED;
						else if (body.contains("DENIED"))
							status = Status.DENIED;
								
						reimbController.getReimbusementList( resp, status);
						
					}else {
						Reimbursement reimbursement = mapper.readValue(body, Reimbursement.class);
						
						User user = (User) session.getAttribute("user");
						reimbursement.setAuthor(user);	
						Date date = new Date(); 			 
				        Timestamp creationDate = new Timestamp(date.getTime());          
				        reimbursement.setCreationDate(creationDate);
				        
				        reimbController.addReimbursement(resp, reimbursement,user.getUsername());		
						
					}
				} else if (req.getMethod().equals("PUT")) {
					BufferedReader reader = req.getReader();

					StringBuilder stBuilder = new StringBuilder();

					String line = reader.readLine();

					while (line != null) {
						stBuilder.append(line);
						line = reader.readLine();
					}

					String body = new String(stBuilder);
					System.out.println(body);

					Reimbursement reimbursement = mapper.readValue(body, Reimbursement.class);
					reimbController.updateReimbusement(session, resp, reimbursement);
				}
			} else {
				resp.setStatus(401);
			}
			break;

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
