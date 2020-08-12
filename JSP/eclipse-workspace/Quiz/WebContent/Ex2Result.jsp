<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/Quiz/Ex2.do" method=post>
<h2>입력하신 숫자는 <%=request.getParameter("num")%>, <%=request.getAttribute("message")%> </h2>
<br>
</form>
</body>
</html>