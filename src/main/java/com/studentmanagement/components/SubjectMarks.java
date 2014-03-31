package com.studentmanagement.components;

public class SubjectMarks {

	private String subjectName;
	private int marks;
	private int total;
	
	public SubjectMarks(String subjectName,int marks,int total)
	{
		this.subjectName=subjectName;
		this.marks=marks;
		this.total=total;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	
	public int getMarks() {
		return marks;
	}
	public int getTotal() {
		return total;
	}
	
	
}
