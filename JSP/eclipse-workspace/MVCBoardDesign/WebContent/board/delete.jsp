<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>글 삭제 화면</title>
<jsp:include page="/incl/staticHeader.jsp" />
</head>
<body class="page">
	<jsp:include page="/incl/header.jsp" />
	<div id="page-banner"
		style="background-image: url(img/photo-1501.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>글 삭제 화면</h1>
			</div>
		</div>
	</div>
	<div id="page-body">
		<div class="container">
			<div class="row wow fdeInUp">
				<!--blog posts container-->
				<div class="col-md-12 page-block">
					<h3>글 삭제 비밀번호 입력</h3>
					<form action='<c:url value="/Board.do"/>' method="post">
						<input type="hidden" name="action" value="${action}"> <input
							type="hidden" name="bbsno" value="${bbsno}"> <input
							type="hidden" name="replynumber" value="${replynumber}">
						<input type="password" name="password"> <input
							type="submit" value="삭 제">
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/incl/footer.jsp" />
</body>
</html>