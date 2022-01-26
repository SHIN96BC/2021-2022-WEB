package sbc.svv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletListPool extends HttpServlet{
	
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
		pw.println("Address List with Poll ");
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

		ConnectionPoolBean pool = null;
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		String sql = "select * from BOARD order by seq desc";
		try{
			pool = getPool();
			con = pool.getConnection();
			stmt = con.createStatement();
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
				if(stmt != null) stmt.close();
				if(con != null) pool.returnConnection(con);  // 닫을때도 메소드 호출해서 닫는다.
			}catch(SQLException se){}
		}

		pw.println("</table>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");
	}
	
}

