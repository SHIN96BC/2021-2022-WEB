package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletInsertPool extends HttpServlet{
	
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
		req.setCharacterEncoding("utf-8");
		String writer = req.getParameter("writer");
		String email = req.getParameter("email");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		if(writer == null || email == null || subject == null || content == null){
			res.sendRedirect("list.do");
			return;
		}
		writer = writer.trim();
		email = email.trim();
		subject = subject.trim();
		content = content.trim();
		if(writer.length() == 0 || email.length() == 0 || subject.length() == 0 || content.length() == 0){
			res.sendRedirect("list.do");
			return;
		}
		
		ConnectionPoolBean pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into BOARD values(BOARD_SEQ.nextval, ?, ?, ?, ?, SYSDATE)";
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();  //�������� �������ְ� printwriter�� ����
		pw.println("<script>");
		try{
			pool = getPool();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, email);
			pstmt.setString(3, subject);
			pstmt.setString(4, content);
			int i = pstmt.executeUpdate();
			if(i>0){
				pw.println("alert('�ۼ�����')");
			}else{
				pw.println("alert('�ۼ�����')");
			}

		}catch(SQLException se){
			pw.println("alert('�ۼ�����') se:" + se);
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) pool.returnConnection(con);  // �������� �޼ҵ� ȣ���ؼ� �ݴ´�.
			}catch(SQLException se){}
		}
		pw.println("location.href='list.do'");
		pw.println("</script>");
	}
}

