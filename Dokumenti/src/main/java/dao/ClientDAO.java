package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import bean.Client;
import logger.MyLogger;

public class ClientDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_RETRIEVE_CLIENT = "select u.username, u.role_name, a.path, a.domen, a.flagCreate, a.flagRetrieve, a.flagUpdate, a.flagDelete from users as u inner join client as a on a.username=u.username where u.username=?;";
	
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
}
