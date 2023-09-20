<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="UTF-8">
    <title> Login Page </title>
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
    <div class="title">App login</div>
    <div class="content">
      <form action="login"  method="post">
        <div class="user-details">
          <div class="input-box">
            <span class="details">Username</span>
            <input type="text" placeholder="Enter your username" name="username" required >
          </div>
          <div class="input-box">
            <span class="details">Password</span>
            <input type="password" name="password" id="password1" placeholder="Enter your password" required>
          </div>
        </div>
        <div class="button">
          <input type="submit" value="Login">
        </div>
      </form>
       Not a member? <a href="<%=request.getContextPath()%>/register">Click here</a><br><br>
      <span id='info' style='color:red; display: table; margin: 0 auto;'></span>
    </div>
  </div>
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

