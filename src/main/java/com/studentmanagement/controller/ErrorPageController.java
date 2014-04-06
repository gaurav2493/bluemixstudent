package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {
	
	@RequestMapping(value="/404")
	public String error404()
	{
		return "404";
	}
	@RequestMapping(value="/500")
	public String error500()
	{
		return "500";
	}
	@RequestMapping(value="/general-error")
	public String generalError(ModelMap model)
	{
		model.addAttribute("message", "There was some error in processing this page.<br/>The error have been logged and will be corrected soon");
		return "somethingwentwrong";
	}

}
