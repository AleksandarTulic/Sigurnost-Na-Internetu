package service;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;
import logger.MyLogger;

public class DOS extends Thread{
	//if some asks for 100 requests in 15s from same address then it's a DOS attack
	private static final int MAX_NUMBERS = 100;
	private static final long TIME_WAITING = 15000; //15s

	private Map<String, List<Long>> map = new HashMap<>();
	
	public DOS() {
		start();
	}
	
	public synchronized boolean newRequest(HttpServletRequest request) {
		boolean flag = false;
		if (map.containsKey(request.getRemoteAddr())) {
			List<Long> arr = map.get(request.getRemoteAddr());
			arr.add(System.currentTimeMillis());
			map.put(request.getRemoteAddr(), arr);
		}else {
			List<Long> arr = new ArrayList<>();
			arr.add(System.currentTimeMillis());
			map.put(request.getRemoteAddr(), arr);
		}
		
		List<Long> arrChanged = getNumberRequests(map.get(request.getRemoteAddr()));
		map.put(request.getRemoteAddr(), arrChanged);
		if (arrChanged.size() >= MAX_NUMBERS) {
			MyLogger.logger.log(Level.SEVERE, "DOS attack from: " + request.getRemoteAddr());
			flag = true;
		}
		
		return flag;
	}
	
	private List<Long> getNumberRequests(List<Long> arr) {
		List<Long> res = new ArrayList<Long>();
		
		for (Long i : arr) {
			if (System.currentTimeMillis() - i < TIME_WAITING) {
				res.add(i);
			}
		}
		
		return res;
	}
	
	@Override
	public void run() {
		do {
			try {
				Thread.sleep(TIME_WAITING);
			}catch (Exception e) {
				MyLogger.logger.log(Level.SEVERE, e.getMessage());
			}
			
			synchronized(this) {
				for (Entry<String, List<Long>> entry : map.entrySet()) {
					map.put(entry.getKey(), getNumberRequests(entry.getValue()));
				}
			}
		}while (true);
	}
}