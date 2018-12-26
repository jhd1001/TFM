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
public class TablasActionException extends Throwable {
    final static Logger logger = (Logger) LogManager.getLogger(TablasActionException.class);
    ConnectionImpl connectionImpl;

    public TablasActionException(String message) {
        super(message);
        logger.error("Mensaje: {}", message);
    }

    public TablasActionException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Mensaje: {}", message);
    }

    public TablasActionException(Throwable cause) {
        super(cause);
        logger.error("causa: {}", cause.getLocalizedMessage());
    }

}
