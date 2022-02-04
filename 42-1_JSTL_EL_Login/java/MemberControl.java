package jstl.member.control;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import jstl.board.domain.Board;
import jstl.board.model.BoardService;
import jstl.member.domain.Member;
import jstl.member.model.MemberService;

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
				case "infoUpdate": infoUpdate(request, response); break;
				case "infoUpdateSet": infoUpdateSet(request, response); break;
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
	private void infoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		MemberService service = MemberService.getInstance();
		String userId = getId(request);
		Member member = service.userInfoS(userId);
		request.setAttribute("member", member);
		
		String view = "info_update.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void infoUpdateSet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		MemberService service = MemberService.getInstance();
		String id = request.getParameter("infoId");
		String passWord = request.getParameter("infoPassword");
		String nickName = request.getParameter("infoNickName");
		String phoneNumber = request.getParameter("infoPhoneNumber");
		String address = request.getParameter("infoAddress");
		if(id != null && passWord != null && nickName != null && phoneNumber != null && address != null) {
			id = id.trim();
			passWord = passWord.trim();
			nickName = nickName.trim();
			phoneNumber = phoneNumber.trim();
			address = address.trim();
			if(id.length() != 0 && passWord.length() != 0 && nickName.length() != 0 && phoneNumber.length() != 0 && address.length() != 0) {
				Member member = new Member(-1, id, passWord, nickName, null, phoneNumber, address, -1, null);
				boolean flag = service.infoUpdateS(member);
				
				request.setAttribute("flag", flag);
			}else {
				request.setAttribute("flag", false);
			}
		}else {
			request.setAttribute("flag", false);
		}
		
		sessionReset(request, response, id);
		String view = "info_update_set.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("logout.jsp");
	}
	private void sessionReset(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException{
		MemberService service = MemberService.getInstance();
		HttpSession session = request.getSession();
		Member member = service.userInfoS(id);
		session.setAttribute("user", member);
	}
	private String getId(HttpServletRequest request){
		String userId = request.getParameter("userId");
		if(userId != null){
			userId = userId.trim();
			if(userId.length() != 0){
				return userId;
			}else {
				return null;
			}
		}else {
			return null;
		}
		
	}
}
