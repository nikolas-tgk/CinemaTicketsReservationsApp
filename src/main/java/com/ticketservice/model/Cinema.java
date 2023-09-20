package com.ticketservice.model;

public class Cinema {
	private String name;
	private String address;
	private String provider;
	
	public Cinema(String name, String provider, String address) {
		this.name = name;
		this.address = address;
		this.provider = provider;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getProvider() {
		return provider;
	}

}
