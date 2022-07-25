<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/main.jsp</title>
</head>
<body>
<%
String id=(String)session.getAttribute("id");
// 세션값이 없으면(세션값이 null이면) loginForm.jsp 이동
if(id==null){
// 	response.sendRedirect("loginForm.jsp");
}
%>
<%=id %>님 로그인 하셨습니다.<br>
<a href="logout.me">로그아웃</a>
<a href="info.me">회원정보조회</a>
<a href="updateForm.me">회원정보수정</a>
<a href="deleteForm.me">회원정보삭제</a>
<%
if(id!=null){
	if(id.equals("admin")){
		%>
		<a href="list.me">회원목록</a>
		<%
	}
}
%>

</body>
</html>