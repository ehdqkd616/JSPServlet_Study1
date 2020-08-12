<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/default.css" media="screen">
<title>마이 페이지</title>
</head>
<body>
<table class="layout">
<tr height="50"><td>
<jsp:include page="/incl/header.jsp" />
</td></tr>
<!-- 본문 표시 코드 -->
<tr height="500" valign="top"><td>
<h1>회원 정보</h1>
<h3 align=center>회원 정보</h3>
<table>
<tr>
<!-- 회원정보는 굳이 칸에 보여줄 필요가 없기에 label같은 th태그를 이용
	css에서 색깔도 설정해 놓음. css파일 th부분 확인. -->
	<th>아이디</th>
	<td>${member.userId}</td>
</tr>
<tr>
	<th>비밀번호</th>
	<td>${member.password}</td>
</tr>
<tr>
	<th>이름</th>
	<td>${member.name}</td>
</tr> 
<tr>
	<th>이메일</th>
	<td>${member.email}</td>
</tr>
<tr>
	<th>주소</th>
	<td>${member.address}</td>
</tr>
<tr>
	<th>글 갯수</th>
	<td>${count}</td>
</tr>
<tr>
<!-- td의 colspan은 td 한 칸을 옆에 숫자가 써진 만큼의 칸 갯수와 같은 크기로 만들겠다는 뜻. 
	줄별로 위에는 몇 칸, 밑에는 한 칸만 써야 하는 경우 활용. 밑에 몇 칸을 합친 한 칸이 아닌 그냥 
	한 칸만 만들게 된다면 페이지를 볼 때 미관상 좋지 않음. -->
<!-- 내가 쓴 목록 조회는 BoardServlet을 통해 -->
	<td colspan=2><a href='<c:url value="/Board.do?action=memberList"/>'>목록 확인</a></td>
	</tr>
<tr>
	<td colspan=2>
	<h3 align="center">
<!-- 회원 수정 및 삭제는 MemberServlet을 통해.
	a태그를 쓰는 경우 자바와 아무 관련 없기에 프로젝트의 주소 모두 입력
	c태그를 쓰는 경우 프로젝트의 JSTL jar파일들을 읽어오기에 해당 프로젝트 내에서 자동 탐색
	= 프로젝트의 주소 안 적어도 됨. 때문에 위의 "c:url value=" 부분은 /로 시작하는 것을 볼 수 있음. -->
	<a href="/MVC/Member.do?action=update"><input type="button" value="회원정보수정"></a>
	&nbsp;&nbsp;
	<a href="/MVC/Member.do?action=delete"><input type="button" value="회원정보삭제"></a>
	</h3>
	</td>
</table>
</td></tr>
<!-- 본문 표시 코드 -->
<tr height="50"><td>
<jsp:include page="/incl/footer.jsp" />
</td></tr>
</table>
</body>
</html>