package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

public class ServletList extends HttpServlet{
	Connection con;
	Statement stmt;
	public void init(){   // DB연결
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String usr = "servlet";
		String pwd = "java";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, usr, pwd);
			stmt = con.createStatement();
		}catch(ClassNotFoundException cbfe){
			System.out.println("Oracle driver loading fauled");
		}catch(SQLException se){
			System.out.println("se: " + se);
		}
	}
	public void service(HttpServletRequest req, HttpServletResponse res)  
		throws ServletException, IOException {   // SQL문 수행 결과를 html에 올려서 보여줌
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
		pw.println("<h1>");
		pw.println("Simple Board");
		pw.println("</h1>");
		pw.println("<a href='input.html'>글쓰기</a>");
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;");  // 옆으로 한칸 띄우기
		pw.println("<a href='../'>인덱스</a>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<table border='1' width='600' align='center' cellpadding='2'>");
		pw.println("<tr>");
		pw.println("<th align='center' width='10%'>글번호</th>");
		pw.println("<th align='center' width='15%'>작성자</th>");
		pw.println("<th align='center' width='30%'>이메일</th>");
		pw.println("<th align='center' width='30%'>글제목</th>");
		pw.println("<th align='center' width='15%'>날짜</th>");
		pw.println("</tr>");







		ResultSet rs = null;
		String sql = "select * from BOARD order by seq desc";
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				int seq = rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String sbj = rs.getString(4);
				Date rdate = rs.getDate(6);    // Date는 util 하고 sql 패키지에 두개다 존재하므로 둘다 인포트 했을 경우에는 앞에다가 java.sql.Date 라고 써준다
				pw.println("<tr>");
				pw.println("<td align='center'>"+seq+"</td>");
				pw.println("<td align='center'>"+name+"</td>");
				pw.println("<td align='center'>"+email+"</td>");
				pw.println("<td align='center'>");
				pw.println("<a href='content.do?seq="+seq+"'>"+sbj+"</a>");
				pw.println("</td>");
				pw.println("<td align='center'>"+rdate+"</td>");
				pw.println("</tr>");
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
			}catch(SQLException se){}
		}

		
		pw.println("</table>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");
		
		
	}
	public void destroy(){   // DB연결해체
		try{
			if(stmt != null) stmt.close();
			if(con != null) con.close();
		}catch(SQLException se){}
		
	} 
}

