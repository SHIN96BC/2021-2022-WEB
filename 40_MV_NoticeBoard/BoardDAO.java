package sbc.mv.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class BoardDAO {
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
	
	public ArrayList<BoardDTO> list() {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		String sql = "select * from BOARD order by seq desc";
		try{
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String email = rs.getString(3);
				String subject = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6); 
				list.add(new BoardDTO(seq, writer, email, subject, content, rdate));
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
	public ArrayList<BoardDTO> content(int seq) {
		ArrayList<BoardDTO> contentList = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from BOARD where SEQ=?";
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String subject = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6); 
				contentList.add(new BoardDTO(seq, name, email, subject, content, rdate));
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
	public boolean insert(BoardDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into BOARD values(BOARD_SEQ.nextval, ?, ?, ?, ?, SYSDATE)";
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
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
	public void delete(int seq) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from BOARD where SEQ=?";
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
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
	public ArrayList<BoardDTO> updateList(int seq) {
		ArrayList<BoardDTO> contentList = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select * from BOARD where SEQ=?";
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String subject = rs.getString(4);
				String content = rs.getString(5);
				Date rdate = rs.getDate(6); 
				contentList.add(new BoardDTO(seq, name, email, subject, content, rdate));
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
	public boolean update(BoardDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update BOARD set EMAIL=?, SUBJECT=?, CONTENT=? where SEQ=?";
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
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
