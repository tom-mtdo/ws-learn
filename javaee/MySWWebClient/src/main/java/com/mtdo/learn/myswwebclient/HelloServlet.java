package com.mtdo.learn.myswwebclient;

import com.mtdo.learn.mywshelloservice.Hello;
import com.mtdo.learn.mywshelloservice.HelloService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet (name="HelloServlet", urlPatterns="/HelloServlet")
public class HelloServlet extends HttpServlet{
    @WebServiceRef(wsdlLocation="http://localhost:8080/MyWSHelloService/HelloService?WSDL")
    private HelloService service;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <title>Start Page</title>");
            out.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <h2>Webservice at " + request.getContextPath() + " </h2>");
            out.println("        <h3>From webservice: " + sayHello("World") + " </h3>");
            out.println("    </body>");
            out.println("</html>");            
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public String sayHello(String name){
        Hello port = service.getHelloPort();
        return port.sayHello(name);
    }
}
