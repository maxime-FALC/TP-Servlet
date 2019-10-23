/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webprojettp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
public class appelClientInState extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //liste des états
            List<String> states = new LinkedList();
            
            //Object méthode qui permet de trouver la liste des états
            Methods dao = new Methods(DataSourceFactory.getDataSource());
            
            
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet appelClientInState</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet appelClientInState at " + request.getContextPath() + "</h1>");
            
            // TITRE
            out.printf("<br/><br/>"
                        + "<h3>Formulaire de recherche des clients : </h3> ");
            
            
            // formulaire liste déroulante 
            out.printf("<form action=\"/TP-Servlet/showClientInState\" method=\"get\">");
            out.printf("<select name =\"state\" >");
            
            try {
                
                states = dao.statesList();
                
                for(String s : states){
                    out.printf("<option>%s</option>", s);
                }
                
            } catch (DAOException ex) {
                Logger.getLogger(appelClientInState.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.printf("</select>");
            out.printf("<button type = \"submit\"> Confirmer </button>");
            out.printf("</form>");
                
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
