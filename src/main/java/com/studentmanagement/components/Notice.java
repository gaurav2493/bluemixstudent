package com.studentmanagement.components;

import java.util.Date;
import java.util.Map;

public class Notice {

	private int notice_id;
	private String subject;
	private String notice;
	private String author;
	private Date date;
	private boolean attachment;
	private Map<String, Integer> attachmentMap;
	
	public int getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isAttachment() {
		return attachment;
	}
	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}
	public Map<String, Integer> getAttachmentMap() {
		return attachmentMap;
	}
	public void setAttachmentMap(Map<String, Integer> attachmentMap) {
		this.attachmentMap = attachmentMap;
	}	
}
