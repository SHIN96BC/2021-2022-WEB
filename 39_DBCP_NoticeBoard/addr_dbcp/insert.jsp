<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.*"%>
<jsp:useBean id="dbcp" class="sbc.dbcp.DbcpBean" scope="application"/>

<%	
	String name = request.getParameter("name");
	String addr = request.getParameter("addr");
	out.println("<script>");
	DataSource ds = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = "insert into ADDRESS values(ADDRESS_SEQ.nextval, ?, ?, SYSDATE)";
	try{
		ds = dbcp.getDs();
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, addr);
		int i = pstmt.executeUpdate();
		if(i>0){
			out.println("alert('입력성공 with JSP_Pool')");
		}else{
			out.println("alert('입력실패 with JSP_Pool')");
		}
	}catch(SQLException se){
		out.println("alert('입력실패 with JSP_Pool')");
	}finally{
		try{
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){}
	}
	out.println("location.href='list.jsp'");
	out.println("</script>");
%>