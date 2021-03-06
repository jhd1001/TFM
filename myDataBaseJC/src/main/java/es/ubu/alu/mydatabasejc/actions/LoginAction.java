/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.ActionSupport;
import es.ubu.alu.mydatabasejc.exceptions.ConnectionException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 * Clase que gestiona las acciones relacionadas con el login y el 
 * control de acceso del usuario a la base de datos
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class LoginAction extends ActionSupport {
    protected String CONEXION = "conexion";
    private String url;
    private String usuario;
    private String password;

    /**
     * Obtiene el texto de la ayuda para una propiedad determinada
     * @param propiedad nombre de la propiedad
     * @return La propiedad idiomatizada
     */
    public String ayuda(String propiedad) {
        ResourceBundle myResources = null;
        try {
            myResources = ResourceBundle.getBundle("es/ubu/alu/mydatabasejc/help", this.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myResources!=null ? myResources.getString(propiedad) : propiedad;
    }
    
    /**
     * Realiza la validación de los campos de formulario
     */
    @Override
    public void validate() {
        super.validate(); 
        if ("".equals(url)) addFieldError("url", "La.URL.es.un.dato.obligatoria");
        if ("".equals(usuario)) addFieldError("usuario", "El.usuario.es.un.dato.obligatorio");
    }
    
    
    /**
     * Presenta siempre la pantalla para logarse
     * @return "success"
     */
    public String logear() {
        return SUCCESS;
    }
    
    /**
     * Se valida el intento de login. Si se produce un error en una conexión
     * previamente establecida, se intentará realizar de nuevo la conexión
     * con los datos previos mediante llamada al método login()
     * @return "error" si no se puede validar el login
     * "success" en caso de que el login se haya validado correctametne
     */
    public String validarLogin() {
        // control de validez de sesión iniciada
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getSession().isNew()) {
            addActionError(getText("Nueva sesión de usuario"));
            return ERROR;
        }
        ConnectionImpl connectionImpl = (ConnectionImpl)request.getSession().getAttribute(CONEXION);
        
        return validarLogin(connectionImpl);
    }
    
    /**
     * Se valida el intento de login. Si se produce un error en una conexión
     * previamente establecida, se intentará realizar de nuevo la conexión
     * con los datos previos mediante llamada al método login()
     * @return "error" si no se puede validar el login
     * "success" en caso de que el login se haya validado correctametne
     * @param connectionImpl Objeto que se debe validar
     */
    protected String validarLogin(ConnectionImpl connectionImpl) {
        // Conexion no establecida
        if (connectionImpl==null) {
            addActionError(getText("Conexión no realizada"));
            return ERROR;
        }
        
        // si la conexión se ha cerrado, se intenta establecer nuevamente
        if (connectionImpl.isClosed()) 
            return login(
                connectionImpl.getUrl(),
                connectionImpl.getUsuario(),
                connectionImpl.getPassword());

        // si la conexión no es válida, se intenta establecer nuevamente
        if (!connectionImpl.isValid(0)) 
            return login(
                connectionImpl.getUrl(),
                connectionImpl.getUsuario(),
                connectionImpl.getPassword());
                
        // validación de login correcta
        return SUCCESS;
    }
    
    /**
     * Establece una conexión física con la base de datos a partir de los 
     * datos recibidos desde el formulario HTML en las propiedades pertinentes
     * y añade el objeto a la sesión web del usuario
     * @return "error" si no se puede hacer login
     * "success" en caso de que se haya realizado el login correctamente
     */
    public String login() {
        return login(url, usuario, password);
    }
    
    /**
     * Intenta conseguir la conexión física con la base de datos. Si la consigue
     * la guarda en la sesión del usuario
     * @param url URL a la base de datos
     * @param usuario Usuario que se quiere conectar a la base de datos
     * @param password Clave del usuario
     * @return "error" si no se consigue la conexión, "success" en caso contrario
     */
    private String login(String url, String usuario, String password) {
        // obtiene la conexión, con control de errores
        try {
            ConnectionImpl connectionImpl = new ConnectionImpl(url, usuario, password);
            // se añade el objeto en la sesión del usuario, si ya existía se cambia
            HttpServletRequest request = ServletActionContext.getRequest();
            request.getSession().setAttribute(CONEXION, connectionImpl);
        } catch (ConnectionException ex) {
            addActionError(ex.getLocalizedMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

}
