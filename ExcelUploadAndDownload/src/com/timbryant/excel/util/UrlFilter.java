package com.timbryant.excel.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * URL地址过滤器
 * @author liuxf
 *
 */
public class UrlFilter implements Filter {
	private Boolean isFilter=false;
	private String message="";
	
	
	public void setIsFilter(Boolean isFilter) {
		this.isFilter = isFilter;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	@Override
	public void destroy() {
		System.out.println("Application Server is Destory...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		if(this.isFilter){
		   System.out.println(message+req.getContextPath()+req.getServletPath()+" ---");
		}
		request.setCharacterEncoding("UTF-8");
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		  System.out.println("Application Server is init...");
		  //得到过滤器的名字
	      String filterName = filterConfig.getFilterName();
	      //得到在web.xml文件中配置的初始化参数
		  String initParam1 = filterConfig.getInitParameter("isFilter");
	      String initParam2 = filterConfig.getInitParameter("message");
	      isFilter=Boolean.parseBoolean(initParam1);
	      message=initParam2;
	}
}
