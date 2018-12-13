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
public class MetaDataSQLInfo extends DatabaseMetaDataInfo {
    
    public MetaDataSQLInfo(DatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
    
    public String getIdentifierQuoteString() throws SQLException {
        return databaseMetaData.getIdentifierQuoteString();
    }

    public String getSQLKeywords() throws SQLException {
        return databaseMetaData.getSQLKeywords();
    }

    public String getNumericFunctions() throws SQLException {
        return databaseMetaData.getNumericFunctions();
    }

    public String getStringFunctions() throws SQLException {
        return databaseMetaData.getStringFunctions();
    }

    public String getSystemFunctions() throws SQLException {
        return databaseMetaData.getSystemFunctions();
    }

    public String getTimeDateFunctions() throws SQLException {
        return databaseMetaData.getTimeDateFunctions();
    }

    public String getSearchStringEscape() throws SQLException {
        return databaseMetaData.getSearchStringEscape();
    }

}
