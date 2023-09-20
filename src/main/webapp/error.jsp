<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage = "true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
</head>
<body>
<centre>
<h1>There has been an error.</h1>
<h2><%response.sendRedirect("login.jsp");%>
</h2>
</centre>
</body>
</html>