package es.ubu.alu.mydatabasejc.exceptions;

import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Excepciones producidas en la conexión con la base de datos
 * @author jhuidobro
 */
public class ConnectionException extends Throwable {
    final static Logger logger = (Logger) LogManager.getLogger(ConnectionException.class);
    ConnectionImpl connectionImpl;

    public ConnectionException(Throwable cause, String driver) {
        super(cause);
        logger.error("driver: {}:\n{}", driver, cause.getLocalizedMessage());
    }
    
    /**
     * Creates a new instance of <code>ConnectionException</code> without detail
     * message.
     */
    public ConnectionException() {
        super();
    }

    /**
     * Constructs an instance of <code>ConnectionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ConnectionException(String msg, ConnectionImpl connectionImpl) {
        super(msg);
        logger.error("url: {}\nusuario: {}:\n{}",
                connectionImpl.getUrl(), 
                connectionImpl.getUsuario(),
                msg);
    }

    public ConnectionException(Throwable cause, ConnectionImpl connectionImpl) {
        super(cause);
        logger.error("url: {}\nusuario: {}:\n{}",
                connectionImpl.getUrl(), 
                connectionImpl.getUsuario(),
                cause.getLocalizedMessage());
    }
    
}
