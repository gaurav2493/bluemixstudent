package com.studentmanagement.components;

import java.util.List;

public class RollList {
	
	private List<Student> studentList;
	private int totalCount;
	
	

	public RollList(List<Student> studentList, int totalCount) {
		
		this.studentList=studentList;
		this.totalCount=totalCount;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public int getTotalCount() {
		return totalCount;
	}	

}
