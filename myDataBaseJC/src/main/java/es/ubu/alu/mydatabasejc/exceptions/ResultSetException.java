/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.exceptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Excepción para el tratamiento de errores en la ejecución de métodos
 * sobre un resultset
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class ResultSetException extends Throwable {
    final static Logger logger = (Logger) LogManager.getLogger(ResultSetException.class);

    /**
     * Creates a new instance of <code>ConnectionException</code> without detail
     * message.
     */
    public ResultSetException() {
        super();
    }

    /**
     * Constructs an instance of <code>ConnectionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param resultSet resultset que provoca la excepción
     * @throws SQLException si error SQL
     */
    public ResultSetException(String msg, ResultSet resultSet) throws SQLException {
        super(msg);
        logger.error("Statement: {}\n{}",
                resultSet.getStatement(), 
                msg);
    }

}
