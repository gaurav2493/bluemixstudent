package com.studentmanagement.controller.ajax;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.databasemanager.RollNoListGenerator;

@Controller
public class RollNoFindController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/ajax/getrollno")
	public String getRollNoList(@RequestParam("count") int count,@RequestParam("term") String start,ModelMap model,HttpServletResponse response)
	{
		RollNoListGenerator rollNoListGenerator=new RollNoListGenerator(dataSource);
		List<String> list = rollNoListGenerator.getRollNoList(start, count);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		model.addAttribute("rollList",list);
		return "ajaxrollno";
	}
	@RequestMapping(value="/ajax/validaterollno")
	public String validateRollNo(@RequestParam("rollno") int rollno,HttpServletResponse response,ModelMap model)
	{
		RollNoListGenerator rollNoListGenerator=new RollNoListGenerator(dataSource);
		boolean exist=rollNoListGenerator.validateRollNo(rollno);
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		model.addAttribute("exist", exist);
		return "ajaxvalidaterollno";
	}
	
}
