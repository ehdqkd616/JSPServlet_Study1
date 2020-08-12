<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="lab.web.mail.EmpVO" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>사원 목록</h1>
<%ArrayList<EmpVO> list = (ArrayList<EmpVO>)request.getAttribute("list"); %>
<table>
<tr><td>사원번호</td><td>이름</td><td>연락처</td><td>급여</td><td>입사일</td></tr>
<%for(EmpVO emp : list) { %>
<tr>
<td><a href="/JDBC/Emp.do?empId=<%=emp.getEmployeeId()%>&action=view"><%=emp.getEmployeeId()%></a></td>
<td><%=emp.getFirstName()%> <%=emp.getLastName()%></td>
<td><%=emp.getPhoneNumber()%></td>
<td><%=emp.getSalary()%></td>
<td><%=emp.getHireDate()%></td>
</tr>
<% } %>
</table>
</body>
</html>