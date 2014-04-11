package com.studentmanagement.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.studentmanagement.components.Notice;
import com.studentmanagement.databasemanager.NoticeManager;

@Controller
public class GeneralController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/*",method= RequestMethod.GET)
	public String viewNotices(ModelMap model)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		List<Notice> noticeList =  noticeManager.getNoticeList(0, 10);
		model.addAttribute("noticeList", noticeList);
		return "home";
	}
	@RequestMapping(value="/home",method= RequestMethod.GET)
	public String homePage(ModelMap model)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		List<Notice> noticeList =  noticeManager.getNoticeList(0, 10);
		model.addAttribute("noticeList", noticeList);
		return "home";
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
