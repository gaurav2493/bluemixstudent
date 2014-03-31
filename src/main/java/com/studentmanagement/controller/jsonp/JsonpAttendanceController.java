package com.studentmanagement.controller.jsonp;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.components.SubjectAttendance;
import com.studentmanagement.databasemanager.AttendanceManager;

@Controller
public class JsonpAttendanceController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/jsonp/getjsonattendance")
	public String getXMLAttendance(ModelMap model,@RequestParam("session") int session,@RequestParam("callback") String callback)
	{
		AttendanceManager attendanceManager=new AttendanceManager(dataSource);
		List<SubjectAttendance> subjectAttendanceList= attendanceManager.getAllSubjectAttendance(session);
		model.addAttribute("subjectAttendanceList", subjectAttendanceList);
		model.addAttribute("callback", callback);
		return "getjsonattendance";
	}

}
