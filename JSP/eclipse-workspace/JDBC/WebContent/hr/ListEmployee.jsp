<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="lab.web.mail.EmpVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록 페이지</title>
</head>
<body>
<h1>사원 목록</h1>
<table>
<tr><td>사원번호</td><td>이름</td><td>연락처</td><td>급여</td><td>입사일</td></tr>
<c:forEach var="emp" items="${list}">
<tr>
<td><a href="/JDBC/Emp.do?empId=${emp.employeeId}&action=view">${emp.getEmployeeId}</a></td>
<td>${emp.firstName} ${emp.lastName}</td>
<td>${emp.phoneNumber}</td>
<td>${emp.salary}</td>
<td>${emp.hireDate}</td>
</tr>
</c:forEach>
</table>
</body>
</html>