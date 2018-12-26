/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.Preparable;
import es.ubu.alu.mydatabasejc.exceptions.ResultSetException;
import es.ubu.alu.mydatabasejc.exceptions.TablasActionException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author jhuidobro
 */
public class TablasAction extends MenuAction implements Preparable, SessionAware {
    private ConnectionImpl connectionImpl;
    private Map<String, Object> sesion;
    private String TABLE_SCHEM;
    private String TABLE_NAME;
    private List<List> listInfo;
    //private List arrayParametros;
    private Set<String> arrayParametros;

    public Object getParameter(int i) {
        String[] a = {};
        return sesion.get(TABLE_SCHEM + "." + TABLE_NAME + "." + arrayParametros.toArray(a)[i]);
    }

    /** Obtenidos los tipos de los parámetros, se convierten los valores
     * recibidos a los tipos correspondientes y se ponen en la sesión para
     * sucesivas llamadas a este método 
     * Convierte cada valor de filtroValores al tipo necesario según se indica 
     * en el mapa obtenido de la sesion y grabado previamente, en la primera
     * ejecución y lo añade a la sesión del usuario con el nombre 
     * correspondiente en filtroArgumentos
     * 
     * @param tabla nombre del esquema y la tabla. Se buscará en la sesión un objeto map
     * con este nombre. Contendrá un mapa de los campos del resultset y su tipo
     * @return mapa del resultset de la tabla con
     * @throws SQLException 
     */
    private Map<String, Integer> setParametrosSesion(String tabla) {
        // busca el mapa del resultset en la sesión del usuario
        Map<String, Integer> mapa = (Map<String, Integer>)sesion.get(tabla);
        // si el mapa aún no se ha creado
        if (mapa == null) {
            // se crea un mapa vacío
            mapa = new HashMap<String, Integer>();
            // y completa el hashmap a partir de un resultset vacío de la tabla
            // obtiene el resultset vacío para llegar al metadata
            ResultSet rs = null;
            try {
                rs = connectionImpl.getConnection().createStatement().executeQuery("SELECT * FROM " + tabla + " WHERE 1=2");
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    if (!rs.getMetaData().isSearchable(i)) continue;
                    // cada elemento del mapa tendrá el nombre y el tipo de las columnas buscables
                    mapa.put(rs.getMetaData().getColumnName(i), rs.getMetaData().getColumnType(i));
                }
                // finalmente se añade el mapa a la sesión para no tener que repetir esta
                // consulta vacía en la base de datos.
                sesion.put(tabla, mapa);
            } catch (SQLException ex) {
                new TablasActionException(ex);
            } finally {
                if (rs!=null) try {rs.close();} catch (SQLException e) {;}
            }
        }
        
