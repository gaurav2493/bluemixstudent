package com.studentmanagement.controller.jsonp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.components.SubjectMarks;
import com.studentmanagement.databasemanager.ExamReports;

@Controller
public class JsonpAcademicController {

	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/jsonp/getexamtypes")
	public String getExamList(ModelMap model,@RequestParam("callback") String callback)
	{
		ExamReports examReports=new ExamReports(dataSource);
		Map<Integer, String> examTypeMap = examReports.getExamTypes();
		model.addAttribute("examTypes", examTypeMap);
		model.addAttribute("callback", callback);
		return "jsonexamlist";
	}
	
	@RequestMapping(value="/jsonp/getmarks")
	public String getMarks(@RequestParam(value="exam_id") int examId,@RequestParam(value="session") int session,@RequestParam("callback") String callback,ModelMap model,HttpServletRequest request)
	{
		ExamReports examReports=new ExamReports(dataSource);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<SubjectMarks> marksList = examReports.getSubjectMarks(Integer.parseInt(auth.getName()), session, examId);
		model.addAttribute("marksList", marksList);
		model.addAttribute("callback", callback);
		return "jsonmarks";
	}
}
