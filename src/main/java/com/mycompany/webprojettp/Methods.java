/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webprojettp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import simplejdbc.DAOException;



/**
 *
 * @author pedago
 */
public class Methods extends simplejdbc.DAO {
    
    public Methods(DataSource dataSource) {
        super(dataSource);
    }

    
    /**
     *
     * @return le nombre d'enregistrements dans la table CUSTOMER
     * @throws DAOException
     */
    public List<String> statesList() throws DAOException {
		
        // Liste des états
        List<String> states = new LinkedList();
            
        String sql = "SELECT DISTINCT STATE FROM CUSTOMER";
	// Syntaxe "try with resources" 
	// cf. https://stackoverflow.com/questions/22671697/try-try-with-resources-and-connection-statement-and-resultset-closing
	try (   Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
       	     Statement stmt = connection.createStatement(); // On crée un statement pour exécuter une requête
             ResultSet rs = stmt.executeQuery(sql) // Un ResultSet pour parcourir les enregistrements du résultat
             ) {
                    
            while (rs.next()) { 
                // On récupère le champ STATE de l'enregistrement courant
                states.add(rs.getString("STATE"));
	    }
                    
	} catch (SQLException ex) {
	    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
	    throw new DAOException(ex.getMessage());
	}
                
        return states;
    }
}
