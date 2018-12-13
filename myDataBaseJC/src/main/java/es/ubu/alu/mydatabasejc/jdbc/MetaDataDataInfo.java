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
public class MetaDataDataInfo extends DatabaseMetaDataInfo {
    
    public MetaDataDataInfo(DatabaseMetaData databaseMetaData) {
        super(databaseMetaData);
    }
    
    public boolean nullsAreSortedHigh() throws SQLException {
        return databaseMetaData.nullsAreSortedHigh();
    }

    public boolean nullsAreSortedLow() throws SQLException {
        return databaseMetaData.nullsAreSortedLow();
    }

    public boolean nullsAreSortedAtStart() throws SQLException {
        return databaseMetaData.nullsAreSortedAtStart();
    }

    public boolean nullsAreSortedAtEnd() throws SQLException {
        return databaseMetaData.nullsAreSortedAtEnd();
    }

}
