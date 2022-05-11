package com.revature.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController{	
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		HttpSession session= req.getSession();
		session.invalidate(); //Deactivate the session. 
//		RequestDispatcher rd = req.getRequestDispatcher("index.html");
//		rd.include(req, resp);
	}

}
