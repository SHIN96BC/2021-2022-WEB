package jstl.member.model;

import static jstl.member.model.MemberSQL.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jstl.member.domain.Member;

class MemberDAO {
	private DataSource ds;
	public MemberDAO() {
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");  // DataSource 이게 우리가 알고있는 pool이다.
			//Connection conn = ds.getConnection();
		}catch(NamingException net){
			System.out.println("#tomcat이 만든 dbcp객체(jdbc/myoracle)이름을 못찾음");
		}
	}

	boolean insert(Member member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());
			pstmt.setString(4, member.getName());
			pstmt.setString(5, member.getPhonenumber());
			pstmt.setString(6, member.getAddress());
			int i = pstmt.executeUpdate();
			if(i>0) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			return false;
		}finally {
			closeAll(con, pstmt, null);
		}
	}

	boolean findById(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYID);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			return false;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	boolean findByNickName(String nickname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYNICKNAME);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			return false;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	boolean findByPhoneNumber(String phonenumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYPHONENUMBER);
			pstmt.setString(1, phonenumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			return false;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	boolean passwordCheck(String id, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(PASSWORDCHECK);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			return false;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	Member userInfo(String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(USERINFO);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				long clientnumber = rs.getLong(1);
				String password = rs.getString(3);
				String nickname = rs.getString(4);
				String name = rs.getString(5);
				String phonenumber = rs.getString(6);
				String address = rs.getString(7);
				int authority = rs.getInt(8);
				Date cdate = rs.getDate(9);
				Member member = new Member(clientnumber, id, password, nickname, name, phonenumber, address, authority, cdate);
				return member;
			}else {
				return null;
			}
		}catch(SQLException se) {
			return null;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	boolean infoUpdate(Member member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INFOUPDATE);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getNickname());
			pstmt.setString(3, member.getPhonenumber());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getId());
			int i = pstmt.executeUpdate();
			if(i>0) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			return false;
		}finally {
			closeAll(con, pstmt, null);
		}
	}
	private void closeAll(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null ) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se) {
		}
	}
}
