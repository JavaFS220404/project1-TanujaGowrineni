package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

public class UserController {

	private UserDAO userDAO = new UserDAO();
	private UserService userService = new UserService(userDAO);
	private ObjectMapper objectMapper = new ObjectMapper();

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

		User user = objectMapper.readValue(body, User.class);

		if (userService.createUser(user)) {
			resp.setStatus(201);
		} else {
			resp.setStatus(406);
		}

	}

}
