package bcm.board.model;

import java.util.ArrayList;

import bcm.board.domain.Board;

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
	public boolean deleteS(long postNumber) {
		return dao.delete(postNumber);
	}
	public boolean updateS(Board board) {
		return dao.update(board);
	} 
}
