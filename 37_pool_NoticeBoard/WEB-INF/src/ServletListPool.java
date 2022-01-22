package sbc.svv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletListPool extends HttpServlet{
	
	private ConnectionPoolBean getPool() throws SQLException{  // ���߿� �ϳ��� ĳġ�� ���������� ���ο쿡 �ϳ��� ���� �������Ÿ� ĳġ���� ������ �ȴ�
		ServletContext application = this.getServletContext(); //getServletContext()�� application scope�� ���� Ÿ���̴�.
		ConnectionPoolBean pool = (ConnectionPoolBean)application.getAttribute("pool"); // pool�̶�� �г����� ���ø����̼ǳ����� �����ؾ��Ѵ�.
		if(pool == null){
			try{
				pool = new ConnectionPoolBean();
				application.setAttribute("pool", pool); //Ű ��� �ѽ����� �����Ѵ�.
			}catch(ClassNotFoundException cnfe){
				System.out.println("����̹� �ε� ����");
			}
		}
		return pool;
	}
	public void service(HttpServletRequest req, HttpServletResponse res)  
		throws ServletException, IOException {   // SQL�� ���� ����� html�� �÷��� ������
		

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
		pw.println("<h1>");
		pw.println("Address List with Poll ");
		pw.println("</h1>");
		pw.println("<a href='input.html'>�۾���</a>");
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;");  // ������ ��ĭ ����
		pw.println("<a href='../'>�ε���</a>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<table border='1' width='600' align='center' cellpadding='2'>");
		pw.println("<tr>");
		pw.println("<th align='center' width='10%'>�۹�ȣ</th>");
		pw.println("<th align='center' width='15%'>�ۼ���</th>");
		pw.println("<th align='center' width='30%'>�̸���</th>");
		pw.println("<th align='center' width='30%'>������</th>");
		pw.println("<th align='center' width='15%'>��¥</th>");
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
				Date rdate = rs.getDate(6);    // Date�� util �ϰ� sql ��Ű���� �ΰ��� �����ϹǷ� �Ѵ� ����Ʈ ���� ��쿡�� �տ��ٰ� java.sql.Date ��� ���ش�
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
				if(con != null) pool.returnConnection(con);  // �������� �޼ҵ� ȣ���ؼ� �ݴ´�.
			}catch(SQLException se){}
		}

		pw.println("</table>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");
	}
	
}

