package board.mvc.model;

import java.util.ArrayList;
import mvc.domain.Board;

public class BoardService {
	private BoardDAO dao;
	private static final BoardService instance = new BoardService();
	private BoardService() {
		dao = new BoardDAO();
	}
	public static BoardService getInstance() {
		return instance;
	}
	
	public ArrayList<Board> listS(){
		return dao.list();
	}
	public ArrayList<Board> contentS(int seq){
		return dao.content(seq);
	}
	public boolean insertS(Board dto) {
		return dao.insert(dto);
	}
	public void deleteS(int seq) {
		dao.delete(seq);
	}
	public boolean updateS(Board dto) {
		return dao.update(dto);
	}
}
