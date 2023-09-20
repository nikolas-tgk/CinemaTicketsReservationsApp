<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="UTF-8">
    <title> Registration Form </title>
    <link rel="stylesheet" href="./css/register.css" type="text/css">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <style>
     <%@ include file="./css/register.css"%>
	 </style>
   </head>
<body>
<% 
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //http 1.1
	response.setHeader("Pragma", "no-cache"); //http 1.0
	response.setHeader("Expires", "0"); //prox
	

	session.removeAttribute("username"); 		
	session.invalidate();
	
%>
  <div class="container">
    <div class="title">Registration Form</div>
    <div class="content">
      <form action="register"  method="post">
        <div class="user-details">
          <div class="input-box">
            <span class="details">Full Name</span>
            <input type="text" name="fullname" placeholder="Enter your name" required>
          </div>
          <div class="input-box">
            <span class="details">Username</span>
            <input type="text" name="username"  placeholder="Enter your username" required>
          </div>
          <div class="input-box">
            <span class="details">E-mail</span>
            <input type="email" name="email"  placeholder="Enter your email" required>
          </div>
          <div class="input-box">
            <span class="details">Age</span>
            <input type="number" name="age"  min=1 max=120  placeholder="Enter your age" required>
          </div>
          <div class="input-box">
            <span class="details">Password</span>
            <input type="password" name="password" id="password1" placeholder="Enter your password" minlength="6" required>
          </div>
          <div class="input-box">
            <span class="details">Confirm Password</span>
            <input type="password" id="password2" placeholder="Confirm your password" required>
          </div>
         <span id='message_pass_val'></span>
        </div>
        <div class="gender-details">
          <input type="radio" name="gender" value="male" id="dot-1" required>
          <input type="radio" name="gender" value="female" id="dot-2">
          <input type="radio" name="gender" value="unkown" id="dot-3" checked>
          <span class="gender-title">Gender</span>
          <div class="category">
            <label for="dot-1">
            <span class="dot one"></span>
            <span class="gender">Male</span>
          </label>
          <label for="dot-2">
            <span class="dot two"></span>
            <span class="gender">Female</span>
          </label>
          <label for="dot-3">
            <span class="dot three"></span>
            <span class="gender">Prefer not to say</span>
            </label>
          </div>
        </div>
        <div class="button">
          <input type="submit"  onclick="return validatePassword()" value="Register">
        </div>
      </form>
       Want to login instead? <a href="<%=request.getContextPath()%>/login">Click here</a><br><br>
       <span id='info' style='color:red; display: table; margin: 0 auto;'></span>
    </div>
  </div>
 
<script>
var validatePassword = function() {
	  if (document.getElementById('password1').value ==
	    document.getElementById('password2').value && document.getElementById('password1').value.length > 0) {
	    document.getElementById('message_pass_val').style.color = 'green';
	    document.getElementById('message_pass_val').innerHTML = 'Passwords match!';
	    return true;
	  } else {
	    document.getElementById('message_pass_val').style.color = 'red';
	    document.getElementById('message_pass_val').innerHTML = 'Passwords do not match!';
	    return false;
	  }
	}

</script>

<script>
 function writeMessage(message_content) {
	    document.getElementById('info').innerHTML = message_content;
	}
</script>
 <% 
 if(request.getAttribute("msg")!=null)
 {
 String message_context = (String)request.getAttribute("msg"); %>
	 <script>var jsMsg = '<%=message_context%>';writeMessage(jsMsg)</script>
 <%}
%>

</body>
</html>

