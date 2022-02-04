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
	public Board contentS(long postNumber) {
		return dao.content(postNumber);
	}
	public void deleteS(long postNumber) {
		dao.delete(postNumber);
	}
	public void updateS(Board board) {
		dao.update(board);
	} 
}
