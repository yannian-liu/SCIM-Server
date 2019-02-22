package servletBag;

import java.sql.*;
import java.util.List;
import java.sql.Connection.*;

import net.sf.json.JSONObject;

//import ch.qos.logback.classic.selector.*;;

public class DatabaseConnection {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prestatment;
	
	public static void main(String[] args)

	{		

		JSONObject JSONObject = new JSONObject();
		JSONObject = loginQuery("1001");
		System.out.println(JSONObject);

		
	}
	
	
	
	public static JSONObject loginQuery(String userID) {
		connection = getConnection(); // connect to database
		JSONObject loginJSON = new JSONObject();
		try {
			String sql = "select * from users where userID = " + userID +";"; // select statement
			statement = (Statement) connection.createStatement();
			ResultSet rs = statement.executeQuery(sql); // execute statement
			
			
			System.out.println("Query results are：");
			while (rs.next()) { // not the last row
				String userIDString = rs.getString("userID");
				String firstNameString = rs.getString("firstName");
				String lastNameString = rs.getString("lastName");
				String typeString = rs.getString("type");
				System.out.println(userIDString +"  " + firstNameString+"  " + lastNameString+ "  " + typeString);
				
				loginJSON.put("userID", userIDString);
				loginJSON.put("type",typeString);
				
				
			}
			//System.out.println(loginJSON.toString());
			connection.close();
		} catch (SQLException e) {
			System.out.println("Query failed" + e.getMessage());
		}
		return loginJSON;
	}
	
	
	
	
	
	
	
	
	// query
	public static void query(String userID) {
		connection = getConnection(); // connect to database
		JSONObject loginJSON = new JSONObject();
		try {
			String sql = "select * from users where userID = " + userID +";"; // select statement
			statement = (Statement) connection.createStatement();
			ResultSet rs = statement.executeQuery(sql); // execute statement
			
			
			System.out.println("Query results are：");
			while (rs.next()) { // not the last row
				String userIDString = rs.getString("userID");
				String typeString = rs.getString("type");
				System.out.println(userIDString + "  " + typeString);
				
				loginJSON.put("userID", userIDString);
				loginJSON.put("firstName",typeString);
				
			}
			System.out.println(loginJSON.toString());
			connection.close();
		} catch (SQLException e) {
			System.out.println("Query failed" + e.getMessage());
		}
	}

	
	

	// insert
	public static void insert()
	{
		connection = getConnection();
		try {
			String sql = "insert into SCIM_DB.TEST(username,password) values(\"testname\",\"testpassword\")";
			statement = (Statement) connection.createStatement();
			int count = statement.executeUpdate(sql);// execute statement
			System.out.println("Insert " + count
					+ " records into departments table");
			connection.close();
		} catch (SQLException e) {
			System.out.println("insert record failed" + e.getMessage());
		}
	}


	// delete
	public static void delete() {
		connection = getConnection();
		try {
			String sql = "delete from SCIM_DB.TEST  where username = 'testname'";
			statement = (Statement) connection.createStatement();
			int count = statement.executeUpdate(sql);
			System.out.println("delete " + count
					+ " record(s) from departments");
			connection.close();
		} catch (SQLException e) {
			System.out.println("Delete record failed" + e.getMessage());
		}
	}
	public static void update() {
		connection = getConnection();
		try {
			String sql = "update departments set dept_name ='New_Lab' where dept_no  = 'd010'";
			statement = (Statement) connection.createStatement();
			int count = statement.executeUpdate(sql);
			System.out.println("update " + count + " records in departments");
			connection.close();
		} catch (SQLException e) {
			System.out.println("Update failed " + e.getMessage());
		}
	}

	public static Connection getConnection()
	{
		Connection c = null;
		String mysqlurl = "jdbc:mysql://localhost:3306/SCIMdatabase";
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
