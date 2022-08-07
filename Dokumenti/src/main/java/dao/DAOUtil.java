package dao;

import java.sql.*;

public class DAOUtil {
	public static PreparedStatement prepareStatement(Connection connection,
			String sql, boolean returnGeneratedKeys, Object... values) throws SQLException{
		PreparedStatement prepared = connection.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		setValues(prepared, values);
		return prepared;
	}
	
	public static void setValues(PreparedStatement prepared, Object ... values) throws SQLException{
		for (int i=0;i<values.length;i++) {
			prepared.setObject(i+1, values[i]);
		}
	}
}
