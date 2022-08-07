package service;

import java.io.Serializable;

import dao.UserDAO;

public class OtherService implements Serializable{
	private static final long serialVersionUID = 1;
	private UserDAO dao = new UserDAO();
	
	public boolean checkIfActive(String username) {
		return dao.isActive(username);
	}
	
	public boolean updateActive(String username) {
		return dao.updateActive(username);
	}
	
	public String getKey(String username) {
		return dao.getKey(username);
	}
	
	public boolean updateKey(String username, String key) {
		return dao.updateKey(username, key);
	}

}
