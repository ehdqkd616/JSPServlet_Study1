<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.model.EmpDetailVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 정보 페이지</title>
</head>
<body>
<h2>사원 상세 정보</h2>
<br>
<%EmpDetailVO emp = (EmpDetailVO)request.getAttribute("emp");%>
<table>
<tr>
<td>사원번호</td><td><%=emp.getEmployeeId()%></td>
</tr>
<tr>
<td>이름</td><td><%=emp.getFirstName()%> <%=emp.getLastName()%></td>
</tr>
<tr>
<td>이메일</td><td><%=emp.getEmail()%></td>
</tr>
<tr>
<td>전화번호</td><td><%=emp.getPhoneNumber()%></td>
</tr>
<tr>
<td>입사일</td><td><%=emp.getHireDate()%></td>
</tr>
<tr>
<td>직무</td><td><%=emp.getJobTitle()%>(<%=emp.getJobId()%>)</td>
</tr>
<tr>
<td>급여</td><td><%=emp.getSalary()%></td>
</tr>
<tr>
<td>보너스율</td><td><%=emp.getCommissionPct()%></td>
</tr>
<tr>
<td>매니저</td><td><%=emp.getManagerName()%>(<%=emp.getManagerId()%>)</td>
</tr>
<tr>
<td>부서</td><td><%=emp.getDepartmentName()%>(<%=emp.getDepartmentId()%>)</td>
</tr>

</table>
</body>
</html>