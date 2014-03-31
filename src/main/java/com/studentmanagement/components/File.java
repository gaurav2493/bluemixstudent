package com.studentmanagement.components;

public class File {
	
	private String fileName;
	private int length;
	private String headerKey;
    private String headerValue;
    private Long savedFileName;
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getHeaderKey() {
		return headerKey;
	}
	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}
	public String getHeaderValue() {
		return headerValue;
	}
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	public Long getSavedFileName() {
		return savedFileName;
	}
	public void setSavedFileName(Long savedFileName) {
		this.savedFileName = savedFileName;
	}	

}
