/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.jdbc;

import alu.ubu.es.mydatabasejc.actions.MetaDataInfoCategorias;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

/**
 *
 * @author jhuidobro
 */
public class DatabaseMetaDataInfo {

    protected DatabaseMetaData databaseMetaData;

    public DatabaseMetaDataInfo(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return databaseMetaData.supportsAlterTableWithAddColumn();
    }

    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return databaseMetaData.supportsAlterTableWithDropColumn();
    }

    public boolean supportsColumnAliasing() throws SQLException {
        return databaseMetaData.supportsColumnAliasing();
    }

    public boolean nullPlusNonNullIsNull() throws SQLException {
        return databaseMetaData.nullPlusNonNullIsNull();
    }

    public boolean supportsConvert() throws SQLException {
        return databaseMetaData.supportsConvert();
    }

    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        return databaseMetaData.supportsConvert(fromType, toType);
    }

    public boolean supportsTableCorrelationNames() throws SQLException {
        return databaseMetaData.supportsTableCorrelationNames();
    }

    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return databaseMetaData.supportsDifferentTableCorrelationNames();
    }

    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return databaseMetaData.supportsExpressionsInOrderBy();
    }

    public boolean supportsOrderByUnrelated() throws SQLException {
        return databaseMetaData.supportsOrderByUnrelated();
    }

    public boolean supportsGroupBy() throws SQLException {
        return databaseMetaData.supportsGroupBy();
    }

    public boolean supportsGroupByUnrelated() throws SQLException {
        return databaseMetaData.supportsGroupByUnrelated();
    }

    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return databaseMetaData.supportsGroupByBeyondSelect();
    }

    public boolean supportsLikeEscapeClause() throws SQLException {
        return databaseMetaData.supportsLikeEscapeClause();
    }

    public boolean supportsMultipleResultSets() throws SQLException {
        return databaseMetaData.supportsMultipleResultSets();
    }

    public boolean supportsMultipleTransactions() throws SQLException {
        return databaseMetaData.supportsMultipleTransactions();
    }

    public boolean supportsNonNullableColumns() throws SQLException {
        return databaseMetaData.supportsNonNullableColumns();
    }

    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return databaseMetaData.supportsMinimumSQLGrammar();
    }

    public boolean supportsCoreSQLGrammar() throws SQLException {
        return databaseMetaData.supportsCoreSQLGrammar();
    }

    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return databaseMetaData.supportsExtendedSQLGrammar();
    }

    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return databaseMetaData.supportsANSI92EntryLevelSQL();
    }

    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return databaseMetaData.supportsANSI92IntermediateSQL();
    }

    public boolean supportsANSI92FullSQL() throws SQLException {
        return databaseMetaData.supportsANSI92FullSQL();
    }

    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return databaseMetaData.supportsIntegrityEnhancementFacility();
    }

    public boolean supportsOuterJoins() throws SQLException {
        return databaseMetaData.supportsOuterJoins();
    }

    public boolean supportsFullOuterJoins() throws SQLException {
        return databaseMetaData.supportsFullOuterJoins();
    }

    public boolean supportsLimitedOuterJoins() throws SQLException {
        return databaseMetaData.supportsLimitedOuterJoins();
    }

    public String getSchemaTerm() throws SQLException {
        return databaseMetaData.getSchemaTerm();
    }

    public String getProcedureTerm() throws SQLException {
        return databaseMetaData.getProcedureTerm();
    }

    public String getCatalogTerm() throws SQLException {
        return databaseMetaData.getCatalogTerm();
    }

    public boolean isCatalogAtStart() throws SQLException {
        return databaseMetaData.isCatalogAtStart();
    }

    public String getCatalogSeparator() throws SQLException {
        return databaseMetaData.getCatalogSeparator();
    }

    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return databaseMetaData.supportsSchemasInDataManipulation();
    }

    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return databaseMetaData.supportsSchemasInProcedureCalls();
    }

    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return databaseMetaData.supportsSchemasInTableDefinitions();
    }

    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return databaseMetaData.supportsSchemasInIndexDefinitions();
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return databaseMetaData.supportsSchemasInPrivilegeDefinitions();
    }

    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return databaseMetaData.supportsCatalogsInDataManipulation();
    }

    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return databaseMetaData.supportsCatalogsInProcedureCalls();
    }

    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return databaseMetaData.supportsCatalogsInTableDefinitions();
    }

    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return databaseMetaData.supportsCatalogsInIndexDefinitions();
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return databaseMetaData.supportsCatalogsInPrivilegeDefinitions();
    }

    public boolean supportsPositionedDelete() throws SQLException {
        return databaseMetaData.supportsPositionedDelete();
    }

    public boolean supportsPositionedUpdate() throws SQLException {
        return databaseMetaData.supportsPositionedUpdate();
    }

    public boolean supportsSelectForUpdate() throws SQLException {
        return databaseMetaData.supportsSelectForUpdate();
    }

    public boolean supportsStoredProcedures() throws SQLException {
        return databaseMetaData.supportsStoredProcedures();
    }

    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return databaseMetaData.supportsSubqueriesInComparisons();
    }

    public boolean supportsSubqueriesInExists() throws SQLException {
        return databaseMetaData.supportsSubqueriesInExists();
    }

    public boolean supportsSubqueriesInIns() throws SQLException {
        return databaseMetaData.supportsSubqueriesInIns();
    }

    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return databaseMetaData.supportsSubqueriesInQuantifieds();
    }

    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return databaseMetaData.supportsCorrelatedSubqueries();
    }

    public boolean supportsUnion() throws SQLException {
        return databaseMetaData.supportsUnion();
    }

    public boolean supportsUnionAll() throws SQLException {
        return databaseMetaData.supportsUnionAll();
    }

    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return databaseMetaData.supportsOpenCursorsAcrossCommit();
    }

    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return databaseMetaData.supportsOpenCursorsAcrossRollback();
    }

    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return databaseMetaData.supportsOpenStatementsAcrossCommit();
    }

    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return databaseMetaData.supportsOpenStatementsAcrossRollback();
    }

    public int getMaxBinaryLiteralLength() throws SQLException {
        return databaseMetaData.getMaxBinaryLiteralLength();
    }

    public int getMaxCharLiteralLength() throws SQLException {
        return databaseMetaData.getMaxCharLiteralLength();
    }

    public int getMaxColumnNameLength() throws SQLException {
        return databaseMetaData.getMaxColumnNameLength();
    }

    public int getMaxColumnsInGroupBy() throws SQLException {
        return databaseMetaData.getMaxColumnsInGroupBy();
    }

    public int getMaxColumnsInIndex() throws SQLException {
        return databaseMetaData.getMaxColumnsInIndex();
    }

    public int getMaxColumnsInOrderBy() throws SQLException {
        return databaseMetaData.getMaxColumnsInOrderBy();
    }

    public int getMaxColumnsInSelect() throws SQLException {
        return databaseMetaData.getMaxColumnsInSelect();
    }

    public int getMaxColumnsInTable() throws SQLException {
        return databaseMetaData.getMaxColumnsInTable();
    }

    public int getMaxConnections() throws SQLException {
        return databaseMetaData.getMaxConnections();
    }

    public int getMaxCursorNameLength() throws SQLException {
        return databaseMetaData.getMaxCursorNameLength();
    }

    public int getMaxIndexLength() throws SQLException {
        return databaseMetaData.getMaxIndexLength();
    }

    public int getMaxSchemaNameLength() throws SQLException {
        return databaseMetaData.getMaxSchemaNameLength();
    }

    public int getMaxProcedureNameLength() throws SQLException {
        return databaseMetaData.getMaxProcedureNameLength();
    }

    public int getMaxCatalogNameLength() throws SQLException {
        return databaseMetaData.getMaxCatalogNameLength();
    }

    public int getMaxRowSize() throws SQLException {
        return databaseMetaData.getMaxRowSize();
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return databaseMetaData.doesMaxRowSizeIncludeBlobs();
    }

    public int getMaxStatementLength() throws SQLException {
        return databaseMetaData.getMaxStatementLength();
    }

    public int getMaxStatements() throws SQLException {
        return databaseMetaData.getMaxStatements();
    }

    public int getMaxTableNameLength() throws SQLException {
        return databaseMetaData.getMaxTableNameLength();
    }

    public int getMaxTablesInSelect() throws SQLException {
        return databaseMetaData.getMaxTablesInSelect();
    }

    public int getMaxUserNameLength() throws SQLException {
        return databaseMetaData.getMaxUserNameLength();
    }

    public int getDefaultTransactionIsolation() throws SQLException {
        return databaseMetaData.getDefaultTransactionIsolation();
    }

    public boolean supportsTransactions() throws SQLException {
        return databaseMetaData.supportsTransactions();
    }

    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        return databaseMetaData.supportsTransactionIsolationLevel(level);
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return databaseMetaData.supportsDataDefinitionAndDataManipulationTransactions();
    }

    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return databaseMetaData.supportsDataManipulationTransactionsOnly();
    }

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return databaseMetaData.dataDefinitionCausesTransactionCommit();
    }

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return databaseMetaData.dataDefinitionIgnoredInTransactions();
    }

    public boolean supportsResultSetType(int type) throws SQLException {
        return databaseMetaData.supportsResultSetType(type);
    }

    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        return databaseMetaData.supportsResultSetConcurrency(type, concurrency);
    }

    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return databaseMetaData.ownUpdatesAreVisible(type);
    }

    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return databaseMetaData.ownDeletesAreVisible(type);
    }

    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return databaseMetaData.ownInsertsAreVisible(type);
    }

    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return databaseMetaData.othersUpdatesAreVisible(type);
    }

    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return databaseMetaData.othersDeletesAreVisible(type);
    }

    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return databaseMetaData.othersInsertsAreVisible(type);
    }

    public boolean updatesAreDetected(int type) throws SQLException {
        return databaseMetaData.updatesAreDetected(type);
    }

    public boolean deletesAreDetected(int type) throws SQLException {
        return databaseMetaData.deletesAreDetected(type);
    }

    public boolean insertsAreDetected(int type) throws SQLException {
        return databaseMetaData.insertsAreDetected(type);
    }

    public boolean supportsBatchUpdates() throws SQLException {
        return databaseMetaData.supportsBatchUpdates();
    }

    public boolean supportsSavepoints() throws SQLException {
        return databaseMetaData.supportsSavepoints();
    }

    public boolean supportsNamedParameters() throws SQLException {
        return databaseMetaData.supportsNamedParameters();
    }

    public boolean supportsMultipleOpenResults() throws SQLException {
        return databaseMetaData.supportsMultipleOpenResults();
    }

    public boolean supportsGetGeneratedKeys() throws SQLException {
        return databaseMetaData.supportsGetGeneratedKeys();
    }

    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        return databaseMetaData.supportsResultSetHoldability(holdability);
    }

    public int getResultSetHoldability() throws SQLException {
        return databaseMetaData.getResultSetHoldability();
    }

    public int getDatabaseMajorVersion() throws SQLException {
        return databaseMetaData.getDatabaseMajorVersion();
    }

    public int getDatabaseMinorVersion() throws SQLException {
        return databaseMetaData.getDatabaseMinorVersion();
    }

    public int getJDBCMajorVersion() throws SQLException {
        return databaseMetaData.getJDBCMajorVersion();
    }

    public int getJDBCMinorVersion() throws SQLException {
        return databaseMetaData.getJDBCMinorVersion();
    }

    public int getSQLStateType() throws SQLException {
        return databaseMetaData.getSQLStateType();
    }

    public boolean locatorsUpdateCopy() throws SQLException {
        return databaseMetaData.locatorsUpdateCopy();
    }

    public boolean supportsStatementPooling() throws SQLException {
        return databaseMetaData.supportsStatementPooling();
    }

    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return databaseMetaData.getRowIdLifetime();
    }

    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return databaseMetaData.supportsStoredFunctionsUsingCallSyntax();
    }

    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return databaseMetaData.autoCommitFailureClosesAllResultSets();
    }

    public boolean generatedKeyAlwaysReturned() throws SQLException {
        return databaseMetaData.generatedKeyAlwaysReturned();
    }

}
