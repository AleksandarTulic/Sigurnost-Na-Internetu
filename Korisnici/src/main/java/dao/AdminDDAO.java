package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import bean.AdminD;
import logger.MyLogger;

public class AdminDDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static UserDAO userDAO = new UserDAO();
	private static final String SQL_INSERT_ADMIND_PATH = "INSERT INTO adminD(username, path) values(?, ?)";
	private static final String SQL_SELECT_ALL_ADMIND = "select u.username, u.role_name, a.path from users as u inner join adminD as a on a.username=u.username;";
	private static final String SQL_DELETE_ADMIND = "delete from adminD as a where a.username=?";
	private static final String SQL_RETRIEVE_ADMIND = "select u.username, u.role_name, a.path from users as u inner join adminD as a on a.username=u.username where u.username=?";
	private static final String SQL_UPDATE_ADMIND = "update adminD as a set a.path=? where a.username=?";
	private static final String SQL_CHECK_IF_PATH_CLEAR = "select count(*) as value "
			+ "from adminD as a where ? like (concat(a.path, '%'))\r\n"
			+ "or a.path like concat(?, '%');";
	
	public boolean addUser(AdminD admind) {
		boolean result2 = userDAO.addUser(admind);
		boolean result1 = false;
		Connection conn = null;
		
		Object values[] = new Object[]{admind.getUsername(), admind.getRoot()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_INSERT_ADMIND_PATH, false, values);
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
	
	public List<AdminD> getAllAdminS(){
		List<AdminD> arr = new ArrayList<AdminD>();
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_ADMIND, false);
			rs = pre.executeQuery();
			
			while (rs.next()) {
				arr.add(new AdminD(rs.getString("username"), "", rs.getString("path")));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		
		return arr;
	}
	
	public boolean updateUser(String username, AdminD adminD) {
		boolean flag = userDAO.updateUser(username, adminD);
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {adminD.getRoot(), adminD.getUsername()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_ADMIND, false, values);
			int result = pre.executeUpdate();
			res = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag && res;
	}
	
	public boolean deleteUser(String username) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_DELETE_ADMIND, false, values);
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
	
	public AdminD getUser(String username) {
		Connection conn = null;
		ResultSet rs = null;
		AdminD adminD = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_RETRIEVE_ADMIND, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				adminD = new AdminD(rs.getString("username"), "", rs.getString("path"));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return adminD;
	}
	
	public boolean getPathCondition(String path) {
		Connection conn = null;
		ResultSet rs = null;
		boolean res = true;
		Object []values = new Object[] {path, path};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_CHECK_IF_PATH_CLEAR, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				if (rs.getInt("value") > 0) {
					res = false;
				}
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
}
