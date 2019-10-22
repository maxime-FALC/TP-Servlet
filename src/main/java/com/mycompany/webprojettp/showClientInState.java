/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webprojettp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
public class showClientInState extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowClient</title>");
            out.println("</head>");
            out.println("<body>");
            
            try {   // Trouver la valeur du paramètre HTTP state
                String val = request.getParameter("state");
                if (val == null) {
                    throw new Exception("Le paramètre state n'a pas été transmis");
                }
                // on doit convertir cette valeur en string (attention aux exceptions !)
                String stateCustomers = String.valueOf(val);
 
                
                // On récupère la liste de tout les clients
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                List<CustomerEntity> customersList = dao.customersInState(stateCustomers);
                
                if (customersList == null) {
                    throw new Exception("Etat inconnu ou sans clients");
                }
                
                // TITRE
                out.printf("<br/><br/>"
                        + "<h1>Liste des clients de l'imperium dans "
                        + "l'état : %s </h1>"
                        + "<br/><br/>", val);
                
                // Debut du tableau
                out.printf("<table border=1>");
                
                // Ligne d'introduction
                out.printf("<tr><b>"
                            + "<td> N° ID </td>"
                            + "<td> Nom </td>"
                            + "<td> Adresse </td>"
                        + "</b></tr>");
                
                // client traité
                CustomerEntity client;
                
                // Afficher les propriétés des clients         
                for(int i =0; i < customersList.size(); i++){
                    
                    // récupération du client
                    client = customersList.get(i);
                    
                    out.printf("<tr><td> %d </td><td> %s </td><td> %s </td>"
                            + "</tr>",
                            client.getCustomerId(),
                            client.getName(),
                            client.getAddressLine1()
                            );
                    
                }
                
                
                
                
                //Fin du tableau
                out.printf("</table>");
                
                
            } catch (Exception e) {
                out.printf("Erreur : %s", e.getMessage());
            }
            out.printf("<hr><a href='%s'>Retour au menu</a>", request.getContextPath());
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            Logger.getLogger("servlet").log(Level.SEVERE, "Erreur de traitement", ex);
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
