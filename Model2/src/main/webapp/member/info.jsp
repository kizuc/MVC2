<%@page import="com.itwillbs.domain.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/info.jsp</title>
</head>
<body>
<%
// // object=>String으로 형변환
// // 로그인하면 세션값이 생성 => 페이지 상관없이 값이 유지 => 세션값 가져오기
// String id=(String)session.getAttribute("id");

// // MemberDAO 객체생성
// MemberDAO memberDAO = new MemberDAO();
// // 리턴형 MemberDTO getMember(String id) 메서드 정의
// // MemberDTO memberDTO = getMember(id) 메서드 호출
// MemberDTO memberDTO=memberDAO.getMember(id);

MemberDTO memberDTO=(MemberDTO)request.getAttribute("memberDTO");
%>
<table border="1">
<tr><td>아이디</td><td>비밀번호</td><td>이름</td><td>가입날짜</td></tr>
	<tr><td><%=memberDTO.getId()%></td>
		<td><%=memberDTO.getPass()%></td>
		<td><%=memberDTO.getName()%></td>
		<td><%=memberDTO.getDate()%></td>
	</tr>
</table>
<a href="main.me">메인으로 이동</a>
</body>
</html>