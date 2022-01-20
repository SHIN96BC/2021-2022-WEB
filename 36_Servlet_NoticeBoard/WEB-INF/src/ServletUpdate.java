package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

//update ACC set BALANCE=((select BALANCE from ACC where EMAIL='one@daum.net')-10000) where EMAIL='one@daum.net'

public class ServletUpdate extends HttpServlet{
	Connection con;
	PreparedStatement pstmt1, pstmt2;
	public void init(){   // DB����
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
	public void service(HttpServletRequest req, HttpServletResponse res)  
		throws ServletException, IOException {   // SQL�� ���� ����� html�� �÷��� ������
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
		PrintWriter pw = res.getWriter();  //�������� �������ְ� printwriter�� ����
		
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
		pw.println("<a href='list.do'>�۸��</a>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<form name='f' method='post' action='update.do'>");

		ResultSet rs = null;
		try{
			pstmt2.setInt(1, seq);
			rs = pstmt2.executeQuery();
			while(rs.next()){
				String name = rs.getString(2);
				String email = rs.getString(3);
				String sbj = rs.getString(4);
				String con = rs.getString(5);
				pw.println("<input type='hidden' name='seq' value='"+seq+"'>");
				pw.println("<input type='hidden' name='writer' value="+name+">");
				pw.println("<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>");
				pw.println("<td width='30%' align='center'>�۾���</td>");
				pw.println("<td align='center'><input type='text' name='aa' size='60' value='"+name+"' disabled></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td width='30%' align='center'>�̸���</td>");
				pw.println("<td align='center'><input type='text' name='email' size='60' value='"+email+"'></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td width='30%' align='center'>������</td>");
				pw.println("<td align='center'><input type='text' name='subject' size='60' value='"+sbj+"'></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td width='30%' align='center'>�۳���</td>");
				pw.println("<td align='center'><textarea name='content' rows='5' cols='53'>"+con+"</textarea></td>");
				pw.println("</tr>");
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
			}catch(SQLException se){}
		}

		pw.println("<tr>");
		pw.println("<td colspan='2' align='center'>");
		pw.println("<input type='submit' value='����'>");
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

		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();  //�������� �������ְ� printwriter�� ����
		pw.println("<script>");
		try{
			pstmt1.setString(1, email);
			pstmt1.setString(2, subject);
			pstmt1.setString(3, content);
			pstmt1.setInt(4, seq);
			int i = pstmt1.executeUpdate();
			if(i>0){
				pw.println("alert('������Ʈ����')");
			}else{
				pw.println("alert('������Ʈ����')");
			}

		}catch(SQLException se){
			pw.println("alert('������Ʈ����') se:" + se);
		}
		pw.println("location.href='list.do'");
		pw.println("</script>");
	}
	public void destroy(){   // DB������ü
		try{
			if(pstmt1 != null) pstmt1.close();
			if(con != null) con.close();
		}catch(SQLException se){}
		
	} 
}

