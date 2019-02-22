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


public class GetQOLHistory extends HttpServlet {
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prestatment;

	
	@SuppressWarnings("null")
	public static void main(String[] args) throws ServletException, IOException 

	{
		String SCIMHistoryString = queryQOLHistory("All","All","All","19");
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
        
        String SCIMHistoryString = queryQOLHistory(userIDInput,fromDate,toDate,startRowNum);
        
        
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
    
    

	public static String queryQOLHistory(String userIDInput, String fromDate, String toDate, String startRowNum) {
		connection = getConnection(); // connect to database
		String QOLHistoryString = "{\"QOLHistory\":[";
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
			sql = "select * from SCIMdatabase.QualityOfLifeResponse " + 	sqlUserIDInput + sqlFromDate +sqlToDate + " ORDER BY response_id DESC LIMIT "+ startRowNum +",20;"; // select statement
            System.out.println(sql);

			//String sql = "select * from SCIMdatabase.response where user_id = " + 	userIDInput +" ORDER BY qualityOfLifeResponse_id DESC;"; // select statement
			statement = (Statement) connection.createStatement();
			ResultSet rs = statement.executeQuery(sql); // execute statement
			
			System.out.println("Query results are：");
			
			int historyCount = 0;
			
			while (rs.next()) { // not the last row
				String responseIDString = rs.getString("response_id");
				String dateAndTimeString = rs.getString("dateAndTime");
				String userIDString = rs.getString("user_id");
				String Q1 = rs.getString("Q1");
				String Q2 = rs.getString("Q2");
				String Q3 = rs.getString("Q3");
				
				
				System.out.println(responseIDString +"  " + dateAndTimeString+"  " + userIDString+ "  " + Q1 + " " + Q2+ " " + Q3);
				
				JSONObject oneHistory = new JSONObject();
				
				oneHistory.put("responseID", responseIDString);
				oneHistory.put("dateAndTime", dateAndTimeString);
				oneHistory.put("userID", userIDString);
				oneHistory.put("Q1",Q1);
				oneHistory.put("Q2",Q2);
				oneHistory.put("Q3",Q3);
				
				
				String oneHistoryString = oneHistory.toString();
				if (historyCount ==0) {
					QOLHistoryString = QOLHistoryString+oneHistoryString;
				}else{
					QOLHistoryString = QOLHistoryString+","+oneHistoryString;
				}
				
                historyCount ++;
			}
			QOLHistoryString = QOLHistoryString+"]}";
			//SCIMHistoryArray=(String[]) SCIMHistoryArrayList.toArray();
			
			System.out.println(QOLHistoryString);
			connection.close();
			System.out.println("Connection is over");
		} catch (SQLException e) {
			System.out.println("Query failed" + e.getMessage());
		}
		return QOLHistoryString;
		
	}
	
}
