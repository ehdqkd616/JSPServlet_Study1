<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>로그인 화면</title>
<%@ include file="/incl/staticHeader.jsp"%>
</head>
<body class="page">
	<%@ include file="/incl/header.jsp"%>
	<div id="page-banner"
		style="background-image: url(img/photo-1501.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>로그인 화면</h1>
			</div>
		</div>
	</div>
	<div id="page-body">
		<div class="container">
			<div class="row wow fdeInUp">
				<!--blog posts container-->
				<div class="col-md-12 page-block">
				<c:if test="${!empty message}">
				${message}
				</c:if>
				<h2></h2>
					<h3>아이디와 비밀번호를 입력하세요.</h3>
					<form action='<c:url value="/Login.do"/>' method="post">
						<input type="text" name="userid" >
						<input type="password" name="password">
					<input type="submit" value="로그인">
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/incl/footer.jsp"%>
</body>
</html>