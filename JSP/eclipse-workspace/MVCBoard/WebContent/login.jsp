<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link rel="stylesheet" href="./css/default.css" media="screen">
<title>로그인 화면</title>
</head>
<body>
<table class="layout">
<tr height="50"><td>
<jsp:include page="/incl/header.jsp" />
</td></tr>
<!-- 본문 표시 코드 -->
<tr height="500" valign="top"><td>
<h1>로그인 화면</h1>
				<c:if test="${!empty message}">
				<%-- 로그인 실패 또는 회원 가입 및 회원 삭제 등의 경우에 표시할 메세지 --%>
				${message}
				</c:if>
				<h2></h2>
					<h3>아이디와 비밀번호를 입력하세요.</h3>
					<%-- core 태그 이용시 프로젝트의 주소는 빼줘도 됨 - 어차피 프로젝트 내에서만 호출 --%>
					<form action='<c:url value="/Login.do"/>' method="post">
						<input type="text" name="userid" >
						<input type="password" name="password">
					<input type="submit" value="로그인">
					</form>
</td></tr>
<!-- 본문 표시 코드 -->
<tr height="50"><td>
<jsp:include page="/incl/footer.jsp" />
</td></tr>
</table>
</body>
</html>