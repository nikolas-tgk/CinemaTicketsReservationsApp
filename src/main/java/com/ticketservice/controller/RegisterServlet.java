package com.ticketservice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ticketservice.dao.UserDao;
import com.ticketservice.model.Utility;
import com.ticketservice.model.User.Customer;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private Utility utility;
	
	public void init(){
		userDao = new UserDao();
		utility = new Utility();
	}
       

    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullname = request.getParameter("fullname");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String discount = "none";
		
		String salt = utility.generateSalt();
		String encryptedPassword = utility.encrypt(password,salt);
	
		Customer customer = new Customer(username,email,encryptedPassword,salt,fullname,discount,gender,age);
		boolean result = userDao.insertCustomer(customer);
		if(result)
		{
			request.setAttribute("msg","User has been successfully registered!");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login?method=post");
			requestDispatcher.forward(request,response);
		} else {
			request.setAttribute("msg","Username or E-mail already in use!");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/register.jsp");
			requestDispatcher.forward(request,response);
		}
	}

}
