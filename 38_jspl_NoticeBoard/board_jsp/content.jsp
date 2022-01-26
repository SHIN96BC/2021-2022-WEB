<%@ page contentType = "text/html;charset=utf-8" import="java.sql.*"%>

<%!
	Connection con;
	PreparedStatement pstmt;
	public void jspInit(){   // DB연결
		String sql = "select * from BOARD where SEQ=?";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String usr = "servlet";
		String pwd = "java";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, usr, pwd);
			pstmt = con.prepareStatement(sql);
		}catch(ClassNotFoundException cbfe){
			System.out.println("Oracle driver loading fauled");
		}catch(SQLException se){
			System.out.println("se: " + se);
		}
	}
	public void jspDestroy(){   // DB연결해체
		try{
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){}
		
	} 
%>


	<meta charset='utf-8'>
	<style>
	table, th, td {
	border: 1px solid black
	border-collapse: collapse;
	}
	th, td {
	padding: 5px;
	}
	a { text-decoration:none }
	</style>
	<center>
	<hr width='600' size='2' noshade>
	<h1>
	Simple Board
	</h1>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='input.jsp'>글쓰기</a>
	<hr width='600' size='2' noshade>
	<table border='1' width='600' align='center' cellpadding='3'>






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
		
		ResultSet rs = null;
		try{
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String sbj = rs.getString(4);
				String con = rs.getString(5);
				Date rdate = rs.getDate(6);    // Date는 util 하고 sql 패키지에 두개다 존재하므로 둘다 인포트 했을 경우에는 앞에다가 java.sql.Date 라고 써준다
				
			
%>
		<tr>
		<td width='100' align='center'>글번호</td>
		<td><%=seq%></td>
		</tr>
		<tr>
		<td align='center'>글쓴이</td>
		<td><%=name%></td>
		</tr>
		<tr>
		<td align='center'>이메일</td>
		<td><%=email%></td>
		</tr>
		<tr>
		<td align='center'>글제목</td>
		<td><%=sbj%></td>
		</tr>
		<tr>
		<td align='center'>글내용</td>
		<td><%=con%></td>
		</tr>
		<tr>
		<th width='100' align='center'>날짜</th>
		<td><%=rdate%></td>
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

</table>
<hr width='600' size='2' noshade>
<b>
<a  href='update.jsp?seq=<%=seq%>'>수정</a>
 | 
<a href='delete.jsp?seq=<%=seq%>'>삭제</a>
 | 
<a href='list.jsp'>목록</a>
</b>
<hr width='600' size='2' noshade>
</center>