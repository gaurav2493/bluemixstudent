package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

public class BranchManager {
	
	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet res;
	
	public BranchManager(DataSource dataSource)
	{
		this.dataSource=dataSource;
	}
	
	public Map<String, String> getSubjects()
	{
		String sql="SELECT BRANCH_CODE,BRANCH_NAME FROM branch ORDER BY BRANCH_NAME";
		Map<String, String> branchMap=null;
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			res=statement.executeQuery();
			branchMap=new TreeMap<String,String>();
			while(res.next())
			{
				branchMap.put(res.getString("branch_code"), res.getString("branch_name"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			close();
		}
		return branchMap;
	}
	public boolean addBranch(String branch_code,String branch_name)
	{
		String sql="INSERT INTO branch(BRANCH_CODE,BRANCH_NAME) VALUES(?,?)";
		
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setString(1, branch_code);
			statement.setString(2, branch_name);
			statement.executeUpdate();
			
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			close();
		}
		return true;
	}
	private void close() {
		try {
			if(res!=null){
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
