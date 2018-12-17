/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.Preparable;
import es.ubu.alu.mydatabasejc.Menu;
import es.ubu.alu.mydatabasejc.PropiedadValor;
import es.ubu.alu.mydatabasejc.ValoresPorDefecto;
import es.ubu.alu.mydatabasejc.exceptions.DatabaseMetaDataException;
import es.ubu.alu.mydatabasejc.exceptions.ResultSetException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionInfo;
import es.ubu.alu.mydatabasejc.jdbc.DatabaseMetaDataImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author jhuidobro
 */
public class ConsultaAction extends LoginAction implements Preparable, SessionAware {

    private ConnectionImpl connectionImpl;
    private List<PropiedadValor> listInicial;
    private String metodo;
    private List<List> listInfo;
    private String parametros;
    private Map<String, Object> sesion;
    private List<Menu> menus;
    
    public String info() {
        // Si metodo no contiene ningún valor, error
        if (metodo==null || metodo.length()==0) {
            addActionError("Llamada.a.la.accion.resultset.incorrecta");
            return ERROR;
        }
        try {
            listInfo = (List<List>)getInvoke(connectionImpl.getConnection().getMetaData(), metodo, parametros);
            
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | ClassNotFoundException | DatabaseMetaDataException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public String resultset() {
        // Si metodo no contiene ningún valor, error
        if (metodo==null || metodo.length()==0) {
            addActionError("Llamada.a.la.accion.resultset.incorrecta");
            return ERROR;
        }
        ResultSet rs = null;
        try {
            rs = (ResultSet)getInvoke(connectionImpl.getConnection().getMetaData(), metodo, parametros);
            listInfo = getListInfo(rs);
        } catch (ResultSetException | SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | ClassNotFoundException | DatabaseMetaDataException ex) {
            try {if (rs!=null) rs.close();} catch (SQLException ex1) {;}
            addActionError(ex.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Ejecuta el método del objeto databaseMetaData indicado. Devuelve el resultset
     * resultado de la ejecución.
     * @param databaseMetaData
     * @param metodo Nombre del método a invocar
     * @param parametros Array de clases que conforman los parámetros del método
     * codificado en Base64
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws DatabaseMetaDataException Si el resultado de la ejecución del método
     * no produce un objeto de tipo ResultSet se genera una excepción de este tipo
     */
    private Object getInvoke(DatabaseMetaData databaseMetaData, String metodo, String parametros) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, DatabaseMetaDataException, IOException, ClassNotFoundException {
        // Obtiene el objeto DatabaseMetaDataImpl
        DatabaseMetaDataImpl dbMetadata = new DatabaseMetaDataImpl(databaseMetaData);
        // se decodifica y obtiene el array de bytes que forman los parámetros del métod
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(parametros));
        ObjectInputStream ois = new ObjectInputStream(bais);
        // se obtiene el array de parámetros a partir de los datos obtenidos
        Class[] parameterTypes = (Class[])ois.readObject();
        // Se invoca el método solicitado, con los parámetros calculados
        Method method = DatabaseMetaDataImpl.class.getMethod(metodo, parameterTypes);
        // Obtiene los nombres de los parametros
        Parameter[] arrayParametros = method.getParameters();
        // crea una lista de objetos que serán finalmente los parametros a pasar al metodo invocado
        List<Object> listaParametros = new ArrayList<>();
        // busca cada parametro en la sesión del usuario
        for (Parameter parametro : arrayParametros) {
            // y lo asigna a la lista de objetos a pasar
            listaParametros.add(sesion.get(parametro.getName()));
        }
        // Obtiene la lista de objetos en forma de array. Serán los argumentos de 
        // la función invocada
        Object[] args = listaParametros.toArray();
        // e invoca al método pasandole los argumentos necesarios
        Object o = method.invoke(dbMetadata, args);
        if (o instanceof ResultSet) return (ResultSet)o;
        if (o instanceof List) return (List<List>)o;
        throw new DatabaseMetaDataException("El.objeto.obtenido.no.es.del.tipo.adecuado", metodo);
    }
    
    /**
     * Obtiene en una List<List> el resultset recibido. El primer elemento de
     * la lista representa los nombres de los campos del resultset subyacente
     * @param resultSet
     * @return
     * @throws SQLException 
     */
    private List<List> getListInfo(ResultSet resultSet) throws SQLException, ResultSetException {
        List<List> lista = new ArrayList();
        // se obtiene el metadata del result set
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        // y se almacenan los nombres de las columnas como primer elemento
        // de la lista
        int i = 0;
        List cabecera = new ArrayList();
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
                throw new ResultSetException("Demasiados.registros.Filtrar", resultSet);
            List record = new ArrayList();
            for (int j = 1; j < i; j++)
                record.add(resultSet.getObject(j));
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

    /**
     * Se valida la conexión obtenida. Si error, se pasará a login.jsp
     */
    @Override
    public void validate() {
        super.validarLogin(connectionImpl);
    }

    public String inicial() {
        // Modela la clase de información de la conexión para su presentación
        ConnectionInfo connectionInfo = new ConnectionInfo(connectionImpl.getConnection());
        // inicia la lista de valores 
        listInicial = new ArrayList<>();
        // obtiene los métodos de la clase
        Method[] methods = ConnectionInfo.class.getMethods();
        // para cada método
        for (Method method : methods) {
            // solo de la clase base 8excluye los de clase padre
            if (method.getDeclaringClass().equals(connectionInfo.getClass())) {
                Object o = null;
                try {
                    o = method.invoke(connectionInfo, new Object[]{});
                } catch (Exception e) {
                    o = e.getLocalizedMessage();
                }
                // crea el metodo/valor. La propiedad es el nombre del método
                // y el valor es el resultado de la ejecución del método
                PropiedadValor p = new PropiedadValor(method.getName(), o);
                // y lo añade a la lista
                listInicial.add(p);
            }
        }
        return SUCCESS;
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

    public ConnectionImpl getConnectionImpl() {return connectionImpl;}

    public void setConnectionImpl(ConnectionImpl connectionImpl) {this.connectionImpl = connectionImpl;}

    public List<PropiedadValor> getListInicial() {return listInicial;}

    public void setListInicial(List<PropiedadValor> listInicial) {this.listInicial = listInicial;}

    public String getMetodo() {return metodo;}

    public void setMetodo(String metodo) {this.metodo = metodo;    }

    public List<List> getListInfo() {return listInfo;}

    public void setListInfo(List<List> listInfo) {this.listInfo = listInfo;}

    public String getParametros() {return parametros;}

    public void setParametros(String parametros) {this.parametros = parametros;}

    public void setMenus(List<Menu> menus) {this.menus = menus;}

}
