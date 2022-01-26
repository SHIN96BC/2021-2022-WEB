<%@ page contentType = "text/html;charset=utf-8" import="java.sql.*"
import="java.io.*"%>

<%! 
	Connection con;
	PreparedStatement pstmt1, pstmt2;
	public void jspInit(){   // DB연결
		String sql1 = "update BOARD set EMAIL=?, SUBJECT=?, CONTENT=? where SEQ=?";
		String sql2 = "select * from BOARD where SEQ=?";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String usr = "servlet";
		String pwd = "java";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, usr, pwd);
			pstmt1 = con.prepareStatement(sql1);
			pstmt2 = con.prepareStatement(sql2);
		}catch(ClassNotFoundException cbfe){
			System.out.println("Oracle driver loading fauled");
		}catch(SQLException se){
			System.out.println("se: " + se);
		}
	}
	public void jspDestroy(){   // DB연결해체
		try{
			if(pstmt1 != null) pstmt1.close();
			if(con != null) con.close();
		}catch(SQLException se){}
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response){
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
		
		try{
			pstmt1.setString(1, email);
			pstmt1.setString(2, subject);
			pstmt1.setString(3, content);
			pstmt1.setInt(4, seq);
			pstmt1.executeUpdate();
			response.sendRedirect("list.jsp");
		}catch(SQLException se){
		}catch(IOException ie){
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
		if(writer != null) doGet(request, response);
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
	
		ResultSet rs = null;
		try{
			pstmt2.setInt(1, seq);
			rs = pstmt2.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String sbj = rs.getString(4);
				String con = rs.getString(5);
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
				<td align='center'><textarea name='content' rows='5' cols='53'><%=con%></textarea></td>
				</tr>
<%
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
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