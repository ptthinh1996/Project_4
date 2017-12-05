

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCStatement {
		public static void statement() {
		try {
		Statement statement=JDBCConectionMysql.getJDBCConnection().createStatement();
		String sql="SELECT * FROM student.info";
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next())
		{
			int id=rs.getInt("id");
			String fname=rs.getString("fname");
			String lname=rs.getString("lname");
			String bd=rs.getString("bd");
			String email=rs.getString("email");
			System.out.println(id+" "+fname+" "+lname+" "+bd+" "+email);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
