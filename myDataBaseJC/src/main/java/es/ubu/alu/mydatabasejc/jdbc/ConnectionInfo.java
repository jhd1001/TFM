package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.exceptions.ConnectionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Properties;

/**
 * Define los métodos informativos de la conexión
 * @author jhuidobro
 */
public class ConnectionInfo {
    protected Connection connection;
    
    public boolean isReadOnly() throws SQLException { 
        return connection.isReadOnly();
    }

    public String getSchema() throws SQLException {
        return connection.getSchema();
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    public String getCatalog() throws SQLException {
        return connection.getCatalog();
    }

    public Properties getClientInfo() throws SQLException {
        return connection.getClientInfo();
    }

    public int getHoldability() throws SQLException {
        return connection.getHoldability();
    }

    public int getNetworkTimeout() throws SQLException {
        return connection.getNetworkTimeout();
    }

    public int getTransactionIsolation() throws SQLException {
        return connection.getTransactionIsolation();
    }

    public ConnectionInfo(Connection connection) {
        this.connection = connection;
    }
    
}
