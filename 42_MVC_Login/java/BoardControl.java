package bcm.board.control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import bcm.board.domain.Board;
import bcm.board.model.BoardService;

@WebServlet("/boardclient/boardclient.do")
public class BoardControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String b = request.getParameter("b");
		if(b != null) {
			b.trim();
			switch(b) {
				case "list": list(request, response); break;
				case "index": index(request, response); break;
				case "input": input(request, response); break;
				case "insert": insert(request, response); break;
				default: list(request, response);
			}
		}else {
			list(request, response);
		}
	}
	private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("../");
	}
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		ArrayList<Board> list = service.listS();
		request.setAttribute("list", list);
		String view = "mainboard.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("input.jsp");
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		String nickName = request.getParameter("nickName");
		String id = request.getParameter("id");
		String postsubject = request.getParameter("postsubject");
		String postcontent = request.getParameter("postcontent");
		String authorityStr = request.getParameter("authority");
		int authority = -1;
		if(nickName != null && id != null && postsubject != null && postcontent != null && authorityStr != null) {
			nickName.trim();
			id.trim();
			postsubject.trim();
			postcontent.trim();
			authorityStr.trim();
			try {
				authority = Integer.parseInt(authorityStr);
			}catch(NumberFormatException nfe) {
			}
			if(nickName.length() != 0 && id.length() != 0 && postsubject.length() != 0 && postcontent.length() != 0 && authority != -1) {
				Board board = new Board(-1, nickName, id, postsubject, postcontent, authority, null);
				boolean flag = service.insertS(board);
				request.setAttribute("flag", flag);
			}else {
				request.setAttribute("flag", false);
			}
		}else {
			request.setAttribute("flag", false);
		}

		String view = "insert.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
}
