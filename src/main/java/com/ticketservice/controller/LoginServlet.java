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

import javax.servlet.http.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao; 
    private Utility utility;
	
	public void init(){
		userDao = new UserDao();
		utility = new Utility();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    // to disable access via Get
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean result = false;
		String salt = userDao.selectSalt(username);
		if(salt!=null)
		{
			String hashedPassword = utility.encrypt(password,salt);
			result = userDao.validateLogin(username,hashedPassword);
			if(result)
			{
				System.out.println("Login Success! Setting up session..");
				String userType=userDao.getUserType(username);
				HttpSession session = request.getSession();
				synchronized(session) 
				{	
					session.setAttribute("username", username);		
					if (userType.equals("admin")) 
					{
						System.out.println("ADMIN");
						session.setAttribute("userType", "admin");
						//request.setAttribute("action","showUsers");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/admin?action=userlist");
						dispatcher.forward(request, response);
					}
					else if (userType.equals("content_admin")) 
					{
						System.out.println("CONTENT_ADMIN");
						session.setAttribute("userType", "content_admin");
						//request.setAttribute("action","showMovies");
						RequestDispatcher dispatcher =request.getRequestDispatcher("/contentadmin?action=movielist");
						dispatcher.forward(request, response);					
					}
					else 
					{
						System.out.println("CUSTOMER");
						session.setAttribute("userType", "customer");
						//request.setAttribute("action","showReservations");
						RequestDispatcher dispatcher = request.getRequestDispatcher("customer?action=availablemovielist");
						dispatcher.forward(request, response);
					}
				}				
			}
			else {
				System.out.println("Invalid password!");
				request.setAttribute("msg","Invalid password!");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
				requestDispatcher.forward(request,response);
			}
		}
		else {
			System.out.println("Username not found!");
			request.setAttribute("msg","Username not in use!");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request,response);
		}	
	}
}
