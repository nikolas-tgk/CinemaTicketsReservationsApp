<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Reservation Form</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
<% 
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //http 1.1
	response.setHeader("Pragma", "no-cache"); //http 1.0
	response.setHeader("Expires", "0"); //prox
	
	if(session.getAttribute("username")==null)
	{
		response.sendRedirect("login.jsp");
	}else if(session.getAttribute("userType")!="customer"){
		session.removeAttribute("username"); 		
		session.invalidate();
		request.setAttribute("msg","Invalid user type.");
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		requestDispatcher.forward(request,response);
	}
%>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: blue">
			<div>
				<a href=customer?action=availablemovielist class="navbar-brand">Customer Portal </a>
			</div>

			<ul class="navbar-nav">
				<li><a href=customer?action=availablemovielist
					class="nav-link">Available Movies</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=customer?action=screeninglist
					class="nav-link">Screenings</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=customer?action=reservationlist
					class="nav-link">Reservations</a></li>
			</ul>
			<ul class="navbar-nav" >
				<li ><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Logout</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">


				<form action="customer?action=bookreservation" method="post">
				
				<caption>
					<h3 style="text-align: center;">
            			Reservation
					</h3>
				</caption>
				<br>
				<input name="screening_id"  hidden=true type="text" value="<c:out value='${screening.getId()}'/>" />
				<fieldset class="form-group">
					<label>Movie</label> <input type="text"  id="fullname"
						 class="form-control"
						name="movie" required="required" value="<c:out value='${screening.getMovieName()}' />" disabled>
				</fieldset>				
				<fieldset class="form-group">
					<label>Screen</label> <input type="text"
						 class="form-control"
						name="screen" required="required" value="<c:out value='${screening.getScreenName()}' />" disabled>
				</fieldset>
				<fieldset class="form-group">
					<label>Screen Type</label> <input type="text"
						 class="form-control"
						name="screen_type" required="required" value="<c:out value='${screening.getScreenType()}' />" disabled>
				</fieldset>
				<fieldset class="form-group">
					<label>Cinema</label> <input type="text"
						class="form-control"
						name="cinema" required="required" value="<c:out value='${screening.getCinemaName()}' />" disabled>
				</fieldset>	
				<fieldset class="form-group">
					<label>Date</label> <input type="text"
						class="form-control"
						name="cinema" required="required" value="<c:out value='${screening.getDate()}' />" disabled>
				</fieldset>	
				<fieldset class="form-group">
					<label>Time</label> <input type="text"
						class="form-control"
						name="cinema" required="required" value="<c:out value='${screening.getTimeStart()}' />" disabled>
				</fieldset>			
				<fieldset class="form-group">
					<label>Seats</label>
					<select name="seats" id="seats" required="required" class="form-control">
					<option value="1">1</option>
					<option value="2">2 </option>
					<option value="3">3 </option>
					<option value="4">4 </option>
					<option value="5">5 </option>
					</select>
				</fieldset>
				<button type="submit" class="btn btn-success">Book</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>