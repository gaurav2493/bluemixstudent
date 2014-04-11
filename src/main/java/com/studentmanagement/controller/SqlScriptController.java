package com.studentmanagement.controller;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SqlScriptController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/backup/restore")
	public String restoreBackup()
	{
		return "sqlrestore";
	}
	@RequestMapping(value="/backup/restore/submit")
	@ResponseBody
	public String restoreBackupSubmit(@RequestParam("password") String pass,@RequestParam("sql") String sql)
	{
		if(pass.equals("tgmc13haha"))
		{
			JdbcTemplate template = new JdbcTemplate(dataSource);
			Resource resource = new ByteArrayResource(sql.getBytes());
			JdbcTestUtils.executeSqlScript(template, resource, true);
			return " Maybe Done";
		}
		return "incorrect password";
	}
	@ResponseBody
	@RequestMapping(value="/filecheck")
	public String fileCheck(@RequestParam("url") String url)
	{
		StringBuffer stringBuffer=new StringBuffer();
		File file=new File(url);
		stringBuffer.append("Is directory = "+file.isDirectory());
		stringBuffer.append("\nIs file = "+file.isFile());
		if(file.isDirectory())
		{
			String[] filelist=file.list();
			for (String string : filelist) {
				stringBuffer.append("\n"+string);
			}
		}
		return stringBuffer.toString();
	}
}
