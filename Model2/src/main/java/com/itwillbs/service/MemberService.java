package com.itwillbs.service;

import java.sql.Timestamp;

import com.itwillbs.dao.MemberDAO;
import com.itwillbs.domain.MemberDTO;

public class MemberService {

// memberService.insertPro(memberDTO); 메서드 정의
	public void insertPro(MemberDTO memberDTO) {
		System.out.println("MemberService insertPro()");
		
		// 처리작업
		memberDTO.setDate(new Timestamp(System.currentTimeMillis()));
		// 디비작업 MemberDAO 객체생성
		// 메서드 호출()
		MemberDAO memberDAO=new MemberDAO();
		memberDAO.insertMember(memberDTO);
	}
	
// memberDTO memberDTO=memberSerice.userCheck(id,pass);
	public MemberDTO userCheck(String id, String pass) {
		System.out.println("MemberService userCheck()");
		
		MemberDAO memberDAO=new MemberDAO();
		MemberDTO memberDTO=memberDAO.userCheck(id, pass);
		return memberDTO;
	}
	
// memerDTO memberDTO=memberSerice.getMember(id);
	public MemberDTO getMember(String id) {
		System.out.println("MemberService getMember()");
		
		MemberDAO memberDAO=new MemberDAO();
		MemberDTO memberDTO=memberDAO.getMember(id);
		return memberDTO;
	}
	
// memerDTO memberDTO=memberSerice.updateMember(updateDTO);	
	public void updateMember(MemberDTO updateDTO) {
		System.out.println("MemberService updateMember()");
		//디비작업  MemberDAO 객체생성
		//    메서드호출()
		MemberDAO memberDAO=new MemberDAO();
		memberDAO.updateMember(updateDTO);
	}
	
// memerDTO memberDTO=memberSerice.deleteMember(id);
	public void deleteMember(String id) {
		System.out.println("MemberService deleteMember()");
		
		MemberDAO memberDAO=new MemberDAO();
		memberDAO.deleteMember(id);
	}
	
	
	
	
}
