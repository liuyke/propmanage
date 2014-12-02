package com.prop.servlet;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prop.util.IOUtils;
import com.prop.util.PropertiesUtil;
import com.prop.util.SimpleJsonUtil;

public class PropManageServlet extends HttpServlet {

	private static final long serialVersionUID = 3057532871374553136L;
	
	private String loginUsername;
	private String loginPwd;
	private final static String resourcePath = "com/prop/resources";
	private final static String SESSION_USER = "prop_loginuser";
	
	private static final List<String> urls = Arrays.asList(new String[]{"/login.html","/login"});
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextPath = request.getContextPath();
	    String servletPath = request.getServletPath();
		String requestURI = request.getRequestURI();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		if(contextPath == null) {
		    contextPath = "";
	    }
		
		String uri = contextPath + servletPath;
	    String path = requestURI.substring(contextPath.length() + servletPath.length());
	    if(!isLogin(request, path) && !path.endsWith(".css") && !path.endsWith(".js") && !path.endsWith(".png")) {
	    	response.sendRedirect(uri + "/login.html");
			return;
	    }
	    
	    if("/login.html".equals(path) && request.getSession().getAttribute(SESSION_USER) != null) {
	    	response.sendRedirect(uri + "/index.html");
			return;
	    }
	    
	    if("/login".equals(path)) {
	    	String name = request.getParameter("name");
	    	String pwd = request.getParameter("pwd");
	    	if(loginUsername.equals(name) && loginPwd.equals(pwd)) {
	    		request.getSession().setAttribute(SESSION_USER, name);
	    		response.getWriter().print("success");
	    	} else {
	    		response.getWriter().print("error");
	    	}
	    	return;
	    }
	    
	    if("/list".equals(path)) {
	    	response.setContentType("application/json;charset=utf-8");
	    	response.getWriter().print(SimpleJsonUtil.map2json(PropertiesUtil.get()));
	    	return;
	    }
	    
	    if("/saveorupdate".equals(path)) {
	    	String name = request.getParameter("name");
	    	String value = request.getParameter("value");
    		try {
				PropertiesUtil.set(name, value);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(1);
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print(0);
			}
	    	return;
	    }
	    
	    if("/delete".equals(path)) {
	    	response.setContentType("application/json;charset=utf-8");
	    	String names = request.getParameter("names");
	    	String[] keys = names.split(",");
	    	for(String k:keys)
	    		PropertiesUtil.remove(k);
	    	response.getWriter().print(1);
	    	return;
	    }
		renderResurces(response, uri, path);
	}
	
	private boolean isLogin(HttpServletRequest request,String path) {
		if(urls.contains(path)) {
			return true;
		}
		return request.getSession().getAttribute(SESSION_USER) != null;
	}
	
	private boolean isImage(String path) {
		return path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".gif");
	}
	
	private String getPath(String path) {
		return resourcePath + path;
	}
	
	public void renderResurces(HttpServletResponse response,String uri, String path) throws IOException {
		if(path.endsWith(".png")) {
			response.setContentType("image/jpeg");
	    	byte[] bytes = IOUtils.readByteArrayFromResource(getPath(path));
	    	if(bytes != null)
	    		response.getOutputStream().write(bytes);
	    	return;
	    }
		
		String text = IOUtils.readFile2String(getPath(path));
		if(text == null) {
			response.sendRedirect(uri + "/login.html");
			return;
		}
		if(path.endsWith(".css")) {
			response.setContentType("text/css;charset=utf-8");
		} else if (path.endsWith(".js")) {
		    response.setContentType("text/javascript;charset=utf-8");
	    }
		response.getWriter().write(text);
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		loginUsername = getInitParameter("loginUsername");
		loginPwd = getInitParameter("loginPwd");
		if(loginUsername == null) {
			log("init param 'loginUsername' is not config");
		}
		
		if(loginPwd == null) {
			log("init param 'loginPwd' is not config");
		}
		
		String configFile = getInitParameter("configFile");
		if(configFile == null || "".equals(configFile.trim())) {
			throw new ServletException("init param 'configFile' not config");
		}
		PropertiesUtil.init(configFile.trim());
	}
	
}
