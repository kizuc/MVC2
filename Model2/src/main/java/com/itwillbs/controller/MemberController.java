package com.itwillbs.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.service.MemberService;

// web.xml에서 지정한 서블릿(처리담당자)
public class MemberController extends HttpServlet{
	// HttpServelet 상속받아서 서블릿됨
	// 서블릿 동작 메서드를 자동으로 호출한다 - doGet() doPost() service() 
	// doGet() doPost()를 재정의 하고 싶다! => 메서드 오버라이딩
	
	// 가상주소 http://localhost:8080/Model2/insertForm.me
	
	// doProcess - doGet()이나 doPost()에서 호출되게 -> 반복작업ㄴㄴ
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet(), doPost() 호출");
		System.out.println("MemberController doPro()");
		System.out.println("주소 매핑 => 가상주소가 들어오면 실제주소 연결");
	
		
		// 가상주소를 뽑아오는 작업을 해야한다!
		// 가상주소 http://localhost:8080/Model2/insertForm.me의 insertForm.me 만 뽑아오는 식
		
		String requestURL = request.getRequestURL().toString();
		// http://localhost:8080/Model2/insertForm.me
		System.out.println(requestURL);
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		// /Model2/insertForm.me
		// 프로젝트 경로(contextPath) /Model2
		String contextPath=request.getContextPath();
		System.out.println(contextPath);
		// /Model2
		System.out.println(contextPath.length());
		// 문자열 뽑기 substring(시작위치) => 시작위치에서 끝까지 뽑아오기
		String substringPath=requestURI.substring(contextPath.length());
		System.out.println("전체주소에서 가상주소 뽑아옴 : " + substringPath);
		// 뽑아온 주소 /insertForm.me
		
