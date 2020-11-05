<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>데이터 목록</h2>
<%HashMap<String,String> map = (HashMap<String,String>)request.getAttribute("map");
Set<String> keySet = map.keySet(); %>
<%for(String key : keySet) { %>
이름 : <%=key%>, 연락처 : <%=map.get(key)%><br>
<% } %>
</body>
</html>
