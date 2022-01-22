package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletUpdatePool extends HttpServlet{
	
	private ConnectionPoolBean getPool() throws SQLException{  // 둘중에 하나만 캐치로 잡고싶을때는 쓰로우에 하나만 적고 잡고싶은거만 캐치절에 적으면 된다
		ServletContext application = this.getServletContext(); //getServletContext()는 application scope의 방의 타입이다.
		ConnectionPoolBean pool = (ConnectionPoolBean)application.getAttribute("pool"); // pool이라는 닉네임은 어플리케이션내에서 고유해야한다.
		if(pool == null){
			try{
				pool = new ConnectionPoolBean();
				application.setAttribute("pool", pool); //키 밸류 한쌍으로 저장한다.
			}catch(ClassNotFoundException cnfe){
				System.out.println("드라이버 로딩 실패");
			}
		}
		return pool;
	}
	public void service(HttpServletRequest req, HttpServletResponse res)  
		throws ServletException, IOException {   // SQL문 수행 결과를 html에 올려서 보여줌
		req.setCharacterEncoding("utf-8");
		String seqStr = req.getParameter("seq");
		String writer = req.getParameter("writer");
		if(writer != null) doGet(req, res);
		int seq = -1;
		if(seqStr == null){
			res.sendRedirect("list.do");
			return;
		}
		seqStr = seqStr.trim();
		if(seqStr.length() == 0){
			res.sendRedirect("list.do");
			return;
		}else{
			try{
				seq = Integer.parseInt(seqStr);
			}catch(NumberFormatException nfe){
				res.sendRedirect("list.do");
				return;
			}
		}

		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();  //유저한테 보낼수있게 printwriter를 연결
		
		pw.println("<meta charset='utf-8'>");
		pw.println("<style>");
		pw.println("table, th, td {");
		pw.println("border: 1px solid black;");
		pw.println("border-collapse: collapse;");
		pw.println("}");
		pw.println("th, td {");
		pw.println("padding: 5px;");
		pw.println("}");
		pw.println("a { text-decoration:none }");
		pw.println("</style>");
		pw.println("<center>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<h2>Simple Board with Servlet</h2>");
		pw.println("&nbsp;&nbsp;&nbsp;");
		pw.println("<a href='list.do'>글목록</a>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<form name='f' method='post' action='update.do'>");

		ConnectionPoolBean pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select * from BOARD where SEQ=?";
		ResultSet rs = null;
		try{
			pool = getPool();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String sbj = rs.getString(4);
				String content = rs.getString(5);
				pw.println("<input type='hidden' name='seq' value='"+seq+"'>");
				pw.println("<input type='hidden' name='writer' value="+name+">");
				pw.println("<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>");
				pw.println("<td width='30%' align='center'>글쓴이</td>");
				pw.println("<td align='center'><input type='text' name='aa' size='60' value='"+name+"' disabled></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td width='30%' align='center'>이메일</td>");
				pw.println("<td align='center'><input type='text' name='email' size='60' value='"+email+"'></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td width='30%' align='center'>글제목</td>");
				pw.println("<td align='center'><input type='text' name='subject' size='60' value='"+sbj+"'></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td width='30%' align='center'>글내용</td>");
				pw.println("<td align='center'><textarea name='content' rows='5' cols='53'>"+content+"</textarea></td>");
				pw.println("</tr>");
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) pool.returnConnection(con);  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}

		pw.println("<tr>");
		pw.println("<td colspan='2' align='center'>");
		pw.println("<input type='submit' value='수정'>");
		pw.println("</td>");
		pw.println("</tr>");
		pw.println("</table>");
		pw.println("</form>");
		pw.println("</table>");
		


		
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		
		req.setCharacterEncoding("utf-8");
		String seqStr = req.getParameter("seq");
		String email = req.getParameter("email");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		int seq = -1;
		if(seqStr == null){
			res.sendRedirect("list.do");
			return;
		}
		seqStr = seqStr.trim();
		if(seqStr.length() == 0){
			res.sendRedirect("list.do");
			return;
		}else{
			try{
				seq = Integer.parseInt(seqStr);
			}catch(NumberFormatException nfe){
				res.sendRedirect("list.do");
				return;
			}
		}
		
		ConnectionPoolBean pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update BOARD set EMAIL=?, SUBJECT=?, CONTENT=? where SEQ=?";
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();  //유저한테 보낼수있게 printwriter를 연결
		pw.println("<script>");
		try{
			pool = getPool();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, subject);
			pstmt.setString(3, content);
			pstmt.setInt(4, seq);
			int i = pstmt.executeUpdate();
			if(i>0){
				pw.println("alert('업데이트성공')");
			}else{
				pw.println("alert('업데이트실패')");
			}
		}catch(SQLException se){
			pw.println("alert('업데이트실패') se:" + se);
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) pool.returnConnection(con);  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}
		pw.println("location.href='list.do'");
		pw.println("</script>");
	}
}

