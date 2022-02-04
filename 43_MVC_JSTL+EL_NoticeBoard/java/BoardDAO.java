package board.mvc.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mvc.domain.Board;
import static board.mvc.model.BoardSQL.*;

class BoardDAO {
	private DataSource ds;
	public BoardDAO() {
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");  // DataSource 이게 우리가 알고있는 pool이다.
			//Connection conn = ds.getConnection();
		}catch(NamingException net){
			System.out.println("#tomcat이 만든 dbcp객체(jdbc/myoracle)이름을 못찾음");
		}
	}
	
	ArrayList<Board> list() {
		ArrayList<Board> list = new ArrayList<Board>();
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try{
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(LIST);
			while(rs.next()){
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String email = rs.getString(3);
				String subject = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6); 
				list.add(new Board(seq, writer, email, subject, content, rdate));
			}
			return list;
		}catch(SQLException se){
			return null;
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}
	}
	ArrayList<Board> content(int seq) {
		ArrayList<Board> contentList = new ArrayList<Board>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(CONTENT);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String subject = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6); 
				contentList.add(new Board(seq, name, email, subject, content, rdate));
			}
			return contentList;
		}catch(SQLException se){
			return null;
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}
	}
	boolean insert(Board dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			int i = pstmt.executeUpdate();
			if(i>0) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se){
			return false;
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}
	}
	void delete(int seq) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, seq);
			pstmt.executeUpdate();
		}catch(SQLException se){
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se){}
		}
	}
	
	boolean update(Board dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getSeq());
			int i = pstmt.executeUpdate();
			if(i>0) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se){
			return false;
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}
		
	}
}
