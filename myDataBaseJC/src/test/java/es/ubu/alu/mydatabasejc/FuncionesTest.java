/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jhuidobro
 */
public class FuncionesTest {
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testGetCatalog() {
        String retorno = Funciones.getCatalog("CATA", "ESQU");
        Assert.assertEquals("CATA.ESQU", retorno);
        retorno = Funciones.getCatalog(null, "ESQU");
        Assert.assertEquals("ESQU", retorno);
        retorno = Funciones.getCatalog("CATA", null);
        Assert.assertEquals("CATA", retorno);
        retorno = Funciones.getCatalog(null, null);
        Assert.assertEquals("", retorno);
        retorno = Funciones.getCatalog("", "ESQU");
        Assert.assertEquals("ESQU", retorno);
        retorno = Funciones.getCatalog("CATA", "");
        Assert.assertEquals("CATA", retorno);
        retorno = Funciones.getCatalog("", "");
        Assert.assertEquals("", retorno);
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
