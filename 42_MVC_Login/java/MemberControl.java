package bcm.member.control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import bcm.member.domain.Member;
import bcm.member.model.MemberService;

@WebServlet("/login/login.do")
public class MemberControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if(m != null) {
			m = m.trim();
			switch(m) {
				case "loginmenu": loginmenu(request, response); break;
				case "signin": signin(request, response); break;
				case "insert": insert(request, response); break;
				case "login": login(request, response); break;
				case "info": info(request, response); break;
				case "logout": logout(request, response); break;
				default: response.sendRedirect("../");
			}
		}else {
			response.sendRedirect("../");
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
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String phonenumber = request.getParameter("phonenumber");
		String address = request.getParameter("address");
		if(id != null && password != null && nickname != null && name != null && phonenumber != null && address != null) {
			id = id.trim();
			password = password.trim();
			nickname = nickname.trim();
			name = name.trim();
			phonenumber = phonenumber.trim();
			address = address.trim();
			if(id.length() != 0 && password.length() != 0 && nickname.length() != 0 && name.length() != 0 && phonenumber.length() != 0 && address.length() != 0) {
				boolean flagId = service.findByIdS(id);
				if(!flagId) {
					boolean flagNickName = service.findByNickNameS(nickname);
					if(!flagNickName) {
						Member member = new Member(-1, id, password, nickname, name, phonenumber, address, -1, null);
						boolean falg = service.insertS(member);
						request.setAttribute("message", 3);
					}else {
						request.setAttribute("message", 2);
					}
				}else {
					request.setAttribute("message", 1);
				}
			}else {
				request.setAttribute("message", 0);
			}
		}else {
			request.setAttribute("message", 0);
		}
		
		String view = "signin_check.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		MemberService service = MemberService.getInstance();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		if(id != null && password != null) {
			id.trim();
			password.trim();
			if(id.length() != 0 && password.length() != 0) {
				boolean flag = service.passwordCheckS(id, password);
				if(flag) {
					HttpSession session = request.getSession();
					Member member = service.userInfoS(id);
					session.setAttribute("user", member);
					request.setAttribute("message", 2);
				}else {
					request.setAttribute("message", 1);
				}
			}else {
				request.setAttribute("message", 0);
			}
		}else {
			request.setAttribute("message", 0);
		}
		
		String view = "login_check.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("info.jsp");
	}
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("logout.jsp");
	}
}