        // En la segunda fase de la función, se analizan los parámetros de la request
        // para añadir a la sesión los datos de filtrado enviados por el usuario
        if (filtroArgumentos == null || filtroValores == null || 
            filtroArgumentos.length==0 || filtroValores.length==0) return mapa;
        // obtiene el conjunto de parámetros
        Set<String> set = mapa.keySet();
        int i = 0;
        // para cada columna del resultset
        for (String columna : set) {
            try {
                String atributo = TABLE_SCHEM + "." + TABLE_NAME + "." + filtroArgumentos[i];
                Object valor = null;
                // si el valor recibido no está en blanco
                if (!"".equals(filtroValores[i])) 
                    // se asigna a la variable valor
                    switch (mapa.get(columna)) {
                    //switch (rs.getMetaData().getColumnType(i)) {
                        case Types.ARRAY: 
                            String[] valores = filtroValores[i].split(",",0); 
                            List<String> lvalores = new ArrayList();
                            for (int k = 0; k< valores.length; k++)
                                if (!"".equals(valores[k])) lvalores.add(valores[k].trim());
                            valor = lvalores.toArray(valores);
                            break;
                        // para tipos Integer
                        case Types.BIGINT: 
                        case Types.INTEGER: 
                        case Types.ROWID: valor = Integer.valueOf(filtroValores[i]); break;
                        // para tipos Short
                        case Types.SMALLINT: valor = Short.valueOf(filtroValores[i]); break;
                        // para tipos boolean
                        case Types.BIT: 
                        case Types.BOOLEAN: valor = Boolean.valueOf(filtroValores[i]); break;
                        // para tipos fecha
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP:
                        case Types.TIME_WITH_TIMEZONE: valor = Date.valueOf(filtroValores[i]); break;
                        // para tipos float
                        case Types.DECIMAL:
                        case Types.DOUBLE:
                        case Types.FLOAT:
                        case Types.NUMERIC:
                        case Types.REAL: valor = Float.valueOf(filtroValores[i]); break;
                        // para tipos string
                        case Types.CHAR:
                        case Types.DATALINK:
                        case Types.LONGNVARCHAR:
                        case Types.LONGVARCHAR:
                        case Types.NCHAR:
                        case Types.NVARCHAR:
                        case Types.SQLXML:
                        case Types.VARCHAR: valor = filtroValores[i]; break;
                        // en otro caso, valor por defecto
                        default: valor = filtroValores[i];
                    }
                // y se añade a la sesión del usuario con el nombre de esquema,
                // tabla y columna
                sesion.put(atributo, valor);
            } catch (NumberFormatException ex) {
                new TablasActionException(ex);
            } finally {
                i++;
            }
        }
        // se devuelve el mapa del resultset para su uso posterior.
        return mapa;
    }

    /**
     * Consulta la tabla recibida en TABLE_NAME y en TABLE_SCHEM y la presenta
     * en jsp en forma de lista
     * @return 
     */
    public String consulta() {
        if (TABLE_SCHEM == null || "".equals(TABLE_SCHEM) || TABLE_NAME == null || "".equals(TABLE_NAME)) {
            addActionError("Faltan.esquema.o.nombre.de.tabla");
            return "back";
        }
        List<Object> listaParametros = new ArrayList<>();
        
        // establece y recoge los parámetros de búsqueda en sesión del usuario
        Map<String, Integer> mapa = setParametrosSesion(TABLE_SCHEM + "." + TABLE_NAME);

        // define el array de posibles parámetros para presentarlos en la jsp
        arrayParametros = mapa.keySet();
        // y la lista de parámetros que hay que añadir a la consulta sql
        String where = "";
        // para cada columna del resultset
        for (String columna : arrayParametros) {
            // se busca en la sesión del usuario
            Object o = sesion.get(TABLE_SCHEM + "." + TABLE_NAME + "." + columna);
            // si el parámetro existe y no es "", se añade la condición a la cadena where
            // y el valor a la lista de parametros
            if (o!=null && !"".equals(o.toString())) {
                // si no es el primer elemento se antepone AND
                if (listaParametros.size()!=0) where = where + "AND ";
                // si es el primer filtro, se antepone where
                else where = "where ";
                // y a continuación se añade el nombre del campo y la posición del parámetro.
                // esto evitará ataques por SQL inyection
                where = where + columna + " = ? ";
                listaParametros.add(o);
            }
        }
            

        PreparedStatement ps = null;
        ResultSet rs = null;
        // Se obtiene la cadena de consulta definitiva
        String sql = String.format("SELECT * FROM %s.%s %s", TABLE_SCHEM, TABLE_NAME, where);            
        try {
            // se obtiene el PreparedStatemtne
            ps = connectionImpl.getConnection().prepareStatement(sql);
            // se asignan los parámetros
            int parameterIndex = 1;
            for (Object o : listaParametros)
                ps.setObject(parameterIndex++, o);
            // y se resuelve el resultset
            rs = ps.executeQuery();

            // se transforma el resultset en una lista para su visualización
            String[] linkParametros = {};
            listInfo = getListInfo(rs, false, linkParametros);
        } catch (ResultSetException | Exception ex) {
            addActionError(ex.getMessage());
            return ERROR;
        } finally {
            cierraRS(rs, ps);
        }
        return SUCCESS;
    }
    
    /**
     * Se valida la conexión obtenida. Si error, se pasará a login.jsp
     */
    @Override
    public void validate() {
        super.validarLogin(connectionImpl);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.sesion = map;
    }

    /**
     * Se obtiene la conexión de la sesión del usuario
     *
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        connectionImpl = (ConnectionImpl) sesion.get("conexion");
    }

    public String getTABLE_SCHEM() {return TABLE_SCHEM;}

    public void setTABLE_SCHEM(String TABLE_SCHEM) {this.TABLE_SCHEM = TABLE_SCHEM;}

    public String getTABLE_NAME() {return TABLE_NAME;}

    public void setTABLE_NAME(String TABLE_NAME) {this.TABLE_NAME = TABLE_NAME;}

    public List<List> getListInfo() {return listInfo;}

    public void setListInfo(List<List> listInfo) {this.listInfo = listInfo;}

    public Set<String> getArrayParametros() {return arrayParametros;}

    public void setArrayParametros(Set<String> arrayParametros) {this.arrayParametros = arrayParametros;}

}
