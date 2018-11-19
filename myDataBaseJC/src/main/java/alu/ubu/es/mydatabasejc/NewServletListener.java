/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alu.ubu.es.mydatabasejc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author jhuidobro
 */
public class NewServletListener implements ServletContextListener {

    private Map<Integer, Hoja> mapa;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // creación del primer nivel
        int id = 1;
        mapa = new HashMap<>();
        mapa.put(id,new Hoja(id++,0,"Conexion"));
        mapa.put(id,new Hoja(id++,0,"DatabaseMetaData"));
        
        //Conexion conexion = new Conexion(con);
        Method[] metodos = DatabaseMetaData.class.getMethods();
        for (int i = 0; i < metodos.length; i++) {
            if (metodos[i].getReturnType().equals("interface java.sql.ResultSet")) {
                mapa.put(id, new Hoja(id++, 2, metodos[i].getName()));
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
