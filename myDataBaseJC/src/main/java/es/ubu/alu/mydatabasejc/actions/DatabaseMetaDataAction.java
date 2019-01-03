/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.Preparable;
import es.ubu.alu.mydatabasejc.PropiedadValor;
import es.ubu.alu.mydatabasejc.annotations.MetaDataLink;
import es.ubu.alu.mydatabasejc.exceptions.DatabaseMetaDataException;
import es.ubu.alu.mydatabasejc.exceptions.ResultSetException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionInfo;
import es.ubu.alu.mydatabasejc.jdbc.DatabaseMetaDataImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author jhuidobro
 */
public class DatabaseMetaDataAction extends MenuAction implements Preparable, SessionAware {

    private ConnectionImpl connectionImpl;
    private List<PropiedadValor> listInicial;
    private String metodo;
    private List<List> listInfo;
    private String parametros;
    Parameter[] arrayParametros; // parametros del método invocado
    private String linkAction;
    private String linkNamespace;
    private int linkColumnNumber;
    private boolean metodoLink;
    private String[] linkParametros;

    public Object getParameter(int i) {
        return sesion.get(arrayParametros[i].getName());
    }
    
    public List getArrayParametros() {
        List camposFiltro = new ArrayList();
        for (Parameter p : arrayParametros) {
            camposFiltro.add(p.getName());
        }
        return camposFiltro;
    }
    
