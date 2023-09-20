package com.ticketservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ticketservice.model.User;
import com.ticketservice.model.User.Customer;

public class UserDao {
	
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/dbname";
	private String jdbcUsername = "username";
	private String jdbcPassword = "password";
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	
	
	private static final String INSERT_USER = "Insert into users" + "(username, fullname, email, password, salt, type, created_at) VALUES " + " (?,?,?,?,?,?,CURRENT_TIMESTAMP());";
	private static final String INSERT_CUSTOMER = "Insert into customers" + "(age,gender,username,discount_type) VALUES " + "(?,?,?,?);";
	private static final String SELECT_USER_BY_ID = "Select * from users where id = ?";
	private static final String VALIDATE_LOGIN = "Select * from users where username = ? and password = ?";
	private static final String SELECT_SALT = "Select salt from users where username = ?";
	private static final String SELECT_PASSWORD = "Select password from users where username = ?";
	private static final String SELECT_ALL_USERS = "Select * from users";
	private static final String SELECT_ADMINS = "Select * from users where type = 'admin'";
	private static final String SELECT_CONTENT_ADMINS = "Select * from users where type = 'content_admin'";
	private static final String SELECT_CUSTOMERS = "SELECT * FROM users INNER JOIN customers ON users.username = customers.username;";
	private static final String DELETE_USER_ON_ID = "Delete from users where id = ?";
	private static final String SELECT_TYPE_ON_USERNAME = "Select type from users where username = ?";
	private static final String SELECT_ID_FROM_USERNAME = "Select id from users where username = ?";

