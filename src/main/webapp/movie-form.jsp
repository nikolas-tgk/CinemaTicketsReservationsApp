<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>New Movie</title>
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
				<form action="contentadmin?action=insertmovie" method="post">
				<caption>
					<h3 style="text-align: center;">
            			Movie Form
					</h3>
				</caption>
				<br>
				<fieldset class="form-group">
					<label>Title</label> <input type="text"  id="title"
						 class="form-control"
						name="title" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Description</label> <input type="text"  id="description"
						 class="form-control"
						name="description" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Director</label> <input type="text"  id="director"
						 class="form-control"
						name="director" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Category</label> <input type="text"  id="category"
						 class="form-control"
						name="category" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Duration</label> <input type="number" min=1
						 class="form-control"
						name="duration" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Age Rating</label>
					<select name="age_rating" id="age_rating" required="required" class="form-control">
					<option value="G">G</option>
					<option value="PG">PG</option>
					<option value="PG-13">PG-13</option>
					<option value="R">R</option>
					<option value="NC-17">NC-17</option>
					</select>
				</fieldset>
				<fieldset class="form-group">
					<label>Year releashed</label> <input type="number" min=1888
						 class="form-control"
						name="year" required="required">
				</fieldset>
				<button type="submit" class="btn btn-success">Create</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>