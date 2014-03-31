package com.studentmanagement.controller;

import java.util.Calendar;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.databasemanager.BranchManager;
import com.studentmanagement.databasemanager.NewStuffAdder;
import com.studentmanagement.databasemanager.SubjectsChooser;

@Controller
public class CommonDataEntryController {
	
	@Autowired
	DataSource dataSource;
	
	@RequestMapping(value="/submit_new_student",method= RequestMethod.POST)
	public String addStudentParams(@RequestParam Map<String,String> allRequestParams)
	{
		NewStuffAdder newUserAdder=new NewStuffAdder(dataSource, allRequestParams);
		newUserAdder.addStudent();
		return "submitted";
	}
	@RequestMapping(value="/submit_new_class",method= RequestMethod.POST)
	public String addClassParams(@RequestParam Map<String,String> allRequestParams)
	{
		NewStuffAdder newStuffAdder=new NewStuffAdder(dataSource, allRequestParams);
		newStuffAdder.createNewClass();
		return "submitted";
	}
	@RequestMapping(value="/submit_new_subject",method= RequestMethod.POST)
	public String addSubjectParams(@RequestParam Map<String,String> allRequestParams)
	{
		NewStuffAdder newStuffAdder=new NewStuffAdder(dataSource, allRequestParams);
		newStuffAdder.addSubject();
		return "submitted";
	}
	@RequestMapping(value="/addstudent")
	public String addStudent(ModelMap model)
	{
		BranchManager branchManager=new BranchManager(dataSource);
		Map<String,String> branchMap = branchManager.getSubjects();
		model.addAttribute("branchmap",branchMap);
		return "addstudent";
	}
	
	@RequestMapping(value="/addclass")
	public String addClass(ModelMap model)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, String> subjectMap=new SubjectsChooser(dataSource).getAllSubjects();
		model.addAttribute("currentYear", new Integer(year));
		model.addAttribute("subjectsMap", subjectMap);
		return "addclass";
	}
	
	@RequestMapping(value="/addsubject")
	public String addSubject()
	{
		return "addsubject";
	}

}
