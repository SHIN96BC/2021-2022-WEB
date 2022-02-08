package jstl.board.model;

import java.util.ArrayList;

import jstl.board.domain.Board;

public class BoardService {
	private BoardDAO dao;
	private static final BoardService INSTANCE = new BoardService();
	private BoardService() {
		dao = new BoardDAO();
	}
	public static BoardService getInstance() {
		return INSTANCE;
	}
	public ArrayList<Board> listS(){
		return dao.list();
	}
	public boolean insertS(Board board) {
		return dao.insert(board);
	}
	public boolean insertReS(Board board) {
		long refer = dao.findByRefer(board.getPostnumber());
		board.setRefer(refer);
		long lev = dao.findByLev(board.getPostnumber());
		board.setRefer(lev+1);
		long sunbun = dao.findBySunbun(board.getRefer());
		board.setRefer(sunbun+1);
		return dao.insertRe(board);
	}
	public Board contentS(long postNumber) {
		return dao.content(postNumber);
	}
	public void deleteS(long postNumber) {
		dao.delete(postNumber);
	}
	public void updateS(Board board) {
		dao.update(board);
	} 
	public long viewsCheckS(long postNumber) {
		return dao.viewsCheck(postNumber);
	}
	public void viewsUpdateS(long postNumber, long views) {
		views++;
		dao.viewsUpdate(postNumber, views);
	}
	public long findByLevS(long postNumbera) {
		return dao.findByLev(postNumbera);
	}
}
