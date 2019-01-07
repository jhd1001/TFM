/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import es.ubu.alu.mydatabasejc.Menu;
import es.ubu.alu.mydatabasejc.ValoresPorDefecto;
import es.ubu.alu.mydatabasejc.exceptions.ResultSetException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import es.ubu.alu.mydatabasejc.jdbc.DatabaseMetaDataImpl;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author jhuidobro
 */
public class MenuAction extends LoginAction implements SessionAware {
    protected String ACTION_ERROR = "ACTION_ERROR";
    protected String ACTION_MESSAGE = "ACTION_MESSAGE";
    private List<Menu> menus;
    protected String[] filtroArgumentos;
    protected String[] filtroValores;
    protected Map<String, Object> sesion;

    protected void cierraRS(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs!=null) rs.close();
        } catch (SQLException ex) {
            ;
        }
        try {
            if (ps!=null) ps.close();
        } catch (SQLException ex) {
            ;
        }
    }
    
      /**
     * Obtiene en una List<List> el resultset recibido. El primer elemento de
     * la lista representa los nombres de los campos del resultset subyacente
     * @param resultSet Resultset a convertir en List<List>
     * @param metodoLink Indica si el método que proporciona el resultset debe
     * tener un link cuando sea presentado en la jsp (true) o no (false)
     * @linkParametros Cuando se requiera incluir una columna de link (metodoLink = true)
     * se deberán indicar en este argumento la lista de parámetros que se deben
     * incluir en la primera columna de la lista con la información requerida 
     * para realizar el link correctamente.
     * @return
     * @throws SQLException 
     */
    protected List<List> getListInfo(ResultSet resultSet, boolean metodoLink, String[] linkParametros) throws SQLException, ResultSetException {
        return getListInfo(resultSet, metodoLink, linkParametros, null, null);
    }
    
   /**
     * Obtiene en una List<List> el resultset recibido. Los elementos de la lista
     * están organizados en forma de formulario, el primer elemento contiene el
     * nombre del campo, el segundo contiene el valor. En principio pensado para
     * resultset con un solo registro, aunque si tuviera más no cambiaría la 
     * organización de los datos.
     * @param resultSet Resultset a convertir en List<List>
     * @param metodoLink Indica si el método que proporciona el resultset debe
     * tener un link cuando sea presentado en la jsp (true) o no (false)
     * @param linkParametros Cuando se requiera incluir una columna de link (metodoLink = true)
     * se deberán indicar en este argumento la lista de parámetros que se deben
     * incluir en la primera columna de la lista con la información requerida 
     * para realizar el link correctamente.
     * @param argumentos Si este parametro es null, la primera columna contendrá
     * la cedena de la forma linkParmetro1=valor1&linkParametro2=valor2. Pero si 
     * este parametro no es nulo la cadena será de la forma [argumentos]=linkParametro1&
     * [valores]=valor1&[argumentos]=linkParametro2&[valores]=valor2
     * @param valores Contiene el nombre del parámetro bajo el que se incluyen los
     * valores
     * @return
     * @throws SQLException 
     */
    protected List<List> getListInfoReverse(ResultSet resultSet) { //throws SQLException, ResultSetException {
        try {
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        List<List> lista = new ArrayList();
        for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
            List record = new ArrayList();
            while (resultSet.next()) {
                if (record==null || record.size()==0) {
                    record = new ArrayList();
                    record.add(rsMetadata.getColumnName(i));
                }
                record.add(resultSet.getObject(i));
            }
            lista.add(record);
            resultSet.beforeFirst();
        }
        return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
    
   /**
     * Obtiene en una List<List> el resultset recibido. El primer elemento de
     * la lista representa los nombres de los campos del resultset subyacente
     * @param resultSet Resultset a convertir en List<List>
     * @param metodoLink Indica si el método que proporciona el resultset debe
     * tener un link cuando sea presentado en la jsp (true) o no (false)
     * @param linkParametros Cuando se requiera incluir una columna de link (metodoLink = true)
     * se deberán indicar en este argumento la lista de parámetros que se deben
     * incluir en la primera columna de la lista con la información requerida 
     * para realizar el link correctamente.
     * @param argumentos Si este parametro es null, la primera columna contendrá
     * la cedena de la forma linkParmetro1=valor1&linkParametro2=valor2. Pero si 
     * este parametro no es nulo la cadena será de la forma [argumentos]=linkParametro1&
     * [valores]=valor1&[argumentos]=linkParametro2&[valores]=valor2
     * @param valores Contiene el nombre del parámetro bajo el que se incluyen los
     * valores
     * @return
     * @throws SQLException 
     */
    protected List<List> getListInfo(ResultSet resultSet, boolean metodoLink, String[] linkParametros, String argumentos, String valores) throws SQLException, ResultSetException {
        List<List> lista = new ArrayList();
        // se obtiene el metadata del result set
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        // y se almacenan los nombres de las columnas como primer elemento
        // de la lista
        int i = 0;
        List cabecera = new ArrayList();
        // si es un método que debe llevar link, se añade un objeto antes de los datos
        // en cada registro. La primera columna tendrá los parámetros del link
        if (metodoLink) cabecera.add(null);
        for (i = 1; i <= rsMetadata.getColumnCount(); i++) {
            cabecera.add(rsMetadata.getColumnName(i));
        }
        lista.add(cabecera);
            
        // para cada registro del resultset se añade una lista con sus valores
        // a la lista del resultset
        int n = 1;
        while (resultSet.next()) {
            // si se supera el máximo de registros, se manda error
            if (n++>ValoresPorDefecto.numMaxRecords)
                throw new ResultSetException(getText("Demasiados.registros.Filtrar"), resultSet);
            List record = new ArrayList();
            String urlParametro = "";
            if (metodoLink) record.add(urlParametro);
            for (int j = 1; j < i; j++) {
                if (metodoLink) {
                    for (String linkParametro : linkParametros)
                        if (linkParametro.equals(resultSet.getMetaData().getColumnName(j))) {
                            if (argumentos==null)
                                urlParametro = urlParametro + "&" + linkParametro + "=" + resultSet.getObject(j);
                            else {
                                urlParametro = urlParametro + "&" + argumentos + "=" + linkParametro;
                                urlParametro = urlParametro + "&" + valores + "=" + resultSet.getObject(j);
                            }
                        }
                }
                record.add(resultSet.getObject(j));
            }
            if (metodoLink) record.set(0,urlParametro.substring(1));
            lista.add(record);
        }
        return lista;
    }
    
    public List<Menu> getMenus() { //throws IOException {
        // inicia la lista de valores del menu
        menus = new ArrayList<>();
        // A continuación obtiene los métodos de la MetaData que devuelven
        // objetos de tipo ResultSet que serán añadidos como opciones de menú
        Method[] methods = DatabaseMetaDataImpl.class.getMethods();
        int index;
        // para cada método
        for (Method method : methods) {
            // solo si el método retorna un tipo ResultSet
            if (method.getReturnType()==ResultSet.class || 
                method.getReturnType()==List.class) {
                    // crea el menu. 
                    Menu menu = new Menu(
                            method.getReturnType()==ResultSet.class ? "resultset" : "info",
                            method.getName(), 
                            method.getParameterTypes());
                    // lo busca en la lista
                    if ((index = menus.indexOf(menu))==-1) {
                        // y lo añade el nombre del método a la lista si no existe aún
                        menus.add(menu);
                    }
            }
        }
        return menus;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.sesion = map;
    }
    
    public void setMenus(List<Menu> menus) {this.menus = menus;}
    
    public void setFiltroArgumentos(String[] filtroArgumentos) {this.filtroArgumentos = filtroArgumentos;}
    
    public void setFiltroValores(String[] filtroValores) {this.filtroValores = filtroValores;}

}