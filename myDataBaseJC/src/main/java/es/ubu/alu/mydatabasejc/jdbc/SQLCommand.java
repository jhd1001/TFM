/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.exceptions.SQLCommandException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jhuidobro
 */
public class SQLCommand {

    public static int OPERACION_UPDATE = 1;
    public static int OPERACION_DELETE = 2;
    public static int OPERACION_INSERT = 4;
    public static int OPERACION_WHERE = 8;
    public static int ISAUTOINCREMENT = 1;
    public static int ISCASESENSITIVE = 2;
    public static int ISCURRENCY = 4;
    public static int ISDEFINITELYWRITABLE = 8;
    public static int ISNULLABLE = 16;
    public static int ISREADONLY = 32;
    public static int ISSEARCHABLE = 64;
    public static int ISSIGNED = 128;
    public static int ISWRITABLE = 256;
    public static int ISALL = -1;
    public ConnectionImpl connectionImpl;
    public String cadenaWhere;
    public List<Object> listaParametrosWhere;
    public String cadenaSet;
    public List<Object> listaParametrosSet;
    public String sql;
    public String cadenaInsert;
    public String cadenaInsert2;
    public List<Object> listaParametrosInsert;

    public static Object getValor(int tipo, String valor) {
        switch (tipo) {
            case Types.ARRAY:
                String[] valores = valor.split(",", 0);
                List<String> lvalores = new ArrayList();
                for (int k = 0; k < valores.length; k++) {
                    if (!"".equals(valores[k])) {
                        lvalores.add(valores[k].trim());
                    }
                }
                return lvalores.toArray(valores);
            // para tipos Integer
            case Types.BIGINT:
            case Types.INTEGER:
            case Types.ROWID:
                return Integer.valueOf(valor);
            // para tipos Short
            case Types.SMALLINT:
                return Short.valueOf(valor);
            // para tipos boolean
            case Types.BIT:
            case Types.BOOLEAN:
                return Boolean.valueOf(valor);
            // para tipos fecha
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
            case Types.TIME_WITH_TIMEZONE:
                return Date.valueOf(valor);
            // para tipos float
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.NUMERIC:
            case Types.REAL:
                return Float.valueOf(valor);
            // para tipos string
            case Types.CHAR:
            case Types.DATALINK:
            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.SQLXML:
            case Types.VARCHAR:
                return valor;
            // en otro caso, valor por defecto
            default:
                return valor;
        }
    }

