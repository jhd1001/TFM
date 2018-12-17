/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.exceptions;

import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 *
 * @author jhuidobro
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
