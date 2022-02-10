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
	ArrayList<Board> list(long max, long min) {
		ArrayList<Board> list = new ArrayList<Board>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LIST);
			pstmt.setLong(1, min);
			pstmt.setLong(2, max);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				// 1번은 rnum
				long postNumber = rs.getLong(2);
				String writerNickName = rs.getString(3);
				String writerId = rs.getString(4);
				String postSubject = rs.getString(5);
				String postContent = rs.getString(6);
				long views = rs.getLong(7);
				Date pdate = rs.getDate(8);
				long refer = rs.getLong(9);
				long lev = rs.getLong(10);
				long sunbun = rs.getLong(11);
				list.add(new Board(postNumber, writerNickName, writerId, postSubject, postContent, views, pdate, refer, lev, sunbun));
			}
			System.out.println("list : " + list);
			return list;
		}catch(SQLException se) {
			System.out.println("list se: " + se);
			return null;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	long boardCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BOARD_SIZE);
			rs = pstmt.executeQuery();
			long size = 0;
			while(rs.next()) {
				size++;
			}
			return size;
		}catch(SQLException se) {
			return -1;
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
	boolean insertRe(Board board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_RE);
			pstmt.setString(1, board.getWriternickname());
			pstmt.setString(2, board.getWriterid());
			pstmt.setString(3, board.getPostsubject());
			pstmt.setString(4, board.getPostcontent());
			pstmt.setLong(5, board.getRefer());
			pstmt.setLong(6, board.getLev());
			pstmt.setLong(7, board.getSunbun());
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
	long findByRefer(long postNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_REFER);
			pstmt.setLong(1, postNumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				long refer = rs.getLong(1);
				return refer;
			}else {
				return -1;
			}
		}catch(SQLException se) {
			return -1;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	long findByLev(long postNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_LEV);
			pstmt.setLong(1, postNumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				long Lev = rs.getLong(1);
				return Lev;
			}else {
				return -1;
			}
		}catch(SQLException se) {
			return -1;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	long findBySunbun(long refer) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_SUNBUN);
			pstmt.setLong(1, refer);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				long sunbun = rs.getLong(1);
				return sunbun;
			}else {
				return -1;
			}
		}catch(SQLException se) {
			return -1;
		}finally {
			closeAll(con, pstmt, rs);
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
				long views = rs.getLong(6);
				Date pdate = rs.getDate(7);
				long refer = rs.getLong(8);
				long level = rs.getLong(9);
				long sunbun = rs.getLong(10);
				Board board = new Board(postNumber, writerNickName, writerId, postSubject, postContent, views, pdate, refer, level, sunbun);
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
		}finally {
			closeAll(con, pstmt, null);
		}
	}
	long viewsCheck(long postNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(VIEWS_CHECK);
			pstmt.setLong(1, postNumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				long views = rs.getLong(1);
				return views;
			}else {
				return -1;
			}
		}catch(SQLException se) {
			return -1;
		}finally {
			closeAll(con, pstmt, rs);
		}
	}
	void viewsUpdate(long postNumber, long views) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(VIEWS_UPDATE);
			pstmt.setLong(1, views);
			pstmt.setLong(2, postNumber);
			pstmt.executeUpdate();
		}catch(SQLException se) {
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
