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
 * Excepción producida al convertir un valor al tipo de objeto requerido
 * por un campo JDBC
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class ConversionException extends Throwable {
    final static Logger logger = (Logger) LogManager.getLogger(ConversionException.class);
    String fieldName;
    

    public ConversionException(String fieldName, Throwable cause) {
        super(cause);
        this.fieldName = fieldName;
        logger.error("causa: {}\ncampo: {}", cause.getLocalizedMessage(), fieldName);
    }

    public String getFieldName() {return fieldName;}

    public void setFieldName(String fieldName) {this.fieldName = fieldName;}

}
