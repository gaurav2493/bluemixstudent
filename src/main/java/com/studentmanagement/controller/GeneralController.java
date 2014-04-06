package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GeneralController {
	
	
	@RequestMapping(value="/",method= RequestMethod.GET)
	public String teacherlogin()
	{
		return "homepage";
	}
	@RequestMapping(value="/home",method= RequestMethod.GET)
	public String homePage()
	{
		return "homepage";
	}
	@RequestMapping(value="/loginfailed",method= RequestMethod.GET)
	public String getNoticeXml(ModelMap model)
	{
		
			model.addAttribute("error", "true");
			return "homepage";
	}
	@RequestMapping(value="/contact",method= RequestMethod.GET)
	public String contact()
	{
		return "contact";
	}
	@RequestMapping(value="/downloadsource",method= RequestMethod.GET)
	public String downloadsorce()
	{
		return "downloadsource";
	}
	
}
