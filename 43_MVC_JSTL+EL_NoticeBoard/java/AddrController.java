package addr.jstl.control;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.domain.Address;

import java.io.IOException;
import java.util.ArrayList;

import addr.mvc.model.AddrService;

@WebServlet("/addr/addr.do")
public class AddrController extends HttpServlet {
	private static final long serialVersionUID = 1L;  //안전상 써주는 것이 좋다.

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if(m != null) {
			m = m.trim();
			switch(m) {
				case "list": list(request, response); break;
				case "input": input(request, response); break;
				case "insert": insert(request, response); break;
				case "delete": delete(request, response); break;
			}
		}else {
			list(request, response);
		}
	}
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model 핸들링 ( java )
		AddrService service = AddrService.getInstance();
		ArrayList<Address> list = service.listS();
		request.setAttribute("list", list);
		
		//(2)View 지정 ( jsp )
		String view = "list.jsp";  // 이렇게 경로를 쓰고싶지 않다면 폴더명을 addr로 맞춰주면 된다.
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		//컨트롤러에 직접 뷰의 코드를 작성하지 않는다.
	}
	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)View 지정 ( jsp )
		response.sendRedirect("input.jsp");
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model 핸들링 ( java )
		AddrService service = AddrService.getInstance();
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		Address dto = new Address(-1, name, addr, null);
		boolean flag = service.insertS(dto);
		request.setAttribute("flag", flag);
		
		//(2)View 지정 ( jsp )
		String view = "insert.jsp";  // 이렇게 경로를 쓰고싶지 않다면 폴더명을 addr로 맞춰주면 된다.
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		//컨트롤러에 직접 뷰의 코드를 작성하지 않는다.
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model 핸들링 ( java )
		AddrService service = AddrService.getInstance();
		int seq = getSeq(request);
		service.deleteS(seq);
		response.sendRedirect("addr.do");
	}
	private int getSeq(HttpServletRequest request){
		int seq = -1;
		String seqStr = request.getParameter("seq");
		if(seqStr != null){
			seqStr = seqStr.trim();
			if(seqStr.length() != 0){
				try{
					seq = Integer.parseInt(seqStr);
					return seq;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return seq;
	}
}
