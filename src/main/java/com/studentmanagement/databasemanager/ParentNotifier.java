package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

public class ParentNotifier implements Runnable {
	
	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet res;
	

	
	public ParentNotifier(DataSource dataSource)
	{
		this.dataSource=dataSource;
	}

	@Override
	public void run() {
		
		
	}

	public void notifyAllParentsAboutMarksUploaded(String subject,
			Map<String, String> allRequestParams) {
		int i=1;
		
		try{
			connect=dataSource.getConnection();
			StringBuffer sql=new StringBuffer("SELECT parent_email,name FROM student_info WHERE ");
			for(Entry<String, String> e : allRequestParams.entrySet()) {
				sql.append("rollno=? OR ");
		    }
			sql.append(true);
			System.out.println(sql);
			statement=connect.prepareStatement(sql.toString());
			for(Entry<String, String> e : allRequestParams.entrySet()) {
				System.out.println(Integer.parseInt(e.getKey()));
				statement.setInt(i++, Integer.parseInt(e.getKey()));
		    }
			res=statement.executeQuery();
			
			while(res.next())
			{
				System.out.println(res.getString("name"));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}finally{
			close();
		}
		
	}
	private void close() {
		try {
			if (res != null) {
				res.close();
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
