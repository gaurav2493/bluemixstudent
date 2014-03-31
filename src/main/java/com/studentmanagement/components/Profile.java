package com.studentmanagement.components;

public class Profile{
	
	private String name;
	private int rollNo;
	private long phonNo;
	private String photoUrl;
	private String fatherName;
	private String motherName;
	private String email;
	private String parentEmail;
	private String branch;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	public void setPhonNo(long phonNo) {
		this.phonNo = phonNo;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getName() {
		return name;
	}
	public int getRollNo() {
		return rollNo;
	}
	public long getPhonNo() {
		return phonNo;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public String getFatherName() {
		return fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public String getEmail() {
		return email;
	}
	public String getParentEmail() {
		return parentEmail;
	}
	public String getBranch() {
		return branch;
	}

}

