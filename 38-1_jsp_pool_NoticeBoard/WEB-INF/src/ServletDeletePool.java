package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletDeletePool extends HttpServlet{

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
		
		ConnectionPoolBean pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from BOARD where SEQ=?";
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();  //�������� �������ְ� printwriter�� ����
		pw.println("<script>");
		try{
			pool = getPool();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			int i = pstmt.executeUpdate();
			if(i>0){
				pw.println("alert('��������')");
			}else{
				pw.println("alert('��������')");
			}

		}catch(SQLException se){
			pw.println("alert('��������') se:" + se);
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

