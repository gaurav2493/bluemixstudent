package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.studentmanagement.components.Profile;

public class ProfileManager{
	
	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet res;
	
	public ProfileManager(DataSource dataSource)
	{
		this.dataSource=dataSource;
	}
	
	public int changePassword(String oldpass,String newpass)
	{
		String sql="UPDATE users set password=? WHERE username=? AND password=?";
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
		String oldhash = encoder.encodePassword(oldpass, "");
		String newhash=encoder.encodePassword(newpass, "");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    int ret=0;
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setString(1, newhash);
			statement.setString(2, name);
			statement.setString(3, oldhash);
			ret=statement.executeUpdate();
						
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		finally{
			close();
		}
		return ret;
	}
	
	public Profile getProfile(int rollNo)
	{
		String sql="SELECT NAME,FATHER_NAME,MOTHER_NAME,EMAIL,PARENT_EMAIL,COURSE,BRANCH,ROLLNO,PHONENO,PHOTOURL " +
				"FROM student_info WHERE rollno=?";
		Profile profile=null;
		try {
			
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setInt(1, rollNo);
			res=statement.executeQuery();
			
			if(res.next())
			{
				profile=new Profile();
				profile.setRollNo(rollNo);
				profile.setBranch(res.getString("branch"));
				profile.setEmail(res.getString("email"));
				profile.setFatherName(res.getString("father_name"));
				profile.setMotherName(res.getString("mother_name"));
				profile.setParentEmail(res.getString("parent_email"));
				profile.setPhonNo(res.getLong("phoneno"));
				profile.setPhotoUrl(res.getString("photourl"));
				
			}			
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		} finally {
			close();
		}
		return profile;
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
