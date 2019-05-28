package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.exceptions.ConnectionException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Establece y manteniene la información de una conexión con la base de datos
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class ConnectionImpl {
    protected Connection connection;
    private String url;
    private String usuario;
    private String password;
    private String driver;

    /**
     * Cierra la conexión si estuviera establecida
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ;
        } finally {
            connection = null;
            return;
        }
    }

    /**
     * Instancia un objeto de la clase ConnectionImpl
     * @param url URL de conexión a la base de datos
     * @param usuario Usuario de la base de datos
     * @param password Contraseña del usuario
     * @throws ConnectionException Cualquier error producido en el proceso
     * de conexión devuelve una excepción que es pasada previamente por 
     * el log del sistema
     */
    public ConnectionImpl(String url, String usuario, String password) throws ConnectionException {
        // Determina cual será el driver JDBC a utilizar
        if (url==null)
            driver = "url.no.indicada";
        else if (url.toLowerCase().contains("oracle"))
            driver = "oracle.jdbc.driver.OracleDriver";
        else if (url.toLowerCase().contains("mysql"))
            driver = "com.mysql.jdbc.Driver";
        else if (url.toLowerCase().contains("sqlserver"))
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        else
            driver = "url.no.valida.Sin.driver";
        
        // inicializa el driver JDBC
        try {
            Class jdbcClass = Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new ConnectionException(ex, driver);
        }
        
        // Establece la conexión JDBC
        try {
            this.url = url;
            this.usuario = usuario;
            this.password = password;
            connection = DriverManager.getConnection(url, usuario, password);
            if (connection == null) {
                throw new ConnectionException("Error.Estableciendo.Conexion", this);
            }
        } catch (Exception ex) {
            throw new ConnectionException(ex, this);
        }
    }

    /**
     * Comprueba si la conexión a la base de datos está o no cerrada
     * @return true si la conexión está cerrada, false en caso contrario
     */
    public boolean isClosed() {
        if (connection==null) return true;
        try {
            return connection.isClosed();
        } catch (SQLException ex) {
            new ConnectionException(ex, this);
            return true;
        }
    }
    
    /**
     * Comprueba si la conexión con la base de datos es válida
     * @param timeout Ver el mismo parámetro de java.sql.Connection.isValid
     * @return true si la conexión es una conexión válida, false en caso contrario
     */
    public boolean isValid(int timeout) {
        if (connection==null) return false;
        try {
            return connection.isValid(0);
        } catch (SQLException ex) {
            new ConnectionException(ex, this);
            return false;
        }
    }
    
    /**
     * Cuando el objeto es recolectado para su destrucción, se cierra
     * la conexión con la base de datos, si es que existía alguna.
     * @throws Throwable si se produce un error Throwable 
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            connection.close();
        } finally {
            connection = null;
        }
    }
    
    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Connection getConnection() {return connection;}

    public void setConnection(Connection connection) {this.connection = connection;}

    public String getDriver() {return driver;}

    public void setDriver(String driver) {this.driver = driver;}
    
}
