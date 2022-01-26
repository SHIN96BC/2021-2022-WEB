package sbc.svv.addr;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import soo.db.ConnectionPoolBean;

public class ServletDeletePool extends HttpServlet{

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
		PrintWriter pw = res.getWriter();  //유저한테 보낼수있게 printwriter를 연결
		pw.println("<script>");
		try{
			pool = getPool();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seq);
			int i = pstmt.executeUpdate();
			if(i>0){
				pw.println("alert('삭제성공')");
			}else{
				pw.println("alert('삭제실패')");
			}

		}catch(SQLException se){
			pw.println("alert('삭제실패') se:" + se);
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

