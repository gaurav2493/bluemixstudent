package com.studentmanagement.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.components.StudentsSubjectMarks;
import com.studentmanagement.databasemanager.BranchManager;
import com.studentmanagement.databasemanager.ExamReports;
import com.studentmanagement.databasemanager.StudentListGenerator;
import com.studentmanagement.databasemanager.SubjectsChooser;

@Controller
public class AcademicMarksController {
	
	@Autowired
	DataSource dataSource;
	
	@RequestMapping(value="/academicreports")
	public String academicReports(ModelMap model)
	{
		ExamReports examReports=new ExamReports(dataSource);
		model.addAttribute("examList", examReports.getExamTypes());
		return "academicreports";
	}
	@RequestMapping(value="/addexam")
	public String addExams(ModelMap model)
	{
		return "addexam";
	}
	@RequestMapping(value="/createnewexam",method=RequestMethod.POST )
	public String createNewExam(ModelMap model,@RequestParam(value = "exam_name") String param)
	{
		ExamReports examReports=new ExamReports(dataSource);
		if(examReports.createNewExamType(param))
			model.addAttribute("message", "New Exam added Successfully");
		else
			model.addAttribute("message", "New Exam Couldnt be added.<br/>Recheck the input values");
		return "submitted";
	}
	@RequestMapping(value="/academicreports/options",method=RequestMethod.GET )
	public String academicreportsOptions(ModelMap model,@RequestParam(value = "examid") int examid,HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute("examid", new Integer(examid));
		return "examoptions";
	}
	@RequestMapping(value="/academicreports/uploadmarks",method=RequestMethod.GET )
	public String uploadmarks(ModelMap model,HttpServletRequest request)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, String> subjectMap=new SubjectsChooser(dataSource).getAllSubjects();
		BranchManager branchManager=new BranchManager(dataSource);
		Map<String,String> branchMap = branchManager.getSubjects();
		model.addAttribute("branchmap",branchMap);
		model.addAttribute("currentYear", new Integer(year));
		model.addAttribute("subjectsMap", subjectMap);
		return "uploadmarks";
	}
	@RequestMapping(value="/academicreports/uploadmarks/insertmarks",method=RequestMethod.POST )
	public String insertmarks(ModelMap model,@RequestParam Map<String,String> allRequestParams,HttpServletRequest request)
	{
		StudentListGenerator studentListGenerator=new StudentListGenerator(dataSource, allRequestParams);
		Map<Integer, String> rollList =studentListGenerator.getStudentList();
		request.getSession().setAttribute("examparams", allRequestParams);
		if(rollList.size()==0)
		{
			model.addAttribute("message", "The class you are looking for is not found.<br/> If you are sure this is your class, create a new class from left sidebar");
			return "somethingwentwrong";
		}
		model.addAttribute("rollList", rollList);
		return "insertmarks";
	}
	@RequestMapping(value="/academicreports/uploadmarks/submitmarks",method=RequestMethod.POST )
	public String submitmarks(@RequestParam Map<String,String> allRequestParams,HttpServletRequest request,ModelMap model)
	{
		HttpSession session=request.getSession();
		ExamReports examReports=new ExamReports(dataSource);
		boolean bool=examReports.insertMarks(allRequestParams, session);
		if(bool)
			model.addAttribute("message", "Marks Uploaded successfully");
		else 
			model.addAttribute("message", "Marks could not be uploaded.<br/>Please Recheck the input values");
		return "submitted";
	}	
	@RequestMapping(value="/academicreports/viewmarks")
	public String viewMarks(ModelMap model)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, String> subjectMap=new SubjectsChooser(dataSource).getAllSubjects();
		BranchManager branchManager=new BranchManager(dataSource);
		Map<String,String> branchMap = branchManager.getSubjects();
		model.addAttribute("branchmap",branchMap);
		model.addAttribute("currentYear", new Integer(year));
		model.addAttribute("subjectsMap", subjectMap);
		return "viewmarks";
	}
	@RequestMapping(value="/academicreports/uploadmarks/viewfetchedmarks")
	public String viewFetchedMarks(@RequestParam Map<String,String> allRequestParams,ModelMap model,HttpServletRequest request)
	{
		ExamReports examReports=new ExamReports(dataSource);
		int examid=(Integer)request.getSession().getAttribute("examid");
		List<StudentsSubjectMarks> list = examReports.getStudentsSubjectMarksList(allRequestParams,examid);
		if(list.size()==0)
		{
			model.addAttribute("message", "The class you are looking for is not found.<br/> If you are sure this is your class, create a new class from left sidebar");
			return "somethingwentwrong";
		}
		model.addAttribute("subjectmarkslist", list);
		
		return "viewfetchedmarks";
	}
	
	
}
