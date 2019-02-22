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


public class SubmitSCIMSelfReport extends HttpServlet {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prestatment;

	
	@SuppressWarnings("null")
	public static void main(String[] args) throws ServletException, IOException 

	{
		//submitInsert ("1003","d","e");
		submitInsert ("9999-12-31 23:59:59","1001","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1");
	}
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        PrintWriter out = response.getWriter();  

        
        String timestamp = request.getParameter("timestamp");
        String userIDInput = request.getParameter("userIDInput"); 
        String Q1AnsInput = request.getParameter("Q1AnsInput");
        String Q2AnsInput = request.getParameter("Q2AnsInput");
        String Q3AnsInput = request.getParameter("Q3AnsInput");
        String Q4AnsInput = request.getParameter("Q4AnsInput");
        String Q5AnsInput  = request.getParameter("Q5AnsInput");
        String Q6AnsInput = request.getParameter("Q6AnsInput");
        String Q7AnsInput = request.getParameter("Q7AnsInput");
        String Q8AnsInput = request.getParameter("Q8AnsInput");
        String Q9AnsInput = request.getParameter("Q9AnsInput");
        String Q10AnsInput = request.getParameter("Q10AnsInput");
        String Q11AnsInput = request.getParameter("Q11AnsInput");
        String Q12AnsInput = request.getParameter("Q12AnsInput");
        String Q13AnsInput = request.getParameter("Q13AnsInput");
        String Q14AnsInput= request.getParameter("Q14AnsInput");
        String Q15AnsInput = request.getParameter("Q15AnsInput"); 
        String Q16AnsInput = request.getParameter("Q16AnsInput");
        String Q17AnsInput = request.getParameter("Q17AnsInput");
        String Q18AnsInput = request.getParameter("Q18AnsInput");
        String Q19AnsInput = request.getParameter("Q19AnsInput");
        String Q20AnsInput = request.getParameter("Q20AnsInput");
        String Q21AnsInput = request.getParameter("Q21AnsInput");
        String Q22AnsInput = request.getParameter("Q22AnsInput");
        String Q23AnsInput  = request.getParameter("Q23AnsInput");
        
      //  JSONObject loginResponseJSON = new JSONObject(); 
        
        submitInsert (timestamp,  userIDInput,  Q1AnsInput,  Q2AnsInput,  Q3AnsInput,  Q4AnsInput,  Q5AnsInput ,  Q6AnsInput,  Q7AnsInput,  Q8AnsInput,  Q9AnsInput,  Q10AnsInput,  Q11AnsInput,  Q12AnsInput,  Q13AnsInput,  Q14AnsInput,  Q15AnsInput,  Q16AnsInput,  Q17AnsInput,  Q18AnsInput,  Q19AnsInput,  Q20AnsInput,  Q21AnsInput,  Q22AnsInput,  Q23AnsInput);
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
    
    
    public static void submitInsert(String timestamp, String userIDInput, String Q1AnsInput, String Q2AnsInput, String Q3AnsInput, String Q4AnsInput, String Q5AnsInput , String Q6AnsInput, String Q7AnsInput, String Q8AnsInput, String Q9AnsInput, String Q10AnsInput, String Q11AnsInput, String Q12AnsInput, String Q13AnsInput, String Q14AnsInput, String Q15AnsInput, String Q16AnsInput, String Q17AnsInput, String Q18AnsInput, String Q19AnsInput, String Q20AnsInput, String Q21AnsInput, String Q22AnsInput, String Q23AnsInput)
	{
		connection = getConnection();
		try {
			//\"testname\",\"testpassword\"
			//String sql = "insert into SCIMdatabase.test (user_id,Q1,Q2) values(" + userIDInput + ",\""+Q1_ansInput +"\",\""+ Q2_ansInput +"\""+ ")";
			String sql = "insert into SCIMdatabase.SCIMSelfReportResponse (dateAndTime,user_id,Q01S1,Q02S2A,Q03S2B,Q04S3A,Q05S3B,Q06S4, Q07S5,Q08S6A,Q09S6B,Q10S6C,Q11S7A,Q12S7B,Q13S7C,Q14S8,Q15S9,Q16S10,Q17S11,Q18S12,Q19S13,Q20S14,Q21S15,Q22S16,Q23S17) VALUES ('" + timestamp +"'," + userIDInput + "," +Q1AnsInput  + "," +Q2AnsInput + "," + Q3AnsInput + "," +Q4AnsInput + "," +Q5AnsInput + "," +Q6AnsInput + "," +Q7AnsInput + "," +Q8AnsInput + "," +Q9AnsInput + "," +Q10AnsInput + "," +Q11AnsInput + "," +Q12AnsInput + "," +Q13AnsInput + "," + Q14AnsInput + "," +Q15AnsInput + "," +Q16AnsInput + "," + Q17AnsInput + "," + Q18AnsInput + "," +Q19AnsInput + "," +Q20AnsInput + "," +Q21AnsInput + "," +Q22AnsInput + "," +Q23AnsInput + ")";
			
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
