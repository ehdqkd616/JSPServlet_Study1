<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>검색할 이름을 입력하세요.</h3>
<form action="/Save.do">
이름 : <input type=text name=name><br>
<input type=hidden name=action value=search>
<input type=submit value=저장><br>
</form>
</body>
</html>