package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Level;

import bean.UserAction;
import logger.MyLogger;

public class UserActionDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_CLIENT_ACTIONS = "select u.username, u.dateAction, u.timeAction, u.typeAction, u.documentName from useractions as u inner join client as a on u.username=a.username;";
	private static final String SQL_SELECT_ADMINS_ACTIONS = "select u.username, u.dateAction, u.timeAction, u.typeAction, u.documentName from useractions as u inner join adminS as a on u.username=a.username;";
	private static final String SQL_SELECT_ADMIND_ACTIONS = "select u.username, u.dateAction, u.timeAction, u.typeAction, u.documentName from useractions as u inner join adminD as a on u.username=a.username;";
	private static final String SQL_INPUT_USER_ACTION = "insert into useractions(username, dateAction, timeAction, typeAction, documentName) values(?, date(now()), time(now()), ?, ?)";

	public boolean addUserAction(UserAction userAction) {
		Connection conn = null;
		boolean result = false;
		Object values[] = new Object[]{userAction.getUsername(), userAction.getTypeAction(), userAction.getDocumentName()};
	
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_INPUT_USER_ACTION, false, values);
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
	
	public List<UserAction> getClientActions(){
		return getUserActions(SQL_SELECT_CLIENT_ACTIONS);
	}
	
	public List<UserAction> getAdminSActions(){
		return getUserActions(SQL_SELECT_ADMINS_ACTIONS);
	}
	
	public List<UserAction> getAdminDActions(){
		return getUserActions(SQL_SELECT_ADMIND_ACTIONS);
	}
	
	private List<UserAction> getUserActions(String sql){
		Connection conn = null;
		ResultSet rs = null;
		List<UserAction> arr = new ArrayList<UserAction>();
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, sql, false);
			rs = pre.executeQuery();
			
			while (rs.next()) {
				arr.add(new UserAction(rs.getString("username"), rs.getString("dateAction"), rs.getString("timeAction"),
						rs.getString("typeAction"), rs.getString("documentName")));
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
