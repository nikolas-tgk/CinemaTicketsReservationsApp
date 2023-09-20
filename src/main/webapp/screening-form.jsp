<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>New Screening</title>
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
		request.setAttribute("msg","You are not logged in!");
		response.sendRedirect("login.jsp");
	}else if(session.getAttribute("userType")!="content_admin"){
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
				<a href=contentadmin?action=movielist class="navbar-brand">Content Admin Panel </a>
			</div>
			<ul class="navbar-nav">
				<li><a href=contentadmin?action=cinemalist
					class="nav-link">Cinemas</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=contentadmin?action=screenlist
					class="nav-link">Screens</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=contentadmin?action=screeninglist
					class="nav-link">Screenings</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=contentadmin?action=movielist
					class="nav-link">Movies</a></li>
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
				<form action="contentadmin?action=insertscreening" method="post">
				<caption>
					<h3 style="text-align: center;">
            			Screening Form
					</h3>
				</caption>
				<br>
				<fieldset class="form-group">
					<label>Movie</label>
					<select name="movie_id" id="movie_id" required="required" class="form-control">
						<c:forEach var="existingMovie" items="${movieList}">
							<option value="<c:out value="${existingMovie.getMovieId()}"/>"><c:out value="${existingMovie.getTitle()}"/></option>
						</c:forEach>
					  	</select>
				</fieldset>
				<fieldset class="form-group">
					<label>Screen</label>
					<select name="screen_id" id="screen_id" required="required" class="form-control">
						<c:forEach var="existingScreen" items="${screenList}">
							<option value="<c:out value="${existingScreen.getId()}"/>"><c:out value="${existingScreen.getName()}"/> - <c:out value="${existingScreen.getCinema()}"/></option>
						</c:forEach>
					  	</select>
				</fieldset>
				<fieldset class="form-group">
					<label>Date</label> <input type="date"
						 class="form-control"
						name="date" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Time</label> <input type="time"
						 class="form-control"
						name="time" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Available</label>
					<select name="available" id="available" required="required" class="form-control">
					<option value="true">Yes</option>
					<option value="false">No </option>
					</select>
				</fieldset>
				<button type="submit" class="btn btn-success">Create</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>