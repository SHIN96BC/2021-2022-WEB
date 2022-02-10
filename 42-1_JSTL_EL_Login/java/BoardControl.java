package jstl.board.control;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import jstl.board.domain.Board;
import jstl.board.model.BoardService;
import jstl.member.model.MemberService;
import static jstl.board.model.BoardConst.*;

@WebServlet("/boardclient/boardclient.do")
public class BoardControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("service 진입");
		String b = request.getParameter("b");
		if(b != null) {
			b.trim();
			switch(b) {
				case "list": list(request, response); break;
				case "index": index(request, response); break;
				case "input": input(request, response); break;
				case "insert": insert(request, response); break;
				case "content": content(request, response); break;
				case "delete": delete(request, response); break;
				case "updateList": updateList(request, response); break;
				case "update": update(request, response); break;
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
		System.out.println("list 진입");
		BoardService service = BoardService.getInstance();
		long ps = getPs(request);
		long cp = getCp(request);
		ArrayList<Long> cpList = service.getCpList(ps, cp);
		ArrayList<Board> list = service.listS(ps, cp);
		long maxPage = service.getMaxPage(cp);
		long size = service.boardCountS();
		request.setAttribute("ps", ps);
		System.out.println("ps: "+ ps);
		request.setAttribute("list", list);
		System.out.println("list: "+ list);
		request.setAttribute("size", size);
		System.out.println("size: "+ size);
		request.setAttribute("cp", cp);
		System.out.println("list cp: "+ cp);
		request.setAttribute("cpList", cpList);
		System.out.println("cpList: "+ cpList);
		request.setAttribute("maxPage", maxPage);
		System.out.println("maxPage: "+ maxPage);
		
		String view = "mainboard.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		long postnumber = getPostNumber(request);
		int type = getType(request);
		if(type == MAIN) {
			response.sendRedirect("input.jsp");
		}else if(type == RE) {
			long lev = service.findByLevS(postnumber);
			request.setAttribute("lev", lev);
			request.setAttribute("postNumber", postnumber);
			
			String view = "input_re.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		}else {
			response.sendRedirect("boardclient.do");
		}
	}
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		String nickName = request.getParameter("nickName");
		String id = request.getParameter("id");
		String postsubject = request.getParameter("postsubject");
		String postcontent = request.getParameter("postcontent");
		String typeStr = request.getParameter("type");
		if(nickName != null && id != null && postsubject != null && postcontent != null && typeStr != null) {
			nickName.trim();
			id.trim();
			postsubject.trim();
			postcontent.trim();
			typeStr = typeStr.trim();
			if(nickName.length() != 0 && id.length() != 0 && postsubject.length() != 0 && postcontent.length() != 0 && typeStr.length() != 0) {
				int type = getType(request);
				if(type == MAIN) {
					Board board = new Board(-1, nickName, id, postsubject, postcontent, -1, null, -1, 0, 0);
					boolean flag = service.insertS(board);
					request.setAttribute("flag", flag);
				}else if(type == RE){
					long postNumber = getPostNumber(request);
					Board board = new Board(postNumber, nickName, id, postsubject, postcontent, -1, null, -1, 0, 0);
					boolean flag = service.insertReS(board);
					request.setAttribute("flag", flag);
				}else {
					response.sendRedirect("boardclient.do");
				}
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
	private void content(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		long postNumber = getPostNumber(request);
		long views = service.viewsCheckS(postNumber);
		service.viewsUpdateS(postNumber, views);
		Board board = service.contentS(postNumber);
		request.setAttribute("board", board);
		
		String view = "content.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		long postNumber = getPostNumber(request);
		service.deleteS(postNumber);
		
		response.sendRedirect("boardclient.do");
	}
	private void updateList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		long postNumber = getPostNumber(request);
		Board board = service.contentS(postNumber);
		request.setAttribute("board", board);
		
		String view = "update.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BoardService service = BoardService.getInstance();
		long postNumber = getPostNumber(request);
		String postSubject = request.getParameter("postSubject");
		String postContent = request.getParameter("postContent");
		Board board = new Board(postNumber, null, null, postSubject, postContent, -1, null, -1, -1, -1);
		service.updateS(board);
		
		response.sendRedirect("boardclient.do");
	}
	private long getPostNumber(HttpServletRequest request){
		long postNumber = -1;
		String postNumberStr = request.getParameter("postNumber");
		if(postNumberStr != null){
			postNumberStr = postNumberStr.trim();
			if(postNumberStr.length() != 0){
				try{
					postNumber = Integer.parseInt(postNumberStr);
					return postNumber;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return postNumber;
	}
	private int getType(HttpServletRequest request){
		int type = -1;
		String typeStr = request.getParameter("type");
		if(typeStr != null){
			typeStr = typeStr.trim();
			if(typeStr.length() != 0){
				try {
					type = Integer.parseInt(typeStr);
					return type;
				}catch(NumberFormatException nfe) {
					return type;
				}
			}
		}
		return type;
	}
	private long getPs(HttpServletRequest request){
		long ps = -1;
		String psStr = request.getParameter("ps");
		if(psStr != null){
			psStr = psStr.trim();
			if(psStr.length() != 0){
				try{
					ps = Integer.parseInt(psStr);
					return ps;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return ps;
	}
	private long getCp(HttpServletRequest request){
		long cp = -1;
		String cpStr = request.getParameter("cp");
		if(cpStr != null){
			cpStr = cpStr.trim();
			if(cpStr.length() != 0){
				try{
					cp = Integer.parseInt(cpStr);
					return cp;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return cp;
	}
}
