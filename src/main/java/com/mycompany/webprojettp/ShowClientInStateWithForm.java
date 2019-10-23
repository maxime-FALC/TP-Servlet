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
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
public class ShowClientInStateWithForm extends HttpServlet {

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
            List<String> states;
            
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
            
            
            // formulaire liste déroulante -- PREMIERE PARTIE --
            out.printf("<form action=\"/TP-Servlet/ShowClientInStateWithForm\" method=\"get\">");
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
                
            
            
            
            // Affichage des résultats -- DEUXIEME PARTIE --
            out.println("<br/>");
            
            
            try {   // Trouver la valeur du paramètre HTTP state
                String val = request.getParameter("state");
                if (val != null) {
                    // on doit convertir cette valeur en string (attention aux exceptions !)
                    String stateCustomers = String.valueOf(val);
 
                
                    // On récupère la liste de tout les clients
                    DAO daoF = new DAO(DataSourceFactory.getDataSource());
                    List<CustomerEntity> customersList = daoF.customersInState(stateCustomers);
                
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
                }
                
                
            } catch (Exception e) {
                out.printf("Erreur : %s", e.getMessage());
            }
            out.printf("<hr><a href='%s'>Retour au menu</a>", request.getContextPath());
            
            
            
            out.println("</body>");
            out.println("</html>");
        }
    }

}
