<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%if(session.getAttribute("name")==null) {
	response.sendRedirect("/Login.jsp");
}else { %>
<%=session.getAttribute("name")%>님 환영합니다.
오늘도 좋은하루 되세요.
<form action="/Login.do" method=post>
<input type=hidden name=action value=logout>
<input type=submit value=로그아웃>
<% } %>
</form>
</body>
</html>