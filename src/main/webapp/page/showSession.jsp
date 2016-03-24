<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>showSession</title>
</head>
<body>
<%
	String sessionValue=(String)session.getAttribute("testSession");
%>

<h1>Session Value From Servlet is: <%=sessionValue%></h1>
</body>
</html>