    public int executeUpdate(String esquema, String tabla, Map<String, Integer[]> mapa, String[] campos, String[] valores) throws SQLCommandException {
        int retorno = 0;
        setSQLPartList(mapa, campos, valores, OPERACION_INSERT);
        sql = String.format("INSERT INTO %s%s (%s) VALUES (%s)", getEsquema(esquema), tabla, cadenaInsert, cadenaInsert2);
        PreparedStatement ps = null;
        try {
            ps = connectionImpl.getConnection().prepareStatement(sql);
            // se asignan los parámetros
            int parameterIndex = 1;
            // se insertan los valores para los campos
            for (Object o : listaParametrosInsert) {
                ps.setObject(parameterIndex++, o);
            }
            retorno = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLCommandException(this, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {;
            }
        }
        return retorno;
    }

    private ResultSet executeQuery(String TABLE_SCHEM, String TABLE_NAME, int TYPE_SCROLL, int CONCUR) throws SQLCommandException {
        // Se obtiene la cadena de consulta definitiva
        String sql = String.format("SELECT * FROM %s%s %s", getEsquema(TABLE_SCHEM), TABLE_NAME, cadenaWhere);
        PreparedStatement ps = null;
        try {
            // se obtiene el PreparedStatemtne
            ps = connectionImpl.getConnection().prepareStatement(sql, TYPE_SCROLL, CONCUR);
            // se asignan los parámetros
            int parameterIndex = 1;
            for (Object o : listaParametrosWhere) {
                ps.setObject(parameterIndex++, o);
            }
            ResultSet rs = null;
            // y se resuelve el resultset
            return ps.executeQuery();
        } catch (SQLException ex) {
            throw new SQLCommandException(this, ex);
        }
    }

    public ResultSet executeQuery(String TABLE_SCHEM, String TABLE_NAME, Set<String> arrayParametros, Map<String, Object> sesion) throws SQLCommandException {
        setSQLPartList(TABLE_SCHEM, TABLE_NAME, arrayParametros, sesion);
        return executeQuery(TABLE_SCHEM, TABLE_NAME, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    public void setSQLPartList(String TABLE_SCHEM, String TABLE_NAME, Set<String> arrayParametros, Map<String, Object> sesion) {
        inicializa();
        // para cada columna del resultset
        for (String columna : arrayParametros) {
            // se busca en la sesión del usuario
            Object o = sesion.get(getEsquema(TABLE_SCHEM) + TABLE_NAME + "." + columna);
            // si el parámetro existe y no es "", se añade la condición a la cadena where
            // y el valor a la lista de parametros
            if (o != null && !"".equals(o.toString())) {
                // si no es el primer elemento se antepone AND
                if (listaParametrosWhere.size() != 0) {
                    cadenaWhere = cadenaWhere + "AND ";
                } // si es el primer filtro, se antepone where
                else {
                    cadenaWhere = "where ";
                }
                // y a continuación se añade el nombre del campo y la posición del parámetro.
                // esto evitará ataques por SQL inyection
                cadenaWhere = cadenaWhere + columna + " = ? ";
                listaParametrosWhere.add(o);
            }
        }
    }
/*
    private boolean tipoSeleccionable(ResultSet rs, int columna, int tipoCampo) throws SQLException {
        if (rs.getMetaData().isAutoIncrement(columna) && ((tipoCampo & ISAUTOINCREMENT) != 0)) {
            return true;
        }
        if (rs.getMetaData().isCaseSensitive(columna) && ((tipoCampo & ISCASESENSITIVE) != 0)) {
            return true;
        }
        if (rs.getMetaData().isCurrency(columna) && ((tipoCampo & ISCURRENCY) != 0)) {
            return true;
        }
        if (rs.getMetaData().isDefinitelyWritable(columna) && ((tipoCampo & ISDEFINITELYWRITABLE) != 0)) {
            return true;
        }
        if (rs.getMetaData().isReadOnly(columna) && ((tipoCampo & ISREADONLY) != 0)) {
            return true;
        }
        if (rs.getMetaData().isSearchable(columna) && ((tipoCampo & ISSEARCHABLE) != 0)) {
            return true;
        }
        if (rs.getMetaData().isSigned(columna) && ((tipoCampo & ISSIGNED) != 0)) {
            return true;
        }
        if (rs.getMetaData().isWritable(columna) && ((tipoCampo & ISWRITABLE) != 0)) {
            return true;
        }
        return false;
    }
*/
    public Map<String, Integer> getMap(Map<String, Integer[]> mapa, int tipo) {
        Map<String, Integer> retorno = new HashMap<String, Integer>();
        for (String columna : mapa.keySet()) {
            if ((mapa.get(columna)[1] & tipo) != 0)
                retorno.put(columna, mapa.get(columna)[0]);
        }
        return retorno;
    }
    
    public Map<String, Integer[]> getMap(String TABLE_SCHEM, String TABLE_NAME) {
        // se crea un mapa vacío
        Map<String, Integer[]> mapa = new HashMap<String, Integer[]>();
        // y completa el hashmap a partir de un resultset vacío de la tabla
        // obtiene el resultset vacío para llegar al metadata
        ResultSet rs = null;
        try {
            rs = connectionImpl.getConnection().createStatement().executeQuery(String.format("SELECT * FROM %s%s WHERE 1=2", getEsquema(TABLE_SCHEM), TABLE_NAME));
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                // cada elemento del mapa tendrá el nombre, el tipo de las columnas buscables y las características del campo
                int característica = 0;
                if (rs.getMetaData().isAutoIncrement(i)) característica += ISAUTOINCREMENT;
                if (rs.getMetaData().isCaseSensitive(i)) característica += ISCASESENSITIVE;
                if (rs.getMetaData().isCurrency(i)) característica += ISCURRENCY;
                if (rs.getMetaData().isDefinitelyWritable(i)) característica += ISDEFINITELYWRITABLE;
                if (rs.getMetaData().isReadOnly(i)) característica += ISREADONLY;
                if (rs.getMetaData().isSearchable(i)) característica += ISSEARCHABLE;
                if (rs.getMetaData().isSigned(i)) característica += ISSIGNED;
                if (rs.getMetaData().isWritable(i)) característica += ISWRITABLE;
                Integer[] datos = {rs.getMetaData().getColumnType(i),característica};
                mapa.put(rs.getMetaData().getColumnName(i), datos);
            }
        } catch (SQLException ex) {
            throw new SQLCommandException(TABLE_SCHEM, TABLE_NAME, ex.getLocalizedMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {;
                }
            }
            return mapa;
        }
        
    }

    public ResultSet executeQuery(String TABLE_SCHEM, String TABLE_NAME, Map<String, Integer[]> mapa, String[] pkArgumentos, String[] pkValores, int TYPE_SCROLL, int CONCUR) throws SQLCommandException {
        setSQLPartList(mapa, pkArgumentos, pkValores, OPERACION_WHERE);
        return executeQuery(TABLE_SCHEM, TABLE_NAME, TYPE_SCROLL, CONCUR);
    }

    public SQLCommand(ConnectionImpl connectionImpl) {
        this.connectionImpl = connectionImpl;
        this.cadenaSet = null;
        this.cadenaWhere = null;
        this.listaParametrosSet = new ArrayList();
        this.listaParametrosWhere = new ArrayList();
        this.cadenaInsert = null;
        this.cadenaInsert2 = null;
        this.listaParametrosInsert = new ArrayList();
    }

    private void inicializa() {
        listaParametrosSet.clear();
        listaParametrosWhere.clear();
        listaParametrosInsert.clear();
        cadenaSet = "";
        cadenaWhere = "";
        cadenaInsert = "";
        cadenaInsert2 = "";
        sql = "";
    }

    private void setSQLPartList(Map<String, Integer[]> mapa, String[] pkArgumentos, String[] pkValores, int tipo) {
        inicializa();
        int n = 0;
        if (tipo == OPERACION_UPDATE) {
            for (String columna : pkArgumentos) {
                Object o = getValor(mapa.get(columna)[0], pkValores[n++]);
                //Object o = pkValores[n++];
                if (listaParametrosSet.size() != 0) {
                    cadenaSet = cadenaSet + ", ";
                }
                cadenaSet = cadenaSet + columna + " = ? ";
                listaParametrosSet.add(o);
            }
        } else if (tipo == OPERACION_WHERE) {
            for (String columna : pkArgumentos) {
                Object o = getValor(mapa.get(columna)[0], pkValores[n++]); //pkValores[n++];
                if (listaParametrosWhere.size() != 0) {
                    cadenaWhere = cadenaWhere + "AND  ";
                } else {
                    cadenaWhere = "where ";
                }
                cadenaWhere = cadenaWhere + columna + " = ? ";
                listaParametrosWhere.add(o);
            }
        } else if (tipo == OPERACION_INSERT) {
            for (String columna : pkArgumentos) {
                Object o = getValor(mapa.get(columna)[0], pkValores[n++]); //pkValores[n++];
                if (o == null) {
                    continue;
                }
                if ("".equals(String.valueOf(o))) {
                    continue;
                }
                if (listaParametrosInsert.size() != 0) {
                    cadenaInsert = cadenaInsert + ", ";
                    cadenaInsert2 = cadenaInsert2 + ", ";
                }
                cadenaInsert = cadenaInsert + columna;
                cadenaInsert2 = cadenaInsert2 + "?";
                listaParametrosInsert.add(o);
            }
        } else {
            ;// errror
        }
    }

    /**
     * Siempre será update
     *
     * @param pkArgumentos
     * @param pkValores
     * @param campos
     * @param valores
     */
    private void setSQLPartList(Map<String, Integer[]> mapa, String[] pkArgumentos, String[] pkValores, String[] campos, String[] valores) {
        inicializa();
        int n = 0;
        for (String columna : campos) {
            Object o = getValor(mapa.get(columna)[0], valores[n++]); //valores[n++];
            if (listaParametrosSet.size() != 0) {
                cadenaSet = cadenaSet + ", ";
            }
            cadenaSet = cadenaSet + columna + " = ? ";
            listaParametrosSet.add(o);
        }
        n = 0;
        for (String columna : pkArgumentos) {
            Object o = getValor(mapa.get(columna)[0], valores[n++]); //pkValores[n++];
            if (listaParametrosWhere.size() != 0) {
                cadenaWhere = cadenaWhere + "AND  ";
            } else {
                cadenaWhere = "where ";
            }
            cadenaWhere = cadenaWhere + columna + " = ? ";
            listaParametrosWhere.add(o);
        }
    }

    private String getEsquema(String esquema) {
        if (esquema == null) {
            return "";
        }
        if ("".equals(esquema)) {
            return "";
        }
        if (!esquema.endsWith(".")) {
            return esquema + ".";
        }
        return esquema;
    }

    public int executeUpdate(String esquema, String tabla, Map<String, Integer[]> mapa, int operacion, String[] pkArgumentos, String[] pkValores, String[] campos, String[] valores) throws SQLCommandException { //throws Exception {
        int retorno = 0;
        if (operacion == OPERACION_UPDATE) {
            setSQLPartList(mapa, pkArgumentos, pkValores, campos, valores);
            sql = String.format("UPDATE %s%s SET %s %s",
                    getEsquema(esquema),
                    tabla,
                    cadenaSet,
                    cadenaWhere);
        } else if (operacion == OPERACION_DELETE) {
            setSQLPartList(mapa, pkArgumentos, pkValores, OPERACION_WHERE);
            sql = String.format("DELETE %s%s %s",
                    getEsquema(esquema),
                    tabla,
                    cadenaWhere);
        }
        if ("".equals(sql)) {
            throw new SQLCommandException(this, operacion);
        }
        PreparedStatement ps = null;
        try {
            ps = connectionImpl.getConnection().prepareStatement(sql);
            // se asignan los parámetros
            int parameterIndex = 1;
            // en primer lugar la parte update, no habrá parámetros si no hay
            // parte update
            for (Object o : listaParametrosSet) {
                ps.setObject(parameterIndex++, o);
            }
            // y luego la parte where
            for (Object o : listaParametrosWhere) {
                ps.setObject(parameterIndex++, o);
            }
            retorno = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLCommandException(this, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {;
            }
        }
        return retorno;
    }
}
