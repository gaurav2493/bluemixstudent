package com.studentmanagement.controller.jsonp;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanagement.components.Notice;
import com.studentmanagement.databasemanager.NoticeManager;

@Controller
public class JsonpNoticeController {
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/jsonp/viewnotices")
	public String viewNotices(@RequestParam("pageno") int pageNo,@RequestParam("callback") String callback, ModelMap model)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		List<Notice> noticeList =  noticeManager.getNoticeList(10*(pageNo-1), 10);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("callback", callback);
		return "viewjsonnotices";
	}
	@RequestMapping(value="/jsonp/viewnotice")
	public String viewNotice(@RequestParam("notice_id") int noticeId,ModelMap model,@RequestParam("callback") String callback)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		Notice notice=noticeManager.viewNotice(noticeId);
		model.addAttribute("notice", notice);
		//notice.setNotice(notice.getNotice().replaceAll("\\r|\\n", "<br/>"));
		notice.setNotice(notice.getNotice().replaceAll("\\r\\n|\\r|\\n", "<br/>"));
		model.addAttribute("callback", callback);
		return "jsonnotice";
	}
	

}
