/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alu.ubu.es.mydatabasejc;

import alu.ubu.es.mydatabasejc.actions.MetaDataInfoCategorias;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

/**
 *
 * @author jhuidobro
 */
public class MetaDataInfoImpl extends MetaData {

    public MetaDataInfoImpl() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public MetaDataInfoImpl(DatabaseMetaData metadata) {
        super(metadata);
    }

    public MetaDataInfoImpl(Connection connection) throws SQLException {
        super(connection);
    }
    
    @Override
    @MetaDataInfoCategorias(categoria = "Usuario")
    public boolean allProceduresAreCallable() throws SQLException {
        return metadata.allProceduresAreCallable();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Usuario")
    public boolean allTablesAreSelectable() throws SQLException {
        return metadata.allTablesAreSelectable();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public String getURL() throws SQLException {
        return metadata.getURL();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public String getUserName() throws SQLException {
        return metadata.getUserName();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public boolean isReadOnly() throws SQLException {
        return metadata.isReadOnly();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Datos")
    public boolean nullsAreSortedHigh() throws SQLException {
        return metadata.nullsAreSortedHigh();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Datos")
    public boolean nullsAreSortedLow() throws SQLException {
        return metadata.nullsAreSortedLow();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Datos")
    public boolean nullsAreSortedAtStart() throws SQLException {
        return metadata.nullsAreSortedAtStart();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Datos")
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return metadata.nullsAreSortedAtEnd();
    }

    @Override
    @MetaDataInfoCategorias
    public String getDatabaseProductName() throws SQLException {
        return metadata.getDatabaseProductName();
    }

    @Override
    @MetaDataInfoCategorias
    public String getDatabaseProductVersion() throws SQLException {
        return metadata.getDatabaseProductVersion();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public String getDriverName() throws SQLException {
        return metadata.getDriverName();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public String getDriverVersion() throws SQLException {
        return metadata.getDriverVersion();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public int getDriverMajorVersion() {
        return metadata.getDriverMajorVersion();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Conexión")
    public int getDriverMinorVersion() {
        return metadata.getDriverMinorVersion();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean usesLocalFiles() throws SQLException {
        return metadata.usesLocalFiles();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean usesLocalFilePerTable() throws SQLException {
        return metadata.usesLocalFilePerTable();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return metadata.supportsMixedCaseIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return metadata.storesUpperCaseIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return metadata.storesLowerCaseIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return metadata.storesMixedCaseIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return metadata.supportsMixedCaseQuotedIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return metadata.storesUpperCaseQuotedIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return metadata.storesLowerCaseQuotedIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return metadata.storesMixedCaseQuotedIdentifiers();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getIdentifierQuoteString() throws SQLException {
        return metadata.getIdentifierQuoteString();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getSQLKeywords() throws SQLException {
        return metadata.getSQLKeywords();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getNumericFunctions() throws SQLException {
        return metadata.getNumericFunctions();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getStringFunctions() throws SQLException {
        return metadata.getStringFunctions();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getSystemFunctions() throws SQLException {
        return metadata.getSystemFunctions();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getTimeDateFunctions() throws SQLException {
        return metadata.getTimeDateFunctions();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "SQL")
    public String getSearchStringEscape() throws SQLException {
        return metadata.getSearchStringEscape();
    }

    @Override
    @MetaDataInfoCategorias(categoria = "Base de datos")
    public String getExtraNameCharacters() throws SQLException {
        return metadata.getExtraNameCharacters();
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return metadata.supportsAlterTableWithAddColumn();
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return metadata.supportsAlterTableWithDropColumn();
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        return metadata.supportsColumnAliasing();
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return metadata.nullPlusNonNullIsNull();
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        return metadata.supportsConvert();
    }

    @Override
    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        return metadata.supportsConvert(fromType, toType);
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        return metadata.supportsTableCorrelationNames();
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return metadata.supportsDifferentTableCorrelationNames();
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return metadata.supportsExpressionsInOrderBy();
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        return metadata.supportsOrderByUnrelated();
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        return metadata.supportsGroupBy();
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        return metadata.supportsGroupByUnrelated();
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return metadata.supportsGroupByBeyondSelect();
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        return metadata.supportsLikeEscapeClause();
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        return metadata.supportsMultipleResultSets();
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        return metadata.supportsMultipleTransactions();
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        return metadata.supportsNonNullableColumns();
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return metadata.supportsMinimumSQLGrammar();
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return metadata.supportsCoreSQLGrammar();
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return metadata.supportsExtendedSQLGrammar();
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return metadata.supportsANSI92EntryLevelSQL();
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return metadata.supportsANSI92IntermediateSQL();
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        return metadata.supportsANSI92FullSQL();
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return metadata.supportsIntegrityEnhancementFacility();
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        return metadata.supportsOuterJoins();
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        return metadata.supportsFullOuterJoins();
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return metadata.supportsLimitedOuterJoins();
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        return metadata.getSchemaTerm();
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        return metadata.getProcedureTerm();
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        return metadata.getCatalogTerm();
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        return metadata.isCatalogAtStart();
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        return metadata.getCatalogSeparator();
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return metadata.supportsSchemasInDataManipulation();
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return metadata.supportsSchemasInProcedureCalls();
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return metadata.supportsSchemasInTableDefinitions();
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return metadata.supportsSchemasInIndexDefinitions();
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return metadata.supportsSchemasInPrivilegeDefinitions();
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return metadata.supportsCatalogsInDataManipulation();
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return metadata.supportsCatalogsInProcedureCalls();
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return metadata.supportsCatalogsInTableDefinitions();
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return metadata.supportsCatalogsInIndexDefinitions();
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return metadata.supportsCatalogsInPrivilegeDefinitions();
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        return metadata.supportsPositionedDelete();
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        return metadata.supportsPositionedUpdate();
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        return metadata.supportsSelectForUpdate();
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        return metadata.supportsStoredProcedures();
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return metadata.supportsSubqueriesInComparisons();
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        return metadata.supportsSubqueriesInExists();
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        return metadata.supportsSubqueriesInIns();
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return metadata.supportsSubqueriesInQuantifieds();
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return metadata.supportsCorrelatedSubqueries();
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        return metadata.supportsUnion();
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        return metadata.supportsUnionAll();
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return metadata.supportsOpenCursorsAcrossCommit();
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return metadata.supportsOpenCursorsAcrossRollback();
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return metadata.supportsOpenStatementsAcrossCommit();
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return metadata.supportsOpenStatementsAcrossRollback();
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        return metadata.getMaxBinaryLiteralLength();
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        return metadata.getMaxCharLiteralLength();
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        return metadata.getMaxColumnNameLength();
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        return metadata.getMaxColumnsInGroupBy();
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        return metadata.getMaxColumnsInIndex();
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        return metadata.getMaxColumnsInOrderBy();
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        return metadata.getMaxColumnsInSelect();
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        return metadata.getMaxColumnsInTable();
    }

    @Override
    public int getMaxConnections() throws SQLException {
        return metadata.getMaxConnections();
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        return metadata.getMaxCursorNameLength();
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        return metadata.getMaxIndexLength();
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        return metadata.getMaxSchemaNameLength();
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        return metadata.getMaxProcedureNameLength();
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        return metadata.getMaxCatalogNameLength();
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        return metadata.getMaxRowSize();
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return metadata.doesMaxRowSizeIncludeBlobs();
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        return metadata.getMaxStatementLength();
    }

    @Override
    public int getMaxStatements() throws SQLException {
        return metadata.getMaxStatements();
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        return metadata.getMaxTableNameLength();
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        return metadata.getMaxTablesInSelect();
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        return metadata.getMaxUserNameLength();
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        return metadata.getDefaultTransactionIsolation();
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        return metadata.supportsTransactions();
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        return metadata.supportsTransactionIsolationLevel(level);
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return metadata.supportsDataDefinitionAndDataManipulationTransactions();
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return metadata.supportsDataManipulationTransactionsOnly();
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return metadata.dataDefinitionCausesTransactionCommit();
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return metadata.dataDefinitionIgnoredInTransactions();
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        return metadata.supportsResultSetType(type);
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        return metadata.supportsResultSetConcurrency(type, concurrency);
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return metadata.ownUpdatesAreVisible(type);
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return metadata.ownDeletesAreVisible(type);
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return metadata.ownInsertsAreVisible(type);
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return metadata.othersUpdatesAreVisible(type);
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return metadata.othersDeletesAreVisible(type);
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return metadata.othersInsertsAreVisible(type);
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        return metadata.updatesAreDetected(type);
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        return metadata.deletesAreDetected(type);
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        return metadata.insertsAreDetected(type);
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        return metadata.supportsBatchUpdates();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return metadata.getConnection();
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        return metadata.supportsSavepoints();
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        return metadata.supportsNamedParameters();
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        return metadata.supportsMultipleOpenResults();
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return metadata.supportsGetGeneratedKeys();
    }

    @Override
    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        return metadata.supportsResultSetHoldability(holdability);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return metadata.getResultSetHoldability();
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        return metadata.getDatabaseMajorVersion();
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        return metadata.getDatabaseMinorVersion();
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        return metadata.getJDBCMajorVersion();
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        return metadata.getJDBCMinorVersion();
    }

    @Override
    public int getSQLStateType() throws SQLException {
        return metadata.getSQLStateType();
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        return metadata.locatorsUpdateCopy();
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        return metadata.supportsStatementPooling();
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return metadata.getRowIdLifetime();
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return metadata.supportsStoredFunctionsUsingCallSyntax();
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return metadata.autoCommitFailureClosesAllResultSets();
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        return metadata.generatedKeyAlwaysReturned();
    }

}
