package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

public class ServletDelete extends HttpServlet{
	Connection con;
	PreparedStatement pstmt;
	public void init(){   // DB연결
		String sql = "delete from BOARD where SEQ=?";
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
	public void service(HttpServletRequest req, HttpServletResponse res)  
		throws ServletException, IOException {   // SQL문 수행 결과를 html에 올려서 보여줌
		req.setCharacterEncoding("utf-8");
		String seqStr = req.getParameter("seq");
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
		pw.println("<script>");
		try{
			pstmt.setInt(1, seq);
			int i = pstmt.executeUpdate();
			if(i>0){
				pw.println("alert('삭제성공')");
			}else{
				pw.println("alert('삭제실패')");
			}

		}catch(SQLException se){
			pw.println("alert('삭제실패') se:" + se);
		}
		pw.println("location.href='list.do'");
		pw.println("</script>");
	}
	public void destroy(){   // DB연결해체
		try{
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){}
		
	} 
}

