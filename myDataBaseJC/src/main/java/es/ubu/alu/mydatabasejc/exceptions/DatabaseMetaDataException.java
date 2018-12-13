/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 *
 * @author jhuidobro
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
     */
    public DatabaseMetaDataException(String msg, String metodo) {
        super(msg);
        logger.error("metodo: {}\n{}",
                metodo, 
                msg);
    }

}