    /**
     * Invoca el método de DatabaseMetaData con los parámetros adecuados y forma la 
     * lista con la información a presentar
     * @return "success" si no se produce error, "error" en caso contrario
     */
    public String info() {
        // Si metodo no contiene ningún valor, error
        if (metodo==null || metodo.length()==0) {
            addActionError(getText("Llamada.a.la.accion.resultset.incorrecta"));
            return ERROR;
        }
        try {
            Class[] parameterTypes = getParameterTypes(parametros);
            setParametrosSesion(parameterTypes);
            listInfo = (List<List>)getInvoke(connectionImpl.getConnection().getMetaData(), metodo, parameterTypes);
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | ClassNotFoundException | DatabaseMetaDataException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Invoca el método de DatabaseMetaData con los parámetros adecuados y forma la 
     * lista con la información a presentar
     * @return "success" si no se produce error, "error" en caso contrario
     */
    public String resultset() {
        // Recoge los mensajes de error previos y los establece en esta acción
        if (sesion.get(ACTION_ERROR)!=null) {
            addActionError(getText((String)sesion.get(ACTION_ERROR)));
            sesion.remove(ACTION_ERROR);
        }

        // Si metodo no contiene ningún valor, error
        if (metodo==null || metodo.length()==0) {
            addActionError(getText("Llamada.a.la.accion.resultset.incorrecta"));
            return ERROR;
        }
        ResultSet rs = null;
        try {
            Class[] parameterTypes = getParameterTypes(parametros);
            setParametrosSesion(parameterTypes);
            rs = (ResultSet)getInvoke(connectionImpl.getConnection().getMetaData(), metodo, parameterTypes);
            listInfo = getListInfo(rs, metodoLink, linkParametros);
        } catch (ResultSetException | SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | ClassNotFoundException | DatabaseMetaDataException ex) {
            try {if (rs!=null) rs.close();} catch (SQLException ex1) {;}
            addActionError(ex.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /** Obtenidos los tipos de los parámetros, se convierten los valores
     * recibidos a los tipos correspondientes y se ponen en la sesión para
     * sucesivas llamadas a este o a otros métodos que usen el mismo
     * parámetro (con el mismo nombre)
     * Convierte cada valor de filtroValores al tipo necesario según se indica 
     * en parameterTypes y lo añade a la sesión del usuario con el nombre 
     * correspondiente en filtroArgumentos
     * 
     * @param parameterTypes Array de clases de los argumentos del método que 
     * se va a invocar en un momento posterior. Este array marca el orden en el
     * que se procesan los elementos de filtroArgumentos y de filtroValores
     */
    private void setParametrosSesion(Class[] parameterTypes) {
        if (filtroArgumentos == null || filtroValores == null) return;
        for (int i = 0; i < parameterTypes.length; i++) {
            String atributo = filtroArgumentos[i];
            Object valor = null;
            if (!"".equals(filtroValores[i]))
                switch (parameterTypes[i].getName()) {
                    case "[Ljava.lang.String;": 
                        String[] valores = filtroValores[i].split(",",0); 
                        List<String> lvalores = new ArrayList();
                        for (int j = 0; j< valores.length; j++)
                            if (!"".equals(valores[j])) lvalores.add(valores[j].trim());
                        valor = lvalores.toArray(valores);
                        break;
                    case "boolean": valor = Boolean.valueOf(filtroValores[i]); break;
                    case "int": valor = Integer.valueOf(filtroValores[i]); break;
                    case "short": valor = Short.valueOf(filtroValores[i]); break;
                    default: valor = filtroValores[i];
                }
            sesion.put(atributo, valor);
        }
    }
    
    private Class[] getParameterTypes(String parametros) throws IOException, ClassNotFoundException {
        // se decodifica y obtiene el array de bytes que forman los parámetros del métod
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(parametros));
        ObjectInputStream ois = new ObjectInputStream(bais);
        // se obtiene el array de parámetros a partir de los datos obtenidos
        return (Class[])ois.readObject();
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
    private Object getInvoke(DatabaseMetaData databaseMetaData, String metodo, Class[] parameterTypes) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, DatabaseMetaDataException, IOException, ClassNotFoundException {
        // Obtiene el objeto DatabaseMetaDataImpl
        DatabaseMetaDataImpl dbMetadata = new DatabaseMetaDataImpl(databaseMetaData);
        // Se invoca el método solicitado, con los parámetros calculados
        Method method = DatabaseMetaDataImpl.class.getMethod(metodo, parameterTypes);

        // obtiene la anotación del tipo MetaDataLink para determinar si el método
        // debe conllevar un link asociado a cada registro del resultset
        MetaDataLink anotacion = method.getAnnotation(MetaDataLink.class);
        metodoLink = false;
        // si existe la anotación, se definen la acción, el namespace y los parámetros
        if (anotacion != null) {
            metodoLink = true;
            linkAction = anotacion.action();
            linkNamespace = anotacion.namespace();
            linkParametros = anotacion.parametros();
            linkColumnNumber = anotacion.columnNumber();
        }

        // Obtiene los nombres de los parametros
        arrayParametros = method.getParameters();
        // crea una lista de objetos que serán finalmente los parametros a pasar al metodo invocado
        List<Object> listaParametros = new ArrayList<>();
        // busca cada parametro en la sesión del usuario
        for (Parameter parametro : arrayParametros) {
            // y lo asigna a la lista de objetos a pasar
            Object o = sesion.get(parametro.getName());
            if (o!=null) if ("".equals(o.toString())) o = null;
            listaParametros.add(o);
        }
        // Obtiene la lista de objetos en forma de array. Serán los argumentos de 
        // la función invocada
        Object[] args = listaParametros.toArray();
        // e invoca al método pasandole los argumentos necesarios
        Object o = method.invoke(dbMetadata, args);
        if (o instanceof ResultSet) return (ResultSet)o;
        if (o instanceof List) return (List<List>)o;
        throw new DatabaseMetaDataException(getText("El.objeto.obtenido.no.es.del.tipo.adecuado"), metodo);
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

    /**
     * Se obtiene la conexión de la sesión del usuario
     *
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        connectionImpl = (ConnectionImpl) sesion.get(CONEXION);
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

    public String getLinkAction() {return linkAction;}

    public void setLinkAction(String linkAction) {this.linkAction = linkAction;}

    public String getLinkNamespace() {return linkNamespace;}

    public void setLinkNamespace(String linkNamespace) {this.linkNamespace = linkNamespace;}

    public boolean isMetodoLink() {return metodoLink;}

    public void setMetodoLink(boolean metodoLink) {this.metodoLink = metodoLink;}

    public String[] getLinkParametros() {return linkParametros;}

    public void setLinkParametros(String[] linkParametros) {this.linkParametros = linkParametros;}

    public int getLinkColumnNumber() {return linkColumnNumber;}

    public void setLinkColumnNumber(int linkColumnNumber) {this.linkColumnNumber = linkColumnNumber;}
    
}
