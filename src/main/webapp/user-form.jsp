<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Add new admin</title>
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
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">


				<form action="admin?action=insert" method="post">
				
				<caption>
					<h3 style="text-align: center;">
            			Admin Form
					</h3>
				</caption>
				<br>

				<fieldset class="form-horizontal">
					 <p>Type</p>
				     <label>Content Admin&nbsp;</label><input type="radio" checked id="content_admin" name="user_type" value="content_admin" onclick="onRadioChange()">					 
					 <label>Admin&nbsp;</label><input type="radio" id="admin" name="user_type" value="admin" onclick="onRadioChange()">				 
				</fieldset>

				<fieldset class="form-group">
					<label>Full Name</label> <input type="text"  id="fullname"
						 class="form-control"
						name="fullname" required="required">
				</fieldset>
				
				<fieldset class="form-group">
					<label>Username</label> <input type="text"
						 class="form-control"
						name="username" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>E-mail</label> <input type="email"
						 class="form-control"
						name="email" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Password</label> <input type="password" id="passInput"
						class="form-control"
						name="password" minlength="6" required="required" >
				</fieldset>
				
				<fieldset class="form-group">
					<label>Repeat Password</label> <input type="password" id="passInput2"
						 class="form-control"
						name="password2" required="required" >
						<span id='message'></span>
				</fieldset>
				

				<button type="submit" onclick="return validatePassword()" class="btn btn-success">Create</button>
				</form>
			</div>
		</div>
	</div>

<script>
function showPassword() {
  var inpt = document.getElementById("passInput");
  if (inpt.type === "password") {
	  inpt.type = "text";
  } else {
	  inpt.type = "password";
  }
}

function onRadioChange() {
	var customerRadio = document.getElementById("customer");
	
	 var respons = document.getElementById("fullname");
	if (customerRadio.checked){
		respons.value = "customer";
	} else {
		respons.value = "non-customer";
	}
}

var validatePassword = function() {
	  if (document.getElementById('passInput').value ==
	    document.getElementById('passInput2').value && document.getElementById('passInput').value.length > 0) {
	    document.getElementById('message').style.color = 'green';
	    document.getElementById('message').innerHTML = 'Passwords match!';
	    return true;
	  } else {
	    document.getElementById('message').style.color = 'red';
	    document.getElementById('message').innerHTML = 'Passwords do not match!';
	    return false;
	  }
	}
</script>
</body>
</html>