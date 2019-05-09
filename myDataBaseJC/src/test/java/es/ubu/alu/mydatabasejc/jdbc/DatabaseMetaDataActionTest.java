/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.PropiedadValor;
import es.ubu.alu.mydatabasejc.actions.DatabaseMetaDataAction;
import es.ubu.alu.mydatabasejc.exceptions.ConnectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author jhuidobro
 */
public class DatabaseMetaDataActionTest extends DatosConexion {
    DatabaseMetaDataAction action;
    private List<PropiedadValor> listInicial;
    private ConnectionImpl conexion;
    
    public DatabaseMetaDataActionTest() {
        action = null;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    //@Before
    public void setUp() { 
        try {
            conexion = new ConnectionImpl(url, usuario, password);
            action = new DatabaseMetaDataAction();
            action.setConnectionImpl(conexion);
            listInicial = new ArrayList<PropiedadValor>();
        } catch (ConnectionException ex) {
            ;
        }
    }
    
    //@After
    public void tearDown() {
        try {
            conexion.close();
        } catch (Exception e) {
            ;
        }
    }

    //@org.junit.Test
    public void testListInicial() {
        try {
            System.out.println("testListInicial");
            if (!action.inicial().equals("success"))
                fail("El testInfo falló");
            listInicial = action.getListInicial();
            System.out.println("listInicial: " + listInicial);
        } catch (Exception e) {
            ;
        }
    }
}
