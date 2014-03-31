package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import com.studentmanagement.components.RollList;
import com.studentmanagement.components.Student;

public class StudentListGenerator {

	private DataSource dataSource;
	private Map<String,String> parametersMap;
	private Connection connect = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet;
	private RollList rollList;
	private List<Student> studentList;
	
	public StudentListGenerator(DataSource dataSource,Map<String, String> map) {

		this.dataSource=dataSource;
		this.parametersMap=map;
	}
	
	public RollList getAttendanceList()
	{
		try {
	    	connect=dataSource.getConnection();
	        statement = connect
	        .prepareStatement("SELECT a.COUNT,a.ROLLNO,s.NAME " +
	        		"FROM attendence a,student_info s WHERE" +
	        		" a.subject_id=? and a.class_id=(select class_id from class where session_begin=? and year_no=? and branch=? and section=?) and a.rollno=s.rollno " +
	        		"ORDER BY a.ROLLNO");
	        
	        statement.setString(1, parametersMap.get("subject"));
	        statement.setInt(2, Integer.parseInt(parametersMap.get("session")));
	        statement.setInt(3, Integer.parseInt(parametersMap.get("year")));
	        statement.setString(4, parametersMap.get("branch"));
	        statement.setString(5, parametersMap.get("section"));
	        
	        resultSet=statement.executeQuery();
	        studentList=new ArrayList<Student>();
	        Student student;
	        while (resultSet.next())
	        {
	        	student=new Student(resultSet.getInt("rollno"),resultSet.getInt("count"),resultSet.getString("name"));
	        	studentList.add(student);
			}
	      
	    } catch (Exception e) {
	      
	    } finally {
	      close();
	    }
				
		try {
	    	connect=dataSource.getConnection();
	        statement = connect
	        .prepareStatement("SELECT TOTAL_COUNT " +
	        		"FROM class_attendence WHERE" +
	        		" subject_id=? and class_id=(select class_id from class where session_begin=? and year_no=? and branch=? and section=?)");
	        
	        statement.setString(1, parametersMap.get("subject"));
	        statement.setInt(2, Integer.parseInt(parametersMap.get("session")));
	        statement.setInt(3, Integer.parseInt(parametersMap.get("year")));
	        statement.setString(4, parametersMap.get("branch"));
	        statement.setString(5, parametersMap.get("section"));
	        
	        resultSet=statement.executeQuery();
	        resultSet.next();
	        int totalCount=resultSet.getInt("total_count");
	        rollList=new RollList(studentList, totalCount);
	      
	    } catch (Exception e) {
	      
	    } finally {
	      close();
	    }
		return rollList;
	}
	
	public Map<Integer,String> getStudentList()
	{
		String sql="SELECT a.ROLLNO,s.NAME " +
        		"FROM attendence a,student_info s WHERE" +
        		" a.subject_id=? and a.class_id=(select class_id from class where session_begin=? and year_no=? and branch=? and section=?) and a.rollno=s.rollno " +
        		"ORDER BY a.ROLLNO";
		Map<Integer,String> studentMap=null;
		try{
		connect=dataSource.getConnection();
		statement = connect.prepareStatement(sql);
		
		statement.setString(1, parametersMap.get("subject"));
        statement.setInt(2, Integer.parseInt(parametersMap.get("session")));
        statement.setInt(3, Integer.parseInt(parametersMap.get("year")));
        statement.setString(4, parametersMap.get("branch"));
        statement.setString(5, parametersMap.get("section"));
        
        resultSet=statement.executeQuery();
        studentMap=new TreeMap<Integer,String>();
        while(resultSet.next())
        {
        	studentMap.put(new Integer(resultSet.getInt("rollno")), resultSet.getString("name"));
        }
        
		} 
		catch (Exception e) {
		      
		    } finally {
		      close();
		    }
		
		return studentMap;
	}
	
	private void close() {
	    try {
	    	 if(resultSet!=null){
	    		 resultSet.close();
	    	 }
	      if (statement != null) {
	        statement.close();
	      }
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	}
}
