package com.ticketservice.model;

public class Screening {
	private int id;
	private int screenId;
	private int contentAdminId;
	private int filmId;
	private String timeStart;
	private String date;
	private boolean availability;
	
	private String movieName;
	private String cinemaName;
	private String screenName;
	private String screenType;

	
	public Screening(int id, int screenId, int contentAdminId, int filmId, String timeStart, String date, boolean availability, String movieName, String cinemaName, String screenName, String screenType) {
		this.id = id;
		this.screenId = screenId;
		this.contentAdminId = contentAdminId;
		this.filmId = filmId;
		this.timeStart = timeStart;
		this.date = date;
		this.availability = availability;
		
		this.movieName = movieName;
		this.cinemaName = cinemaName;
		this.screenName = screenName;
		this.screenType = screenType;

	}
	
	public Screening(int screenId, int contentAdminId, int filmId, String timeStart, String date, boolean availability) {
		this.screenId = screenId;
		this.contentAdminId = contentAdminId;
		this.filmId = filmId;
		this.timeStart = timeStart;
		this.date = date;
		this.availability = availability;
	}
	
public int getId() {
	return id;
}

public int getMovieId() {
	return filmId;
}

public int getScreenId() {
	return screenId;
}

public int getContentAdminId() {
	return contentAdminId;
}

public String getTimeStart() {
	return timeStart;
}

public String getDate() {
	return date;
}

public boolean getAvailability() {
	return availability;
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

public String getScreenType() {
	return screenType;
}



}
