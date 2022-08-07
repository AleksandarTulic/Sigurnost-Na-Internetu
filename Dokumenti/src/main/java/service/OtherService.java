package service;

import java.io.Serializable;
import java.util.List;

import bean.User;
import bean.UserAction;
import dao.UserActionDAO;
import dao.UserDAO;

public class OtherService implements Serializable{
	private static final long serialVersionUID = 1;
	private UserDAO dao = new UserDAO();
	private UserActionDAO userActionDAO = new UserActionDAO();
	
	public boolean checkIfActive(String username) {
		return dao.isActive(username);
	}
	
	public boolean updateActive(String username) {
		return dao.updateActive(username);
	}
	
	public String getKey(String username) {
		return dao.getKey(username);
	}
	
	public User getUser(String username) {
		return dao.getUser(username);
	}
	
	public List<UserAction> getClientAction(){
		return userActionDAO.getClientActions();
	}
	
	public List<UserAction> getAdminSAction(){
		return userActionDAO.getAdminSActions();
	}
	
	public List<UserAction> getAdminDAction(){
		return userActionDAO.getAdminDActions();
	}
	
	public boolean addUserAction(String username, String typeAction, String documentName) {
		return userActionDAO.addUserAction(new UserAction(username, "", "", typeAction, documentName));
	}
}
