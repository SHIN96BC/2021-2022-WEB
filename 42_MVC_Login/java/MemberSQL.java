package bcm.member.model;

class MemberSQL {
	static final String INSERT = "insert into BOARDCLIENTMEMBER values(BOARDCLIENTMEMBER_SEQ.nextval, LOWER(?), ?, LOWER(?), ?, ?, ?, default, SYSDATE)";
	static final String FINDBYID = "select * from BOARDCLIENTMEMBER where ID like LOWER('%'||?||'%')";
	static final String FINDBYNICKNAME = "select * from BOARDCLIENTMEMBER where NICKNAME=LOWER(?)";
	static final String FINDBYPHONENUMBER = "select * from BOARDCLIENTMEMBER where PHONENUMBER=?";
	static final String PASSWORDCHECK = "select * from BOARDCLIENTMEMBER where ID=LOWER(?) and PASSWORD=?";
	static final String USERINFO = "select * from BOARDCLIENTMEMBER where ID=LOWER(?)";
}
