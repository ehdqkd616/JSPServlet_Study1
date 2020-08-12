<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
<jsp:include page="/incl/staticHeader.jsp" />
</head>
<body class="page">
<jsp:include page="/incl/header.jsp" />
	<div id="page-banner"
		style="background-image: url(img/photo-typo.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>회원 정보</h1>
			</div>
		</div>
	</div>
	<div id="page-body">
		<div class="container">
			<div class="row">
				<!--blog posts container-->
				<div class="col-md-offset-3 col-md-6 page-block">
<h3 align=center>회원 정보</h3>
<table class="table table-striped table-bordered">
<tr>
	<td>아이디</td>
	<td><input type="text" name="userid"
	value = "${member.userId}" ${empty member.userId ? "": "readonly"}></td>
</tr>
<tr>
	<td>비밀번호</td>
	<td><input type="password" name="password"
	value="${member.password}" ${empty member.userId ? "": "readonly"}></td>
</tr>
<tr>
	<td>이름</td>
	<td><input type="text" name="name"
	value="${member.name}" ${empty member.userId ? "": "readonly"}></td>
</tr> 
<tr>
	<td>이메일</td>
	<td><input type="text" name="email"
	value="${member.email}" ${empty member.userId ? "": "readonly"}></td>
</tr>
<tr>
	<td>주소</td>
	<td><input type="text" name="address"
	value="${member.address}" ${empty member.userId ? "": "readonly"}></td>
</tr>
<tr>
	<td>글 갯수</td>
	<td><input type="text" name="count" value="${count}" ${empty count ? "" : "readonly"}>&nbsp;
	<a href='<c:url value="/Board.do?action=memberList"/>'>목록 확인</a></td>
	</tr>
<tr>
	<td colspan=2><h3 align="center"><a href="/M3/Member.do?action=update"><input type="button" value="회원정보수정"></a>&nbsp;&nbsp;<a href="/M3/Member.do?action=delete"><input type="button" value="회원정보삭제"></a></h3></td>
</table>
</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
<jsp:include page="/incl/footer.jsp" />
</body>
</html>