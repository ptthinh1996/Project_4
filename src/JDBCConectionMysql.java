

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConectionMysql {
	public static Connection getJDBCConnection() {
		final String url="jdbc:mysql://localhost:3306/student?characterEncoding=utf8";
		final String user="root";
		final String password="admin";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url,user,password);
		return connection;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
