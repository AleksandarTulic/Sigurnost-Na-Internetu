package service;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.*;
import jakarta.servlet.http.HttpServletRequest;
import logger.MyLogger;

public class ParameterTampering implements CheckInterface{
	private static List<String> isString = Arrays.asList("type", "action", "username", "oldUsername", "pwd", "dir", "ip", 
			"create", "update", "retrieve", "delete", "tokenValue", "j_username", "j_password", "optCreate",
			"optDelete", "optUpdate", "opt1", "opt2", "opt3", "opt4", "opt5");
	private static List<String> isInteger = Arrays.asList();
	
	@Override
	public boolean check(HttpServletRequest request) {
		return checkParameters(request);
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
			String value = enu.nextElement() + "";
			boolean flag = true;
			
			if (isString.contains(value)) {
				flag = checkString(request.getParameter(value));
			}else if (isInteger.contains(value)) {
				flag = checkInteger(request.getParameter(value));
			}
			
			if (!flag) {
				MyLogger.logger.log(Level.SEVERE, "Parameter Tampering from: " + request.getRemoteAddr());
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkInteger(String value) {
		if (value == null)
			return true;
		
		boolean flag = false;
		
		try {
			@SuppressWarnings("unused")
			Integer intValue = Integer.parseInt(value);
			flag = true;
		}catch (Exception e) {
			flag = false;
		}
		
		return flag;
	}
	
	private boolean checkString(String value) {
		if (value == null || value.length() < 1024)
			return true;
		
		return false;
	}
}
