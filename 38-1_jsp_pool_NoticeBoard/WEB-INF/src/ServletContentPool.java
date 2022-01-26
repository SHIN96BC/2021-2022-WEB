package sbc.svv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletContentPool extends HttpServlet{

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
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();  //유저한테 보낼수있게 printwriter를 연결
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
		pw.println("<h1>");
		pw.println("Simple Board");
		pw.println("</h1>");
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;");  // 옆으로 띄우기
		pw.println("<a href='input.html'>글쓰기</a>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<table border='1' width='600' align='center' cellpadding='3'>");

		ConnectionPoolBean pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from BOARD where SEQ=?";
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
				Date rdate = rs.getDate(6);    // Date는 util 하고 sql 패키지에 두개다 존재하므로 둘다 인포트 했을 경우에는 앞에다가 java.sql.Date 라고 써준다
				pw.println("<tr>");
				pw.println("<td width='100' align='center'>글번호</th>");
				pw.println("<td>"+seq+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>글쓴이</th>");
				pw.println("<td>"+name+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>이메일</th>");
				pw.println("<td>"+email+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>글제목</th>");
				pw.println("<td>"+sbj+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>글내용</th>");
				pw.println("<td>"+content+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>날짜</th>");
				pw.println("<td>"+rdate+"</td>");
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
		pw.println("</table>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<b>");
		pw.println("<a  href='update.do?seq="+seq+"'>수정</a>");
		pw.println("| ");
		pw.println("<a href='delete.do?seq="+seq+"'>삭제</a>");
		pw.println("| ");
		pw.println("<a href='list.do'>목록</a>");
		pw.println("</b>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");	
	}
}

