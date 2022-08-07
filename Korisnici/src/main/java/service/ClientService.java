package service;

import java.io.File;
import java.io.Serializable;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;

import bean.Client;
import dao.ClientDAO;
import interfaces.CheckConditionInterface;
import interfaces.CommonCrudOperation;
import validation.ClientValidation;
import help.QR;

public class ClientService implements Serializable, CheckConditionInterface, CommonCrudOperation{
	private static final long serialVersionUID = 1;
	private ClientValidation validation = new ClientValidation();
	private FileService fileService = new FileService();
	private List<Client> arr = new ArrayList<Client>();
	private ClientDAO dao = new ClientDAO();
	
	public List<Client> getUsers(){
		arr.clear();
		arr = dao.getAllAdminS();
		return arr;
	}
	
	public boolean addUser(String username, String password, String path, String domen, boolean flagCreate, 
			boolean flagRetrieve, boolean flagUpdate, boolean flagDelete) {
		Client client = new Client(username, password, path, domen, flagCreate, flagRetrieve, flagUpdate, flagDelete);
		if (checkPathCondition(path) && validation.check(client)) {
			client.setDomen(getCompressedDomen(domen));
			boolean flag = dao.addUser(client);
		
			if (flag) {
				fileService.addFolder(path);
			}
			
			return flag;
		}
		
		return false;
	}
	
	public Client updateUser(String oldUsername, String newUsername, String password, String path, String domen, boolean create, boolean retrieve,
			boolean update, boolean delete) {
		Client client = new Client(newUsername, password, path, domen, create, retrieve, update, delete);
		
		boolean flag = false;
		if (checkPathCondition(path) && validation.check(client)) {
			flag = dao.updateUser(oldUsername, client);
		
			if (flag) {
				File f = new File(QR.SAVE_PATH + File.separator + oldUsername + ".png");
				if (f.exists()) {
					File newF = new File(QR.SAVE_PATH + File.separator + newUsername + ".png");
					f.renameTo(newF);
				}
			}
		}
		
		return flag ? client : null;
	}
	
	public boolean deleteUser(String username) {
		return dao.deleteUser(username);
	}
	
	public boolean checkIfExists(String username) {
		return dao.checkIfExists(username);
	}
	
	public Client getUser(String username) {
		return dao.getUser(username);
	}
	
	private String getCompressedDomen(String ip) {
		try {
			String adr = Inet6Address.getByName(ip).toString();
			return adr.substring(1, adr.length());
		}catch (Exception e) {
			
		}

		return ip;
	}
	
	@Override
	public boolean checkPathCondition(String path) {
		List<String> arr = dao.getAllPaths();
		
		for (String i : arr) {
			if (i.startsWith(path + File.separator) || path.startsWith(i + File.separator)) {
				System.out.println("NASAO: " + i);
				return false;
			}
		}
		
		return true;
	}
}
