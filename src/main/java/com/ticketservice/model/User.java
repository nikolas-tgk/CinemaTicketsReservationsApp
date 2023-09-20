package com.ticketservice.model;

public class User {

		private String name;
		protected String username;
		protected String email;
		protected String user_type;
		protected String created_at ="";
		private String password;
		private String salt ="";
		protected int userId;
		
		static public class Customer extends User {

			protected int age;
			protected String gender;
			protected String discountType; // 'none', 'student50'
			
			public Customer(int id, String username,String email, String name, String creationTime, String discount, String gender, int age) {
				super(id,username,name,email,"customer",creationTime);
				this.age = age;
				this.gender = gender;
				this.discountType = discount;			
			}
			
			
			//without id and creationTime, with password,salt for customer creation
			public Customer(String username,String email, String password, String salt, String fullname, String discount, String gender, int age)
			{
				super(username,fullname,email,password,salt,"customer");
				this.age = age;
				this.gender = gender;
				this.discountType = discount;
			}
			
			/**
			 * @return the age
			 */
			public int getAge() {
				return age;
			}

			/**
			 * @param age the age to set
			 */
			public void setAge(int age) {
				this.age = age;
			}

			/**
			 * @return the gender
			 */
			public String getGender() {
				return gender;
			}

			/**
			 * @param gender the gender to set
			 */
			public void setGender(String gender) {
				this.gender = gender;
			}

			/**
			 * @return the discountType
			 */
			public String getDiscountType() {
				return discountType;
			}

			/**
			 * @param discountType the discountType to set
			 */
			public void setDiscountType(String discountType) {
				this.discountType = discountType;
			}	
		}
	
		public User(int id,String username,String name, String email, String userType, String createdAt)
		{
			this.userId = id;
			this.username = username;
			this.name = name;
			this.email = email;
			this.user_type = userType;
			this.created_at = createdAt;
		}
		
		// insert customer constructor
		public User(String username,String fullname, String email, String password, String salt, String userType)
		{
			this.username = username;
			this.name = fullname;
			this.email = email;
			this.password = password;
			this.salt = salt;
			this.user_type = userType;
		}
		
		public User(int id,String username, String user_type,String email)
		{
			this.userId = id;
			this.username = username;
			this.email = email;
			this.user_type = user_type;
		}
		//Getters Setters
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		
		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}
		
		
		/**
		 * @return the salt
		 */
		public String getSalt() {
			return salt;
		}
		
		/**
		 * @return user creation timedate
		public String getUserCreation() {
			return created_at;
		}
		
		/**
		 * @return the type
		 */
		public String getType() {
			return user_type;
		}
		
		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}


		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @return the userId
		 */
		public int getUserId() {
			return userId;
		}

		/**
		 * @param userId the userId to set
		 */
		public void setUserId(int userId) {
			this.userId = userId;
		}
		
		public String getCreationTime() {
			return created_at;
		}
		

}
