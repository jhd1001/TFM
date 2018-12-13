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
public class MetaDataUserInfo extends DatabaseMetaDataInfo {
    
    public boolean allProceduresAreCallable() throws SQLException {
        return databaseMetaData.allProceduresAreCallable();
    }

    public boolean allTablesAreSelectable() throws SQLException {
        return databaseMetaData.allTablesAreSelectable();
    }

    public MetaDataUserInfo(DatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
    
}
