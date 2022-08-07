package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import bean.AdminD;
import logger.MyLogger;

public class AdminDDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_RETRIEVE_ADMIND = "select u.username, u.role_name, a.path from users as u inner join adminD as a on a.username=u.username where u.username=?";
	
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
}
