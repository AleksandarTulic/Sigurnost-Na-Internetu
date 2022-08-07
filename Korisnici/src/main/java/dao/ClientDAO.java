package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import bean.Client;
import logger.MyLogger;

public class ClientDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static UserDAO userDAO = new UserDAO();
	private static final String SQL_INSERT_CLIENT_REST = "INSERT INTO client(username, path, domen, flagCreate, flagRetrieve, flagUpdate, flagDelete) values(?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_ALL_CLIENT = "select u.username, u.role_name, a.path, a.domen, a.flagCreate, a.flagRetrieve, a.flagUpdate, a.flagDelete from users as u inner join client as a on a.username=u.username;";
	private static final String SQL_DELETE_CLIENT = "delete from client as a where a.username=?";
	private static final String SQL_RETRIEVE_CLIENT = "select u.username, u.role_name, a.path, a.domen, a.flagCreate, a.flagRetrieve, a.flagUpdate, a.flagDelete from users as u inner join client as a on a.username=u.username where u.username=?;";
	private static final String SQL_UPDATE_CLIENT = "update client as c set c.path=?,c.domen=?,c.flagCreate=?,c.flagRetrieve=?,c.flagUpdate=?,c.flagDelete=? where c.username=?";
	private static final String SQL_RETRIEVE_ALL_PATHS = "select path from client;";
	
	public boolean addUser(Client client) {
		boolean result2 = userDAO.addUser(client);
		boolean result1 = false;
		Connection conn = null;
		
		Object values[] = new Object[]{client.getUsername(), client.getRoot(), client.getDomen(), client.isCreate(), client.isRetrieve(), client.isUpdate(), client.isDelete()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_INSERT_CLIENT_REST, false, values);
			pre.executeUpdate();
			
			if (pre.getUpdateCount() > 0)
				result1 = true;
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return result1 && result2;
	}
	
	public List<Client> getAllAdminS(){
		List<Client> arr = new ArrayList<Client>();
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_CLIENT, false);
			rs = pre.executeQuery();
			
			while (rs.next()) {
				arr.add(new Client(rs.getString("username"), "", rs.getString("path"), rs.getString("domen"),
						rs.getBoolean("flagCreate"), rs.getBoolean("flagRetrieve"), 
						rs.getBoolean("flagUpdate"), rs.getBoolean("flagDelete")));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return arr;
	}
	
	public boolean updateUser(String username, Client client) {
		boolean res1 = userDAO.updateUser(username, client);
		boolean res2 = false;
		Connection conn = null;
		Object []values = new Object[] {client.getRoot(), client.getDomen(), client.isCreate(), client.isRetrieve(), 
				client.isUpdate(), client.isDelete(), client.getUsername()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_CLIENT, false, values);
			int result = pre.executeUpdate();
			res2 = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res1 && res2;
	}
	
	public boolean deleteUser(String username) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_DELETE_CLIENT, false, values);
			int result = pre.executeUpdate();
			
			res = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res && userDAO.deleteUser(username);
	}
	
	public boolean checkIfExists(String username) {
		return userDAO.checkIfExists(username);
	}
	
	public Client getUser(String username) {
		Connection conn = null;
		ResultSet rs = null;
		Client client = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_RETRIEVE_CLIENT, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				client = new Client(rs.getString("username"), "", rs.getString("path"), rs.getString("domen"),
						rs.getBoolean("flagCreate"), rs.getBoolean("flagRetrieve"), rs.getBoolean("flagUpdate"),
						rs.getBoolean("flagDelete"));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return client;
	}

	
	public List<String> getAllPaths() {
		Connection conn = null;
		ResultSet rs = null;
		List<String> arr = new ArrayList<String>();
		Object []values = new Object[] {};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_RETRIEVE_ALL_PATHS, false, values);
			rs = pre.executeQuery();
			
			while (rs.next()) {
				arr.add(rs.getString("path"));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return arr;
	}
}
