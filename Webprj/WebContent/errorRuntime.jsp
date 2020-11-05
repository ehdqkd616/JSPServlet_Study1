<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type=text/javascript>
alert("에러 원인 : <%=exception.getMessage()%>")
history.back();
<%--location.href="주소" 해당 주소로 이동--%>
</script>
<h1>페이지를 찾을 수 없습니다.</h1>
</body>
</html>