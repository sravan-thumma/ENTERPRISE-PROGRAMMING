/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 *
 * @author Vamsi
 */
public class signup extends HttpServlet {
Connection con;
Statement st;
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","40253224550");
            st=con.createStatement();
            System.out.print("Connection success!");
            st.execute("CREATE TABLE IF NOT EXISTS fbusers(sid int not null AUTO_INCREMENT,username varchar(30),password varchar(30),PRIMARY KEY(sid))");
            System.out.print("Table created");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
        Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname=req.getParameter("uname");
        String pass=req.getParameter("pass");
    try {
        st.executeUpdate("INSERT INTO FBUSERS(USERNAME,PASSWORD) VALUES('"+uname+"','"+pass+"')");
        System.out.print("Inserted");
        resp.sendRedirect("index.html");
    } catch (SQLException ex) {
        Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    

}
