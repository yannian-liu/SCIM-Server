package servletBag;

import java.sql.*;

public class DBConGenerator {

	public static Connection getConnection()

	{

		Connection c = null;

		String mysqlurl = "jdbc:mysql://localhost:3306/SCIM_DB";

		String username = "root";

		String password = "";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			c = DriverManager.getConnection(mysqlurl, username, password);

			System.out.println("Connect Success");

		}
		catch (ClassNotFoundException cnfex) {

			System.out.println("Failed to load JDBC driver.");

			cnfex.printStackTrace();

			System.exit(1);

		} catch (SQLException sqlex) {

			System.err.println("Unable to connect");

			sqlex.printStackTrace();

		}

		return c;

	}

}
