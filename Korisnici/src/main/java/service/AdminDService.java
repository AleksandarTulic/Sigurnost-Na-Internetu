package service;

import java.io.*;
import java.util.*;

import bean.AdminD;
import dao.AdminDDAO;
import interfaces.CheckConditionInterface;
import interfaces.CommonCrudOperation;
import validation.AdminDValidation;
import help.QR;

public class AdminDService implements Serializable, CheckConditionInterface, CommonCrudOperation{
	private static final long serialVersionUID = 1;
	private AdminDValidation validation = new AdminDValidation();
	private List<AdminD> arr  =new ArrayList<AdminD>();
	private AdminDDAO dao = new AdminDDAO();
	
	public List<AdminD> getUsers(){
		arr.clear();
		arr = dao.getAllAdminS();
		return arr;
	}
	
	public boolean addUser(String username, String password, String path) {
		AdminD adminD = new AdminD(username, password, path);
		if (checkPathCondition(path) && validation.check(adminD))
			return dao.addUser(adminD);
		
		return false;
	}
	
	public AdminD updateUser(String oldUsername, String newUsername, String password, String path) {
		AdminD adminD = new AdminD(newUsername, password, path);
		
		boolean flag = false;
		if (checkPathCondition(path) && validation.check(adminD)) {
			flag = dao.updateUser(oldUsername, adminD);
			
			if (flag) {
				File f = new File(QR.SAVE_PATH + File.separator + oldUsername + ".png");
				if (f.exists()) {
					File newF = new File(QR.SAVE_PATH + File.separator + newUsername + ".png");
					f.renameTo(newF);
				}
			}
		}
		
		return flag ? adminD : null;
	}
	
	public boolean deleteUser(String username) {
		return dao.deleteUser(username);
	}
	
	public boolean checkIfExists(String username) {
		return dao.checkIfExists(username);
	}
	
	public AdminD getUser(String username) {
		return dao.getUser(username);
	}

	@Override
	public boolean checkPathCondition(String path) {
		return dao.getPathCondition(path);
	}
}
