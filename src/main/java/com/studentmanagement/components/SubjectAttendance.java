package com.studentmanagement.components;

public class SubjectAttendance {
	
	private String subjectName;
	private int count;
	private int total;
	
	public SubjectAttendance(String subjectName,int count,int total)
	{
		this.subjectName=subjectName;
		this.count=count;
		this.total=total;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public int getCount() {
		return count;
	}

	public int getTotal() {
		return total;
	}
	

}
