package com.ticketservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ticketservice.model.Cinema;
import com.ticketservice.model.Movie;
import com.ticketservice.model.Reservation;
import com.ticketservice.model.Screen;
import com.ticketservice.model.Screening;

public class ReservationDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/dbname";
	private String jdbcUsername = "username";
	private String jdbcPassword = "password";
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	
	private static final String SELECT_CINEMAS = "Select * from cinemas";
	private static final String SELECT_ALL_SCREENS = "Select * from screens";
	private static final String SELECT_ALL_SCREENINGS = "Select screenings.*,movies.title,screens.name,screens.type,cinemas.name AS cinename from screenings inner join movies ON screenings.film_id = movies.movie_id inner join screens ON screenings.of_screen = screens.screen_id inner join cinemas ON screens.of_cinema = cinemas.name;";
	private static final String SELECT_SCREENING_ON_ID = "Select screenings.*,movies.title,screens.name,screens.type,cinemas.name AS cinename from screenings inner join movies ON screenings.film_id = movies.movie_id inner join screens ON screenings.of_screen = screens.screen_id inner join cinemas ON screens.of_cinema = cinemas.name where screenings.screening_id = ?;";
	private static final String SELECT_AVAILABLE_SCREENINGS = "Select screenings.*,movies.title,screens.name,screens.type,cinemas.name AS cinename from screenings inner join movies ON screenings.film_id = movies.movie_id inner join screens ON screenings.of_screen = screens.screen_id inner join cinemas ON screens.of_cinema = cinemas.name where screenings.available = true;";
	private static final String SELECT_AVAILABLE_SCREENINGS_BY_DATE = "Select screenings.*,movies.title,screens.name,screens.type,cinemas.name AS cinename from screenings inner join movies ON screenings.film_id = movies.movie_id inner join screens ON screenings.of_screen = screens.screen_id inner join cinemas ON screens.of_cinema = cinemas.name where screenings.available = true AND screenings.date BETWEEN ? AND ?;";

	private static final String SELECT_ALL_MOVIES = "Select * from movies";
	private static final String SELECT_AVAILABLE_MOVIES = "Select DISTINCT movies.title, movies.* from movies inner join screenings on movies.movie_id = screenings.film_id where screenings.available = true;";
	private static final String SELECT_SCREENING_ON_MOVIE_ID = "Select screenings.*,movies.title,movies.movie_id,screens.name,screens.type,cinemas.name AS cinename from screenings inner join movies ON screenings.film_id = movies.movie_id inner join screens ON screenings.of_screen = screens.screen_id inner join cinemas ON screens.of_cinema = cinemas.name where movies.movie_id = ?;";

	private static final String SELECT_RESERVATIONS_ON_USER = "Select reservations.*,screenings.*,movies.title,screens.name,screens.type,cinemas.name AS cinename from reservations inner join screenings ON reservations.of_screening = screenings.screening_id inner join movies ON screenings.film_id = movies.movie_id inner join screens ON screenings.of_screen = screens.screen_id inner join cinemas ON screens.of_cinema = cinemas.name where reservations.username = ?;";
	
	private static final String INSERT_CINEMA = "Insert into cinemas" + "(name,address,provider) VALUES " + "(?,?,?);";
	private static final String INSERT_SCREEN = "Insert into screens" + "(of_cinema,name,seats,type,available) VALUES " + "(?,?,?,?,?);";
	private static final String INSERT_SCREENING = "Insert into screenings" + "(content_admin_id,of_screen,film_id,time_start,date,available) VALUES " + "(?,?,?,?,?,?);";
	private static final String INSERT_MOVIE = "Insert into movies" + "(title,description,age_rating,category,duration,director,year,content_admin_id) VALUES " + "(?,?,?,?,?,?,?,?);";
	private static final String INSERT_RESERVATION = "Insert into reservations" + "(of_screening,username,seats) VALUES " + "(?,?,?);";
	
	private static final String DELETE_RESERVATION_ON_ID = "Delete from reservations where reservation_id = ?";
	private static final String DELETE_SCREENING_ON_ID = "Delete from screenings where screening_id = ?";
	private static final String DELETE_MOVIE_ON_ID = "Delete from movies where movie_id = ?";
	private static final String DELETE_SCREEN_ON_ID = "Delete from screens where screen_id = ?";
	private static final String DELETE_CINEMA_ON_NAME = "Delete from cinemas where name = ?";

	public ReservationDao() {
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
	
	// select all movies
	public List<Movie> selectAllMovies(){	
		List<Movie> movies = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MOVIES);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				String title = rs.getString("title");
				String director = rs.getString("director");
				String description = rs.getString("description");
				String category = rs.getString("category");
				String ageRating = rs.getString("age_rating");
				int contentAdminId = rs.getInt("content_admin_id");
				int duration = rs.getInt("duration");
				int year = rs.getInt("year");
				movies.add(new Movie(id,title, description, category, director, ageRating, duration, year, contentAdminId));
				System.out.println("Movie: "+title+" "+director+" "+year+" found.");
			}
			System.out.println("Found "+movies.size()+" movies.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return movies;		
	}
	
	// select available movies only (for customers)
	public List<Movie> selectAvailableMovies(){	
		List<Movie> movies = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVAILABLE_MOVIES);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				String title = rs.getString("title");
				String director = rs.getString("director");
				String description = rs.getString("description");
				String category = rs.getString("category");
				String ageRating = rs.getString("age_rating");
				int contentAdminId = rs.getInt("content_admin_id");
				int duration = rs.getInt("duration");
				int year = rs.getInt("year");
				movies.add(new Movie(id,title, description, category, director, ageRating, duration, year, contentAdminId));
				System.out.println("Movie: "+title+" "+director+" "+year+" found.");
			}
			System.out.println("Found "+movies.size()+" movies.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return movies;		
	}
	
	
	
	// select all cinemas
	public List<Cinema> selectAllCinemas(){	
		List<Cinema> cinemas = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CINEMAS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String provider = rs.getString("provider");
				cinemas.add(new Cinema(name,provider,address));
				System.out.println("Cinema: "+name+" "+address+" "+provider+" found.");
			}
			System.out.println("Found "+cinemas.size()+" cinemas.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return cinemas;		
	}
	
	// select all screens
	public List<Screen> selectAllScreens(){	
		List<Screen> screens = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SCREENS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String ofCinema = rs.getString("of_cinema");
				String type = rs.getString("type");
				int screenId = rs.getInt("screen_id");
				int seats = rs.getInt("seats");
				boolean isAvailable = rs.getBoolean("available");
				
				screens.add(new Screen(screenId,seats,name,ofCinema,type,isAvailable));
				System.out.println("Screen: "+name+" "+screenId+" "+type+" found.");
			}
			System.out.println("Found "+screens.size()+" screens.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return screens;		
	}
	
	// select all screenings
	public List<Screening> selectAllScreenings(){	
		List<Screening> screenings = new ArrayList<>();
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SCREENINGS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String timeStart = rs.getString("time_start");
				String date = rs.getString("date");
				String movieName = rs.getString("title");
				String cinemaName = rs.getString("cinename");
				String screenName = rs.getString("name");
				String screenType = rs.getString("type");
				int screeningId = rs.getInt("screening_id");
				int screenId = rs.getInt("of_screen");
				int filmId = rs.getInt("film_id");
				int contentAdminId = rs.getInt("content_admin_id");			
				boolean available = rs.getBoolean("available");
				
				screenings.add(new Screening(screeningId,screenId,contentAdminId,filmId,timeStart,date,available,movieName,cinemaName,screenName,screenType));
				System.out.println("Screening: "+date+" "+timeStart+" "+available+" found.");
			}
			System.out.println("Found "+screenings.size()+" screenings.");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return screenings;		
	}
	
	// select only available screenings (for customers)
		public List<Screening> selectAvailableScreenings(){	
			List<Screening> screenings = new ArrayList<>();
			try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVAILABLE_SCREENINGS);) {
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String timeStart = rs.getString("time_start");
					String date = rs.getString("date");
					String movieName = rs.getString("title");
					String cinemaName = rs.getString("cinename");
					String screenName = rs.getString("name");
					String screenType = rs.getString("type");
					int screeningId = rs.getInt("screening_id");
					int screenId = rs.getInt("of_screen");
					int filmId = rs.getInt("film_id");
					int contentAdminId = rs.getInt("content_admin_id");			
					boolean available = rs.getBoolean("available");			
					screenings.add(new Screening(screeningId,screenId,contentAdminId,filmId,timeStart,date,available,movieName,cinemaName,screenName,screenType));
					System.out.println("Screening: "+date+" "+timeStart+" "+available+" found.");
				}
				System.out.println("Found "+screenings.size()+" screenings.");
			} catch (SQLException e) {
				printSQLException(e);
			}
			return screenings;		
		}
		
		// select screenings by date
				public List<Screening> selectAvailableScreeningsByDate(String fromdate, String todate){	
					List<Screening> screenings = new ArrayList<>();
					try (Connection connection = getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVAILABLE_SCREENINGS_BY_DATE);) {
						preparedStatement.setString(1,fromdate);
						preparedStatement.setString(2,todate);	
						System.out.println(preparedStatement);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							String timeStart = rs.getString("time_start");
							String date = rs.getString("date");
							String movieName = rs.getString("title");
							String cinemaName = rs.getString("cinename");
							String screenName = rs.getString("name");
							String screenType = rs.getString("type");
							int screeningId = rs.getInt("screening_id");
							int screenId = rs.getInt("of_screen");
							int filmId = rs.getInt("film_id");
							int contentAdminId = rs.getInt("content_admin_id");			
							boolean available = rs.getBoolean("available");			
							screenings.add(new Screening(screeningId,screenId,contentAdminId,filmId,timeStart,date,available,movieName,cinemaName,screenName,screenType));
							System.out.println("Screening: "+date+" "+timeStart+" "+available+" found.");
						}
						System.out.println("Found "+screenings.size()+" screenings.");
					} catch (SQLException e) {
						printSQLException(e);
					}
					return screenings;		
				}
		
		// select screenings of specific movie only
				public List<Screening> selectSpecificMovieScreenings(int movieId){	
					List<Screening> screenings = new ArrayList<>();
					try (Connection connection = getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCREENING_ON_MOVIE_ID);) {
						System.out.println(preparedStatement);
						preparedStatement.setInt(1, movieId);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							String timeStart = rs.getString("time_start");
							String date = rs.getString("date");
							String movieName = rs.getString("title");
							String cinemaName = rs.getString("cinename");
							String screenName = rs.getString("name");
							String screenType = rs.getString("type");
							int screeningId = rs.getInt("screening_id");
							int screenId = rs.getInt("of_screen");
							//int filmId = rs.getInt("film_id");
							int contentAdminId = rs.getInt("content_admin_id");			
							boolean available = rs.getBoolean("available");			
							screenings.add(new Screening(screeningId,screenId,contentAdminId,movieId,timeStart,date,available,movieName,cinemaName,screenName,screenType));
							System.out.println("Screening: "+date+" "+timeStart+" "+available+" found.");
						}
						System.out.println("Found "+screenings.size()+" screenings.");
					} catch (SQLException e) {
						printSQLException(e);
					}
					return screenings;		
				}
		
		// select reservations on customer username
				public List<Reservation> selectReservationOnUsername(String username){	
					List<Reservation> reservations = new ArrayList<>();
					try (Connection connection = getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESERVATIONS_ON_USER);) {
						preparedStatement.setString(1, username);	
						System.out.println(preparedStatement);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							String timeStart = rs.getString("time_start");
							String customerUsername = rs.getString("username");
							String date = rs.getString("date");
							String movieName = rs.getString("title");
							String cinemaName = rs.getString("cinename");
							String screenName = rs.getString("name");
							String screenType = rs.getString("type");
							int reservationId = rs.getInt("reservation_id");
							int screeningId = rs.getInt("of_screening");	
							int seats = rs.getInt("seats");	
							reservations.add(new Reservation(reservationId,screeningId,seats,customerUsername,movieName,screenName,cinemaName,timeStart,date,screenType));
						}
						System.out.println("Found "+reservations.size()+" reservations.");
					} catch (SQLException e) {
						printSQLException(e);
					}
					return reservations;		
				}
				
				// select specific screening
				public Screening selectScreeningOnId(int screeningId){	
					try (Connection connection = getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCREENING_ON_ID);) {
						preparedStatement.setInt(1, screeningId);	
						System.out.println(preparedStatement);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							String timeStart = rs.getString("time_start");
							//String customerUsername = rs.getString("username");
							String date = rs.getString("date");
							String movieName = rs.getString("title");
							String cinemaName = rs.getString("cinename");
							String screenName = rs.getString("name");
							String screenType = rs.getString("type");
							//int reservationId = rs.getInt("reservation_id");
							int contentAdminId = rs.getInt("content_admin_id");
							int screenId = rs.getInt("of_screen");
							int filmId = rs.getInt("film_id");	
							//int seats = rs.getInt("seats");	
							boolean availability = true;
							Screening screening = new Screening(screeningId,screenId,contentAdminId,filmId,timeStart,date,availability,movieName,cinemaName,screenName,screenType);
							System.out.println("Exctracted screening.");
							return screening;
						}
					} catch (SQLException e) {
						printSQLException(e);
					}
					return null;		
				}
	
	public boolean removeScreening(int screeningId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCREENING_ON_ID);) {
				System.out.println(preparedStatement);
				preparedStatement.setInt(1, screeningId);		
				int rowsDel = preparedStatement.executeUpdate();
				System.out.println("Movies deleted: "+rowsDel);
				if(rowsDel>0) {
					return true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		return false;
	}
	
	public boolean removeScreen(int screenId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCREEN_ON_ID);) {
				System.out.println(preparedStatement);
				preparedStatement.setInt(1,screenId);		
				int rowsDel = preparedStatement.executeUpdate();
				System.out.println("Screens deleted: "+rowsDel);
				if(rowsDel>0) {
					return true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		return false;
	}
	
	public boolean removeMovie(int movieId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE_ON_ID);) {
				System.out.println(preparedStatement);
				preparedStatement.setInt(1,movieId);		
				int rowsDel = preparedStatement.executeUpdate();
				System.out.println("Movies deleted: "+rowsDel);
				if(rowsDel>0) {
					return true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		return false;
	}
	
	public boolean removeCinema(String cinemaName) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CINEMA_ON_NAME);) {
				System.out.println(preparedStatement);
				preparedStatement.setString(1,cinemaName);		
				int rowsDel = preparedStatement.executeUpdate();
				System.out.println("Cinemas deleted: "+rowsDel);
				if(rowsDel>0) {
					return true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		return false;
	}
	
	public boolean deleteReservation(int reservationId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_ON_ID);) {
				System.out.println(preparedStatement);
				preparedStatement.setInt(1,reservationId);		
				int rowsDel = preparedStatement.executeUpdate();
				System.out.println("Reservations deleted: "+rowsDel);
				if(rowsDel>0) {
					return true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		return false;
	}
	
	public boolean insertCinema(Cinema cinema){
		System.out.println(INSERT_CINEMA);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CINEMA)){
			preparedStatement.setString(1, cinema.getName());
			preparedStatement.setString(2, cinema.getAddress());
			preparedStatement.setString(3, cinema.getProvider());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}
			catch (SQLException e)
			{
				printSQLException(e);
				return false; // cinema name already exists in db
			}
		return true;
	}
	
	public boolean insertScreen(Screen screen){
		System.out.println(INSERT_SCREEN);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SCREEN)){
			preparedStatement.setString(1, screen.getCinema());
			preparedStatement.setString(2, screen.getName());
			preparedStatement.setInt(3, screen.getSeats());
			preparedStatement.setString(4, screen.getType());
			preparedStatement.setBoolean(5, screen.getAvailability());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}
			catch (SQLException e)
			{
				printSQLException(e);
				return false;
			}
		return true;
	}
	
	public boolean insertMovie(Movie movie){
		System.out.println(INSERT_MOVIE);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MOVIE)){
			preparedStatement.setString(1, movie.getTitle());
			preparedStatement.setString(2, movie.getDescription());
			preparedStatement.setString(3, movie.getAgeRating());
			preparedStatement.setString(4, movie.getCategory());
			preparedStatement.setInt(5, movie.getDuration());
			preparedStatement.setString(6, movie.getDirector());
			preparedStatement.setInt(7, movie.getYear());
			preparedStatement.setInt(8, movie.getContentAdminId());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}
			catch (SQLException e)
			{
				printSQLException(e);
				return false;
			}
		return true;
	}
	
	public boolean insertScreening(Screening screening){
		System.out.println(INSERT_SCREENING);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SCREENING)){
			preparedStatement.setInt(1, screening.getContentAdminId());
			preparedStatement.setInt(2, screening.getScreenId());
			preparedStatement.setInt(3, screening.getMovieId());
			preparedStatement.setString(4, screening.getTimeStart());
			preparedStatement.setString(5, screening.getDate());
			preparedStatement.setBoolean(6, screening.getAvailability());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}
			catch (SQLException e)
			{
				printSQLException(e);
				return false;
			}
		return true;
	}
	
	public boolean insertReservation(Reservation reservation){
		System.out.println(INSERT_RESERVATION);
		try (Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESERVATION)){
			preparedStatement.setInt(1, reservation.getScreeningId());
			preparedStatement.setString(2, reservation.getCustomerUsername());
			preparedStatement.setInt(3, reservation.getSeats());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}
			catch (SQLException e)
			{
				printSQLException(e);
				return false;
			}
		return true;
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
