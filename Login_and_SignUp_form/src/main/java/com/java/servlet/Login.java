package com.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	Connection conn =null;
	PreparedStatement ps = null;
	ResultSet res = null;
	String query = "select * from userdata where username =(?) and password =(?)";
	String url = "jdbc:mysql://localhost:3306/login_practice";
	String username = "root";
	String password = "Mushraf@2001";
	
	@Override
	public void init() throws ServletException {
		System.out.println("JDBC start loading");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Loaded Successfully");
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("Established connection");
		} catch (ClassNotFoundException | SQLException  e) {
			
			e.printStackTrace();
		}
	}
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String username = req.getParameter("us");
	String password1 = req.getParameter("password");
	PrintWriter pw = resp.getWriter();
	
	try {
		
		ps=conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password1);
		 res = ps.executeQuery();
		if(res.next())
		{
			pw.println("<h1>log in successfull</h1>");
			System.out.println("log in success");
		}
		else {
			pw.println("<h1>invalid data</h1>");
			System.out.println("invalid data");
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	
}
@Override
		public void destroy() {
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
}
}