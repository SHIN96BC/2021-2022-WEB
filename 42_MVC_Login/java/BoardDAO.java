package bcm.board.model;

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

import bcm.board.domain.Board;
import static bcm.board.model.BoardSQL.*;

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
			pstmt.setString(1, board.getWriterNickName());
			pstmt.setString(2, board.getWriterId());
			pstmt.setString(3, board.getPostSubject());
			pstmt.setString(4, board.getPostContent());
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
		return null;
	}
	boolean delete(long postNumber) {
		return false;
	}
	boolean update(Board board) {
		return false;
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
