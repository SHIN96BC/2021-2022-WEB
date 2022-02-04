package jstl.member.model;

import jstl.member.domain.Member;

public class MemberService {
	private MemberDAO dao;
	private static final MemberService INSTANCE = new MemberService();
	private MemberService() {
		dao = new MemberDAO();
	}
	public static MemberService getInstance() {
		return INSTANCE;
	}
	public boolean insertS(Member member) {
		return dao.insert(member);
	}
	public boolean findByIdS(String id) {
		return dao.findById(id);
	}
	public boolean findByNickNameS(String nickname) {
		return dao.findByNickName(nickname);
	}
	public boolean findByPhoneNumberS(String phonenumber) {
		return dao.findByPhoneNumber(phonenumber);
	}
	public boolean passwordCheckS(String id, String password) {
		return dao.passwordCheck(id, password);
	}
	public Member userInfoS(String id) {
		Member member = dao.userInfo(id);
		member.setPassword("");
		return member;
	}
	public boolean infoUpdateS(Member member) {
		return dao.infoUpdate(member);
	}
}
