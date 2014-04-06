package com.studentmanagement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanagement.components.FileUploadForm;
import com.studentmanagement.components.Notice;
import com.studentmanagement.databasemanager.FileManager;
import com.studentmanagement.databasemanager.NoticeManager;

@Controller
public class NoticeFileController {

	private static final int BUFFER_SIZE = 10000;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private String fileSaveUrl;

	@RequestMapping(value = "/notice/uploadpage", method = RequestMethod.GET)
	public String displayForm() {
		return "file_upload_form";
	}

	@RequestMapping(value = "/notice/uploadnotice", method = RequestMethod.POST)
	public String save(@ModelAttribute("uploadForm") FileUploadForm uploadForm,
			ModelMap model) {

		boolean uploaded = false;
		List<MultipartFile> files = uploadForm.getFiles();

		Map<String, Long> fileNames = new HashMap<String, Long>();

		if (null != files && files.size() > 0) {

			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				if(fileName.length()==0)
					continue;
				Long l = new Long(new Date().getTime());
				fileNames.put(fileName, l);
				try {
					String url=fileSaveUrl+l;
					multipartFile.transferTo(new File(url));
					uploaded = true;
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String author = auth.getName();
		NoticeManager noticeManager = new NoticeManager(dataSource);

		int noticeId = noticeManager.addNotice(uploadForm.getContent(), author,
				uploadForm.getSubject(), uploaded);
		FileManager fileManager = new FileManager(dataSource);
		fileManager.makeDatabaseFileEntry(fileNames, noticeId);
		model.addAttribute("message", "Notice uploaded successfully");
		return "submitted";
	}
	@RequestMapping(value="/notice/downloadfile")
	public void downloadFile(@RequestParam("notice_id") int noticeId,@RequestParam("sequence_no") int seq,HttpServletResponse response)
	{
		FileManager fileManager=new FileManager(dataSource);
		com.studentmanagement.components.File file= fileManager.downloadFile(noticeId, seq);
		try{
		File downloadFile = new File(fileSaveUrl+file.getSavedFileName());
        FileInputStream inputStream = new FileInputStream(downloadFile);
        
        response.setContentLength((int) downloadFile.length());
        
        // set headers for the response
        response.setHeader(file.getHeaderKey(), file.getHeaderValue());
        response.setContentType("application/octet-stream");
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	@RequestMapping(value="/notice/viewnotices/{pageno}")
	public String viewNotices(@PathVariable("pageno") int pageNo,ModelMap model)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		List<Notice> noticeList =  noticeManager.getNoticeList(10*(pageNo-1), 10);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pageno", pageNo);
		return "viewnotices";
	}
	@RequestMapping(value="/notice/viewnotice/{noticeId}")
	public String viewNotice(@PathVariable("noticeId") int noticeId,ModelMap model)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		Notice notice=noticeManager.viewNotice(noticeId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    if(notice.getAuthor().equals(name))
	    	model.addAttribute("delete", true);
		model.addAttribute("notice", notice);
		return "shownotice";
	}
	@RequestMapping(value="/notice/delete")
	public String deleteNotice(@RequestParam("noticeid") int noticeid,ModelMap model)
	{
		NoticeManager noticeManager=new NoticeManager(dataSource);
		int no=noticeManager.deleteNotice(noticeid);
		if(no>0)
			model.addAttribute("message","Notice Deleted successfully");
		else
			model.addAttribute("message","Notice Could not be deleted, Try again later");
		return "submitted";
	}
	@RequestMapping(value="/notice/feeschedules")
	public String addFeeSchedule(ModelMap model)
	{
		model.addAttribute("subject", "Fee Schedule for Year - ");
		model.addAttribute("body", "Fee Schedule for Year - <br/>could be found in the attached document.<br/><font color='red'>Please Contact Account section for furthur clarifications</font>");
		return "file_upload_form";
	}
	
}
