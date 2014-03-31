package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.studentmanagement.components.Notice;

public class NoticeManager {

	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet res;

	public NoticeManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int addNotice(String content, String author, String subject,
			boolean attachment) {
		String sql = "INSERT INTO notice(NOTICE,AUTHOR,TIMESTAMP,SUBJECT,ATTACHMENT) VALUES "
				+ "(?,?,?,?,?)";
		int noticeId = -1;
		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, content);
			statement.setString(2, author);
			statement.setDate(3, new Date(new java.util.Date().getTime()));
			statement.setString(4, subject);
			statement.setBoolean(5, attachment);

			statement.executeUpdate();

			res = statement.getGeneratedKeys();

			if (res.next())
				noticeId = res.getInt(1);

		} catch (SQLException ex) {
			System.out.println(ex);
			return noticeId;
		} finally {
			close();
		}
		return noticeId;
	}

	public List<Notice> getNoticeList(int begin, int end) {
		String sql = "SELECT NOTICE_ID,AUTHOR,TIMESTAMP,SUBJECT,ATTACHMENT FROM notice ORDER BY notice_id DESC LIMIT ?,?";
		List<Notice> noticeList = new ArrayList<Notice>();
		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement(sql);

			statement.setInt(1, begin);
			statement.setInt(2, end);

			res = statement.executeQuery();

			while (res.next()) {
				Notice notice = new Notice();
				notice.setAuthor(res.getString("author"));
				notice.setDate(res.getDate("timestamp"));
				notice.setNotice_id(res.getInt("notice_id"));
				notice.setSubject(res.getString("subject"));
				notice.setAttachment(res.getBoolean("attachment"));

				noticeList.add(notice);
			}

		} catch (SQLException ex) {
			System.out.println(ex);
			// return false;
		} finally {
			close();
		}
		return noticeList;
	}

	public Notice viewNotice(int notice_id) {
		Notice notice = new Notice();
		String sql = "SELECT NOTICE,AUTHOR,TIMESTAMP,SUBJECT,ATTACHMENT FROM notice WHERE notice_id=?";
		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement(sql);

			statement.setInt(1, notice_id);

			res = statement.executeQuery();
			res.next();
			notice.setAuthor(res.getString("author"));
			notice.setDate(res.getDate("timestamp"));
			notice.setNotice_id(notice_id);
			notice.setSubject(res.getString("subject"));
			notice.setNotice(res.getString("notice"));
			notice.setAttachment(res.getBoolean("attachment"));

			if (res.getBoolean("attachment")) {
				if (statement != null)
					statement.close();
				if (res != null)
					res.close();

				sql = "SELECT file_name,sequence_no FROM files WHERE notice_id=?";
				statement = connect.prepareStatement(sql);
				statement.setInt(1, notice_id);
				Map<String, Integer> attachmentMap =new HashMap<String, Integer>();
				res=statement.executeQuery();
				while(res.next())
				{
					attachmentMap.put(res.getString("file_name"), res.getInt("sequence_no"));
				}
				notice.setAttachmentMap(attachmentMap);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return notice;
	}
	public int deleteNotice(int noticeId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    String sql="DELETE FROM notice WHERE notice_id=? AND author=?";
	    try{
	    	connect=dataSource.getConnection();
	    	statement=connect.prepareStatement(sql);
	    	statement.setInt(1, noticeId);
	    	statement.setString(2, name);
	    	
	    	return statement.executeUpdate();
	    	
	    }catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }finally{
	    		close();
	    		}
	    return 0;
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
