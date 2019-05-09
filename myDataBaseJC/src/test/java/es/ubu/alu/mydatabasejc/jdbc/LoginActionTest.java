/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.actions.LoginAction;
import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jhuidobro
 */
public class LoginActionTest extends DatosConexion {
    LoginAction action;
    
    public LoginActionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    //@Before
    public void setUp() {
        action = new LoginAction();
        action.setUrl(url);
        action.setUsuario(usuario);
        action.setPassword(password);
    }
    
    //@org.junit.Test
    public void testLogin() {
        try {
            System.out.println("testLogin");
            if (!action.login().equals("success"))
                fail("El login falló");
        } catch (Exception e) {
            ;
        }
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
