package com.studentmanagement.controller.jsonp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Authenticator {
	
	@Autowired @Qualifier("org.springframework.security.authenticationManager")
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/login", method = {RequestMethod.GET })
	public @ResponseBody String authentication(@RequestParam("user") String userName,@RequestParam("password") String password,@RequestParam("callback") String callback, HttpServletRequest request,HttpServletResponse response) {
		
		 response.setContentType("application/javascript");
		 response.setCharacterEncoding("UTF-8");
	    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
	            userName, password);
	    try {

	        Authentication authentication = authenticationManager
	                .authenticate(authenticationToken);


	        SecurityContext securityContext = SecurityContextHolder
	                .getContext();

	        securityContext.setAuthentication(authentication);

	        HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	        System.out.println("success");
	        return callback+"('sucess');";
	    } catch (AuthenticationException ex) {
	        return callback+"('fail');";
	    }
	}

}
