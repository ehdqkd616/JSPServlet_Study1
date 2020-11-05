<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 검색 페이지</title>
</head>
<body>
<h2>사원 정보 검색</h2>
<form action ="/JDBC/Emp.do">
<input type=hidden name=action value=search>
<p>사원 번호 입력 : <input type=text name=empId>
<input type=submit value=검색>
</p>
</form>
<p>검색 정보 = <%=request.getAttribute("emp")%></p>
<form action ="/JDBC/Emp.do">
<input type=hidden name=action value=search_D>
<p>사원 부서 입력 : <input type=text name=DepId>
<input type=submit value=검색></p>
<p>검색 정보 = <%=request.getAttribute("emp")%></p>
</form>
</body>
</html>