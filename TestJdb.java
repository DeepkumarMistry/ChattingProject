import java.sql.*;
class TestJdb
{
	public static void main(String args[]) throws Exception
	{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		System.out.println("loaded...");
		Connection con=DriverManager.getConnection("jdbc:odbc:ddd","system","manager");
		System.out.println("connecting...");
		Statement stat=con.createStatement();

		ResultSet rs=stat.executeQuery("select * from ch");
		while(rs.next())
		{
			System.out.println(rs.getString(1));
		}
	}
}