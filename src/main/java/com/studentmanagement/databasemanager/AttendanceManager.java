package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.studentmanagement.components.SubjectAttendance;

public class AttendanceManager {
	
	private DataSource dataSource;
	private List<SubjectAttendance> subjectAttendanceList;
	private ResultSet res;
	private Connection connect;
	private PreparedStatement statement;
	
	public AttendanceManager(DataSource dataSource)
	{
		this.dataSource=dataSource;
	}
	public List<SubjectAttendance> getAllSubjectAttendance(int session)
	{
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String name = auth.getName();
			String sql="SELECT s.subject_name, a.count,c.total_count,a.year_no " +
					"FROM attendence a, subject s,class_attendence c " +
					"WHERE rollno=? AND s.subject_code=a.subject_id AND c.class_id=a.class_id and s.subject_code=c.subject_id AND a.class_id in (SELECT class_id from class where session_begin=?)";
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			
			statement.setInt(1, Integer.parseInt(name));
			statement.setInt(2, session);
			res=statement.executeQuery();
			subjectAttendanceList=new ArrayList<SubjectAttendance>();
			
			while(res.next())
			{	
				subjectAttendanceList.add(new SubjectAttendance(res.getString("subject_name"), res.getInt("count"), res.getInt("total_count")));
			}
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		} finally {
			close();
		}
		return subjectAttendanceList;
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
