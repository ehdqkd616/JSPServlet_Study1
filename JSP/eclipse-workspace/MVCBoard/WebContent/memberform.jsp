<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- css 호출 코드 -->
<link rel="stylesheet" href="./css/default.css" media="screen">
<title>회원 정보 입력</title>
</head>
<body>
<!-- 테이블 생성 - 메인 레이아웃 -->
<table class="layout">
<!-- 첫 줄의 크기는 50픽셀 -->
<tr height="50"><td>
<!-- 첫 줄에 한 칸을 만들고 헤더파일 집어넣음 한 칸에 홈으로, 게시글쓰기, 게시글목록 등등이 작성되어 있는 것 -->
<jsp:include page="/incl/header.jsp" />
</td></tr>
<!-- 본문을 표시할 줄 만듦. 본문은 각 기능별로 표시할게 많기에 500픽셀 사용 -->
<tr height="500" valign="top"><td>
<!-- 입력일땐 회원가입, 수정일땐 회원정보 수정 출력 -->
<h1>${message}</h1>
<h3 align=center>회원 정보 입력</h3>
<!-- 해당 데이터는 입력이든 수정이든 무조건 MemberServlet에 post방식으로 넘어감 -->
<form action="/MVC/Member.do" method="post">
<!-- 테이블을 넣을 큰 네모 공간을 만드는 태그 = fieldset -->
<fieldset>
<!-- fieldset의 메인 글자 태그 = legend -->
<legend>회원 정보</legend>
<!-- 테이블 생성 -->
<table>
<!-- 각 줄 생성후 각 칸에 
	아이디 : 값
	비밀번호 : 값
	이름 : 값 ... 형태의 코드 작성
-->
<tr>
	<td >아이디</td>
	<!-- 수정시에는 값이 들어오기에 출력하고, 입력시에는 출력하지 않아야 함
		있으면 출력 없으면 출력안함 - EL표현식 사용.
		수정인 경우 id는 수정할 수 없게 해야함 - 3항 표현식을 이용해 id가 있다면 readonly(읽기전용) 없다면 입력 가능하게끔 -->
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
	<!-- 해당 요청이 수정인지 가입인지 서블릿의 post에선 알 방법이 없음 - 수정이나 가입이나 넘어가는 데이터는 아이디, 이름, 이메일, 비밀번호, 주소 전부 동일
		때문에 action 파라미터로 구분 - 서블릿의 get단계에서부터 가입요청인지 수정요청인지 구분하여 넘겨줌.
		우리가 볼 필요는 없기 때문에 hidden 파라미터로 넘겨줌-->
	<input type="hidden" name="action" value="${action}">
	<input type="submit" value="저 장">
	<input type="reset" value="취 소">
</fieldset>
</form>
</td></tr>
<tr height="50"><td>
<!-- footer 호출 코드 -->
<jsp:include page="/incl/footer.jsp" />
</td></tr>
</table>
</body>
</html>