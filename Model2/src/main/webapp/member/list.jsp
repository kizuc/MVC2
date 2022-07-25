<%@page import="member.MemberDTO"%>
<%@page import="java.util.List"%>
<%@page import="member.MemberDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/list.jsp</title>
</head>
<body>
<%
// MemberDAO 객체 생성
MemberDAO memberDAO=new MemberDAO();
// 리턴할형 List getMemberList()
// getMemberList() 메서드 호출
List memberList=memberDAO.getMemberList();
// 배열은 while문 아닌 for문
%>
<table border="1">
<tr><td>아이디</td><td>비밀번호</td><td>이름</td><td>가입날짜</td></tr>
<%
for(int i=0;i<memberList.size();i++){
	// 값 가져올때 get, Object형으로 바뀌니 강제형변환
	MemberDTO memberDTO=(MemberDTO)memberList.get(i);
	%>
<tr><td><%=memberDTO.getId() %></td><td><%=memberDTO.getPass() %></td>
    <td><%=memberDTO.getName() %></td><td><%=memberDTO.getDate() %></td></tr>	
	<%
}
%>
</table>
</body>
</html>