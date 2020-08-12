<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%if(request.getAttribute("message")==null) { %>
이름 : <%=request.getParameter("name")%>, 연락처 : <%=request.getAttribute("tel")%>
<% }else { %>
<%=request.getAttribute("message")%>
<% } %>

</body>
</html>