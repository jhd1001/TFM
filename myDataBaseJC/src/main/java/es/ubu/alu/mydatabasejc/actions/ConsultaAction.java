/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.Preparable;
import es.ubu.alu.mydatabasejc.PropiedadValor;
import es.ubu.alu.mydatabasejc.exceptions.DatabaseMetaDataException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionInfo;
import es.ubu.alu.mydatabasejc.jdbc.DatabaseMetaDataImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author jhuidobro
 */
public class ConsultaAction extends LoginAction implements Preparable {

    private ConnectionImpl connectionImpl;
    private List<PropiedadValor> listInfo;
    private List<PropiedadValor> listMenu;
    private String metodo;
    private List<List> listResultSet;
    
    public String resultset() {
        // Si metodo no contiene ningún valor, error
        if (metodo==null || metodo.length()==0) {
            addActionError("Llamada.a.la.accion.resultset.incorrecta");
            return ERROR;
        }
        try {
            ResultSet rs = getResultSet(connectionImpl.getConnection().getMetaData(), metodo);
            listResultSet = getListResultSet(rs);
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | DatabaseMetaDataException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Ejecuta el método del objeto databaseMetaData indicado. Devuelve el resultset
     * resultado de la ejecución.
     * @param databaseMetaData
     * @param metodo
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws DatabaseMetaDataException Si el resultado de la ejecución del método
     * no produce un objeto de tipo ResultSet se genera una excepción de este tipo
     */
    private ResultSet getResultSet(DatabaseMetaData databaseMetaData, String metodo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, DatabaseMetaDataException {
        // Obtiene el objeto DatabaseMetaDataImpl
        DatabaseMetaDataImpl dbMetadata = new DatabaseMetaDataImpl(databaseMetaData);
        // Se invoca el método solicitado
        Method method = DatabaseMetaDataImpl.class.getMethod(metodo, null);
        Object o = method.invoke(dbMetadata, new Object[]{});
        if (!(o instanceof ResultSet)) {
            throw new DatabaseMetaDataException("El.objeto.obtenido.no.es.del.tipo.adecuado", metodo);
        }
        return (ResultSet)o;
        
    }
    /**
     * Obtiene en una List<List> el resultset recibido. El primer elemento de
     * la lista representa los nombres de los campos del resultset subyacente
     * @param resultSet
     * @return
     * @throws SQLException 
     */
    private List<List> getListResultSet(ResultSet resultSet) throws SQLException {
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
        while (resultSet.next()) {
            List record = new ArrayList();
            for (int j = 1; j < i; j++)
                record.add(resultSet.getObject(j));
            lista.add(record);
        }
        return lista;
    }
    
    public List<PropiedadValor> getListMenu() { //throws IOException {
        // inicia la lista de valores del menu
        listMenu = new ArrayList<>();
        // A continuación obtiene los métodos de la MetaData que devuelven
        // objetos de tipo ResultSet que serán añadidos como opciones de menú
        Method[] methods = DatabaseMetaDataImpl.class.getMethods();
        int index;
        // para cada método
        for (Method method : methods) {
            // solo si el método retorna un tipo ResultSet
            if (method.getReturnType()==ResultSet.class) {
                ObjectOutputStream os = null;
                try {
System.out.println("1");
                    // crea el metodo/valor. La propiedad es el nombre del método
                    // y el valor es el resultado de la ejecución del método
                    Class[] parametros = method.getParameterTypes();
System.out.println("2");
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
System.out.println("3");
                    //Base64OutputStream b64os = new Base64OutputStream(bs);
                    os = new ObjectOutputStream(bs);
System.out.println("4");
                    //ObjectOutputStream os = new ObjectOutputStream(b64os);
                    os.writeObject(parametros);
System.out.println("5");
                    byte[] bsArray = bs.toByteArray();
System.out.println("5.1");
                    String params = Base64.encodeBase64URLSafeString(bsArray);
System.out.println("6");
                    PropiedadValor p = new PropiedadValor(method.getName(), params);
System.out.println("7");
                    // lo busca en la lista
                    if ((index = listMenu.indexOf(p))==-1) {
                        // y lo añade el nombre del método a la lista si no existe aún
                        listMenu.add(p);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        os.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return listMenu;
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
        listInfo = new ArrayList<>();
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
                listInfo.add(p);
            }
        }
        return SUCCESS;
    }

    /**
     * Se obtiene la conexión de la sesión del usuario
     *
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        connectionImpl = (ConnectionImpl) request.getSession().getAttribute("conexion");
    }

    public ConnectionImpl getConnectionImpl() {return connectionImpl;}

    public void setConnectionImpl(ConnectionImpl connectionImpl) {this.connectionImpl = connectionImpl;}

    public List<PropiedadValor> getListInfo() {return listInfo;}

    public void setListInfo(List<PropiedadValor> listInfo) {this.listInfo = listInfo;}

    public void setListMenu(List<PropiedadValor> listMenu) {this.listMenu = listMenu;}

    public String getMetodo() {return metodo;}

    public void setMetodo(String metodo) {this.metodo = metodo;    }

    public List<List> getListResultSet() {return listResultSet;}

    public void setListResultSet(List<List> listResultSet) {this.listResultSet = listResultSet;}
    
}
