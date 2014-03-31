package com.studentmanagement.controller;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.components.RollList;
import com.studentmanagement.databasemanager.AttendanceSubmitter;
import com.studentmanagement.databasemanager.BranchManager;
import com.studentmanagement.databasemanager.StudentListGenerator;
import com.studentmanagement.databasemanager.SubjectsChooser;

@Controller
public class AttendanceController {
	
	@Autowired
	DataSource dataSource;
	
	
	@RequestMapping(value="/addattendance",method= RequestMethod.POST)
	public String addAttendance(HttpServletRequest request,@RequestParam Map<String,String> allRequestParams, ModelMap model)
	{
		StudentListGenerator studentListGenerator=new StudentListGenerator(dataSource, allRequestParams);
		RollList rollList=studentListGenerator.getAttendanceList();
		model.addAttribute("rollList",rollList);
		if(rollList==null)
		{
			model.addAttribute("message", "The class you are looking for is not found.<br/> If you are sure this is your class, create a new class from left sidebar");
			return "somethingwentwrong";
		}
			
		request.getSession().setAttribute("allReqParamsForAddAttendance",allRequestParams);
		return "addattendance";
	}
	@RequestMapping(value="/submitattendance",method= RequestMethod.POST)
	public String submitAttendance(HttpServletRequest request,@RequestParam Map<String,String> allRequestParams, ModelMap model)
	{
		@SuppressWarnings("unchecked")
		Map<String,String> attribute = (Map<String,String>)request.getSession().getAttribute("allReqParamsForAddAttendance");
		AttendanceSubmitter attendanceSubmitter=new AttendanceSubmitter(dataSource,allRequestParams,attribute);
		attendanceSubmitter.addToAttendance();
		return "submitattendance";
	}
	@RequestMapping(value="/selectattendance")
	public String selectAttendence(ModelMap model)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, String> subjectMap=new SubjectsChooser(dataSource).getAllSubjects();
		BranchManager branchManager=new BranchManager(dataSource);
		Map<String,String> branchMap = branchManager.getSubjects();
		model.addAttribute("branchmap",branchMap);
		model.addAttribute("currentYear", new Integer(year));
		model.addAttribute("subjectsMap", subjectMap);
		return "selectattendance";
	}
	
}
