<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/default.css" media="screen">
<title>문의 페이지</title>
</head>
<body>
<table class="layout">
<tr height="50"><td>
<jsp:include page="/incl/header.jsp" />
</td></tr>
<tr height="500" valign="top"><td>
<!-- BoardServlet에서 메일전송 실행. -->
<form action='<c:url value="/Board.do"/>' method="post">
<fieldset>
<legend>문의 메일</legend>
<table>
<tr>
<td>보내시는 분</td><td><input type=text name=from value="답장을 받으실 메일 주소를 적어주세요."></td>
</tr>
<tr>
<!-- readonly 속성은 해당 칸의 값을 보기만 하고 수정 불가능하게 만듦. -->
<td>수신</td><td><input type=text value="ehdqkd61616@naver.com" readonly></td>
</tr>
<tr>
<td>제목</td><td><input type=text name=subject></td>
</tr>
<tr>
<td>이름</td><td><input type=text name=name></td>
</tr>
<tr>
<td>내용</td><td><textarea name=content cols=15 rows=5>
문의 내용을 입력해 주세요. 최대한 빨리 답장해 드리겠습니다.
</textarea></td>
</tr>
<tr>
<td colspan=2><input type=submit value=메일전송></td>
</tr>
</table>
</fieldset>
<!-- action이 contact_do로 넘어가야 서블릿의 메일 전송 부분 실행. -->
<input type=hidden name=action value="contact_do">
</form>
<tr height="50"><td>
<jsp:include page="/incl/footer.jsp" />
</td></tr>
</table>
</body>
</html>