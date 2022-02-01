package bcm.mvc.control;

import jakarta.servlet.RequestDispatcher;
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
				case "loginmenu": loginmenu(request, response); break;
				case "signin": signin(request, response); break;
				case "insert": insert(request, response); break;
				default: response.sendRedirect("../");
			}
			
		}
	}
	private void loginmenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("login.jsp");
	}
	private void signin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("signin.jsp");
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		MemberService service = MemberService.getInstance();
		System.out.println("insert 진입");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String phonenumber = request.getParameter("phonenumber");
		String address = request.getParameter("address");
		if(id != null && password != null && nickname != null && name != null && phonenumber != null && address != null) {
			id = id.strip();
			password = password.strip();
			nickname = nickname.strip();
			name = name.strip();
			phonenumber = phonenumber.strip();
			address = address.strip();
			if(id.length() != 0 && password.length() != 0 && nickname.length() != 0 && name.length() != 0 && phonenumber.length() != 0 && address.length() != 0) {
				String idcheck = id.split("@")[0];
				boolean flagId = service.findByIdS(idcheck);
				if(flagId) {
					boolean flagNickName = service.findByNickNameS(nickname);
					if(flagNickName) {
						Member member = new Member(-1, id, password, nickname, name, phonenumber, address, -1, null);
						boolean falg = service.insertS(member);
						request.setAttribute("message", 4);
					}else {
						request.setAttribute("message", 3);
					}
				}else {
					request.setAttribute("message", 2);
				}
			}else {
				request.setAttribute("message", 1);
			}
		}else {
			request.setAttribute("message", 0);
		}
		System.out.println("if 문 끝");
		
		String view = "signin_check.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
}
