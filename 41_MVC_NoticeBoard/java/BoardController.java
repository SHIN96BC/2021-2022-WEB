package board.mvc.control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.domain.Board;

import java.io.IOException;
import java.util.ArrayList;

import board.mvc.model.BoardService;

@WebServlet("/board/board.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String b = request.getParameter("b");
		if(b != null) {
			b = b.trim();
			switch(b){
				case "list": list(request, response); break;
				case "input": input(request, response); break;
				case "insert": insert(request, response); break;
				case "contentList": contentList(request, response); break;
				case "delete": delete(request, response); break;
				case "updateList": updateList(request, response); break;
				case "update": update(request, response); break;
			}
		}else {
			list(request, response);
		}
	}
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model 핸들링 ( java )
		BoardService service = BoardService.getInstance();
		ArrayList<Board> list = service.listS();
		request.setAttribute("list", list);
		
		//(2)View 지정 ( jsp )
		String view = "../board_mvc/list.jsp";   // forward를 쓰는 경우는 보여주기만 할때 사용하고 response.sendRedirect 는 데이터베이스등의 기능을 수행할때 사용됨.
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(2)View 지정 ( jsp )
		response.sendRedirect("../board_mvc/input.jsp");
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//(1)Model 핸들링 ( java )
		BoardService service = BoardService.getInstance();
		String writer = request.getParameter("writer");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		Board dto = new Board(-1, writer, email, subject, content, null);
		boolean flag = service.insertS(dto);
		request.setAttribute("flag", flag);
		//(2)View 지정 ( jsp )
		String view = "../board_mvc/insert.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void contentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService service = BoardService.getInstance();
		int seq = getSeq(request);
		ArrayList<Board> contentList = service.contentS(seq);
		request.setAttribute("contentList", contentList);
		
		String view = "../board_mvc/content.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService service = BoardService.getInstance();
		int seq = getSeq(request);
		service.deleteS(seq);
		response.sendRedirect("board.do");
	}
	private void updateList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService service = BoardService.getInstance();
		int seq = getSeq(request);
		ArrayList<Board> updateList = service.contentS(seq);
		request.setAttribute("updateList", updateList);
		
		String view = "../board_mvc/update.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService service = BoardService.getInstance();
		int seq = getSeq(request);
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		Board dto = new Board(seq, null, email, subject, content, null);
		service.updateS(dto);
		response.sendRedirect("board.do");
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
