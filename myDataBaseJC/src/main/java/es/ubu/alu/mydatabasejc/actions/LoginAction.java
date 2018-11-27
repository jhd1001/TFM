/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author jhuidobro
 */
public class LoginAction extends ActionSupport {

    private String url;
    private String usuario;
    private String password;
    
    /**
     * Se valida el intento de login
     * @return "error" si no se puede hacer login
     * "success" en caso de que el login se haya realizado correctametne
     */
    public String validarLogin() {
        // control de sesión iniciada
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getSession().isNew()) return ERROR;
        
        // control de validez de sesión iniciada
        
        // validación de login correcta
        return SUCCESS;
    }
    
    /**
     * Establece una conexión física con la base de datos a partir de los 
     * datos recibidos desde el formulario HTML en las propiedades pertinentes
     * y añade el objeto y otra información a la sesión web del usuario
     * @return 
     */
    public String login() {
        return SUCCESS;
    }
}
