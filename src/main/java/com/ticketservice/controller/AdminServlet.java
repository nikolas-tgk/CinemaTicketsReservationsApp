package com.ticketservice.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ticketservice.dao.UserDao;
import com.ticketservice.model.User;
import com.ticketservice.model.Utility;
import com.ticketservice.model.User.Customer;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private Utility utility;
	
	public void init() {
		userDao = new UserDao();
		utility = new Utility();
	}
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("username")==null)
		{
			request.setAttribute("msg","You are not logged in!");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request,response);
			return;
		}else if(session.getAttribute("userType")!="admin"){	
			System.out.println("ses usern "+session.getAttribute("username"));
			session.removeAttribute("username"); 		
			session.invalidate();
			request.setAttribute("msg","Invalid user type.");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request,response);
			return;
		}
		
		String action = request.getParameter("action");
		
		try {
			if(action==null) {
				System.out.println("null action");
				showUserList(request, response); // auto-run
			}
			else {
				switch (action) {
				case "new":
					showNewForm(request, response);
					break;
				case "delete":
					deleteUser(request, response);
					break;
				case "edit":
					showEditForm(request, response);
					break;
				case "update":
					updateUserOnId(request, response);
					break;
				case "adminlist":
					System.out.println("adminlist");
					showAdminList(request, response);
					break;
				case "contentadminlist":
					showContentAdminList(request, response);
					break;
				case "customerlist":
					showCustomerList(request, response);
					break;
				case "userlist":
					System.out.println("userlist");
					showUserList(request, response);
					break;
				/*default:
					System.out.println("default");
					showUserList(request, response); // auto-run
					break;*/
				}	
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	// default
	private void showUserList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = userDao.selectAllUsers();
		
		//System.out.println("adminpanelservlet says  "+listUser.get(0).getUsername()+" .");
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	// show all admins
	private void showAdminList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = userDao.selectAdmins();
		
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	// show all content admins
	private void showContentAdminList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = userDao.selectContentAdmins();
		
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	// show customers only
	private void showCustomerList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Customer> customerList = userDao.selectCustomers();
		
		request.setAttribute("customerList", customerList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
		dispatcher.forward(request, response);
	}
	
	// insert a new user
	private void insertAdmin(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String full_name = request.getParameter("fullname");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String type = request.getParameter("user_type");
		
		String salt = utility.generateSalt();
		String encryptedPassword = utility.encrypt(password,salt);
		
		User newUser = new User(username, full_name, email,encryptedPassword,salt,type);
		if(userDao.insertNonCustomer(newUser))
		{
			request.setAttribute("info", "Admin created!");
			request.setAttribute("info-color", "green");
		} else {
			request.setAttribute("info", "Admin Creation Fail, username or e-mail is already in use!");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin?action=userlist");
		dispatcher.forward(request, response);
	}
	
	// delete user
	private void deleteUser(HttpServletRequest request , HttpServletResponse response)
	throws SQLException, IOException, ServletException
	{
		int id  = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession(false);
		int sessionAdminId = userDao.selectIdFromUsername(session.getAttribute("username").toString());
		if(id==sessionAdminId)
		{
			request.setAttribute("info", "Delete fail, you cant delete yourself!");
			request.setAttribute("info-color", "red");
		}
		else {		
			if(userDao.deleteUserOnId(id))
			{
				request.setAttribute("info", "User deleted!");
				request.setAttribute("info-color", "green");	
			}
			else {
				request.setAttribute("info", "Delete Fail, user not found, or foreign key constraint block.");
				request.setAttribute("info-color", "red");	
			}

		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin?action=userlist");
		dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userDao.selectUserById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", user);
		dispatcher.forward(request, response);
	}
	
	// update user on id
	private void updateUserOnId(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		// to implement
		response.sendRedirect("admin");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("username")==null)
		{
			request.setAttribute("msg","You are not logged in!");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request,response);
			return;
		}else if(session.getAttribute("userType")!="admin"){	
			System.out.println("ses usern "+session.getAttribute("username"));
			session.removeAttribute("username"); 		
			session.invalidate();
			request.setAttribute("msg","Invalid user type.");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request,response);
			return;
		}
		
		String action = request.getParameter("action");
		
		try {
			if(action==null) {
				System.out.println("#POST# null action");
			}
			else {
				switch (action) {
				case "insert":
					System.out.println("#POST# insert action");
					insertAdmin(request, response);
					break;
				}	
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
		doGet(request, response);
	}

}
