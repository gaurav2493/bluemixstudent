package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


public class SubjectsChooser {
	
	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet resultSet;
	private Map<String, String> subjectMap;
	
	public SubjectsChooser(DataSource dataSource)
	{
		this.dataSource=dataSource;
	}
	
	public Map<String,String> getAllSubjects()
	{
		String sql="SELECT subject_code,subject_name " +
				"FROM subject";
		try {
	    	connect=dataSource.getConnection();
	    	statement =connect.prepareStatement(sql);
	    	resultSet=statement.executeQuery();
	    	subjectMap=new HashMap<String, String>();
	    	while(resultSet.next())
	    	{
	    		subjectMap.put(resultSet.getString("subject_code"), resultSet.getString("subject_name"));
	    	}
	         
	    } catch (Exception e) {
	      
	    } finally {
	      close();
	    }
		return subjectMap;
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
