package com.studentmanagement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {
	
	final int BUFFER_SIZE=5000;

	@RequestMapping(value = "/photos")
	public void getPhoto(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		String fileName = "download";
		try {
			String fullPath = "C:/Users/Devesh/Desktop/Desktop/"+id;
			File downloadFile = new File(fullPath);
			//downloadFile.getName().
			FileInputStream inputStream = new FileInputStream(downloadFile);
			
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					fileName);
			response.setHeader(headerKey, headerValue);

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
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
