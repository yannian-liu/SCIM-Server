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


public class SubmitQualityOfLife extends HttpServlet {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prestatment;

	
	@SuppressWarnings("null")
	public static void main(String[] args) throws ServletException, IOException 

	{
		//submitInsert ("1003","d","e");
		submitInsert ("2016-2-11 11:21:59","1001","1","1","1");
	}
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        PrintWriter out = response.getWriter();  

        
        String timestamp = request.getParameter("timestamp");
        String userIDInput = request.getParameter("userIDInput"); 
        String Q1AnsInput = request.getParameter("Q1AnsInput");
        String Q2AnsInput = request.getParameter("Q2AnsInput");
        String Q3AnsInput = request.getParameter("Q3AnsInput");
       
      //  JSONObject loginResponseJSON = new JSONObject(); 
        
        submitInsert (timestamp,  userIDInput,  Q1AnsInput,  Q2AnsInput,  Q3AnsInput);
        out.write("insert secessfully");

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
    
    
    public static void submitInsert(String timestamp, String userIDInput, String Q01AnsInput, String Q02AnsInput, String Q03AnsInput)
	{
		connection = getConnection();
		try {
			//\"testname\",\"testpassword\"
			//String sql = "insert into SCIMdatabase.test (user_id,Q1,Q2) values(" + userIDInput + ",\""+Q1_ansInput +"\",\""+ Q2_ansInput +"\""+ ")";
			String sql = "insert into SCIMdatabase.QualityOfLifeResponse (dateAndTime,user_id,Q1,Q2,Q3) VALUES ('" + timestamp +"'," + userIDInput + "," +Q01AnsInput  + "," +Q02AnsInput + "," + Q03AnsInput + ")";
			
			System.out.println(sql);
			statement = (Statement) connection.createStatement();
			int count = statement.executeUpdate(sql);// execute statement
			System.out.println("Insert " + count
					+ " records into departments table");
			connection.close();
		} catch (SQLException e) {
			System.out.println("insert record failed" + e.getMessage());
		}
	}

}
