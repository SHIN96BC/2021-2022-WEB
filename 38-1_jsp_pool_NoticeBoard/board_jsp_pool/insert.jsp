<%@ page contentType = "text/html;charset=utf-8" import="java.sql.*"%>
<jsp:useBean id="pool" class="soo.db.ConnectionPoolBean" scope="application"/>


<%
		String writer = request.getParameter("writer");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		if(writer == null || email == null || subject == null || content == null){
			response.sendRedirect("list.jsp");
			return;
		}
		writer = writer.trim();
		email = email.trim();
		subject = subject.trim();
		content = content.trim();
		if(writer.length() == 0 || email.length() == 0 || subject.length() == 0 || content.length() == 0){
			response.sendRedirect("list.jsp");
			return;
		}
		out.println("<script>");
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into BOARD values(BOARD_SEQ.nextval, ?, ?, ?, ?, SYSDATE)";
		try{
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, email);
			pstmt.setString(3, subject);
			pstmt.setString(4, content);
			pstmt.executeUpdate();
		}catch(SQLException se){
		}finally{
		try{
			if(pstmt != null) pstmt.close();
			if(con != null) pool.returnConnection(con);
		}catch(SQLException se){}
	}
		response.sendRedirect("list.jsp");
%>