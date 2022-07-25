<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@page import="java.lang.reflect.Member"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/insertPro.jsp</title>
</head>
<body>
<%
// form post request 처리
request.setCharacterEncoding("UTF-8");
// request id pass name 파라미터값 저장 => 변수저장
String id=request.getParameter("id");
String pass=request.getParameter("pass");
String name=request.getParameter("name");

// 자바파일이 데이터를 담아서 전달하게끔 ~
// 패키지 member 파일이름 MemberDTO
// 멤버변수 정의, 멤버변수 접근 메서드 정의
// 객체생성 => 기억장소 할당
MemberDTO memberDTO=new MemberDTO();
// 멤버변수에 값을 저장하는 메서드 호출
memberDTO.setId(id);
memberDTO.setPass(pass);
memberDTO.setPass(name);


// 자바파일 메서드정의(DB) 메서드 호출
// 패키지 member 파일이름 MemberDAO
// insertMember() 메서드 정의

// 객체 생성 => 클래스 기억장소 할당
MemberDAO memberDAO=new MemberDAO();
// 메서드 호출
// memberDAO.insertMember(id,pass,name);
memberDAO.insertMember(memberDTO);

%>
 <script type="text/javascript">
	alert("회원가입 성공");
	location.href="loginForm.jsp";
</script> 
</body>
</html>