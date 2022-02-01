package bcm.mvc.model;

import bcm.mvc.domain.Member;

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
}
