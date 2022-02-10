package jstl.board.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
	public ArrayList<Board> listS(long ps, long cp){
		long psTemp = -1;
		long cpTemp = -1;
		long temp = -1;
		long max = -1;
		long min = 0;
		
		if(ps == -1) psTemp = 5;
		else psTemp = ps;
		if(cp == -1) cpTemp = 0;
		else cpTemp = cp;
		
		if(cpTemp/psTemp != 0) {
			temp = cpTemp/psTemp+1;
			if(psTemp*(temp-1) == cpTemp){
				min = psTemp*(temp-2)+1;
				temp -= 1;
			}else{
				min = psTemp*(temp-1)+1;
			}
		}else {
			temp = 1;
		}
		max = psTemp*temp;
		System.out.println("max: " + max + " min: " + min);
		return dao.list(max, min);
	}
	public boolean insertS(Board board) {
		return dao.insert(board);
	}
	public boolean insertReS(Board board) {
		long refer = dao.findByRefer(board.getPostnumber());
		board.setRefer(refer);
		long lev = dao.findByLev(board.getPostnumber());
		board.setLev(lev+1);
		long sunbun = dao.findBySunbun(board.getRefer());
		board.setSunbun(sunbun+1);
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
	public long boardCountS() {
		return dao.boardCount();
	}
	public ArrayList<Long> getCpList(long ps, long cp){
		ArrayList<Long> cpList = new ArrayList<Long>();
		long loop = 0;
		long start = 1;
		long psTemp = -1;
		long cpTemp = -1;
		if(ps == -1) psTemp = 5;
		else psTemp = ps;
		if(cp == -1) cpTemp = 0;
		else cpTemp = cp;
		
		if(cpTemp/psTemp != 0) {
			loop = cpTemp/psTemp+1;
			if(psTemp*(loop-1) == cpTemp){
				start = psTemp*(loop-2)+1;
				loop -= 1;
			}else{
				start = psTemp*(loop-1)+1;
			}
		}else {
			loop = 1;
		}
		for(long i=start; i<=psTemp*loop; i++) {
			cpList.add(i);
		}
		return cpList;
	}
	public long getMaxPage(long cp) {
		long maxPage = -1;
		long cpTemp = -1;
		if(cp == -1) {
			cpTemp = 5;
		}else {
			cpTemp = cp;
		}
		long boardCount = boardCountS();
		maxPage = boardCount/cpTemp;
		if(boardCount != cpTemp*maxPage) {
			maxPage = boardCount/cpTemp+1;
		}
		return maxPage;
	}
}
