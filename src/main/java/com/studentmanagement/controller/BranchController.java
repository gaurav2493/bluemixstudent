package com.studentmanagement.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.databasemanager.BranchManager;

@Controller
public class BranchController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/branch/addbranchform")
	public String addBranchForm()
	{
		return "addbranch";
	}
	@RequestMapping(value="/branch/add")
	public String addBranch(@RequestParam("code") String code,@RequestParam("name") String name,ModelMap model)
	{
		BranchManager branchManager=new BranchManager(dataSource);
		boolean bool=branchManager.addBranch(code, name);
		if(bool)
			model.addAttribute("message", "Branch added successfully");
		else 
			model.addAttribute("message", "Branch could not be added.<br/>Please Recheck the input values");
		return "submitted";
	}

}
