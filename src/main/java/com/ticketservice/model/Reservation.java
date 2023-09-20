package com.ticketservice.model;

public class Reservation {
	private int id;
	private int screeningId;
	private int seats;
	private String customerUsername;
	
	private String movieName;
	private String screenName;
	private String screenType;
	private String cinemaName;
	private String timeStart;
	private String date;

	public Reservation(int id, int screeningId, int numberOfSeats, String customerUsername) {
		this.id = id;
		this.screeningId = screeningId;
		this.seats = numberOfSeats;
		this.customerUsername = customerUsername;
	}
	
	public Reservation(int id, int screeningId, int numberOfSeats, String customerUsername, String movieName, String screenName, String cinemaName, String timeStart, String date, String screenType) {
		this.id = id;
		this.screeningId = screeningId;
		this.seats = numberOfSeats;
		this.customerUsername = customerUsername;
		
		this.movieName = movieName;
		this.screenName = screenName;
		this.screenType = screenType;
		this.cinemaName = cinemaName;
		this.timeStart = timeStart;
		this.date = date;
		
	}
	
	// wihout id for reservation creation
	public Reservation(int screeningId, int numberOfSeats, String customerUsername) {
		this.screeningId = screeningId;
		this.seats = numberOfSeats;
		this.customerUsername = customerUsername;
	}
	
	
	public int getId() {
		return id;
	}

	public int getScreeningId() {
		return screeningId;
	}
	
	public int getSeats() {
		return seats;
	}
	
	public String getMovieName() {
		return movieName;
	}
	
	public String getCinemaName() {
		return cinemaName;
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public String getTimeStart() {
		return timeStart;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getScreenType() {
		return screenType;
	}
	
	public String getCustomerUsername() {
		return customerUsername;
	}

}
