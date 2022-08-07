package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import bean.User;
import logger.MyLogger;

public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_USER = "select u.username, u.role_name from users as u where u.username=?";
	private static final String SQL_CHECK_IF_ACTIVE = "SELECT TIMESTAMPDIFF(second, concat(date, ' ', time), concat(date(now()), ' ', time(now()))) < 7200 as value from users as u where u.username=?";
	private static final String SQL_UPDATE_USER_ACTIVE = "update users as u set u.date=date(now()), u.time=time(now()) where u.username=?";
	private static final String SQL_SELECT_USER_KEY = "select u.keyValue from users as u where u.username=?";
	
	public User getUser(String username) {
		User user = null;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_USER, false, values);
			rs = pre.executeQuery();
			
			if (rs.next()) {
				user = new User(username, "", rs.getString("role_name"));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return user;
	}
	
	public boolean checkIfExists(String username) {
		User user = getUser(username);
		
		return user != null;
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
