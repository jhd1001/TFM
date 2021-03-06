package es.ubu.alu.mydatabasejc.jdbc;

import es.ubu.alu.mydatabasejc.exceptions.ConversionException;
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
 * Clase que permite al usuario ejecutar comandos sql contra la base de datos
 *
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
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

    /**
     * Dado un tipo de datos Java, devuelve el objeto valor convertido
     * a dicho tipo
     * 
     * @param tipo Tipo de dato JDBC
     * @param valor Valor asignable al campo
     * @return Devuelve el valor adaptado al tipo indicado
     */
    public static Object getValor(int tipo, String valor) {
        valor = valor==null ? null : ("".equals(valor) ? null : valor);
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
                return valor == null ? (Integer) null : Integer.valueOf(valor);
            // para tipos Short
            case Types.SMALLINT:
                return valor == null ? (Short) null : Short.valueOf(valor);
            // para tipos boolean
            case Types.BIT:
            case Types.BOOLEAN:
                return valor == null ? (Boolean) null : Boolean.valueOf(valor);
            // para tipos fecha
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
            case Types.TIME_WITH_TIMEZONE:
                return valor == null ? (Date) null : Date.valueOf(valor);
            // para tipos float
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.NUMERIC:
            case Types.REAL:
                return valor == null ? (Float) null : Float.valueOf(valor);
            // para tipos string
            case Types.CHAR:
            case Types.DATALINK:
            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.SQLXML:
            case Types.VARCHAR:
                return valor == null ? (String) null : valor;
            // en otro caso, valor por defecto
            default:
                return valor;
        }
    }

    /**
     * Ejecuta una operación de inserción SQL 
     * 
     * @param esquema Esquema de la base de datos
     * @param tabla Tabla
     * @param mapa Proporciona los tipos Java de los datos a insertar
     * @param campos Nombre de los campos a insertar
     * @param valores Valores en formato String de los campos a insertar
     * @return Número de registros insertados
     * @throws SQLCommandException si error SQLException 
     */
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

    /**
     * Ejecuta el comando SELECT * de SQL correspondiente
     * 
     * @param TABLE_SCHEM Esquema
     * @param TABLE_NAME Tabla
     * @param TYPE_SCROLL Tipo de scroll
     * @param CONCUR Tipo de acceso concurrente
     * @return Resultset con el resultado del comando
     * @throws SQLCommandException si Error SQLException
     */
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

    /**
     * Ejecuta el comando SELECT * de tipo FORWARD_ONLY y CONCUR_READ_ONLY
     * de SQL 
     * @param TABLE_SCHEM esquema
     * @param TABLE_NAME tabla
     * @param arrayParametros conjunto de columnas que intervienen en la cláusula where
     * @param sesion Mapa donde se encuentran los valores correspondientes a
     * cada uno de los elementos del array de parametros indicado
     * @return Resultset resón del comando
     * @throws SQLCommandException si error SQL
     */
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

    /**
     * Extrae del mapa un nuevo mapa con el nombre de la clave y el primer
     * elemento del array de enteros proporcionado (corresponden a los tipos
     * Java de los campos indicados)
     * 
     * @param mapa Mapa con nombres de campos y tipos de datos Java
     * @param tipo Solo extrae cuando <pre>tipo &#60;&#62; 0</pre>
     * @return Mapa de campos, con tipos de datos y tipos de los campos
     */
    public Map<String, Integer> getMap(Map<String, Integer[]> mapa, int tipo) {
        Map<String, Integer> retorno = new HashMap<String, Integer>();
        for (String columna : mapa.keySet()) {
            if ((mapa.get(columna)[1] & tipo) != 0)
                retorno.put(columna, mapa.get(columna)[0]);
        }
        return retorno;
    }

    /**
     * Obtiene el mapa de campos y tipos de datos JDBC (Java) del resultset
     * obtenido tras un SELECT * FROM de la tabla recibida.
     * 
     * @param TABLE_SCHEM esquema
     * @param TABLE_NAME tabla
     * @return La clave es el nombre del campo, el primer elemento del 
     * array de enteros es el tipo JDBC del campo y el segundo elemento del 
     * array indica el conjunto de características de dicho campo.
     */
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

    /**
     * Ejecuta la consulta de sql sobre la tabla indicada
     * 
     * @param TABLE_SCHEM esquema
     * @param TABLE_NAME tabla
     * @param mapa Conjunto de campos incluidos en la cláusula select
     * @param pkArgumentos Conjunto de campos de la PK
     * @param pkValores Valores de los campos de la PK a consultar
     * @param TYPE_SCROLL tipo de scroll
     * @param CONCUR tipo de acceso concurrente
     * @return Resultset resultado de la ejecución del comando
     * @throws SQLCommandException si error SQL
     */
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

    private void setSQLPartList(Map<String, Integer[]> mapa, String[] pkArgumentos, String[] pkValores, int tipo) throws SQLCommandException {
        inicializa();
        int n = 0;
        if (tipo == OPERACION_UPDATE) {
            for (String columna : pkArgumentos) {
                try {
                    Object o = getValor(mapa.get(columna)[0], pkValores[n]);
                    n++;
                    //Object o = pkValores[n++];
                    if (listaParametrosSet.size() != 0) {
                        cadenaSet = cadenaSet + ", ";
                    }
                    cadenaSet = cadenaSet + columna + " = ? ";
                    listaParametrosSet.add(o);
                } catch (Exception e) {
                    ConversionException ce = new ConversionException(pkArgumentos[n], e);
                    throw new SQLCommandException(ce);
                }
            }
        } else if (tipo == OPERACION_WHERE) {
            for (String columna : pkArgumentos) {
                try {
                    Object o = getValor(mapa.get(columna)[0], pkValores[n]);
                    n++;
                    if (listaParametrosWhere.size() != 0) {
                        cadenaWhere = cadenaWhere + "AND  ";
                    } else {
                        cadenaWhere = "where ";
                    }
                    cadenaWhere = cadenaWhere + columna + " = ? ";
                    listaParametrosWhere.add(o);
                } catch (Exception e) {
                    ConversionException ce = new ConversionException(pkArgumentos[n], e);
                    throw new SQLCommandException(ce);
                }
            }
        } else if (tipo == OPERACION_INSERT) {
            for (String columna : pkArgumentos) {
                try {
                    Object o = getValor(mapa.get(columna)[0], pkValores[n]);
                    n++;
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
                } catch (Exception e) {
                    ConversionException ce = new ConversionException(pkArgumentos[n], e);
                    throw new SQLCommandException(ce);
                }
            }
        } else {
            ;// errror
        }
    }

    /**
     * Siempre será update
     *
     * @param pkArgumentos Lsta de campos del PK
     * @param pkValores Lista de valores del PK
     * @param campos Lista de campos
     * @param valores Lista de valores
     */
    private void setSQLPartList(Map<String, Integer[]> mapa, String[] pkArgumentos, String[] pkValores, String[] campos, String[] valores) throws SQLCommandException {
        inicializa();
        int n = 0;
        for (String columna : campos) {
            try {
                Object o = getValor(mapa.get(columna)[0], valores[n]);
                n++;
                if (listaParametrosSet.size() != 0) {
                    cadenaSet = cadenaSet + ", ";
                }
                cadenaSet = cadenaSet + columna + " = ? ";
                listaParametrosSet.add(o);
            } catch (Exception e) {
                ConversionException ce = new ConversionException(campos[n], e);
                throw new SQLCommandException(ce);
            }
        }
        n = 0;
        for (String columna : pkArgumentos) {
            try {
                Object o = getValor(mapa.get(columna)[0], pkValores[n]);
                n++;
                if (listaParametrosWhere.size() != 0) {
                    cadenaWhere = cadenaWhere + "AND  ";
                } else {
                    cadenaWhere = "where ";
                }
                cadenaWhere = cadenaWhere + columna + " = ? ";
                listaParametrosWhere.add(o);
            } catch (Exception e) {
                ConversionException ce = new ConversionException(pkArgumentos[n], e);
                throw new SQLCommandException(ce);
            }
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

    /**
     * Ejecuta una instrucción UPDATE o DELETE SQL con las condiciones
     * establecidas por los parámetros
     * 
     * @param esquema Esquema
     * @param tabla Tabla
     * @param mapa Conjunto de campos que se actualizarán en la operación
     * UPDATE junto con sus tipos de datos
     * @param operacion Tipo de operación: UPDATE o DELETE
     * @param pkArgumentos Campos de la PK
     * @param pkValores Valores de los campos de la PK
     * @param campos Conjunto de campos que se actualizarán en la operación
     * UPDATE
     * @param valores Valores asignados al conjunto de campos que se actualizarán
     * en la operación UPDATE
     * @return Número de registros actualizados en la operación.
     * @throws SQLCommandException si error SQL
     */
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
            sql = String.format("DELETE FROM %s%s %s",
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
