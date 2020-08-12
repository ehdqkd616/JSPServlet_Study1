<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 입력</title>
<%@ include file="/incl/staticHeader.jsp" %>
</head>
<body class="page">
<%@ include file="/incl/header.jsp" %>
	<div id="page-banner"
		style="background-image: url(img/photo-typo.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>${message}</h1>
			</div>
		</div>
	</div>
	<div id="page-body">
		<div class="container">
			<div class="row">
				<!--blog posts container-->
				<div class="col-md-offset-3 col-md-6 page-block">
<h3 align=center>회원 정보 입력</h3>
<form action="/M3/Member.do" method="post">
<fieldset>
<legend>회원 정보</legend>
<table class="table table-striped table-bordered">
<tr>
	<td >아이디</td>
	<td ><input type="text" name="userid"
	value = "${member.userId}" ${empty member.userId ? "": "readonly"}></td>
</tr>
<tr>
	<td >비밀번호</td>
	<td ><input type="password" name="password"
	value="${member.password}"></td>
</tr>
<tr>
	<td >이름</td>
	<td ><input type="text" name="name"
	value="${member.name}"></td>
</tr>
<tr>
	<td >이메일</td>
	<td ><input type="text" name="email"
	value="${member.email}"></td>
</tr>
<tr>
	<td >주소</td>
	<td ><input type="text" name="address"
	value="${member.address}"></td>
</tr>
</table>
	<input type="hidden" name="action" value="${action}">
	<input type="submit" value="저 장">
	<input type="reset" value="취 소">
</fieldset>
</form>
</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/incl/footer.jsp" %>
</body>
</html>