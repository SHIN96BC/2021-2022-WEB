package bcm.member.domain;

import java.sql.Date;

public class Member {
	private long clientnumber;
	private String id;
	private String password;
	private String nickname;
	private String name;
	private String phoneNumber;
	private String address;
	private int authority;
	private Date cdate;
	public Member() {}
	public Member(long clientnumber, String id, String password, String nickname, String name, String phoneNumber, String address, int authority, Date cdate) {
		super();
		this.clientnumber = clientnumber;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.authority = authority;
		this.cdate = cdate;
	}
	public long getClientnumber() {
		return clientnumber;
	}
	public void setClientnumber(long clientnumber) {
		this.clientnumber = clientnumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickname;
	}
	public void setNickName(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String nickname) {
		this.nickname = nickname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	
}
