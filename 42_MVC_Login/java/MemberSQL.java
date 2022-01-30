package bcm.mvc.model;

class MemberSQL {
	static final String INSERT = "insert into BOARDCLIENTMEMBER values(BOARDCLIENTMEMBER_SEQ.nextval, ?, ?, ?, ?, ?, ?, default, SYSDATE)";
	static final String FINDBYID = "select * from BOARDCLIENTMEMBER where ID=?";
	static final String FINDBYNICKNAME = "select * from BOARDCLIENTMEMBER where NICKNAME=?";
	static final String FINDBYPHONENUMBER = "select * from BOARDCLIENTMEMBER where PHONENUMBER=?";
}
