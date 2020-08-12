<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>로그인</h2>
<%if(request.getAttribute("message")==null) {}else{%>
<%=request.getAttribute("message")%> <% } %>
<%=session.getId()%>
<form action="/Login.do" method=post>
아이디 : <input type=text name=id><br>
비밀번호 : <input type=password name=pw><br>
<input type=hidden name=action value=login>
<input type=submit value=로그인>
</form>
</body>
</html>