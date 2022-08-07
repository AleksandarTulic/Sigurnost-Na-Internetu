package service;

import java.io.Serializable;

import bean.AdminD;
import dao.AdminDDAO;

public class AdminDService implements Serializable{
	private static final long serialVersionUID = 1;
	private AdminDDAO dao = new AdminDDAO();
	
	public AdminD getUser(String username) {
		return dao.getUser(username);
	}
}
