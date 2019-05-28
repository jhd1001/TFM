package es.ubu.alu.mydatabasejc.exceptions;

import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Excepcion para el tratamiento de errores en la ejecución de opciones
 * del menu
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class MenuException extends Throwable {
    final static Logger logger = (Logger) LogManager.getLogger(MenuException.class);
    ConnectionImpl connectionImpl;

    public MenuException(Throwable cause, String action, String metodo) {
        super(cause);
        logger.error("action: {}:\nmetodo: {}\n{}", action, metodo, cause.getLocalizedMessage());
    }
    
    /**
     * Creates a new instance of <code>ConnectionException</code> without detail
     * message.
     */
    public MenuException() {
        super();
    }

}
