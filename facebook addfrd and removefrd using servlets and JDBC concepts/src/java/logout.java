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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vamsi
 */
public class logout extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       HttpSession ses=req.getSession(false);
       if(ses!=null){
           ses.invalidate();
           resp.sendRedirect("index.html");
       }
       else{
           resp.sendRedirect("index.html");
       }
    }

}
