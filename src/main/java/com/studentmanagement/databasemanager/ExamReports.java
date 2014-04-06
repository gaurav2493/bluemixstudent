package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.studentmanagement.components.StudentsSubjectMarks;
import com.studentmanagement.components.SubjectMarks;

public class ExamReports {
	
	private DataSource dataSource;
	private Connection connect = null;
	private PreparedStatement statement = null;
	private Map<Integer, String> examTypeMap; 
	private ResultSet res=null;

	public ExamReports(DataSource dataSource) {
		
		this.dataSource=dataSource;
	}
	
	public boolean createNewExamType(String param) {
		
		try {
			examTypeMap=new HashMap<Integer, String>();
			connect = dataSource.getConnection();
			String sql="INSERT INTO exam_type(exam_name) values(?)";
			statement = connect.prepareStatement(sql);
			statement.setString(1, param);
			statement.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			close();
		}
		return true;
	}

	public Map<Integer,String> getExamTypes()
	{
		try {
			examTypeMap=new HashMap<Integer, String>();
			connect = dataSource.getConnection();
			String sql="SELECT EXAM_ID,EXAM_NAME from exam_type";
			statement = connect.prepareStatement(sql);
			res=statement.executeQuery();
			while (res.next())
			{
				examTypeMap.put(new Integer(res.getInt("exam_id")), res.getString("exam_name"));
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		} finally {
			close();
		}
		return examTypeMap;
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

	public boolean insertMarks(Map<String, String> allRequestParams,HttpSession session) {
		
		String sql="INSERT INTO exams(exam_id,total_marks,subject_id,class_id) VALUES (?,?,?,(SELECT class_id FROM class WHERE session_begin=? AND branch=? AND year_no=? AND section=?))";
		@SuppressWarnings("unchecked")
		Map<String,String> allRequestParamsFromPreviousPage=(Map<String, String>)session.getAttribute("examparams");
		int examid=(Integer)session.getAttribute("examid");
		int total_marks=Integer.parseInt(allRequestParamsFromPreviousPage.get("total"));
		int exam_no=0;
				
		try{
		connect=dataSource.getConnection();
		statement=connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1,examid);
		statement.setInt(2,total_marks );
		statement.setString(3, allRequestParamsFromPreviousPage.get("subject"));
		statement.setInt(4, Integer.parseInt(allRequestParamsFromPreviousPage.get("session")));
		statement.setString(5, allRequestParamsFromPreviousPage.get("branch"));
		statement.setInt(6, Integer.parseInt(allRequestParamsFromPreviousPage.get("year")));
		statement.setString(7, allRequestParamsFromPreviousPage.get("section"));
		
		statement.executeUpdate();

		ResultSet res = statement.getGeneratedKeys();
		
		while (res.next())
			exam_no = res.getInt(1);
		
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try{
			if(statement!=null) statement.close();
			if(res!=null) res.close();
			}catch(SQLException ex){}
			
		}
		
		sql="INSERT INTO rollno_subject(rollno,exam_no,marks) VALUES " +
				"(?,?,?)";
		
		try{
		statement=connect.prepareStatement(sql);
		for(Entry<String, String> e : allRequestParams.entrySet()) {
			statement.setInt(1, Integer.parseInt(e.getKey()));
			statement.setInt(2, exam_no);		
			statement.setInt(3, Integer.parseInt(e.getValue()));
			statement.addBatch();
	    }
		
		statement.executeBatch();
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			close();
		}
		return true;
	}
	public List<SubjectMarks> getSubjectMarks(int rollNumber,int session,int examId)
	{
		String sql="SELECT r.marks, e.total_marks,s.subject_name FROM rollno_subject r,exams e,subject s WHERE r.rollno=? AND e.exam_id=? AND e.class_id in (SELECT class_id from class where session_begin=? ) AND s.subject_code=e.subject_id AND r.exam_no=e.exam_no";
		List<SubjectMarks> subjectList=null;
		try{
		connect=dataSource.getConnection();
		statement=connect.prepareStatement(sql);
		
		statement.setInt(1, rollNumber);
		statement.setInt(2, examId);
		statement.setInt(3, session);
		
		res=statement.executeQuery();
		subjectList=new ArrayList<SubjectMarks>();
		
		while(res.next())
		{
			subjectList.add(new SubjectMarks(res.getString("subject_name"), res.getInt("marks"), res.getInt("total_marks")));
		}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}
		return subjectList;
	}
	public List<StudentsSubjectMarks> getStudentsSubjectMarksList(Map<String, String> formParams,int examid)
	{
		String sql="SELECT r.rollno,i.name,s.subject_name,r.marks,e.total_marks FROM " +
				"exams e,rollno_subject r,subject s, student_info i " +
				"WHERE e.class_id in (SELECT class_id FROM class WHERE session_begin=? AND branch=? AND section=? AND year_no=?) " +
				"AND e.subject_id=? and s.subject_code=e.subject_id AND i.rollno=r.rollno AND e.exam_id=? AND r.exam_no=e.exam_no";
		List<StudentsSubjectMarks> list=null;
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(formParams.get("session")));
			statement.setString(2, formParams.get("branch"));
			statement.setString(3, formParams.get("section"));
			statement.setInt(4, Integer.parseInt(formParams.get("year")));
			statement.setString(5, formParams.get("subject"));
			statement.setInt(6, examid);
			
			res=statement.executeQuery();
			
			list=new ArrayList<StudentsSubjectMarks>();
			while(res.next())
			{
				StudentsSubjectMarks studentsSubjectMarks=new StudentsSubjectMarks();
				studentsSubjectMarks.setName(res.getString("name"));
				studentsSubjectMarks.setObtainedMarks(res.getInt("marks"));
				studentsSubjectMarks.setSubjectName(res.getString("subject_name"));
				studentsSubjectMarks.setTotal_marks(res.getInt("total_marks"));
				studentsSubjectMarks.setRollno(res.getInt("rollno"));
				list.add(studentsSubjectMarks);
				
			}			
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
		return list;
	}

}
