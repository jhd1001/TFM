/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc;

import es.ubu.alu.mydatabasejc.exceptions.MenuException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jhuidobro
 */
public class Menu {

    private String action;
    private String metodo;
    private String parametros;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public Menu(String action, String metodo, String parametros) {
        this.action = action;
        this.metodo = metodo;
        this.parametros = parametros;
    }

    public Menu(String action, String metodo, Class[] parametros) {
        ObjectOutputStream os = null;
        try {
            // lo escribe en un outputstream
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bs);
            os.writeObject(parametros);
            // y lo codifica en base 64 para ser enviado en una URL
            String params = Base64.encodeBase64URLSafeString(bs.toByteArray());
            // crea el metodo/valor. La propiedad es el nombre del método
            this.action = action;
            this.metodo = metodo;
            this.parametros = params;
        } catch (IOException ex) {
            new MenuException(ex, action, metodo);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
