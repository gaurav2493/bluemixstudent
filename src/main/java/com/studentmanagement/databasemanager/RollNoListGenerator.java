package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class RollNoListGenerator {
	
	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet res;
	
	public RollNoListGenerator(DataSource dataSource)
	{
		this.dataSource=dataSource;
	}
	
	public List<String> getRollNoList(String start,int count)
	{
		String sql="SELECT username FROM users WHERE username like ? LIMIT ?";
		List<String> list=null;
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setString(1, start+"%");
			statement.setInt(2, count);
			
			res=statement.executeQuery();
			
			list=new ArrayList<String>();
			while(res.next())
			{
				list.add(res.getString("username"));
			}		
			
		}catch(Exception ex)
		{
			
		}finally{
			close();
		}
		return list;
	}
	public boolean validateRollNo(int rollno)
	{
		String sql="SELECT rollno FROM student_info WHERE rollno=?";
		
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setInt(1, rollno);
					
			res=statement.executeQuery();
			
			if(res.next())
				return true;
					
			
		}catch(Exception ex)
		{
			return false;
		}finally{
			close();
		}
		return false;
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
