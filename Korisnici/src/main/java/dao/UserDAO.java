package dao;

import java.sql.*;
import java.util.logging.Level;

import bean.User;
import logger.MyLogger;

public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_DELETE_USER = "delete from users as u where u.username=?";
	private static final String SQL_ADD_USER = "insert into users(username, password, role_name, date, time, keyValue) values(?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_USER = "update users as u set u.username=?, u.password=? where u.username=?";
	private static final String SQL_SELECT_USER = "select u.username, u.role_name from users as u where u.username=?";
	private static final String SQL_CHECK_IF_ACTIVE = "SELECT TIMESTAMPDIFF(second, concat(date, ' ', time), concat(date(now()), ' ', time(now()))) < 7200 as value from users as u where u.username=?";
	private static final String SQL_UPDATE_USER_ACTIVE = "update users as u set u.date=date(now()), u.time=time(now()) where u.username=?";
	private static final String SQL_UPDATE_USER_KEY = "update users as u set u.keyValue=? where u.username=?";
	private static final String SQL_SELECT_USER_KEY = "select u.keyValue from users as u where u.username=?";
	
	public boolean addUser(User user) {
		boolean result = false;
		Connection conn = null;
		
		Object values[] = new Object[]{user.getUsername(), user.getPassword(), user.getRole(), "1970-05-05", "12:00:00", "AAAAAAAAAAAAAAAAA"};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_ADD_USER, false, values);
			pre.executeUpdate();
			
			if (pre.getUpdateCount() > 0)
				result = true;
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return result;
	}
	
	public boolean deleteUser(String username) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_DELETE_USER, false, values);
			int result = pre.executeUpdate();
			
			res = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
	
	public boolean updateUser(String username, User user) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {user.getUsername(), user.getPassword(), username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_USER, false, values);
			int result = pre.executeUpdate();
			res = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
	
	public boolean checkIfExists(String username) {
		boolean res = false;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_USER, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				res = true;
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
	
	public boolean isActive(String username) {
		boolean res = false;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_CHECK_IF_ACTIVE, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				res = rs.getBoolean("value");
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
	
	public boolean updateActive(String username) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_USER_ACTIVE, false, values);
			int result = pre.executeUpdate();
			res = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
	
	public boolean updateKey(String username, String key) {
		boolean res = false;
		Connection conn = null;
		Object []values = new Object[] {key, username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_USER_KEY, false, values);
			int result = pre.executeUpdate();
			res = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return res;
	}
	
	public String getKey(String username) {
		Connection conn = null;
		ResultSet rs = null;
		String res = "AAAAAAAAAAAAAAAAAAAAAAAAAA";
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_USER_KEY, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				res = rs.getString("keyValue");
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
