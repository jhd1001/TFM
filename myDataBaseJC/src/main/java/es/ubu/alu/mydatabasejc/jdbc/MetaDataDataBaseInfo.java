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
public class MetaDataDataBaseInfo extends DatabaseMetaDataInfo {
    
    public MetaDataDataBaseInfo(DatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
    
    public boolean usesLocalFiles() throws SQLException {
        return databaseMetaData.usesLocalFiles();
    }

    public boolean usesLocalFilePerTable() throws SQLException {
        return databaseMetaData.usesLocalFilePerTable();
    }

    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return databaseMetaData.supportsMixedCaseIdentifiers();
    }

    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return databaseMetaData.storesUpperCaseIdentifiers();
    }

    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return databaseMetaData.storesLowerCaseIdentifiers();
    }

    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return databaseMetaData.storesMixedCaseIdentifiers();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return databaseMetaData.supportsMixedCaseQuotedIdentifiers();
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return databaseMetaData.storesUpperCaseQuotedIdentifiers();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return databaseMetaData.storesLowerCaseQuotedIdentifiers();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return databaseMetaData.storesMixedCaseQuotedIdentifiers();
    }

    public String getExtraNameCharacters() throws SQLException {
        return databaseMetaData.getExtraNameCharacters();
    }

}
