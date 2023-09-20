<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ page isELIgnored="false" %>
<meta charset="ISO-8859-1">
<title>Reservations</title>
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

	<div class="row">

		<div class="container">
			<h3 class="text-center">Reservations</h3>
			<hr>
			<div class="container text-left">

				
			</div>
			<span id='info' style='color:black; display: table; margin: 0 auto;'></span>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>Movie</th>
						<th>Screen</th>
						<th>Screen Type</th>
						<th>Cinema</th>
						<th>Date</th>
						<th>Time</th>
						<th>Seats</th>
						<th></th>
					</tr>
				</thead>
				<tbody>					
					<c:forEach var="reservation" items="${reservationsList}">
						<tr>
							<td><c:out value="${reservation.getId()}"/></td>							
							<td><c:out value="${reservation.getMovieName()}"/></td>	
							<td><c:out value="${reservation.getScreenName()}"/></td>
							<td><c:out value="${reservation.getScreenType()}"/></td>
							<td><c:out value="${reservation.getCinemaName()}"/></td>
							<td><c:out value="${reservation.getDate()}"/></td>
							<td><c:out value="${reservation.getTimeStart()}"/></td>	
							<td><c:out value="${reservation.getSeats()}"/></td>						
							<td><a href="customer?action=deleteres&id=<c:out value='${reservation.getId()}' />">Remove</a>
								</td>
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