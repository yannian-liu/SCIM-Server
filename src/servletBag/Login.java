package servletBag;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.List;
import java.sql.Connection.*;

import net.sf.json.JSONObject;


public class Login extends HttpServlet {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prestatment;

	
	@SuppressWarnings("null")
	public static void main(String[] args) throws ServletException, IOException 

	{
		loginQuery("1001");
	}
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("application/json");
    	//response.setContentType("application/json; charset=UTF-8");
        //获取请求的参数值  
        PrintWriter out = response.getWriter();  
        
        String userIDInput = request.getParameter("userIDInput");
//        String psd =request.getParameter("psd");
        
        JSONObject loginResponseJSON = new JSONObject(); 
        loginResponseJSON=loginQuery(userIDInput);
       // response.getWriter().write(loginResponseJSON.toString());
        out.write(loginResponseJSON.toString());
        //GET方式的请求乱码处理  
       // userName = new String(userName.getBytes("ISO8859-1"),"UTF-8");  
        //out.println(response);
        out.flush();
        out.close();


            //response.getOutputStream().print("Server  coming");
       
          
    }  
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        this.doGet(request, response);  
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
    
    

	public static JSONObject loginQuery(String userIDInput) {
		connection = getConnection(); // connect to database
		JSONObject loginJSON = new JSONObject();
		try {
			String sql = "select * from SCIMdatabase.users where user_id = " + 	userIDInput +";"; // select statement
			statement = (Statement) connection.createStatement();
			ResultSet rs = statement.executeQuery(sql); // execute statement
			
			
			System.out.println("Query results are：");
			while (rs.next()) { // not the last row
				String userIDString = rs.getString("user_id");
				String firstNameString = rs.getString("firstName");
				String lastNameString = rs.getString("lastName");
				String typeString = rs.getString("type");
				System.out.println(userIDString +"  " + firstNameString+"  " + lastNameString+ "  " + typeString);
				
				loginJSON.put("userID", userIDString);
				loginJSON.put("firstName", firstNameString);
				loginJSON.put("lastName", lastNameString);
				loginJSON.put("type",typeString);
				
			}
			System.out.println(loginJSON.toString());
			connection.close();
			System.out.println("Connection is over");
		} catch (SQLException e) {
			System.out.println("Query failed" + e.getMessage());
		}
		return loginJSON;
		
	}
	
}
