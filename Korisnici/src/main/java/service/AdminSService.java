package service;

import java.io.*;
import java.util.*;

import bean.AdminS;
import dao.AdminSDAO;
import dao.UserDAO;
import help.QR;
import interfaces.CommonCrudOperation;
import validation.AdminSValidation;

public class AdminSService implements Serializable, CommonCrudOperation {
	private static final long serialVersionUID = 1;
	private AdminSValidation validation = new AdminSValidation();
	private List<AdminS> arr  =new ArrayList<AdminS>();
	private AdminSDAO dao = new AdminSDAO();
	
	public List<AdminS> getUsers(){
		arr.clear();
		arr = dao.getAllAdminS();
		return arr;
	}
	
	public boolean addUser(String username, String password) {
		AdminS adminS = new AdminS(username, password);
		
		if (validation.check(adminS))
			return dao.addUser(adminS);
		
		return false;
	}
	
	public AdminS updateUser(String oldUsername, String newUsername, String password) {
		AdminS adminS = new AdminS(newUsername, password);
		boolean flag = false;
		
		if (validation.check(adminS)) {
			flag = dao.updateUser(oldUsername, adminS);
			
			if (flag) {
				File f = new File(QR.SAVE_PATH + File.separator + oldUsername + ".png");
				if (f.exists()) {
					File newF = new File(QR.SAVE_PATH + File.separator + newUsername + ".png");
					f.renameTo(newF);
				}
			}
		}
		
		return flag ? adminS : null;
	}
	
	public boolean deleteUser(String username) {
		return dao.deleteUser(username);
	}
	
	public boolean checkIfExists(String username) {
		return dao.checkIfExists(username);
	}
	
	public AdminS getUser(String username) {
		return dao.getUser(username);
	}
}
