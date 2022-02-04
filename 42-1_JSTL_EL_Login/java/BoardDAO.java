package jstl.board.model;

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

import jstl.board.domain.Board;
import static jstl.board.model.BoardSQL.*;

class BoardDAO {
	private DataSource ds;
	public BoardDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");
		}catch(NamingException ne) {
			System.out.println("#tomcat이 만든 dbcp객체(jdbc/myoracle)이름을 못찾음");
		}
	}
	ArrayList<Board> list() {
		ArrayList<Board> list = new ArrayList<Board>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LIST);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				long postNumber = rs.getLong(1);
				String writerNicName = rs.getString(2);
				String writerId = rs.getString(3);
				String postSubject = rs.getString(4);
				String postContent = rs.getString(5);
				int authority = rs.getInt(6);
				Date pdate = rs.getDate(7);
				list.add(new Board(postNumber, writerNicName, writerId, postSubject, postContent, authority, pdate));
			}
			return list;
		}catch(SQLException se) {
			return null;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	boolean insert(Board board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, board.getWriternickname());
			pstmt.setString(2, board.getWriterid());
			pstmt.setString(3, board.getPostsubject());
			pstmt.setString(4, board.getPostcontent());
			pstmt.setInt(5, board.getAuthority());
			int i = pstmt.executeUpdate();
			if(i>0) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException se) {
			System.out.println("se: " + se);
				return false;
		}finally {
			closeAll(con, pstmt, null);
		}
	}
	Board content(long postNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CONTENT);
			pstmt.setLong(1, postNumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String writerNickName = rs.getString(2);
				String writerId = rs.getString(3);
				String postSubject = rs.getString(4);
				String postContent = rs.getString(5);
				Date pdate = rs.getDate(7);
				Board board = new Board(postNumber, writerNickName, writerId, postSubject, postContent, -1, pdate);
				return board;
			}else {
				return null;
			}
		}catch(SQLException se) {
			return null;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	void delete(long postNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setLong(1, postNumber);
			pstmt.executeUpdate();
		}catch(SQLException se) {
		}finally {
			closeAll(con, pstmt, null);
		}
	}
	void update(Board board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, board.getPostsubject());
			pstmt.setString(2, board.getPostcontent());
			pstmt.setLong(3, board.getPostnumber());
			pstmt.executeUpdate();
		}catch(SQLException se) {
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
