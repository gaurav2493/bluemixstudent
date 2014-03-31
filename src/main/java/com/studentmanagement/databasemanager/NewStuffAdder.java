package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class NewStuffAdder {

	private Connection connect = null;
	private PreparedStatement statement = null;
	private DataSource dataSource;
	private Map<String, String> map = null;
	private int id;

	public NewStuffAdder(DataSource dataSource, Map<String, String> map) {

		this.dataSource = dataSource;
		this.map = map;

	}

	public NewStuffAdder(DataSource dataSource) {
		this.dataSource=dataSource;
	}

	public boolean addStudent() {
		try {
			connect = dataSource.getConnection();
			statement = connect
					.prepareStatement("INSERT INTO student_info"
							+ "(NAME,FATHER_NAME,MOTHER_NAME,EMAIL,PARENT_EMAIL,BRANCH,ROLLNO) "
							+ "VALUES(?,?,?,?,?,?,?)");

			statement.setString(1, map.get("name"));
			statement.setString(2, map.get("fname"));
			statement.setString(3, map.get("mname"));
			statement.setString(4, map.get("email"));
			statement.setString(5, map.get("pemail"));
			statement.setString(6, map.get("branch"));
			statement.setInt(7, Integer.parseInt(map.get("rollno")));

			statement.executeUpdate();
			
			if(statement!=null) statement.close();
			String sql="INSERT INTO users(username,password,authority,enabled) VALUES (?,?,?,?)";
			MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
			String password = encoder.encodePassword(map.get("rollno"), "");
			statement=connect.prepareStatement(sql);
			statement.setString(1, map.get("rollno"));
			statement.setString(2, password);
			statement.setString(3, "ROLE_USER");
			statement.setBoolean(4, true);
			
			statement.executeUpdate();

		} catch (Exception e) {

			return false;
		} finally {
			close();
		}
		return true;
	}

	public synchronized boolean createNewClass() {
		addClass();
		addRollnumbersAndAttendance();
		return true;
	}

	public boolean addSubject() {

		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement("INSERT INTO subject"
					+ "(SUBJECT_CODE,SUBJECT_NAME) " + "VALUES(?,?)");

			statement.setString(1, map.get("code"));
			statement.setString(2, map.get("name"));

			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {
			close();
		}
		return true;
	}

	private boolean addClass() {
		try {
			connect = dataSource.getConnection();
			PreparedStatement statement = connect.prepareStatement(
					"INSERT INTO class"
							+ "(SESSION_BEGIN,YEAR_NO,BRANCH,SECTION) "
							+ "VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, Integer.parseInt(map.get("session")));
			statement.setInt(2, Integer.parseInt(map.get("year")));
			statement.setString(3, map.get("branch"));
			statement.setString(4, map.get("section"));

			statement.executeUpdate();

			ResultSet res = statement.getGeneratedKeys();
			while (res.next())
				id = res.getInt(1);
			res.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {
			close();
		}
		return true;
	}

	private boolean addRollnumbersAndAttendance() {
		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement("INSERT INTO attendence"
					+ "(ROLLNO,COUNT,CLASS_ID,SUBJECT_ID,YEAR_NO) "
					+ "VALUES(?,?,?,?,?)");

			int noStudents = Integer.parseInt(map.get("nostudents"));
			int noSubjects = Integer.parseInt(map.get("nosubjects"));
			
			for (int j = 0; j < noSubjects; j++) {
				for (int i = 0; i < noStudents; i++) {
					statement.setInt(1, Integer.parseInt(map.get("stu" + i)));
					statement.setInt(2, 0);
					statement.setInt(3, id);
					statement.setString(4, map.get("sub" + j));
					statement.setInt(5, Integer.parseInt(map.get("year")));
					statement.addBatch();
				}
			}
			statement.executeBatch();
		} catch (SQLException e) {
			Exception ex;
			 while((ex=e.getNextException()) != null) {
				 System.out.println(ex);
			   }
			
			return false;
		} finally {
			close();
		}
		
		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement("INSERT INTO class_attendence"
					+ "(CLASS_ID,TOTAL_COUNT,SUBJECT_ID) "
					+ "VALUES(?,?,?)");

			int noSubjects = Integer.parseInt(map.get("nosubjects"));
			
			for (int j = 0; j < noSubjects; j++) {
				
					statement.setInt(1, id);
					statement.setInt(2, 0);
					statement.setString(3, map.get(("sub" + j)));
					statement.addBatch();
			}

			statement.executeBatch();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {
			close();
		}
		return true;
	}
	public void addUser(String username,String password,String authority)
	{
		try{
		connect=dataSource.getConnection();
		String sql="INSERT INTO users(username,password,authority,enabled) VALUES (?,?,?,?)";
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
		String hash = encoder.encodePassword(password, "");
		statement=connect.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, hash);
		statement.setString(3, authority);
		statement.setBoolean(4, true);
		
		statement.executeUpdate();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally{
			close();
		}
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
