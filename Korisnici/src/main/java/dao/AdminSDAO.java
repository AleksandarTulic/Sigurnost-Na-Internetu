package dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;

import bean.AdminS;
import logger.MyLogger;

public class AdminSDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static UserDAO userDAO = new UserDAO();
	private static final String SQL_INSERT_ADMINS = "INSERT INTO adminS(username) values(?)";
	private static final String SQL_SELECT_ALL_ADMINS = "select u.username, u.role_name from users as u inner join adminS as a on a.username=u.username;";
	private static final String SQL_DELETE_ADMINS = "delete from adminS as a where a.username=?";

	public boolean addUser(AdminS adminS) {
		boolean result2 = userDAO.addUser(adminS);
		boolean result1 = false;
		Connection conn = null;
		
		Object values[] = new Object[]{adminS.getUsername()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_INSERT_ADMINS, false, values);
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
	
	public List<AdminS> getAllAdminS(){
		List<AdminS> arr = new ArrayList<AdminS>();
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_ADMINS, false);
			rs = pre.executeQuery();
			
			while (rs.next()) {
				arr.add(new AdminS(rs.getString("username"), ""));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		
		return arr;
	}
	
	public boolean deleteUser(String username) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_DELETE_ADMINS, false, values);
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
	
	public boolean updateUser(String username, AdminS adminS) {
		return userDAO.updateUser(username, adminS);
	}
	
	public boolean checkIfExists(String username) {
		return userDAO.checkIfExists(username);
	}
	
	public AdminS getUser(String username) {
		return new AdminS(username, "");
	}
}
