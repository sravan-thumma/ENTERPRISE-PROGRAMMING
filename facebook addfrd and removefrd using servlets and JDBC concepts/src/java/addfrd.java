/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vamsi
 */
public class addfrd extends HttpServlet {
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
        Logger.getLogger(addfrd.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(addfrd.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw=resp.getWriter();
        resp.setContentType("text/html");
        HttpSession ses=req.getSession(false);
        if(ses!=null){
            String uname=(String)ses.getAttribute("uname");
            int id=(int)ses.getAttribute("uid");
            ses.setMaxInactiveInterval(20);
            pw.print("Welcome::"+uname);
            pw.print("<a href='logout'><button>logout</button></a><br>");
            if(req.getParameter("add")!=null){
                try {
                    st.executeUpdate("insert into frdlist values("+req.getParameter("add")+","+id+")");
                    resp.sendRedirect("addfrd");
                } catch (SQLException ex) {
                    Logger.getLogger(addfrd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                rs=st.executeQuery("select * from fbusers where sid not in(select frdid from frdlist where uid="+id+")and sid not in("+id+")");
                pw.print("<h1>Add Friends:</h1>");
                while(rs.next()){
                    int i=rs.getInt("sid");
                    pw.println(rs.getString("username"));
                    pw.println("<a href=\"addfrd?add="+i+"\"><button>Add friend</button></a><br>");
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(addfrd.class.getName()).log(Level.SEVERE, null, ex);
            }
            pw.print("<br><a href='index'><button>Home</button></a>");
        }
        else{
            resp.sendRedirect("index.html");
        }
    }
    
}
