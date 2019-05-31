package es.ubu.alu.mydatabasejc;

import es.ubu.alu.mydatabasejc.exceptions.MenuException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 * Gestiona los datos necesarios para mantener un opción del menu
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class Menu implements Comparable<Menu> {

    private String action;
    private String metodo;
    private String parametros;
    private String i18n;

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

    public Menu(String action, String metodo, String parametros, String i18n) {
        this.action = action;
        this.metodo = metodo;
        this.parametros = parametros;
        this.i18n = i18n;
    }

    /**
     * Consturye un objeto Menu a partir de los parámetros indicados. 
     * Transforma Class[] parametros en un String
     * @param action Acción a ejecutar
     * @param metodo Método a ejecutar vía reflection
     * @param parametros array de parámetros que necesita el método
     */
    public Menu(String action, String metodo, Class[] parametros, String i18n) {
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
            this.i18n = i18n;
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

    @Override
    public int compareTo(Menu o) {
        if (o==null) return -1;
        return -1*o.getI18n().compareToIgnoreCase(this.i18n);
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

}
