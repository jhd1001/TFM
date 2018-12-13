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
public class MetaDataBasicInfo extends DatabaseMetaDataInfo {
    
    public MetaDataBasicInfo(DatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
    
    public String getDatabaseProductName() throws SQLException {
        return databaseMetaData.getDatabaseProductName();
    }

    public String getDatabaseProductVersion() throws SQLException {
        return databaseMetaData.getDatabaseProductVersion();
    }

}
