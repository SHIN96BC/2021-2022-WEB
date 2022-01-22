package sbc.svv.addr.pool;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletContentPool extends HttpServlet{

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
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;");  // ������ ����
		pw.println("<a href='input.html'>�۾���</a>");
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
				Date rdate = rs.getDate(6);    // Date�� util �ϰ� sql ��Ű���� �ΰ��� �����ϹǷ� �Ѵ� ����Ʈ ���� ��쿡�� �տ��ٰ� java.sql.Date ��� ���ش�
				pw.println("<tr>");
				pw.println("<td width='100' align='center'>�۹�ȣ</th>");
				pw.println("<td>"+seq+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>�۾���</th>");
				pw.println("<td>"+name+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>�̸���</th>");
				pw.println("<td>"+email+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>������</th>");
				pw.println("<td>"+sbj+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>�۳���</th>");
				pw.println("<td>"+content+"</td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<th width='100' align='center'>��¥</th>");
				pw.println("<td>"+rdate+"</td>");
				pw.println("</tr>");
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) pool.returnConnection(con);  // �������� �޼ҵ� ȣ���ؼ� �ݴ´�.
			}catch(SQLException se){}
		}
		pw.println("</table>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<b>");
		pw.println("<a  href='update.do?seq="+seq+"'>����</a>");
		pw.println("| ");
		pw.println("<a href='delete.do?seq="+seq+"'>����</a>");
		pw.println("| ");
		pw.println("<a href='list.do'>���</a>");
		pw.println("</b>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");	
	}
}

