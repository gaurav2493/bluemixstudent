package com.studentmanagement.components;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
	
	private List<MultipartFile> files;
	private String subject;
	private String content;
	

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

}
