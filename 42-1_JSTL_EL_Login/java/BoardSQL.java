package jstl.board.model;

class BoardSQL {
	static final String LIST = "select * from BOARDCLIENT order by POSTNUMBER";
	static final String CONTENT = "select * from BOARDCLIENT where POSTNUMBER=?";
	static final String INSERT = "insert into BOARDCLIENT values(BOARDCLIENT_SEQ.nextval, LOWER(?), LOWER(?), ?, ?, 0, SYSDATE)";
	static final String DELETE = "delete from BOARDCLIENT where POSTNUMBER=?";
	static final String UPDATE = "update BOARDCLIENT set POSTSUBJECT=?, POSTCONTENT=? where POSTNUMBER=?";
	static final String VIEWS_CHECK = "select VIEWS from BOARDCLIENT where POSTNUMBER=?";
	static final String VIEWS_UPDATE = "update BOARDCLIENT set VIEWS=? where POSTNUMBER=?";
}
