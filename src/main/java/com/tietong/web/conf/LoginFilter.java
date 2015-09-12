package com.tietong.web.conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginFilter implements Filter {
		
	private static final String LOGIN_URL = "pages/login.html";
	
    private FilterConfig config = null;  
    public void destroy() {  
        this.config = null;  
    }  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        HttpSession session = ((HttpServletRequest) request).getSession();  
        Object userObj = session.getAttribute(Constants.SESSION_USER_NAME);  
        String url = ((HttpServletRequest)request).getRequestURI();
        String contextPath = ((HttpServletRequest)request).getContextPath();
        
        String redirectUrl = contextPath + "/" + LOGIN_URL;
        if (!url.equals(redirectUrl) && userObj == null) {
            ((HttpServletResponse)response).sendRedirect(redirectUrl);  
        	//chain.doFilter(request, response);
        } else {  
            chain.doFilter(request, response);   
        }  
    }  
    public void init(FilterConfig config) throws ServletException {  
        this.config = config;  
    }  
}
