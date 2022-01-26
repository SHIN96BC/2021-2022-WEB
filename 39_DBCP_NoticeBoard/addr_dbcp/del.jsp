<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.*"%>
<jsp:useBean id="dbcp" class="sbc.dbcp.DbcpBean" scope="application"/>

<%! 
	private int getSeq(HttpServletRequest request){
		int seq = -1;
		String seqStr = request.getParameter("seq");
		if(seqStr != null){
			seqStr = seqStr.trim();
			if(seqStr.length() != 0){
				try{
					seq = Integer.parseInt(seqStr);
					return seq;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return seq;
	}
%>

<%
	int seq = getSeq(request);
	if(seq != -1){
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from ADDRESS where SEQ=?";
		try{
			ds = dbcp.getDs();
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			int i = pstmt.executeUpdate();
		}catch(SQLException se){
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se){}
		}
	}
	response.sendRedirect("list.jsp");
%>