<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="lab.web.mail.EmpVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서별 사원 검색</title>
</head>
<body>
<h2>부서별 사원 정보 검색</h2>
<form action ="/JDBC/Emp.do">
<input type=hidden name=action value=dept>
<p>사원 부서 입력 : <input type=text name=deptId>
<input type=submit value=검색></p>
</form>
<br>
<c:if test="${!empty list}">
${para.deptId}번 부서<br>
<table>
<tr><td>사원번호</td><td>이름</td><td>연락처</td><td>급여</td><td>입사일</td></tr>
<c:forEach var="emp" items="${list}">
<tr>
<td><a href="/JDBC/Emp.do?empId=${emp.employeeId}&action=view">${emp.employeeId}</a></td>
<td>${emp.firstName} ${emp.lastName}</td>
<td>${emp.phoneNumber}</td>
<td>${emp.salary}</td>
<td>${emp.hireDate}</td>
</tr>
</c:forEach>
</table>
</c:if>
</body>
</html>