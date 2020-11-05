<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="lab.web.model.EmpVO" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 검색 페이지</title>
</head>
<body>
<h2>사원 정보 검색</h2>
<form action ="/JDBC/Emp.do">
<input type=hidden name=action value=search_D>
<p>사원 부서 입력 : <input type=text name=depId>
<input type=submit value=검색>
</p>
</form>
</body>
</html>

<%--
<p>검색 정보</p><br>
<%ArrayList<EmpVO> list = (ArrayList<EmpVO>)request.getAttribute("list"); %>
<table>
<tr><td>사원번호</td><td>이름</td><td>연락처</td><td>급여</td><td>입사일</td></tr>
<%for(EmpVO emp : list) { %>
<tr>
<td><%=emp.getEmployeeId()%></td>
<td><%=emp.getFirstName()%> <%=emp.getLastName()%></td>
<td><%=emp.getPhoneNumber()%></td>
<td><%=emp.getSalary()%></td>
<td><%=emp.getHireDate()%></td>
<td><%=emp.getDepartmentId()%></td>
</tr>
<% } %>
</table>
</body>
</html>
 --%>