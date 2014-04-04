package com.studentmanagement.controller.ajax;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	@RequestMapping(value="/ajax/validaterollno")
	public String validateRollNo(@RequestBody String body,HttpServletResponse response)
	{
		System.out.println(body);
		RollNoListGenerator rollNoListGenerator=new RollNoListGenerator(dataSource);
		boolean exist=false;
		try{
		exist=rollNoListGenerator.validateRollNo(Integer.parseInt(body));
		}catch(Exception ex){
			ex.printStackTrace();
			return "false";
		}
		
		response.setContentType("text/html");
		if(exist)
			return "true";
		return "false";
	}
	@ResponseBody
	@RequestMapping(value="/ajax/validateallrollno")
	public String validateAllrollno(@RequestBody String body)
	{
				
		System.out.println(body);
		return "true";
	}
	
}
