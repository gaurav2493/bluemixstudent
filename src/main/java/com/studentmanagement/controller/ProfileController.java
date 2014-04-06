package com.studentmanagement.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.components.Profile;
import com.studentmanagement.databasemanager.NewStuffAdder;
import com.studentmanagement.databasemanager.ProfileManager;

@Controller
public class ProfileController {
	
	@Autowired
	DataSource dataSource;
	
	@RequestMapping(value="/profile/changepassword")
	public String changePasswordForm()
	{		
		return "changepassword";
	}
	@RequestMapping(value="/profile/changepasswordsubmit")
	public String changePassword(@RequestParam("old") String old,@RequestParam("new") String newpass,ModelMap model)
	{	
		ProfileManager profileManager=new ProfileManager(dataSource);
		int status=profileManager.changePassword(old, newpass);
		if(status==0)
		{
			model.addAttribute("message", "Incorect password");
			return "somethingwentwrong";
		}
		model.addAttribute("message", "Password Changed Successfully");
		return "submitted";
	}
	@RequestMapping(value="/profile/getprofile")
	public String getProfile(@RequestParam("rollno") int rollNo, ModelMap model)
	{
		ProfileManager profileManager=new ProfileManager(dataSource);
		Profile profile=profileManager.getProfile(rollNo);
		model.addAttribute("profile", profile);
		return "getprofile";
	}
	@RequestMapping(value="/adduser")
	public String addUser()
	{
		return "adduser";
	}
	@RequestMapping(value="/adduser/submit")
	public String addUserSubmit(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("role") String role,ModelMap model)
	{
		NewStuffAdder newStuffAdder=new NewStuffAdder(dataSource);
		String message=newStuffAdder.addUser(username, password, role);
		model.addAttribute("message", message);
		return "submitted";
	}
}
