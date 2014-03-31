package com.studentmanagement.components;

public class Student {
	
	private int rollNumber;
	private String name;
	private int attendanceCount;
	
	public Student(int rollNumber, int attendanceCount, String name) {

		this.rollNumber=rollNumber;
		this.attendanceCount=attendanceCount;
		this.name=name;
	}
	public int getRollNumber() {
		return rollNumber;
	}
	public String getName() {
		return name;
	}
	public int getAttendanceCount() {
		return attendanceCount;
	}
}
