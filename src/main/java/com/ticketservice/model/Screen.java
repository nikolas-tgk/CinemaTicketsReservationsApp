package com.ticketservice.model;

public class Screen {
	private int id;
	private int seats;
	private String name;
	private String ofCinema;
	private String type;
	private boolean available;

	public Screen(int id , int seats, String name, String ofCinema, String type, boolean available) {
		this.id = id;
		this.seats = seats;
		this.name = name;
		this.ofCinema = ofCinema;
		this.type = type;
		this.available = available;
	}
	
	// without id for screen creation
	public Screen(int seats, String name, String ofCinema, String type, boolean available) {
		this.seats = seats;
		this.name = name;
		this.ofCinema = ofCinema;
		this.type = type;
		this.available = available;
	}
	
	public int getId() {
		return id;
	}
	
	public int getSeats() {
		return seats;
	}
	
	public boolean getAvailability() {
		return available;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCinema() {
		return ofCinema;
	}
	
	public String getType() {
		return type;
	}

}
