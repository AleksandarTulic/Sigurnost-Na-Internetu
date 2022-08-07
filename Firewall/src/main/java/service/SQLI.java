package service;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import logger.MyLogger;

public class SQLI implements CheckInterface{
	private static final String REGEX_PATTERN_STRING = "(?i)(?s)\\b(select)\\b(.*?)\\b(from)\\b|\\b(insert)\\b(.*?)\\b(into)\\b|\\b(update)\\b(.*?)\\b(set)\\b|\\b(delete)(.*?)\\b(from)\\b";
	
	private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX_PATTERN_STRING, Pattern.CASE_INSENSITIVE);
	
	@Override
	public boolean check(HttpServletRequest request) {
		boolean flag = checkUrl(request) || checkQuery(request) || checkParameters(request);

		if (flag) {
			MyLogger.logger.log(Level.SEVERE, "SQL Injection attack from: " + request.getRemoteAddr());
		}
		
		return flag;
	}
	
	//url
	//example: http://localhost:8080/URLRewrite/HelloWorld
	private boolean checkUrl(HttpServletRequest request) {
		return REGEX_PATTERN.matcher(request.getRequestURL()).find();
	}
	
	//query string
	//example: a=valueA&b=bvalueB
	private boolean checkQuery(HttpServletRequest request) {
		return request.getQueryString() == null ? false : REGEX_PATTERN.matcher(request.getQueryString()).find();
	}
	
	//query string
	//	- parameterNames
	//	- parameterValues
	//example: a=valueA&b=bvalueB
	//	- parameterName=a
	// 	- parameterValue=valueA
	private boolean checkParameters(HttpServletRequest request) {
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String parameterName = enu.nextElement() + "";
			String parameterValue = request.getParameter(parameterName) + "";
			
			boolean flag1 = REGEX_PATTERN.matcher(parameterName).find();
			boolean flag2 = REGEX_PATTERN.matcher(parameterValue).find();
			
			if (flag1 || flag2)
				return true;
		}
		
		return false;
	}
}
