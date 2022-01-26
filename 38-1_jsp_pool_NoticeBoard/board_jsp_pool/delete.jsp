<%@ page contentType = "text/html;charset=utf-8" import="java.sql.*"%>
<jsp:useBean id="pool" class="soo.db.ConnectionPoolBean" scope="application"/>

<%
		String seqStr = request.getParameter("seq");
		int seq = -1;
		if(seqStr == null){
			response.sendRedirect("list.jsp");
			return;
		}
		seqStr = seqStr.trim();
		if(seqStr.length() == 0){
			response.sendRedirect("list.jsp");
			return;
		}else{
			try{
				seq = Integer.parseInt(seqStr);
			}catch(NumberFormatException nfe){
				response.sendRedirect("list.jsp");
				return;
			}
		}
		
		out.println("<script>");
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from BOARD where SEQ=?";
		try{
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
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