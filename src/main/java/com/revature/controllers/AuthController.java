package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.NewUserHasNonZeroIdException;
import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.exceptions.UserAuthenticationException;
import com.revature.exceptions.UsernameNotUniqueException;
import com.revature.models.User;
import com.revature.services.AuthService;

public class AuthController {

	private AuthService authService = new AuthService();
	private ObjectMapper mapper = new ObjectMapper();

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		StringBuilder stBuilder = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			stBuilder.append(line);
			line = reader.readLine();
		}
		String body = new String(stBuilder);
		System.out.println(body);

		User user = mapper.readValue(body, User.class);

		try {
			User authUser = authService.login(user.getUsername(), user.getPassword());
			HttpSession session = req.getSession(); // Upon successful login, create a session
			session.setAttribute("user", authUser);
			resp.setStatus(200);
			String json = mapper.writeValueAsString(authUser); 
			PrintWriter print = resp.getWriter();
			print.print(json);
		} catch (UserAuthenticationException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
			resp.setStatus(401);
		}
	}

	public void newUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		StringBuilder stBuilder = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			stBuilder.append(line);
			line = reader.readLine();
		}
		String body = new String(stBuilder);
		System.out.println(body);

		User user = mapper.readValue(body, User.class);
		try {
			User newUser = authService.register(user);
			HttpSession session = req.getSession();
			session.setAttribute("user", newUser);
			resp.setStatus(201);
			
			String json = mapper.writeValueAsString(newUser);
			PrintWriter print = resp.getWriter();
			print.print(json);
		} catch (RegistrationUnsuccessfulException e) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,e.getMessage());
			resp.setStatus(406);
		}catch (NewUserHasNonZeroIdException e){
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,e.getMessage());
			resp.setStatus(406);
		}catch (UsernameNotUniqueException e){
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,e.getMessage());
			resp.setStatus(406);
		}
		

		
	}
}
