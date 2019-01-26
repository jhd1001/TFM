/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.exceptions;

import es.ubu.alu.mydatabasejc.jdbc.SQLCommand;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 *
 * @author jhuidobro
 */
public class SQLCommandException extends Throwable {
    private SQLCommand sqlCommand;
    final static Logger logger = (Logger) LogManager.getLogger(SQLCommandException.class);

    public SQLCommandException(ConversionException ce) {
        super(ce.getFieldName() + ": " + ce.getLocalizedMessage());
    }
    
    public SQLCommandException(SQLCommand aThis, int operacion) {
        super("La.operación.indicada.es.incorrecta");
        logger.error("La operación indicada es incorrecta: {}", 
                operacion == SQLCommand.OPERACION_UPDATE ? "UPDATE" : 
                        (operacion == SQLCommand.OPERACION_DELETE ? "DELETE" : 
                                "NO DEFINIDA"));
    }

    public SQLCommandException(SQLCommand aThis, SQLException ex) {
        super(ex);
        logger.error("SQL error: {}\nSQL: {}\nvalores SET: {}\nvalores WHERE: {}\nvalores INSERT: {}",
                ex.getMessage(), aThis.sql, aThis.listaParametrosSet, aThis.listaParametrosWhere, aThis.listaParametrosInsert);
    }

    public SQLCommandException(String TABLE_SCHEM, String TABLE_NAME, String localizedMessage) {
        super("Error.obteniendo.metadata.del.resultset");
        logger.error("Error obteniendo getMetaData del resultset de {}.{}\nError SQL: {}", TABLE_SCHEM, TABLE_NAME, localizedMessage);
    }

}
