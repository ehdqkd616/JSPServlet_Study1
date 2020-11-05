<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 첫 예제</title>
</head>
<body>
<%int a = (int)(Math.random()*10); %>
<%if(a>5) { %>
이 숫자는 <%=a%>, 5보다 큽니다.
<% }else { %>
이 숫자는 <%=a%>, 5보다 작거나 같습니다.
<% } %>

</body>
</html>