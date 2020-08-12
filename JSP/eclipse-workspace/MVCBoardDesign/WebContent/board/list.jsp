<%@page import="java.io.IOException"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ElCeil" uri="/WEB-INF/tlds/el-function.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<!--[if IE]><meta http-equiv="x-ua-compatible" content="IE=9" /><![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>게시글 목록</title>
<jsp:include page="/incl/staticHeader.jsp" />
</head>
<body class="page">
<jsp:include page="/incl/header.jsp" />
	<div id="page-banner"
		style="background-image: url(img/photo-1501.jpg);">
		<div class="content  wow fdeInUp">
			<div class="container ">
				<h1>게시판 목록</h1>
			</div>
		</div>
	</div>

	<!--page body-->
	<div id="page-body">
		<div class="container">
			<div class="row wow fdeInUp">
				<!--blog posts container-->
				<div class="col-md-12 page-block">
					<h3>게시판 목록입니다.</h3>
					<table class="table table-striped table-bordered">
						<c:forEach var="board" items="${lists}">
							<tr>
								<td>${board.name}</td>
								<td><a
									href='<c:url value="/Board.do?action=view&bbsno=${board.bbsno}"/>'>${board.subject}</a></td>
								<td>${board.writeDate}</td>
								<td>${board.readCount}</td>
							</tr>
						</c:forEach>
					</table>
					<h6 align="center">
						<c:set var="totalPageBlock" value="${ElCeil:ElCeil(totalPageCount/10.0)}" />
						<c:set var="nowPageBlock" value="${ElCeil:ElCeil(page/10.0)}" />
						<c:set var="startPage" value="${(nowPageBlock-1)*10+1}" />
						<c:choose>
							<c:when test="${totalPageCount gt nowPageBlock*10}">
								<c:set var="endPage" value="${nowPageBlock*10}" />
							</c:when>
							<c:otherwise>
								<c:set var="endPage" value="${totalPageCount}" />
							</c:otherwise>
						</c:choose>
						<c:if test="${nowPageBlock gt 1}">
							<a href='<c:url value="/Board.do?page=${startPage-1}&action=list" />'>◀</a>
						</c:if>
						<c:forEach begin="${startPage}" end="${endPage}" step="1" varStatus="status">
							[<a href='<c:url value="/Board.do?page=${status.count}&action=list" />'>${status.count}</a>]
						</c:forEach>
						<c:if test="${nowPageBlock lt totalPageBlock}">
							<a href='<c:url value="/Board.do?page=${endPage+1}&action=list"/>'>▶</a>
						</c:if>
					</h6>
				</div>
				<!--blog posts container-->
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
<jsp:include page="/incl/footer.jsp" />
</body>
</html>