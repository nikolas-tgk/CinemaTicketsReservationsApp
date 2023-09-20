package com.ticketservice.model;

public class Movie {
	private int id;
	private String title;
	private String ageRating;
	private String director;
	private String description;
	private String category;
	private int duration;
	private int year;
	private int contentAdminId;

	public Movie(int id, String title, String description, String category, String director, String age_rating, int duration, int year, int contentAdminId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.director = director;
		this.category = category;
		this.ageRating = age_rating;
		this.duration = duration;
		this.year = year;
		this.contentAdminId = contentAdminId;
	}
	
	// without id for movie creation
	public Movie(String title, String description, String category, String director, String age_rating, int duration, int year, int contentAdminId) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.director = director;
		this.ageRating = age_rating;
		this.duration = duration;
		this.year = year;
		this.contentAdminId = contentAdminId;
	}
	
	public int getMovieId() {
		return id;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getContentAdminId() {
		return contentAdminId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	
	public String getDirector() {
		return director;
	}
	
	public String getAgeRating() {
		return ageRating;
	}
	
	public String getCategory() {
		return category;
	}

}
