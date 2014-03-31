package com.studentmanagement.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.sql.DataSource;

import com.studentmanagement.components.File;

public class FileManager {

	private DataSource dataSource;
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet res;

	public FileManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void makeDatabaseFileEntry(Map<String, Long> fileNames, int noticeId) {
		String sql = "INSERT INTO files(NOTICE_ID,SEQUENCE_NO,FILE_NAME,TIMESTAMP) VALUES(?,?,?,?)";
		try {
			connect = dataSource.getConnection();
			statement = connect.prepareStatement(sql);
			int seq=1;

			for (Map.Entry<String, Long> entry : fileNames.entrySet()) {
				statement.setInt(1, noticeId);
				statement.setInt(2, seq++);
				statement.setString(3, entry.getKey());
				statement.setLong(4, entry.getValue());
				
				statement.addBatch();
			}
			statement.executeBatch();

		} catch (Exception e) {
			
			//e.getNe
			
		} finally {
			close();
		}
	}
	
	public File downloadFile(int noticeId,int seqNo)
	{
		String sql="SELECT FILE_NAME,TIMESTAMP FROM files WHERE NOTICE_ID=? AND SEQUENCE_NO=?";
		long timeStamp=0;
		String fileName="";
		File file=null;
		try{
			connect=dataSource.getConnection();
			statement=connect.prepareStatement(sql);
			statement.setInt(1, noticeId);
			statement.setInt(2, seqNo);
			res=statement.executeQuery();
			
			if(res.next())
			{
				timeStamp=res.getLong("timestamp");
				fileName=res.getString("file_name");
			}      
	        file=new File();
	        file.setFileName(fileName);
	        file.setHeaderKey("Content-Disposition");
	        file.setHeaderValue(String.format("attachment; filename=\"%s\"",fileName));
	        file.setSavedFileName(timeStamp);
	        
			
		}catch(Exception ex){
			
		}
		finally{
			close();
		}
		 return file;
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
