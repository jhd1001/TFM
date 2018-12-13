/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 *
 * @author jhuidobro
 */
public class MetaDataConnInfo extends DatabaseMetaDataInfo {
    
    public MetaDataConnInfo(DatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
    
    public String getUserName() throws SQLException {
        return databaseMetaData.getUserName();
    }

    public boolean isReadOnly() throws SQLException {
        return databaseMetaData.isReadOnly();
    }

    public String getDriverVersion() throws SQLException {
        return databaseMetaData.getDriverVersion();
    }

    public int getDriverMajorVersion() {
        return databaseMetaData.getDriverMajorVersion();
    }

    public int getDriverMinorVersion() {
        return databaseMetaData.getDriverMinorVersion();
    }

}
