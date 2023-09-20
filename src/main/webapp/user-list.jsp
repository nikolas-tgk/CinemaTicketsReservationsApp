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
	}else if(session.getAttribute("userType")!="admin"){
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
				<a href=admin?action=userlist class="navbar-brand">Admin Panel </a>
			</div>

			<ul class="navbar-nav">
				<li><a href=admin?action=userlist
					class="nav-link">Users</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=admin?action=customerlist
					class="nav-link">Customers</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=admin?action=contentadminlist
					class="nav-link">Content Admins</a></li>
			</ul>
			<ul class="navbar-nav">
				<li><a href=admin?action=adminlist
					class="nav-link">Admins</a></li>
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
			<h3 class="text-center">Query Result</h3>
			<hr>
			<div class="container text-left">

				<a href="admin?action=new" class="btn btn-success">New Admin</a>
			</div>
			<span id='info' style='color:black; display: table; margin: 0 auto;'></span>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>Username</th>
						<th>Email</th>
						<th>Full Name</th>
						<th>Type</th>
						<th>Created</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>					
					<c:forEach var="user" items="${listUser}">
						<tr>
							<td><c:out value="${user.getUserId()}" /></td>
							<td><c:out value="${user.getUsername()}" /></td>
							<td><c:out value="${user.getEmail()}" /></td>
							<td><c:out value="${user.getName()}" /></td>
							<td><c:out value="${user.getType()}" /></td>
							<td><c:out value="${user.getCreationTime()}" /></td>
							<td><a href="admin?action=delete&id=<c:out value='${user.getUserId()}' />">Delete</a></td>
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