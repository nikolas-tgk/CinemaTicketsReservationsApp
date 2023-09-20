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
import com.ticketservice.model.Movie;
import com.ticketservice.model.Reservation;
import com.ticketservice.model.Screening;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationDao reservationDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
    	reservationDao = new ReservationDao();
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
		}else if(session.getAttribute("userType")!="customer"){	
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
			System.out.println("null action --> availablemovielist");
			showAvailableMovieList(request, response); // auto-run
		}
		else {
			switch (action) {
			case "availablemovielist":
				System.out.println("action = availablemovielist");
				showAvailableMovieList(request, response);
				break;
			case "screeninglist":
				System.out.println("action = screeninglist");
				showAvailableScreeningsList(request, response);
				break;
			case "screeninglistbydate":
				System.out.println("action = screeninglistbydate");
				showAvailableScreeningsListByDate(request, response);
				break;
			case "screeningsOfMovie":
				System.out.println("action = screeningsOfMovie");
				showSpecificMovieScreenings(request, response);
				break;
			case "reservationlist":
				System.out.println("action = reservationlist");
				showReservationsOnUsername(request, response);
				break;
			case "book":
				System.out.println("action = book");
				showReservationForm(request, response);
				break;
			case "deleteres":
				System.out.println("action = deleteres");
				deleteReservation(request, response);
				break;
			case "seeDesc":
				System.out.println("action = seeDesc");
				showDescription(request, response);
				break;
			}	
		}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void showAvailableMovieList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Movie> movies = reservationDao.selectAvailableMovies();
		request.setAttribute("movieList", movies);
		RequestDispatcher dispatcher = request.getRequestDispatcher("available-movie-list.jsp");
		dispatcher.forward(request, response);
		return;
	}
	
	private void showAvailableScreeningsList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Screening> availableScreenings = reservationDao.selectAvailableScreenings();
		request.setAttribute("availableScreeningsList", availableScreenings);
		RequestDispatcher dispatcher = request.getRequestDispatcher("available-screening-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showAvailableScreeningsListByDate(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		List<Screening> availableScreenings = reservationDao.selectAvailableScreeningsByDate(fromdate,todate);
		request.setAttribute("availableScreeningsList", availableScreenings);
		RequestDispatcher dispatcher = request.getRequestDispatcher("available-screening-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showReservationsOnUsername(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession(false);
		List<Reservation> reservations = reservationDao.selectReservationOnUsername(session.getAttribute("username").toString());
		request.setAttribute("reservationsList", reservations);
		RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showReservationForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int screeningId = Integer.parseInt(request.getParameter("screening_id"));
		Screening screening = reservationDao.selectScreeningOnId(screeningId);
		request.setAttribute("screening", screening);
		RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addReservation(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession(false);
		String username = session.getAttribute("username").toString();
		int screeningId = Integer.parseInt(request.getParameter("screening_id"));
		int seats = Integer.parseInt(request.getParameter("seats"));
		Reservation reservation = new Reservation(screeningId,seats,username);
		if(reservationDao.insertReservation(reservation))
		{
			request.setAttribute("info", "Reservation created!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Error: reservation could not be created.");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer?action=reservationlist");
		dispatcher.forward(request, response);
	}
	
	private void deleteReservation(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int reservationId = Integer.parseInt(request.getParameter("id"));
		
		if(reservationDao.deleteReservation(reservationId))
		{
			request.setAttribute("info", "Reservation deleted!");
			request.setAttribute("info-color", "green");
		}
		else {
			request.setAttribute("info", "Error: reservation could not be deleted.");
			request.setAttribute("info-color", "red");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer?action=reservationlist");
		dispatcher.forward(request, response);
	}
	
	private void showSpecificMovieScreenings(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int movieId = Integer.parseInt(request.getParameter("id"));
		List<Screening> screenings  = reservationDao.selectSpecificMovieScreenings(movieId);
		request.setAttribute("availableScreeningsList", screenings);
		RequestDispatcher dispatcher = request.getRequestDispatcher("available-screening-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showDescription(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String desc = request.getParameter("desc");
		request.setAttribute("desc", desc);
		RequestDispatcher dispatcher = request.getRequestDispatcher("customer-movie-description.jsp");
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
		}else if(session.getAttribute("userType")!="customer"){	
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
			System.out.println("null action --> availablemovielist");
			showAvailableMovieList(request, response); // auto-run
		}
		else {
			switch (action) {
			case "bookreservation":
				System.out.println("action = bookreservation");
				addReservation(request, response);
				break;
			}	
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
