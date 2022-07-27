package com.itwillbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.domain.MemberDTO;

public class MemberDAO {

	// DB 연결 메서드
	public Connection getConnection() throws Exception{

		Context init=new InitialContext();
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/MysqlDB"); // 자원위치/자원이름
		Connection con=ds.getConnection();
		return con;
	}

	// 유저 체크 메서드
	public MemberDTO userCheck(String id, String pass) {
		MemberDTO memberDTO=null;
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// DB 연결
			con=getConnection();

			String sql="select * from members where id=? and pass=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);

			rs=pstmt.executeQuery();

			if(rs.next()){
				// 데이터 있으면 아이디 비밀번호 일치
				// MemberDTO memberDTO 객체생성
				memberDTO=new MemberDTO();
				// set 메서드 호출 id, pass, name, date 디비열 저장
				memberDTO.setId(rs.getString("id"));
				memberDTO.setPass(rs.getString("pass"));
				memberDTO.setName(rs.getString("name"));
			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)try { pstmt.close(); }catch(SQLException ex){}
			if(con!=null)try { con.close(); }catch(SQLException ex){}
			if(rs!=null)try { rs.close(); }catch(SQLException ex){}
		}
		return memberDTO;
	}
	
	// 회원가입 메서드
	public void insertMember(MemberDTO memberDTO) {
		Connection con = null;
		PreparedStatement pstmt=null;
		try {
			con = getConnection();
			
			String sql="insert into members(id,pass,name,date,email,addnum,address,address2,phone,mobile) values(?,?,?,now(),?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPass());
			pstmt.setString(3, memberDTO.getName());
			pstmt.setString(4, memberDTO.getEmail());
			pstmt.setString(5, memberDTO.getAddnum());
			pstmt.setString(6, memberDTO.getAddress());
			pstmt.setString(7, memberDTO.getAddress2());
			pstmt.setString(8, memberDTO.getPhone());
			pstmt.setString(9, memberDTO.getMobile());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 기억장소 할당해제
			// Connection con, PreparedStatement pstmt, Resultset rs 해제
			if(pstmt!=null)try { pstmt.close(); }catch(SQLException ex){}
			if(con!=null)try { con.close(); }catch(SQLException ex){}
			
		}
	}
	
	public MemberDTO getMember(String id) {
		MemberDTO memberDTO=null;
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = getConnection();
			
			// 3단계 연결정보 이용해서 sql구문 만들기 => PreparedStatement 내장객체
			// String sql="select * from 테이블이름 where 조건열=값";
			String sql="select * from members where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);

			// 4단계 sql구문 실행 => 실행 결과 저장(select) 셀렉트쿼리는 executeQuery();
			// => sql구문을 실행한 결과 저장하는 내장객체 ResultSet
			rs=pstmt.executeQuery();
			// 5단계 결과를 가지고 출력하거나 배열변수 저장(select)
			// if rs.next() 결과에서 다음행 이동하고 데이터 있으면 true=>열접근
			if(rs.next()){
				// 아이디 일치하면기억장소 new에서 만들고 담아서 set~ 저장시켜지게끔
				memberDTO=new MemberDTO();
				memberDTO.setId(rs.getString("id"));
		 		memberDTO.setPass(rs.getString("pass"));
		 		memberDTO.setName(rs.getString("name"));
		 		memberDTO.setDate(rs.getTimestamp("date"));

		 		memberDTO.setEmail(rs.getString("email"));
		 		memberDTO.setAddnum(rs.getString("addnum"));
		 		memberDTO.setAddress(rs.getString("address"));
		 		memberDTO.setAddress2(rs.getString("address2"));
		 		memberDTO.setPhone(rs.getString("phone"));
		 		memberDTO.setMobile(rs.getString("mobile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)try { pstmt.close(); }catch(SQLException ex){}
			if(con!=null)try { con.close(); }catch(SQLException ex){}
			if(rs!=null)try { rs.close(); }catch(SQLException ex){}
		}
		return memberDTO;
	}
	
	// 회원정보 수정
	public void updateMember(MemberDTO updateDTO) {
		Connection con = null;
		PreparedStatement pstmt=null;
		try {
			//1,2 디비연결
			con = getConnection();
			
			String sql="update members set name=?, email=?, addnum=?, address=?, address2=?, phone=?, mobile=? where id=?";
		 	pstmt=con.prepareStatement(sql);
			pstmt=con.prepareStatement(sql);
		 	pstmt.setString(1, updateDTO.getName());
		
		 	pstmt.setString(2, updateDTO.getEmail());
		 	pstmt.setString(3, updateDTO.getAddnum());
		 	pstmt.setString(4, updateDTO.getAddress());
		 	pstmt.setString(5, updateDTO.getAddress2());
		 	pstmt.setString(6, updateDTO.getPhone());
		 	pstmt.setString(7, updateDTO.getMobile());
		 	
		 	pstmt.setString(8, updateDTO.getId());
		 	//4단계 sql구문을 실행 (insert, update, delete)
		 	pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)try { pstmt.close(); }catch(SQLException ex){}
			if(con!=null)try { con.close(); }catch(SQLException ex){}
		}
		
	}
}
