package bcm.mvc.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import bcm.mvc.domain.Member;
import bcm.mvc.model.MemberService;

@WebServlet("/login/login.do")
public class MemberControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if(m != null) {
			m = m.strip();
			switch(m) {
				case "login": login(request, response); break;
				case "signin": signin(request, response); break;
				default: response.sendRedirect("../");
			}
			
		}
	}
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("login.jsp");
	}
	private void signin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("signin.jsp");
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		MemberService service = MemberService.getInstance();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String phonenumber = request.getParameter("phonenumber");
		String address = request.getParameter("address");
		Member member = new Member(-1, id, password, nickname, name, phonenumber, address, -1, null);
		boolean falg = service.insertS(member);
		
	}
}
