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

import com.ticketservice.dao.ReservationDao;
import com.ticketservice.dao.UserDao;
import com.ticketservice.model.Cinema;
import com.ticketservice.model.Movie;
import com.ticketservice.model.Screen;
import com.ticketservice.model.Screening;

/**
 * Servlet implementation class ContentAdminServlet
 */
@WebServlet("/contentadmin")
public class ContentAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationDao reservationDao;
	private UserDao userDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
    	reservationDao = new ReservationDao();
    	userDao = new UserDao();
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
		}else if(session.getAttribute("userType")!="content_admin"){	
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
			showMovieList(request, response); // auto-run
		}
		else {
			switch (action) {
			case "removemovie":
				System.out.println("action = removemovie");
				removeMovie(request, response);
				break;
			case "removescreening":
				System.out.println("action = removescreening");
				removeScreening(request, response);
				break;
			case "removescreen":
				System.out.println("action = removescreen");
				removeScreen(request, response);
				break;
			case "removecinema":
				System.out.println("action = removecinema");
				removeCinema(request, response);
				break;
			case "showdesc":
				System.out.println("action = showdesc");
				showDescription(request, response);
				break;
			case "newmovie":
				System.out.println("action = newmovie");
				showMovieForm(request, response);
				break;
			case "newscreening":
				System.out.println("action = newscreening");
				showScreeningForm(request, response);
				break;
			case "newscreen":
				System.out.println("action = newscreen");
				showScreenForm(request, response);
				break;
			case "newcinema":
				System.out.println("action = newcinema");
				showNewCinemaForm(request, response);
				break;
			case "screeninglist":
				System.out.println("action = screeninglist");
				showScreeningList(request, response);
				break;
			case "screenlist":
				System.out.println("action = screenlist");
				showScreenList(request, response);
				break;
			case "cinemalist":
				System.out.println("action = cinemalist");
				showCinemaList(request, response);
				break;
			case "movielist":
				System.out.println("action = movielist");
				showMovieList(request, response);
				break;
			}	
		}
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void showMovieList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Movie> movies = reservationDao.selectAllMovies();
		request.setAttribute("movieList", movies);
		RequestDispatcher dispatcher = request.getRequestDispatcher("movie-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showCinemaList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Cinema> cinemas = reservationDao.selectAllCinemas();
		request.setAttribute("cinemaList", cinemas);
		RequestDispatcher dispatcher = request.getRequestDispatcher("cinema-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showScreenList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Screen> screens = reservationDao.selectAllScreens();
		request.setAttribute("screenList", screens);
		RequestDispatcher dispatcher = request.getRequestDispatcher("screen-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showScreeningList(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException {
		List<Screening> screenings = reservationDao.selectAllScreenings();
		request.setAttribute("screeningList", screenings);
		RequestDispatcher dispatcher = request.getRequestDispatcher("screening-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void removeScreening(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int screeningId = Integer.parseInt(request.getParameter("id"));
		if(reservationDao.removeScreening(screeningId))
		{
			System.out.println("Delete success!");
			request.setAttribute("info", "Screening deleted successfully!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Delete Fail: screening not found, or foreign key constraint block.");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=screeninglist");
		dispatcher.forward(request, response);
	}
	
	private void removeScreen(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int screenId = Integer.parseInt(request.getParameter("id"));
		if(reservationDao.removeScreen(screenId))
		{
			System.out.println("Delete success!");
			request.setAttribute("info", "Screen deleted successfully!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Delete Fail: screen not found, or foreign key constraint block.");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=screenlist");
		dispatcher.forward(request, response);
	}
	
	private void removeCinema(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String cinemaName = request.getParameter("name");
		if(reservationDao.removeCinema(cinemaName))
		{
			System.out.println("Delete success!");
			request.setAttribute("info", "Cinema deleted successfully!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Delete Fail: cinema not found, or foreign key constraint block.");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=cinemalist");
		dispatcher.forward(request, response);
	}
	
	private void removeMovie(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int movieId = Integer.parseInt(request.getParameter("id"));
		if(reservationDao.removeMovie(movieId))
		{
			System.out.println("Delete success!");
			request.setAttribute("info", "Movie deleted successfully!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Delete Fail: movie not found, or foreign key constraint block.");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=movielist");
		dispatcher.forward(request, response);
	}
	
	private void showNewCinemaForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("cinema-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showScreenForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Cinema> cinemas = reservationDao.selectAllCinemas();
		request.setAttribute("cinemaList", cinemas);
		RequestDispatcher dispatcher = request.getRequestDispatcher("screen-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showMovieForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("movie-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showDescription(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String desc = request.getParameter("desc");
		request.setAttribute("desc", desc);
		RequestDispatcher dispatcher = request.getRequestDispatcher("content-admin-movie-description.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showScreeningForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Screen> screens = reservationDao.selectAllScreens();
		List<Movie> movies = reservationDao.selectAllMovies();
		request.setAttribute("screenList", screens);
		request.setAttribute("movieList", movies);
		RequestDispatcher dispatcher = request.getRequestDispatcher("screening-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertCinema(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String cinemaName = request.getParameter("name");
		String address = request.getParameter("address");
		String provider = request.getParameter("provider");
		
		Cinema cinema = new Cinema(cinemaName, provider, address );

		if(reservationDao.insertCinema(cinema))
		{
			request.setAttribute("info", "Cinema created!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Error: Cinema name already in use!");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=cinemalist");
		dispatcher.forward(request, response);
	}
	
	private void insertScreen(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String cinemaName = request.getParameter("cinema");
		String screenName = request.getParameter("name");
		String screenType = request.getParameter("type");
		boolean available = Boolean.parseBoolean(request.getParameter("available"));
		int seats = Integer.parseInt(request.getParameter("seats"));
		
		Screen screen = new Screen(seats,screenName,cinemaName,screenType,available);

		if(reservationDao.insertScreen(screen))
		{
			request.setAttribute("info", "Screen created!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Error!");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=screenlist");
		dispatcher.forward(request, response);
	}
	
	private void insertMovie(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String title = request.getParameter("title");
		String director = request.getParameter("director");
		String category = request.getParameter("category");
		String description = request.getParameter("description");
		String rating = request.getParameter("age_rating");
		int year = Integer.parseInt(request.getParameter("year"));
		int duration = Integer.parseInt(request.getParameter("duration"));
		
		HttpSession session = request.getSession(false);
		int contentAdminId = userDao.selectIdFromUsername(session.getAttribute("username").toString());
		System.out.println("ContentAdminId: "+contentAdminId);
		
		Movie movie = new Movie(title,description,category,director,rating,duration,year,contentAdminId);

		if(reservationDao.insertMovie(movie))
		{
			request.setAttribute("info", "Movie created!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Error! Session invalid?");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=movielist");
		dispatcher.forward(request, response);
	}
	
	private void insertScreening(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int movieId = Integer.parseInt(request.getParameter("movie_id"));
		int screenId = Integer.parseInt(request.getParameter("screen_id"));
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		boolean available = Boolean.parseBoolean(request.getParameter("available"));
		
		HttpSession session = request.getSession(false);
		int contentAdminId = userDao.selectIdFromUsername(session.getAttribute("username").toString());
		System.out.println("ContentAdminId: "+contentAdminId);
		
		Screening screening = new Screening(screenId,contentAdminId,movieId,time,date,available);

		if(reservationDao.insertScreening(screening))
		{
			request.setAttribute("info", "Screening created!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Error! Session invalid?");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/contentadmin?action=screeninglist");
		dispatcher.forward(request, response);
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
		}else if(session.getAttribute("userType")!="content_admin"){	
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
				//showMovieList(request, response); // auto-run
			}
			else {
				switch (action) {
				case "insertcinema":
					System.out.println("#POST# action = insertcinema");
					insertCinema(request, response);
					break;
				case "insertscreen":
					System.out.println("#POST# action = insertscreen");
					insertScreen(request, response);
					break;
				case "insertmovie":
					System.out.println("#POST# action = insertmovie");
					insertMovie(request, response);
					break;
				case "insertscreening":
					System.out.println("#POST# action = insertscreening");
					insertScreening(request, response);
					break;
				}
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		doGet(request, response);
	}
}
