package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

public class AttendanceSubmitter {
	
	private DataSource dataSource;
	private Map<String,String> allRequestParams;
	private Connection connect = null;
	private PreparedStatement statement = null;
	private Map<String,String> allRequestParamsFromPreviousPage;
	
	public AttendanceSubmitter(DataSource dataSource,Map<String, String> allRequestParams, Map<String, String> attribute) {
		
		this.dataSource=dataSource;
		this.allRequestParams=allRequestParams;
		this.allRequestParamsFromPreviousPage=attribute;		
	}	

	public boolean addToAttendance()
	{
		try {
			connect = dataSource.getConnection();
			StringBuffer rollNumberString=new StringBuffer();
			for(int i=0;i<allRequestParams.size();i++) {
		        rollNumberString.append(" rollno=? OR");
		    }
			
			String sql = "UPDATE " +
	        		"attendence SET count=count+1 WHERE 	" +
	        		"subject_id=? AND year_no=? AND class_id=(select class_id from class where session_begin=? and year_no=? and branch=? and section=?) AND (";
			statement = connect.prepareStatement(sql+rollNumberString.toString()+" false)");
			
			statement.setString(1, allRequestParamsFromPreviousPage.get("subject"));
			statement.setInt(2, Integer.parseInt(allRequestParamsFromPreviousPage.get("year")));
			statement.setInt(3, Integer.parseInt(allRequestParamsFromPreviousPage.get("session")));
			statement.setInt(4, Integer.parseInt(allRequestParamsFromPreviousPage.get("year")));
			statement.setString(5, allRequestParamsFromPreviousPage.get("branch"));
	        statement.setString(6, allRequestParamsFromPreviousPage.get("section"));
	        
	        int i=7;
	        
			for(Entry<String, String> e : allRequestParams.entrySet()) {
				statement.setInt(i++, Integer.parseInt(e.getKey()));
		    }
			statement.executeUpdate();
			
			sql="UPDATE " +
	        		"class_attendence SET total_count=total_count+1 WHERE 	" +
	        		"subject_id=? AND class_id=(select class_id from class where session_begin=? and year_no=? and branch=? and section=?)";
			statement = connect.prepareStatement(sql);
			statement.setString(1, allRequestParamsFromPreviousPage.get("subject"));
			statement.setInt(2, Integer.parseInt(allRequestParamsFromPreviousPage.get("session")));
			statement.setInt(3, Integer.parseInt(allRequestParamsFromPreviousPage.get("year")));
			statement.setString(4, allRequestParamsFromPreviousPage.get("branch"));
	        statement.setString(5, allRequestParamsFromPreviousPage.get("section"));
	        
	        statement.executeUpdate();
			
		} catch (Exception e) {
			System.out.print(e);
			
			return false;
		} finally {
			close();
		}
		return true;
	}
	private void close() {
		try {

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
