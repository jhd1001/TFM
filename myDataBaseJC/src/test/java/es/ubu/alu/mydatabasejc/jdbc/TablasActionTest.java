/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.actions.TablasAction;
import es.ubu.alu.mydatabasejc.exceptions.ConnectionException;
import java.sql.Connection;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 */
public class TablasActionTest extends DatosConexion {
    TablasAction action;
    
    public TablasActionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws ConnectionException {
        action = new TablasAction();
        action.setSession(new HashMap<String,Object>());
        action.setConnectionImpl(new ConnectionImpl(url, usuario, password));
        //System.out.println(action.getConnectionImpl());
        action.validate();
        action.setTABLE_SCHEM("");
        action.setTABLE_NAME("contrato");
        action.setFiltroArgumentos(new String[]{"contrato_id","cuentabancaria","inquilino_nif","limpieza","renta","comunidad","agua","administracion"});
        action.setFormValores(new String[]{"","","11963518E","","","","",""});
    }
    
    @org.junit.Test
    public void testConsulta() {
        String retorno = action.consulta();
        System.out.println(retorno);
        for (String column : action.getArrayParametros()) {
            System.out.println(column);
        }
        assertEquals("success", retorno);
    }

    @After
    public void tearDown() {
        action.getConnectionImpl().close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
