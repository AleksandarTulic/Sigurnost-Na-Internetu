package service;

import java.io.Serializable;

import bean.Client;
import dao.ClientDAO;

public class ClientService implements Serializable{
	private static final long serialVersionUID = 1;
	
	private ClientDAO dao = new ClientDAO();
	
	public Client getUser(String username) {
		return dao.getUser(username);
	}
}
