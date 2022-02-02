package bcm.board.domain;

import java.sql.Date;

public class Board {
	private long postNumber;
	private String writerNickName;
	private String writerId;
	private String postSubject;
	private String postContent;
	private int authority;
	private Date pdate;
	public Board() {}
	public Board(long postNumber, String writerNickName, String writerId, String postSubject, String postContent, int authority, Date pdate) {
		this.postNumber = postNumber;
		this.writerNickName = writerNickName;
		this.writerId = writerId;
		this.postSubject = postSubject;
		this.postContent = postContent;
		this.authority = authority;
		this.pdate = pdate;
	}
	public long getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(long postNumber) {
		this.postNumber = postNumber;
	}
	public String getWriterNickName() {
		return writerNickName;
	}
	public void setWriterNickName(String writerNickName) {
		this.writerNickName = writerNickName;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getPostSubject() {
		return postSubject;
	}
	public void setPostSubject(String postSubject) {
		this.postSubject = postSubject;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	
}
