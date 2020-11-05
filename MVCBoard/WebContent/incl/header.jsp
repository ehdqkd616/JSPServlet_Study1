<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
<table>
<%--  자바의 switch문과 동일. core태그에는 if만있고 els문이 없음. --%>
<c:choose>
<%-- EL표현식은 알아서 page - request - session - context 순으로 해당 이름을 가진 객체를 찾음
	session에 userid가 있는 경우 = 로그인 된 상태 --%>
	<c:when test="${!empty userid}">
	<tr>
	<td colspan=5>${name}님 환영합니다.</td>
	</tr>
	<tr>
	<%-- core 태그를 사용하면 자바 라이브러리 기반 사용 = 컨텍스트(프로젝트)의 jar파일 사용
		html언어가 아니기에 core태그를 이용한 코드는 자동으로 프로젝트 내의 범위에 묶이게 됨
		core태그로 주소를 입력하는 경우 프로젝트 주소를 입력하지 않아도 알아서 프로젝트 내에서만 검색
	 --%>
	<td><a href='<c:url value="/"/>'>홈으로</a></td>
	<td><a href='<c:url value="/Board.do?action=write"/>'>게시글 쓰기</a></td>
	<td><a href='<c:url value="/Board.do?action=list"/>'>게시글 목록</a></td>
	<td><a href='<c:url value="/Login.do?action=logout"/>'>로그아웃</a></td>
	<td><a href='<c:url value="/Board.do?action=member"/>'>마이페이지</a></td>
	</tr>
	</c:when>
	<%-- session에 userid가 없는 경우 = 로그인 안 된 상태 --%>
	<c:when test="${empty userid}">
	<tr>
	<td><a href='<c:url value="/Member.do?action=insert"/>'>회원가입</a></td>
	<td><a href='<c:url value="/login.jsp"/>'>로그인</a></td>
	</tr>
	</c:when>
</c:choose>
</table>
</header>