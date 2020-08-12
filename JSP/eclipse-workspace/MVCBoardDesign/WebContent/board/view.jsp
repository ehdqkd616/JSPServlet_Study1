<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글 상세 내용 화면</title>
<jsp:include page="/incl/staticHeader.jsp" />
</head>
<body class="page">
	<jsp:include page="/incl/header.jsp" />
	<div id="page-banner"
		style="background-image: url(img/photo-1478.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>상세 내용</h1>
			</div>
		</div>
	</div>
	<div id="page-body">
		<div class="container">
			<div class="row">
				<!--blog posts container-->
				<div class="col-md-offset-3 col-md-6 page-block">
					<table class="table table-striped table-bordered">
						<tr>
							<th>작성자 이름</th>
							<td>${board.name}</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>${board.subject}</td>
						</tr>
						<tr>
							<th>내용</th>
							<td>${board.content}</td>
						</tr>
						<tr>
							<td colspan=2><br>
							<br> <h6 align=center><a href='<c:url value="/Board.do?action=list"/>'>목록</a>
								<a
								href='<c:url value="/Board.do?action=reply&bbsno=${board.bbsno}"/>'>댓글</a>
								<a
								href='<c:url value="/Board.do?action=update&bbsno=${board.bbsno}&userid=${board.userId}"/>'>수정</a>
								<a
								href='<c:url value="/Board.do?action=delete&bbsno=${board.bbsno}&replynumber=${board.replyNumber}&userid=${board.userId}"/>'>삭제</a></h6>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/incl/footer.jsp" />
</body>
</html>