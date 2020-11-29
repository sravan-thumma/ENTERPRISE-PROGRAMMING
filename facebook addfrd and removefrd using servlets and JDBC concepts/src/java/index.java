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
public class index extends HttpServlet {

Connection con;
Statement st,stt;
ResultSet rs,rss;
String temp=null;
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","40253224550");
            st=con.createStatement();
            stt=con.createStatement();
            System.out.print("Connection success!");
            st.execute("CREATE TABLE IF NOT EXISTS FRDLIST(FRDID INT,UID INT,FOREIGN KEY (UID) REFERENCES FBUSERS(SID))");
        System.out.print("FRDLIST Table created");
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
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
            if(req.getParameter("delete")!=null){
                try {
                    st.executeUpdate("delete from frdlist where uid="+id+" and frdid="+req.getParameter("delete"));
                    resp.sendRedirect("index");
                } catch (SQLException ex) {
                    Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ses.setMaxInactiveInterval(20);
            pw.print("Welcome::"+uname);
            pw.print("<a href='logout'><button>logout</button></a><br>");
            try {
                rs=st.executeQuery("select frdid from frdlist where uid="+id);
                while(rs.next()){
                    int i=rs.getInt("frdid");
                    pw.print(getname(i));
                    pw.print("<a href=\"index?delete="+i+"\"><button>Remove</button></a><br>");
                }
            } catch (SQLException ex) {
                Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
            }
            pw.print("<br><a href='addfrd'><button>Add friend</button></a>");
        }
        else{
            resp.sendRedirect("index.html");
        }
    }

    private String getname(int i) throws SQLException {
        rss=stt.executeQuery("select username from fbusers where sid="+i);
        while(rss.next()){
            temp=rss.getString("username");
        }
        return temp;
    }
    
}
