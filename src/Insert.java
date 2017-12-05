

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Insert {
	public static void insertInfo(String fname,String lname,String bd,String email)
	{
		Connection connection=JDBCConectionMysql.getJDBCConnection();
		try {
			Statement statement=connection.createStatement();
			String sql="Insert into info(fname,lname,bd,email) values('"+fname+"','"+lname+"','"+bd+"','"+email+"')";
			statement.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
