package servletBag;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
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
import java.sql.Connection.*;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;


public class GetSCIMHistory extends HttpServlet {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prestatment;

	
	@SuppressWarnings("null")
	public static void main(String[] args) throws ServletException, IOException 

	{
		String SCIMHistoryString = querySCIMHistory("All","All","All","20");
	}
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("application/json");
    	//response.setContentType("application/json; charset=UTF-8");
        //获取请求的参数值  
        PrintWriter out = response.getWriter();  
        
        String userIDInput = request.getParameter("userIDInput");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String startRowNum = request.getParameter("startRowNum");
//        String psd =request.getParameter("psd");
        
//        JSONObject loginResponseJSON = new JSONObject(); 
//        loginResponseJSON=querySCIMHistory(userIDInput);
        
        String SCIMHistoryString = querySCIMHistory(userIDInput,fromDate,toDate,startRowNum);
        
        
       // response.getWriter().write(loginResponseJSON.toString());
        out.write(SCIMHistoryString);
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
    
    

	public static String querySCIMHistory(String userIDInput, String fromDate, String toDate, String startRowNum ) {
		connection = getConnection(); // connect to database
		String SCIMHistoryString = "{\"SCIMHistory\":[";

		try {
			String sql = "";
			int flag = 0;
			String sqlUserIDInput = "";
			String sqlFromDate = "";
			String sqlToDate = "";
			
			if (!userIDInput.equals("All")) {
				sqlUserIDInput = " where user_id = "+ userIDInput;
				flag = 1;
			}
			if (!fromDate.equals("All")){
				System.out.println("xxx");
				if (flag == 0){
					sqlFromDate = " where dateAndTime >= '" + fromDate + " 00:00:00'";
				}else{
					sqlFromDate = " && dateAndTime >= '" + fromDate + " 00:00:00'";
				}
				flag =1;
			}
			if (!toDate.equals("All")){
				if (flag ==0){
					sqlToDate = " where dateAndTime <= '" + toDate + " 23:59:59'";
				}else{
					sqlToDate = " && dateAndTime <= '" + toDate + " 23:59:59'";
				}
				flag =1;
			}
			sql = "select * from SCIMdatabase.SCIMSelfReportResponse " + 	sqlUserIDInput + sqlFromDate +sqlToDate + " ORDER BY response_id DESC LIMIT "+ startRowNum +",20;"; // select statement
            System.out.println(sql);
			statement = (Statement) connection.createStatement();
			ResultSet rs = statement.executeQuery(sql); // execute statement
			
			System.out.println("Query results are：");
			
			int historyCount = 0;
			
			while (rs.next()) { // not the last row
				String responseIDString = rs.getString("response_id");
				String dateAndTimeString = rs.getString("dateAndTime");
				String userIDString = rs.getString("user_id");
				String Q01S1 = rs.getString("Q01S1");
				String Q02S2A = rs.getString("Q02S2A");
				String Q03S2B = rs.getString("Q03S2B");
				String Q04S3A = rs.getString("Q04S3A");
				String Q05S3B = rs.getString("Q05S3B");
				String Q06S4 = rs.getString("Q06S4");
				String Q07S5 = rs.getString("Q07S5");
				String Q08S6A = rs.getString("Q08S6A");
				String Q09S6B = rs.getString("Q09S6B");
				String Q10S6C = rs.getString("Q10S6C");
				String Q11S7A = rs.getString("Q11S7A");
				String Q12S7B = rs.getString("Q12S7B");
				String Q13S7C = rs.getString("Q13S7C");
				String Q14S8 = rs.getString("Q14S8");
				String Q15S9 = rs.getString("Q15S9");
				String Q16S10 = rs.getString("Q16S10");
				String Q17S11 = rs.getString("Q17S11");
				String Q18S12 = rs.getString("Q18S12");
				String Q19S13 = rs.getString("Q19S13");
				String Q20S14 = rs.getString("Q20S14");
				String Q21S15 = rs.getString("Q21S15");
				String Q22S16 = rs.getString("Q22S16");
				String Q23S17 = rs.getString("Q23S17");
				
				System.out.println(responseIDString +"  " + dateAndTimeString+"  " + userIDString+ "  " + Q01S1 + " " + Q02S2A+ " " + Q03S2B+" " + "...");
				
				JSONObject oneHistory = new JSONObject();
				
				oneHistory.put("responseID", responseIDString);
				oneHistory.put("dateAndTime", dateAndTimeString);
				oneHistory.put("userID", userIDString);
				oneHistory.put("Q01S1",Q01S1);
				oneHistory.put("Q02S2A",Q02S2A);
				oneHistory.put("Q03S2B",Q03S2B);
				oneHistory.put("Q04S3A",Q04S3A);
				oneHistory.put("Q05S3B",Q05S3B);
				oneHistory.put("Q06S4",Q06S4);
				oneHistory.put("Q07S5",Q07S5);
				oneHistory.put("Q08S6A",Q08S6A);
				oneHistory.put("Q09S6B",Q09S6B);
				oneHistory.put("Q10S6C",Q10S6C);
				oneHistory.put("Q11S7A",Q11S7A);
				oneHistory.put("Q12S7B",Q12S7B);
				oneHistory.put("Q13S7C",Q13S7C);
				oneHistory.put("Q14S8",Q14S8);
				oneHistory.put("Q15S9",Q15S9);
				oneHistory.put("Q16S10",Q16S10);
				oneHistory.put("Q17S11",Q17S11);
				oneHistory.put("Q18S12",Q18S12);
				oneHistory.put("Q19S13",Q19S13);
				oneHistory.put("Q20S14",Q20S14);
				oneHistory.put("Q21S15",Q21S15);
				oneHistory.put("Q22S16",Q22S16);
				oneHistory.put("Q23S17",Q23S17);
				
				String oneHistoryString = oneHistory.toString();
				if (historyCount ==0) {
					SCIMHistoryString = SCIMHistoryString+oneHistoryString;
				}else{
					SCIMHistoryString = SCIMHistoryString+","+oneHistoryString;
				}
				
                historyCount ++;
			}
			SCIMHistoryString = SCIMHistoryString+"]}";
			//SCIMHistoryArray=(String[]) SCIMHistoryArrayList.toArray();
			
			System.out.println(SCIMHistoryString);
			connection.close();
			System.out.println("Connection is over");
		} catch (SQLException e) {
			System.out.println("Query failed" + e.getMessage());
		}
		return SCIMHistoryString;
		
	}
	
}
