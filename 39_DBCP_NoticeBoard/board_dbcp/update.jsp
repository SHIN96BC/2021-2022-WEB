<%@ page contentType = "text/html;charset=utf-8" import="java.sql.*, javax.sql.DataSource"
import="java.io.*" import="sbc.dbcp.DbcpBean"%>
<jsp:useBean id="dbcp" class="sbc.dbcp.DbcpBean" scope="application"/>

<%! 
	public void doGet(HttpServletRequest request, HttpServletResponse response, DbcpBean dbcp){
		String seqStr = "";
		String email = "";
		String subject = "";
		String content = "";
		int seq = -1;
		try{
			seqStr = request.getParameter("seq");
			email = request.getParameter("email");
			subject = request.getParameter("subject");
			content = request.getParameter("content");
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
		}catch(IOException ie){
		}
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update BOARD set EMAIL=?, SUBJECT=?, CONTENT=? where SEQ=?";
		try{
			ds = dbcp.getDs();
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, subject);
			pstmt.setString(3, content);
			pstmt.setInt(4, seq);
			pstmt.executeUpdate();
			response.sendRedirect("list.jsp");
		}catch(SQLException se){
		}catch(IOException ie){
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}
		
	}

%>

		<meta charset='utf-8'>
		<style>
		table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
		}
		th, td {
		padding: 5px;
		}
		a { text-decoration:none }
		</style>
		<center>
		<hr width='600' size='2' noshade>
		<h2>Simple Board with Servlet</h2>
		&nbsp;&nbsp;&nbsp;
		<a href='list.jsp'>글목록</a>
		<hr width='600' size='2' noshade>
		<form name='f' method='post' action='update.jsp'>


<%
		String seqStr = request.getParameter("seq");
		String writer = request.getParameter("writer");
		if(writer != null) doGet(request, response, dbcp);
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
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select * from BOARD where SEQ=?";
		ResultSet rs = null;
		try{
			ds = dbcp.getDs();
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String sbj = rs.getString(4);
				String content = rs.getString(5);
%>
				<input type='hidden' name='seq' value='<%=seq%>'>
				<input type='hidden' name='writer' value="'<%=name%>'">
				<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>
				<td width='30%' align='center'>글쓴이</td>
				<td align='center'><input type='text' name='aa' size='60' value='<%=name%>' disabled></td>
				</tr>
				<tr>
				<td width='30%' align='center'>이메일</td>
				<td align='center'><input type='text' name='email' size='60' value='<%=email%>'></td>
				</tr>
				<tr>
				<td width='30%' align='center'>글제목</td>
				<td align='center'><input type='text' name='subject' size='60' value='<%=sbj%>'></td>
				</tr>
				<tr>
				<td width='30%' align='center'>글내용</td>
				<td align='center'><textarea name='content' rows='5' cols='53'><%=content%></textarea></td>
				</tr>
<%
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}

%>

	<tr>
	<td colspan='2' align='center'>
	<input type='submit' value='수정'>
	</td>
	</tr>
	</table>
	</form>
	</table>