		// 뽑아온 주소와 "/insertForm.me" 비교
		if(substringPath.equals("/insertForm.me")) { // 일치할 경우, 실제주소로 이동
			System.out.println("/insertForm.me");
			// 주소 매핑
			// 가상주소 /insertForm.me => 실제주소 member/insertForm.jsp
				// 이동방법 1. 하이퍼링크(현재 자바파일이므로 작동안됨), 2. location.href(자바파일이므로 작동안됨), 3. responseRedirect()
				// => 페이지에서 페이지로 무조건 이동, 주소 변경 이동 - 값을 가지고 이동하지 않는다
//			response.sendRedirect("member/insertForm.jsp");	// 이 방식으로 이동하면 노출된다.
			
				// 이동 forward() : 가상주소를 유지하고 실제 페이지에는 화면만 보이게 이동한다(주소가 바뀌지 않는다) - request정보도 가지고 이동
			// 주소변경없이 이동하는 프로그램 RequestDispather
			RequestDispatcher dispatcher=request.getRequestDispatcher("member/insertForm.jsp");
			dispatcher.forward(request, response);
			
		}else if(substringPath.equals("/insertPro.me")) { // 일치할 경우, 실제주소로 이동
			System.out.println("/insertPro.me");
			// post request 한글처리
			request.setCharacterEncoding("utf-8");
			// 지금은 수작업으로 하지만 Spring에서는 자동으로 된다(파라미터 값 가져오기)
			// request = > 파라미터값 가져오기 => MemberDTO 저장
			// 처리 작업을 호출할 거야 => 처리 작업을 따로 만들 거야
			String id=request.getParameter("id");
			String pass=request.getParameter("pass");
			String name=request.getParameter("name");
			// 날짜는 따로 적어줘야해서 현재 없음
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId(id);
			memberDTO.setPass(pass);
			memberDTO.setName(name);
			
			// 서블릿(주소매핑) => 처리작업(MemberService) => 디비작업(MemberDAO)
			// 처리작업은 자바파일의 메서드를 호출해주면 되는데 ~
			// 패키지 com.itwillbs.service	파일이름 MemberService
			// 패키지 com.itwillbs.dao		파일이름 MemberDAO
			
			MemberService memberService=new MemberService();
			memberService.insertPro(memberDTO);
			
			System.out.println("회원가입 처리");
			// loginForm.me 주소가 변강되면서 이동 (insertPro.me에서 loginForm.me로 가려면 주소가 바뀌어야 한다)
			response.sendRedirect("loginForm.me");
		}else if(substringPath.equals("/loginForm.me")) {
			// 가상주소 /loginForm.me => 실제주소 member/loginForm.jsp
			RequestDispatcher dispatcher=request.getRequestDispatcher("member/loginForm.jsp");
			dispatcher.forward(request, response);
			
		}else if(substringPath.equals("/loginPro.me")) {
			System.out.println("로그인 처리");
			
			String id=request.getParameter("id");
			String pass=request.getParameter("pass");
			
			MemberService memberService=new MemberService();
			MemberDTO memberDTO=memberService.userCheck(id,pass);
			if(memberDTO!=null) {	// null이 아니면 
				// 아이디 비밀번호 일치
				// 세션 내장객체 생성
				HttpSession session=request.getSession();
				// 세션값 생성
				session.setAttribute("id", id);
				// main.me 이동
			}else {	
				// 아이디 비밀번호 틀림
				// 뒤로 이동
				// response 응답정보를 받아 setContentType 내용을 html 타입으로~
				// 사용자에게 응답하러 갈 때 html로 응답하겠다!
				response.setContentType("text/html; charset=UTF-8");
				// response에 글을 쓸 건데 printWriter에 둬서 응답한 걸 출력하겠다!
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('입력하신 정보 틀림');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				
			}
			
			// 가상주소 main.me의 메인으로 이동
			response.sendRedirect("main.me");
		}else if(substringPath.equals("/main.me")) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("member/main.jsp");
			dispatcher.forward(request, response);
			
		}else if(substringPath.equals("/info.me")) {
			System.out.println("회원정보조회");
			// 세션 내장객체 생성
			HttpSession session=request.getSession();
			// 세션값 생성
			String id=(String)session.getAttribute("id");
			
			MemberService memberService=new MemberService();
			MemberDTO memberDTO=memberService.getMember(id);
			
			// memberDTO를 request에 담아서 저장
//			request.setAttribute("이름", 값);
			request.setAttribute("memberDTO", memberDTO);
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("member/info.jsp");
			dispatcher.forward(request, response);
		}else if(substringPath.equals("/updateForm.me")) {
			
			HttpSession session=request.getSession();
			// 세션값 생성
			String id=(String)session.getAttribute("id");
			
			MemberService memberService=new MemberService();
			MemberDTO memberDTO=memberService.getMember(id);
			
			// memberDTO를 request에 담아서 저장
//			request.setAttribute("이름", 값);
			request.setAttribute("memberDTO", memberDTO);
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("member/updateForm.jsp");
			dispatcher.forward(request, response);
		}else if(substringPath.equals("/updatePro.me")) {
			System.out.println("회원정보 수정");
			
			// post request 한글처리
			request.setCharacterEncoding("utf-8");

			String id=request.getParameter("id");
			String pass=request.getParameter("pass");
			String name=request.getParameter("name");
			
			MemberService memberService=new MemberService();
			MemberDTO memberDTO=memberService.userCheck(id,pass);
			if(memberDTO!=null) {	// null이 아니면 
				// 아이디 비밀번호 일치
				// 수정할 정보를 MemberDTO updateDTO 객체 생성
				// set메서드 호출해 수정할 정보 저장
				MemberDTO updateDTO=new MemberDTO();
				updateDTO.setId(id);
				updateDTO.setPass(pass);
				updateDTO.setName(name);
				
				// 수정 작업
				memberService.updateMember(updateDTO);
				
				// main.me 이동
				response.sendRedirect("main.me");
			}else {	
				// 아이디 비밀번호 틀림
				// 뒤로 이동
				// response 응답정보를 받아 setContentType 내용을 html 타입으로~
				// 사용자에게 응답하러 갈 때 html로 응답하겠다!
				response.setContentType("text/html; charset=UTF-8");
				// response에 글을 쓸 건데 printWriter에 둬서 응답한 걸 출력하겠다!
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('입력하신 정보 틀림');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				
			}
			
		}else if(substringPath.equals("deleteForm.me")) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("member/deleteForm.jsp");
			dispatcher.forward(request, response);
			HttpSession session=request.getSession();
			// 세션값 생성
			String id=(String)session.getAttribute("id");
			
			MemberService memberService=new MemberService();
			MemberDTO memberDTO=memberService.getMember(id);
			
		}else if(substringPath.equals("deletePro.me")) {
			System.out.println("회원정보 삭제");

			String id=request.getParameter("id");
			String pass=request.getParameter("pass");
			
			MemberService memberService=new MemberService();
			MemberDTO memberDTO=memberService.userCheck(id,pass);
			if(memberDTO!=null) {	// null이 아니면 
				// 아이디 비밀번호 일치
				// 삭제 작업
				memberService.deleteMember(id);
				// 세션 내장객체 생성
				HttpSession session=request.getSession();
				// 세션값 초기화
				session.invalidate();
				
				// main.me 이동
				response.sendRedirect("main.me");
			}else {	
				// 아이디 비밀번호 틀림
				// 뒤로 이동
				// response 응답정보를 받아 setContentType 내용을 html 타입으로~
				// 사용자에게 응답하러 갈 때 html로 응답하겠다!
				response.setContentType("text/html; charset=UTF-8");
				// response에 글을 쓸 건데 printWriter에 둬서 응답한 걸 출력하겠다!
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('입력하신 정보 틀림');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				
			}
			
			
		}else if(substringPath.equals("/logout.me")) {
			// 세션 내장객체 생성
			HttpSession session=request.getSession();
			// 세션값 초기화
			session.invalidate();
			response.sendRedirect("main.me");
		}
	
	}
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get 방식 데이터전송");
		System.out.println("MemberController doGet()");
		doPro(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post 방식 데이터전송");
		System.out.println("MemberController doPost()");
		doPro(request, response);
		
	}
	
}
