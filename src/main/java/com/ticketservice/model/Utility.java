package com.ticketservice.model;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.SecureRandom;




public class Utility {

	public Utility() {	
	}
	
	public String generateSalt()
	{
		try {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			String saltString = new String(salt);
			System.out.println("Salt Generated.");
			return saltString;
		}catch(Exception e)
		{
			System.out.println("Salt Generation Fail.");
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String encrypt(String password, String salt)
	{
		try {
			String saltedPassword = password+salt;
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] hashedPassword = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
			password = new String(hashedPassword);
			System.out.println("Password Encryption Success!");
		}catch(Exception e)
		{
			System.out.println("Password Encryption Fail");
			e.printStackTrace();
		}
		
		return password;
	}
	


}
