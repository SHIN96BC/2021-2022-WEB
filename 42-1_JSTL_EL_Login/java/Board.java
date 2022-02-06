package jstl.board.domain;

import java.sql.Date;

public class Board {
	private long postNumber;
	private String writerNickName;
	private String writerId;
	private String postSubject;
	private String postContent;
	private long views;
	private Date pdate;
	public Board() {}
	public Board(long postNumber, String writerNickName, String writerId, String postSubject, String postContent, long views, Date pdate) {
		this.postNumber = postNumber;
		this.writerNickName = writerNickName;
		this.writerId = writerId;
		this.postSubject = postSubject;
		this.postContent = postContent;
		this.views = views;
		this.pdate = pdate;
	}
	public long getPostnumber() {
		return postNumber;
	}
	public void setPostnumber(long postNumber) {
		this.postNumber = postNumber;
	}
	public String getWriternickname() {
		return writerNickName;
	}
	public void setWriternickname(String writerNickName) {
		this.writerNickName = writerNickName;
	}
	public String getWriterid() {
		return writerId;
	}
	public void setWriterid(String writerId) {
		this.writerId = writerId;
	}
	public String getPostsubject() {
		return postSubject;
	}
	public void setPostsubject(String postSubject) {
		this.postSubject = postSubject;
	}
	public String getPostcontent() {
		return postContent;
	}
	public void setPostcontent(String postContent) {
		this.postContent = postContent;
	}
	public long getViews() {
		return views;
	}
	public void setViews(long views) {
		this.views = views;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	
}
