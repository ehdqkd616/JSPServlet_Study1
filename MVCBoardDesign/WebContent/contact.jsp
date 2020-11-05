<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/incl/staticHeader.jsp" />
<title>문의 페이지</title>
<style>
	fieldset{
		width: 500px;
	}
	
	th{
		text-valign: middle;
	}
</style>
</head>
<body class="page">
<jsp:include page="/incl/header.jsp" />
	<div id="page-banner"
		style="background-image: url(img/photo-1501.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>문의 내용 작성</h1>
			</div>
		</div>
	</div>
	<div id="page-body">
		<div class="container">
			<div class="row wow fdeInUp">
				<!--blog posts container-->
				<div class="col-md-offset-3 col-md-6 page-block">
				<form action='<c:url value="/Board.do"/>' method="post">
<!-- BoardServlet에서 메일전송 실행. -->
<fieldset>
<h3 align=center>문의 메일</h3>
<table class="table table-bordered table-striped">
<tr>
<td><h5>보내시는 분</h5></td><td><input type=text name=from value="답장을 받으실 메일 주소를 적어주세요."></td>
</tr>
<tr>
<!-- readonly 속성은 해당 칸의 값을 보기만 하고 수정 불가능하게 만듦. -->
<td><h5>수신</h5></td><td><input type=text value="ehdqkd61616@naver.com" readonly></td>
</tr>
<tr>
<td><h5>제목</h5></td><td><input type=text name=subject></td>
</tr>
<tr>
<td><h5>이름</h5></td><td><input type=text name=name></td>
</tr>
<tr>
<td><h5>내용</h5></td><td><textarea name=content cols=15 rows=5>
문의 내용을 입력해 주세요. 최대한 빨리 답장해 드리겠습니다.
</textarea></td>
</tr>
<tr>
<td colspan=2 align=center><input type=submit value=메일전송></td>
</tr>
</table>
</fieldset>
<input type=hidden name=action value="contact_do">
</form>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="/incl/footer.jsp" />
</body>
</html>