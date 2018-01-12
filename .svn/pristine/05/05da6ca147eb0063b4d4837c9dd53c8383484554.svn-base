package com.mfu.util;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mfu.web.controller.SecurityManager;

import org.springframework.web.filter.OncePerRequestFilter;

public class ServiceFilter extends OncePerRequestFilter {
	
	private final static Logger logger = Logger.getLogger(ServiceFilter.class .getName()); 

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filter)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type,"+SecurityManager.AUTHORIZATION_KEY_SESSION_ATTRIBUTE);
		response.addHeader("Access-Control-Max-Age", "1800");
		
		/*
		boolean isWebServiceCall = request.getRequestURL().indexOf("WS.do")!=-1;
		boolean isAdminCall = request.getRequestURL().indexOf("/admin/")!=-1;
		boolean isAllowAnyAccess = request.getRequestURI().indexOf("registerPatientUserWS")!=-1 
										||  request.getRequestURI().indexOf("loginPatientWS")!=-1
										||  request.getRequestURI().indexOf("_ah/admin")!=-1;
		
		logger.info("doFilterInternal is called "+isWebServiceCall+" "+isAdminCall+" "+isAllowAnyAccess);
		System.out.println("	..."+request.getSession().getAttribute(SecurityManager.AUTHORIZATION_KEY_SESSION_ATTRIBUTE));
		SecurityManager secManager = new SecurityManager();

		if(isWebServiceCall && !isAllowAnyAccess && !secManager.validateAuthority(request))
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"Forbidden to access");
		else if(isAdminCall && !isAllowAnyAccess && !secManager.validateAuthority(request, SecurityManager.ROLE_HOSPITALSTAFF))
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"Forbidden to access");
		else
		*/
			filter.doFilter(request, response);
			

	}

}
