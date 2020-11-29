/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Vamsi
 */
public class valid extends HttpServlet {
Connection con;
Statement st;
ResultSet rs;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","40253224550");
            st=con.createStatement();
            System.out.print("Connection success!");
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(valid.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(valid.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname=req.getParameter("uname");
        String pass=req.getParameter("pass");
    try {
        rs=st.executeQuery("select * from fbusers where username='"+uname+"'");
        System.out.print("Selected");
        HttpSession ses;
        while(rs.next()){
            if(pass.equals(rs.getString("password"))){
                ses=req.getSession();
                ses.setAttribute("uname",uname);
                ses.setAttribute("uid",rs.getInt("sid"));
                resp.sendRedirect("index");
            }
            else
             resp.sendRedirect("error.html");
        }
        resp.sendRedirect("error.html");
    } catch (SQLException ex) {
        Logger.getLogger(valid.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

}
