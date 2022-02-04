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
	private static final long serialVersionUID = 1L;  //������ ���ִ� ���� ����.

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
		//(1)Model �ڵ鸵 ( java )
		AddrService service = AddrService.getInstance();
		ArrayList<Address> list = service.listS();
		request.setAttribute("list", list);
		
		//(2)View ���� ( jsp )
		String view = "list.jsp";  // �̷��� ��θ� ������� �ʴٸ� �������� addr�� �����ָ� �ȴ�.
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		//��Ʈ�ѷ��� ���� ���� �ڵ带 �ۼ����� �ʴ´�.
	}
	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)View ���� ( jsp )
		response.sendRedirect("input.jsp");
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model �ڵ鸵 ( java )
		AddrService service = AddrService.getInstance();
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		Address dto = new Address(-1, name, addr, null);
		boolean flag = service.insertS(dto);
		request.setAttribute("flag", flag);
		
		//(2)View ���� ( jsp )
		String view = "insert.jsp";  // �̷��� ��θ� ������� �ʴٸ� �������� addr�� �����ָ� �ȴ�.
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		//��Ʈ�ѷ��� ���� ���� �ڵ带 �ۼ����� �ʴ´�.
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model �ڵ鸵 ( java )
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
