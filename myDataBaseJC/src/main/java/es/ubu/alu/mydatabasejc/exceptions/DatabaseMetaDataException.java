package es.ubu.alu.mydatabasejc.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Excepción producida en operaciones con el objeto DatabaseMetaDataImpl
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class DatabaseMetaDataException extends Throwable {
    final static Logger logger = (Logger) LogManager.getLogger(DatabaseMetaDataException.class);

    /**
     * Creates a new instance of <code>ConnectionException</code> without detail
     * message.
     */
    public DatabaseMetaDataException() {
        super();
    }

    /**
     * Constructs an instance of <code>ConnectionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param metodo Método que ocasiona la excepción
     */
    public DatabaseMetaDataException(String msg, String metodo) {
        super(msg);
        logger.error("metodo: {}\n{}",
                metodo, 
                msg);
    }

}