	public UserDao() {	
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public String selectSalt(String username){
		System.out.println(SELECT_SALT);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SALT)){
			preparedStatement.setString(1, username);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				System.out.println("Username found! Returning salt.");
				return rs.getString("salt");
			}
			else {
				System.out.println("No Username in db. Returning null.");
			}
		}
		catch(SQLException e)
		{
			printSQLException(e);
		}
		return null;
	}
	
	public String selectPassword(String username) {
		System.out.println(SELECT_PASSWORD);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD)){
			preparedStatement.setString(1, username);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				System.out.println("Username found! Returning hashed password."+rs.getString("password"));
				return rs.getString("password");
			}
			else {
				System.out.println("No Username in db. Returning null.");
			}
		}
		catch (SQLException e)
		{
			printSQLException(e);
		}
		return null;
	}
	
	public boolean validateLogin(String username, String password){
		System.out.println(VALIDATE_LOGIN);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_LOGIN)){
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			//System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				System.out.println("Authentication Successful");
				return true;
			}
			else {
				System.out.println("Authentication Fail");			}
		}
			catch (SQLException e)
			{
				printSQLException(e);
			}
		return false;
	}
	
	// insert non-customer user
	
	public boolean insertNonCustomer(User user){
		System.out.println(INSERT_USER);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)){
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getSalt());
			preparedStatement.setString(6, user.getType());
			//preparedStatement.setString(6, user.getUserCreation());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			return true;
		}
			catch (SQLException e)
			{
				printSQLException(e);
			}
		return false;
	}
	
	// insert customer
	public boolean insertCustomer(Customer customer){
		try {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		PreparedStatement preparedStatement0 = conn.prepareStatement(INSERT_USER);
		preparedStatement0.setString(1, customer.getUsername());
		preparedStatement0.setString(2, customer.getName());
		preparedStatement0.setString(3, customer.getEmail());
		preparedStatement0.setString(4, customer.getPassword());
		preparedStatement0.setString(5, customer.getSalt());
		preparedStatement0.setString(6, customer.getType());
		
		PreparedStatement preparedStatement1 = conn.prepareStatement(INSERT_CUSTOMER);
		preparedStatement1.setInt(1, customer.getAge());
		preparedStatement1.setString(2, customer.getGender());
		preparedStatement1.setString(3, customer.getUsername());
		preparedStatement1.setString(4, customer.getDiscountType());
		
		try {
			System.out.println("Executing.. 1/2 "+preparedStatement0);
			preparedStatement0.execute();
			System.out.println("Executing.. 2/2 "+preparedStatement1);
			preparedStatement1.execute();
			System.out.println("Execute OK");
			conn.commit();
		} catch (Exception e) {
			System.out.println("Execute Fail");
			conn.rollback();
			return false;
		}
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// select user by id
	public User selectUserById(int id ) {
		User user = null;
		//establish connection
		System.out.println(SELECT_USER_BY_ID);
		try (
			Connection connection = getConnection();
			// prepare statement
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);)
		{
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);		
			// execute
			ResultSet rs = preparedStatement.executeQuery();		
			// process resultset object
			while(rs.next())
			{
				String email =  rs.getString("email");
				String username =  rs.getString("username");
				String type =  rs.getString("type");
				
				user = new User(id,username,type,email);
				System.out.println("User found! "+String.valueOf(id)+" "+username+" "+email+" "+type);
			}
		}

		catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}
	
	public String getUserType(String username) {
		System.out.println(SELECT_TYPE_ON_USERNAME);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TYPE_ON_USERNAME)){
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				System.out.println("Username found, extracting user type..");
				return rs.getString("type");
			}
			else {
				System.out.println("Username not found!");			}
		}
			catch (SQLException e)
			{
				printSQLException(e);
			}
		return null;
	}
	

	
	// select all users
	
	public List<User> selectAllUsers(){
		
		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String fullName = rs.getString("fullname");
				String createdAt = rs.getString("created_at");
				String type = rs.getString("type");
				users.add(new User(id,username,fullName,email,type,createdAt));
				System.out.println("User: "+id+username+type+email+" found.");
			}
			System.out.println("Found "+users.size()+" users.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;		
	}
	
	
	// select all admins
	public List<User> selectAdmins(){		
		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMINS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String fullName = rs.getString("fullname");
				String email = rs.getString("email");
				String type = rs.getString("type");
				String createdAt = rs.getString("created_at");
				users.add(new User(id,username,fullName,email,type,createdAt));
			}
			System.out.println("Found "+users.size()+" admins.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;		
	}
	
	// select all content admins
	public List<User> selectContentAdmins(){	
		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTENT_ADMINS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String fullName = rs.getString("fullname");
				String email = rs.getString("email");
				String type = rs.getString("type");
				String createdAt = rs.getString("created_at");
				users.add(new User(id,username,fullName,email,type,createdAt));
			}
			System.out.println("Found "+users.size()+" content-admins.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;		
	}
	
	// select all customers
	public List<Customer> selectCustomers(){
		
		List<Customer> customers = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String fullName = rs.getString("fullname");
				String email = rs.getString("email");
				String type = rs.getString("type");
				String creationTime = rs.getString("created_at");
				String discount = rs.getString("discount_type");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				customers.add(new Customer(id,username,fullName,email,creationTime, discount, gender, age));
				System.out.println("CUSTOMER: "+id+username+type+email+gender+" found.");
			}
			System.out.println("Found "+customers.size()+" customers.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return customers;		
	}
	
	// delete user on id
	public boolean deleteUserOnId(int id) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_ON_ID);)
		{
			preparedStatement.setInt(1, id);		
			int rowsDel = preparedStatement.executeUpdate();
			System.out.println("Users deleted: "+rowsDel);
			if(rowsDel>0) {
				return true;
			}
		}
	 catch (SQLException e) {
		printSQLException(e);
	}
		return false;
	}
	
	public int selectIdFromUsername(String username) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_FROM_USERNAME);)
		{
			preparedStatement.setString(1, username);		
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				System.out.println("User id extracted!");
				return rs.getInt("id");
			}
		}
	 catch (SQLException e) {
		printSQLException(e);
	}
		return -1;
	}
	
	private void printSQLException(SQLException exception) {
		for (Throwable e : exception)
		{
			if( e instanceof SQLException)
			{
				e.printStackTrace(System.err);
				System.err.println("SQLState: "+((SQLException) e).getSQLState());
				System.err.println("Error Code: "+((SQLException) e).getErrorCode());
				System.err.println("Message: "+ e.getMessage());
				Throwable t = exception.getCause();
				while(t != null) {
					System.out.println("Cause: "+t);
					t = t.getCause();
				}
			}

		}
	}

}
