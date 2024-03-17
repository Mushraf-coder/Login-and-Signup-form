package com.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp extends HttpServlet {
	String url = "jdbc:mysql://localhost:3306/login_practice";
	String username = "root";
	String password = "Mushraf@2001";
	Connection conn = null;
	PreparedStatement ps = null;
	String query = "insert into userdata(name,mobile,username,password) values(?,?,?,?)";
	@Override
	public void init() throws ServletException {	
	System.out.println("JDBC start loading");
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("loaded successfully");
		conn = DriverManager.getConnection(url,username,password);
		System.out.println("established connection");
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}

}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("fn");
		System.out.println(fname);
		String lname = req.getParameter("ln");
		System.out.println(lname);
		String mobile = req.getParameter("no");
		System.out.println(mobile);
		String username = req.getParameter("un");
		System.out.println(username);
		String paas = req.getParameter("paas");
		System.out.println(paas);
		PrintWriter pw = resp.getWriter();
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, fname+" "+lname);
			
			ps.setString(2, mobile);
			ps.setString(3, username);
			ps.setString(4, paas);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected>0) {
				System.out.println("Data updated and signup successfully");
				pw.println("<h1>Data updated and signup successfully</h1>");
				pw.println("<h1>name : <h1>"+fname+" "+lname );
				pw.println("<h1>mobile no : <h1>"+mobile);
				pw.println("<h1>username : <h1>"+username);
				pw.println("<h1>password : <h1>"+paas);
				
			}
			else {
				System.out.println("wrong credentials given try again");
				pw.println("<h3>wrong credentials given try again</h3>");
			}
			
		} catch (SQLException e) {
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
