<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ page isELIgnored="false" %>
<meta charset="ISO-8859-1">
<title>Admin Panel</title>
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
	<div class="row">
		<div class="container">
			<h3 class="text-center">Screens</h3>
			<hr>
			<span id='info' style='color:black; display: table; margin: 0 auto;'></span>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Cinema</th>
						<th>Seats</th>
						<th>Type</th>
						<th>Available</th>
						<th><a href="contentadmin?action=newscreen" class="btn btn-success">Add Screen</a></th>
					</tr>
				</thead>
				<tbody>					
					<c:forEach var="screen" items="${screenList}">
						<tr>
							<td><c:out value="${screen.getId()}"/></td>
							<td><c:out value="${screen.getName()}"/></td>
							<td><c:out value="${screen.getCinema()}"/></td>
							<td><c:out value="${screen.getSeats()}"/></td>
							<td><c:out value="${screen.getType()}"/></td>
							<td><c:out value="${screen.getAvailability()}"/></td>
							<td><a href="contentadmin?action=removescreen&id=<c:out value='${screen.getId()}' />">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
  <script>
 function writeMessage(message_content,color) {
	    document.getElementById('info').innerHTML = message_content;
	    document.getElementById('info').style.color = color;
	}
 </script>
 <% 
 if(request.getAttribute("info")!=null)
 {
 String message_context = (String)request.getAttribute("info");
 String text_color = (String)request.getAttribute("info-color");%>
	 <script>var jsMsg = '<%=message_context%>'; var jsTextColor = '<%=text_color%>'; writeMessage(jsMsg,jsTextColor)</script>
 <%}
%>
</body>
</html>