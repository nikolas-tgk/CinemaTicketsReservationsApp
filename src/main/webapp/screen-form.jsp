<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>New Cinema</title>
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
				<form action="contentadmin?action=insertscreen" method="post">
				<caption>
					<h3 style="text-align: center;">
            			Screen Form
					</h3>
				</caption>
				<br>
				<fieldset class="form-group">
					<label>Name</label> <input type="text"  id="name"
						 class="form-control"
						name="name" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Cinema</label>
						<select name="cinema" id="cinema" required="required" class="form-control">
						<c:forEach var="existingCinema" items="${cinemaList}">
							<option <c:out value="${existingCinema.getName()}"/>><c:out value="${existingCinema.getName()}"/></option>
						</c:forEach>
					  	</select>
				</fieldset>
				<fieldset class="form-group">
					<label>Seats</label> <input type="number" min=1
						 class="form-control"
						name="seats" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Type</label>
					<select name="type" id="type" required="required" class="form-control">
					<option value="Standard 2D">Standard 2D</option>
					<option value="2D Dolby">2D Dolby </option>
					<option value="3D">3D</option>
					<option value="IMAX">IMAX</option>
					<option value="Open Air">Open Air</option>
					</select>
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